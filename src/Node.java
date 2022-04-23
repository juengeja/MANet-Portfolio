import java.util.HashMap;
import java.util.HashSet;

public class Node extends Thread{

    private int nodeID;
    private int x;
    private int y;
    private int totalNodes;
    private final TCPReceiver receiver;
    private Thread receiverThread;
    private final TCPSender sender;
    private Thread senderThread;
    private static int range = 5;
    private HashSet<Integer> oneHopNbrs = new HashSet<>();
    private HashMap<Integer, HashSet<Integer>> nodeTwoHopNbrs = new HashMap<>();
    private HashSet<Integer> mprSelectors = new HashSet<>();
    private long mprSelectorsSeq = 0L;
    private volatile boolean mprSelectorSent = true;
    private HashSet<Integer> mprs = new HashSet<>();
  

    public Node(int nodeID, int totalNumberOfNodes){
        this.nodeID = nodeID;
        this.totalNodes = totalNumberOfNodes;
        this.receiver = new TCPReceiver(this);
        this.sender = new TCPSender(this);
    }
    public Node(int nodeID, int x, int y){
        this.nodeID = nodeID;
        this.x = x;
        this.y = y;
        this.receiver = new TCPReceiver(this);
        this.sender = new TCPSender(this);
    }

    public void init(){
       // x = Math.round((float)Math.random()*100);
       // y = Math.round((float)Math.random()*100);
        System.out.println("Initialisierung mit Koordinaten: " + x + ", " + y);
        this.receiverThread = new Thread(receiver);
        receiverThread.start();
        this.senderThread = new Thread(sender);
        senderThread.start();
    }

    @Override
    public void run(){
        System.out.println("Node [" + this.nodeID + "] is running.");
        Node[] nodes = new Node[6];
        for (int i = 1; i < 6; i++) {
          if(i != nodeID){
        sendHelloMsg(i);
        System.out.println("Node [" + this.nodeID + "] sent Hello to [" + i + "]");
        }}
        
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getRange(){
        return range;
    }
    public int getNodeID(){
        return nodeID;
    }


    


    private void sendHelloMsg(int destinationID) {
        HashSet<Integer> twoHopNbrs = new HashSet<>();
        for (HashSet<Integer> potentialTHNbr : this.nodeTwoHopNbrs.values()) {
          for (Integer id : potentialTHNbr) {
            if (id == this.nodeID || this.oneHopNbrs.contains(id))
              continue;
            twoHopNbrs.add(id);
          }
        }
        selectMPR(this.nodeTwoHopNbrs, twoHopNbrs);
        int destinationPort = 7700 + destinationID;        
        this.sender.sendMessage(new HelloMsg(1, Message.Type.HELLO, destinationPort, this.x, this.y, this.oneHopNbrs, this.mprs, this.nodeID));
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
          mprs = MPR;
          mprSelectorsSeq++;
        }
      }
      
      



    public void prcssHelloMsg(HelloMsg helloMsg) {
        this.oneHopNbrs.add(helloMsg.getSenderID());
       
        
        for(int oneHopNbr : helloMsg.getOneHopNbrs()){
          if(!this.oneHopNbrs.contains(oneHopNbr)){
              this.nodeTwoHopNbrs.put(helloMsg.getSenderID(), helloMsg.getOneHopNbrs());
          }
      }  
                  
        if (helloMsg.getMprs().contains(nodeID)) {
          if (!mprSelectors.contains(helloMsg.getSenderID())) {
            mprSelectors.add(helloMsg.getSenderID());
            mprSelectorsSeq++;
            mprSelectorSent = false;
          }
        } else {
          if (mprSelectors.contains(helloMsg.getSenderID())) {
            mprSelectors.remove(helloMsg.getSenderID());
            mprSelectorsSeq++;
            mprSelectorSent = false;
          }
        }
        System.out.println("Node [" + this.nodeID + "] : One Hop [" + this.oneHopNbrs + "] Two Hop [" + this.nodeTwoHopNbrs + "]");
      }
}