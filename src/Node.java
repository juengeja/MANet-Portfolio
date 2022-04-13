

public class Node implements Runnable{

    private int nodeID;
    private int x;
    private int y;
    private int totalNodes;
    private TCPReceiver receiver;
    private TCPSender sender;

    public Node(int nodeID, int totalNumberOfNodes){
        this.nodeID = nodeID;
        this.totalNodes = totalNumberOfNodes;
        this.receiver = new TCPReceiver(nodeID);
        this.sender = new TCPSender(nodeID);
    }

    public void init(){
        x = Math.round((float)Math.random()*100);
        y = Math.round((float)Math.random()*100);
    }

    @Override
    public void run(){
        System.out.println("Node [" + this.nodeID + "] is running.");
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}