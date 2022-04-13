public class Receiver implements Runnable{
    
    private int nodeID;
    private int nodeReceiverID;

    public Receiver(int nodeID, int nodeReceiverID){
        this.nodeID = nodeID;
        this.nodeReceiverID = nodeReceiverID;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("Sender [" + this.nodeReceiverID + "] of Node [" + nodeID + "] is running.");
    }
}
