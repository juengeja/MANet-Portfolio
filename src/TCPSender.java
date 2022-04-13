public class TCPSender implements Runnable{
    
    private int nodeID;
    private int port;

    public TCPSender(int nodeID){
        this.nodeID = nodeID;
        this.port = 7700 + nodeID;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("Sender of Node [" + nodeID + "] is running.");
    }
}
