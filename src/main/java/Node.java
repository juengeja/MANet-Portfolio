import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
public class Node extends Thread {

    private final int nodeID;
    private int x;
    private int y;
    private final TCPReceiver receiver;
    private Thread receiverThread;
    private final TCPSender sender;
    private Thread senderThread;
    
    private static final int RANGE = Controller.RANGE;
    public static final int HEIGHT = Controller.HEIGHT;  //1m = 10px
    public static final int WIDTH = Controller.WIDTH;
    public static final int STEPSIZE = Controller.STEPSIZE; //Bei Bewegung: wie weit gehen die Nodes?

    private HashSet<Integer> oneHopNbrs = new HashSet<>();
    private HashMap<Integer, HashSet<Integer>> nodeTwoHopNbrs = new HashMap<>();
    private HashSet<Integer> mprSelectors = new HashSet<>();
    private long mprSelectorsSeq = 0L;
    private volatile boolean mprSelectorSent = true;
    private HashSet<Integer> mprs = new HashSet<>();
    private HashMap<Integer, Pair<HashSet<Integer>, Long>> TopologyTable = new HashMap<>();
    private HashMap<Integer, Pair<Integer, Integer>> routingTable = new HashMap<>();

    public Node(int nodeID, int x, int y){
        this.nodeID = nodeID;
        this.x = x;
        this.y = y;
        this.receiver = new TCPReceiver(this);
        this.sender = new TCPSender(this);
    }

    public void init(HashSet<Node> nodes){
       // x = Math.round((float)Math.random()*100);
       // y = Math.round((float)Math.random()*100);
        System.out.println("Initialisierung mit Koordinaten: " + x + ", " + y);
        for(Node node : nodes){
        if(this.nodeID != node.getNodeID() && this.getRange() >= Math.sqrt(Math.pow((this.getX()-node.getX()),2) + Math.pow((this.getY()-node.getY()), 2)))
        {
          this.oneHopNbrs.add(node.getNodeID());
        };}
        this.receiverThread = new Thread(receiver);
        receiverThread.start();
        this.senderThread = new Thread(sender);
        senderThread.start();
    }

    @Override
    public void run(){
        System.out.println("Node [" + this.nodeID + "] is running.");
       
        Timer TCTimer = new Timer("TC Timer");
        TCTimer.scheduleAtFixedRate(new TCTask(), TCTask.delay, TCTask.period);

        Timer HelloTimer = new Timer("Hello Timer");
        HelloTimer.scheduleAtFixedRate(new HelloTask(), HelloTask.delay, HelloTask.period);
        }


    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getNodeID(){
        return nodeID;
    }


    public void sendHelloMsg() {
        HashSet<Integer> twoHopNbrs = new HashSet<>();
        for (HashSet<Integer> potentialTHNbr : this.nodeTwoHopNbrs.values()) {
          for (Integer id : potentialTHNbr) {
            if (id == this.nodeID || this.oneHopNbrs.contains(id))
              continue;
            twoHopNbrs.add(id);
          }
        }
        selectMPR(this.nodeTwoHopNbrs, twoHopNbrs);
        HelloMsg typeData = new HelloMsg(
        this.x,
        this.y,
        this.oneHopNbrs,
        this.mprs
         );
        bcastHelloMsg(new Message(Message.Type.HELLO, this.nodeID, typeData));   
      }



      private void selectMPR(HashMap<Integer, HashSet<Integer>> neighbors, HashSet<Integer> twohops) {
        HashSet<Integer> MPR = new HashSet<>();
        while (!twohops.isEmpty()) {
          Integer maxID = -1;
          int maxIntersectionSize = 0;
          for (HashMap.Entry<Integer, HashSet<Integer>> neighbor : neighbors.entrySet()) {
            if (MPR.contains(neighbor.getKey()))
              continue;
            HashSet<Integer> intersection = new HashSet<>(neighbor.getValue());
            intersection.retainAll(twohops);
            if (intersection.size() > maxIntersectionSize) {
              maxID = neighbor.getKey();
              maxIntersectionSize = intersection.size();
            }
          }
          MPR.add(maxID);
          twohops.removeAll(neighbors.get(maxID));
        }
        if (!mprs.equals(MPR)) {
          this.mprs = MPR;
          this.mprSelectorsSeq++;
        }
      }
      
      



    public void prcssHelloMsg(Message msg) {
        HelloMsg typeData = (HelloMsg) msg.getTypeData();
        this.oneHopNbrs.add(msg.getSenderID());
        HashSet<Integer> potTwoHops = new HashSet<>();
        
        for(int oneHopNbr : typeData.getOneHopNbrs()){
          if(oneHopNbr != this.nodeID && !this.oneHopNbrs.contains(oneHopNbr)){
              potTwoHops.add(oneHopNbr);
          }else {
              this.nodeTwoHopNbrs.remove(msg.getSenderID(), oneHopNbr);
          }
          this.nodeTwoHopNbrs.put(msg.getSenderID(), potTwoHops);
      }
                  
        if (typeData.getMprs().contains(nodeID)) {
          if (!this.mprSelectors.contains(msg.getSenderID())) {
            this.mprSelectors.add(msg.getSenderID());
            this.mprSelectorsSeq++;
            this.mprSelectorSent = false;
          }
        } else {
          if (this.mprSelectors.contains(msg.getSenderID())) {
            this.mprSelectors.remove(msg.getSenderID());
            this.mprSelectorsSeq++;
            this.mprSelectorSent = false;
          }
        }
        //System.out.println("Node [" + this.nodeID + "] : One Hop [" + this.oneHopNbrs + "] Two Hop [" + this.nodeTwoHopNbrs + "]");
        //System.out.println("Node [" + this.nodeID + "] : MPR [" + this.mprs + "] MPRSelectors [" + this.mprSelectors + "]");
      }


     

      public void sendTCMsg() {
        if (mprSelectorSent)
          return;
        TCMsg typeData = new TCMsg(
          this.mprSelectors,
          this.mprSelectorsSeq
        );

        bcastTCMsg(new Message(Message.Type.TC, nodeID, typeData));
        mprSelectorSent = false;
      }



      public void prcssTCMsg(Message message) {
        TCMsg typeData = (TCMsg) message.getTypeData();
        Long currentSeq = 0L;
        if (TopologyTable.containsKey(message.getSenderID()))
          currentSeq = TopologyTable.get(message.getSenderID()).getRight();
    
        if (currentSeq >= typeData.getMprSelectorsSeq())
          return;
        TopologyTable.put(message.getSenderID(), new ImmutablePair<>(typeData.getMprSelectors(), typeData.getMprSelectorsSeq()));
        
        forwardMsg(message);
        calcRoutingTable();
        System.out.println("Node [" + this.nodeID + "] : MPR [" + this.mprs + "] MPRSelectors [" + this.mprSelectors + "] routingTable [" + this.routingTable + "]");
      }


      public void bcastHelloMsg(Message message) {
        for (int nbr : this.oneHopNbrs){
          message.setDestinationPort(7700 + nbr);
          this.sender.sendMessage(message);
         // System.out.println("Broadcast \t" + message);
        }
      }

      public void bcastTCMsg(Message message) {
        for (int nbr : this.oneHopNbrs){
          message.setDestinationPort(7700 + nbr);
          this.sender.sendMessage(message);
         // System.out.println("Broadcast \t" + message);
        }
      }


      private void forwardMsg(Message message) {
        if (this.mprSelectors.contains(message.getSenderID())) {
          int mprSelectorID = message.getSenderID();
          message.setSenderID(this.nodeID);

          sender.sendMessage(message);
          for (int nbr : this.oneHopNbrs){
          if (nbr != mprSelectorID){
          message.setDestinationPort(8800 + nbr);
          sender.sendMessage(message);
          System.out.println("Forward \t" + message);
        }}}
      }

      private HashMap<Integer, HashSet<Integer>> getTopologyTableSnapshot() {
        HashMap<Integer, HashSet<Integer>> topology = new HashMap<>();
        topology.put(this.nodeID, this.oneHopNbrs);
        for (Map.Entry<Integer, Pair<HashSet<Integer>, Long>> topoEntry : this.TopologyTable.entrySet()) {
          topology.put(topoEntry.getKey(), topoEntry.getValue().getLeft());
        }
        return topology;
      }

      private void calcRoutingTable() {
        HashMap<Integer, HashSet<Integer>> TopoSnapshot = getTopologyTableSnapshot();
        HashMap<Integer, Pair<Integer, Integer>> RoutingTable = new HashMap<>();
        HashMap<Integer, Pair<Integer, Integer>> NHopNeighbors = new HashMap<>();
        HashMap<Integer, Pair<Integer, Integer>> NplusOneHopNeighbors;
        NHopNeighbors.put(this.nodeID, new ImmutablePair<>(this.nodeID, 0));
        while (!NHopNeighbors.isEmpty()) {
          RoutingTable.putAll(NHopNeighbors);
          NplusOneHopNeighbors = new HashMap<>();
          for (HashMap.Entry<Integer, Pair<Integer, Integer>> NHopNeighbor : NHopNeighbors.entrySet()) {
            Integer NHopID = NHopNeighbor.getKey();
            if (!TopoSnapshot.containsKey(NHopID))
              continue;
            Pair<Integer, Integer> NHopNextHop = NHopNeighbor.getValue();
            for (Integer NplusOneHopNeighbor : TopoSnapshot.get(NHopID)) {
              if (RoutingTable.containsKey(NplusOneHopNeighbor))
                continue;
              NplusOneHopNeighbors.put(
                NplusOneHopNeighbor,
                new ImmutablePair<>(
                  NHopNextHop.getLeft() == this.nodeID ? NplusOneHopNeighbor : NHopNextHop.getLeft(),
                  NHopNextHop.getRight() + 1
                )
              );
            }
          }
          NHopNeighbors = NplusOneHopNeighbors;
        }
        this.routingTable = RoutingTable;
      }

      public class TCTask extends TimerTask {

        final static long delay = 1000L;
        final static long period = 100L;

        @Override
        public void run() {
          sendTCMsg();
        }
      }

      private class HelloTask extends TimerTask {

        final static long delay = 1000L;
        final static long period = 100L;
    
        @Override
        public void run() {
          sendHelloMsg();
        }
      }
      
      

      public boolean getMprSelectorSent(){
        return this.mprSelectorSent;
      }

      public void setMprSelectorSent(boolean mprSelectorSent){
        this.mprSelectorSent = mprSelectorSent;
      }

      public HashSet<Integer> getMprSelectors() {
        return this.mprSelectors;
      }

      public Long getMprSelectorsSeq() {
        return this.mprSelectorsSeq;
      }

      private int getNextHop(int receiverID) {
        return this.routingTable.get(receiverID).getLeft();
      }

      public void forwardContentMsg(Message msg) {
        msg.setSenderID(this.nodeID);
        ContentMsg typeData = (ContentMsg) msg.getTypeData();
        msg.setDestinationPort(7700 + getNextHop(typeData.getReceiverID()));
        this.sender.sendMessage(msg);}














      //Stationen zufällig bewegen lassen
    public Node bewegung() {
      /*
      Wahrscheinlichkeiten:
      ----------------------------
      Stehen bleiben: 50%         /
      Links: 7%                   /
      Rechts: 7%                  /
      Oben: 7%                    /
      Unten: 7%                   /
      Links-Unten: 5.5%           /
      Links-Oben: 5.5%            /
      Rechts-Unten: 5.5%          /
      Rechts-Oben: 5.5%           /
                                  /
      Total: 100%                 /
      ----------------------------
       */


      double zufallszahl = Math.random(); //Diese Zufallszahl soll zufällig entscheiden, wohin sich die Stationen bewegen

      if (zufallszahl >= 0 && zufallszahl < 0.5) {
          //Zu 50% bewegen sich die Stationen nicht
      }
      if (zufallszahl >= 0.5 && zufallszahl < 0.57) {
          //Zu 7% bewegen sich die Stationen nach links. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
          if (this.getX() > STEPSIZE) {
              this.setX(this.getX() - STEPSIZE);
          }
      }
      if (zufallszahl >= 0.57 && zufallszahl < 0.64) {
          //Zu 7% bewegen sich die Stationen nach rechts. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
          if (this.getX() + STEPSIZE < Controller.WIDTH) {
              this.setX(this.getX() + STEPSIZE);
          }
      }

      if (zufallszahl >= 0.64 && zufallszahl < 0.71) {
          //Zu 7% bewegen sich die Stationen nach oben. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
          if (this.getY() + STEPSIZE < Controller.HEIGHT) {
              this.setY(this.getY() + STEPSIZE);
          }
      }

      if (zufallszahl >= 0.71 && zufallszahl < 0.78) {
          //Zu 7% bewegen sich die Stationen nach unten. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
          if (this.getY() > STEPSIZE) {
              this.setY(this.getY() - STEPSIZE);
          }
      }

      if (zufallszahl >= 0.78 && zufallszahl < 0.835) {
          //Zu 5.5% bewegen sich die Stationen nach links oben. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
          if (this.getX() > STEPSIZE) {
              this.setX(this.getX() - STEPSIZE);
          } else {
              this.setX(this.getX() + STEPSIZE);
          }
          if (this.getY() > STEPSIZE) {
              this.setY(this.getY() - STEPSIZE);
          } else {
              this.setY(this.getY() + STEPSIZE);
          }
      }

      if (zufallszahl >= 0.835 && zufallszahl < 0.89) {
          //Zu 5.5% bewegen sich die Stationen nach links unten. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
          if (this.getX() > STEPSIZE) {
              this.setX(this.getX() - STEPSIZE);
          } else {
              this.setX(this.getX() + STEPSIZE);
          }
          if (this.getY() < Controller.HEIGHT - STEPSIZE) {
              this.setY(this.getY() + STEPSIZE);
          } else {
              this.setY(this.getY() - STEPSIZE);
          }
      }

      if (zufallszahl >= 0.89 && zufallszahl < 0.945) {
          //Zu 5.5% bewegen sich die Stationen nach rechts oben. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
          if (this.getX() < Controller.WIDTH - STEPSIZE) {
              this.setX(this.getX() + STEPSIZE);
          } else {
              this.setX(this.getX() - STEPSIZE);
          }
          if (this.getY() > STEPSIZE) {
              this.setY(this.getY() - STEPSIZE);
          } else {
              this.setY(this.getY() + STEPSIZE);
          }
      }

      if (zufallszahl >= 0.945 && zufallszahl < 1) {
          //Zu 5.5% bewegen sich die Stationen nach rechts unten. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
          if (this.getX() < Controller.WIDTH - STEPSIZE) {
              this.setX(this.getX() + STEPSIZE);
          } else {
              this.setX(this.getX() - STEPSIZE);
          }
          if (this.getY() < Controller.HEIGHT - STEPSIZE) {
              this.setY(this.getY() + STEPSIZE);
          } else {
              this.setY(this.getY() - STEPSIZE);
          }
      }
      return this;
  }

  public void setX(int x) {
    this.x = x;
}

public void setY(int y) {
    this.y = y;
}

public int getRange(){
    return RANGE;
}
      }




