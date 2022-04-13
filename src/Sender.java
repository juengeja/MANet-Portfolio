public class Sender implements Runnable{
    
    private int nodeID;
    private int nodeSenderID;

    public Sender(int nodeID, int nodeSenderID){
        this.nodeID = nodeID;
        this.nodeSenderID = nodeSenderID;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("Sender [" + this.nodeSenderID + "] of Node [" + nodeID + "] is running.");
    }
}
