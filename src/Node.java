public class Node extends Thread{

    private int nodeID;
    private int x;
    private int y;
    private int totalNodes;
    private TCPReceiver receiver;
    private Thread receiverThread;
    private TCPSender sender;
    private Thread senderThread;
    private static int range = 100;

    public Node(int nodeID, int totalNumberOfNodes){
        this.nodeID = nodeID;
        this.totalNodes = totalNumberOfNodes;
        this.receiver = new TCPReceiver(this);
        this.sender = new TCPSender(this);
    }

    public void init(){
        x = Math.round((float)Math.random()*1000);
        y = Math.round((float)Math.random()*1000);
        System.out.println("Initialisierung mit Koordinaten: " + x + ", " + y);
        this.receiverThread = new Thread(receiver);
        receiverThread.start();
        this.senderThread = new Thread(sender);
        senderThread.start();
    }

    @Override
    public void run(){
        System.out.println("Node [" + this.nodeID + "] is running.");
        if(this.nodeID == 0){
            int destinationPort = 7700 + totalNodes - 1;
            this.sender.sendMessage(new Message(1, Message.Type.HELLO, destinationPort, this.x, this.y));
        }
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
}
