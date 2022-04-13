import java.io.IOException;
import java.net.ServerSocket;

public class TCPReceiver implements Runnable{
    
    private int nodeID;
    private int port;

    public TCPReceiver(int nodeID){
        this.nodeID = nodeID;
        this.port = 7700 + nodeID;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("Sender of Node [" + nodeID + "] is running.");
        while(true){
            try(ServerSocket server = new ServerSocket(this.port)){

            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
