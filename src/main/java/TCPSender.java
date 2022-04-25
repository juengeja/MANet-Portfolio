import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class TCPSender implements Runnable{
    
    private final Node ownNode;
    private final int ownPort;
    private final ConcurrentLinkedQueue<Message> messageQueue;
    private final String hostname = "localhost";
    private PrintWriter networkOut;
    private Semaphore[] semaphores;

    public TCPSender(Node ownNode, Semaphore[] semaphores){
        this.ownNode = ownNode;
        this.ownPort = 7700 + ownNode.getNodeID();
        messageQueue = new ConcurrentLinkedQueue<>();
        this.semaphores = semaphores;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        //System.out.println("Sender of Node [" + ownNode.getNodeID() + "] is running.");

        while(true){
            // Checking for a Message to send
            Message nextMessage = messageQueue.poll();
            if(nextMessage != null){
                try {
                    semaphores[nextMessage.getDestinationPort() - 7701].acquire();
                
                    // Creating socket as a ressource
                    try(Socket socket = new Socket(hostname, nextMessage.getDestinationPort())){
            
                        ObjectOutputStream networkOut = new ObjectOutputStream(socket.getOutputStream());
                        // Send message
                        networkOut.writeObject(nextMessage);
                        networkOut.flush();
                        //System.out.println("Sender [" + this.ownPort + "] send a message to " + nextMessage.getDestinationPort());
                        
                    } catch(IOException e){
                        e.printStackTrace();
                    } finally {
                        // Closing networkOut connection, socket is closed automatically
                        if(networkOut != null ){
                            networkOut.close();
                        }
                    }
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }

    
    public void sendMessage(Message message){
        messageQueue.add(message);
    }
}
