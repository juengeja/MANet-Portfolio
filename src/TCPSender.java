import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TCPSender implements Runnable{
    
    private Node ownNode;
    private int ownPort;
    private ConcurrentLinkedQueue<Message> messageQueue;
    private String hostname = "localhost";
    private PrintWriter networkOut;

    public TCPSender(Node ownNode){
        this.ownNode = ownNode;
        this.ownPort = 7700 + ownNode.getNodeID();
        messageQueue = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println("Sender of Node [" + ownNode.getNodeID() + "] is running.");

        while(true){
            // Checking for a Message to send
            Message nextMessage = messageQueue.poll();
            if(nextMessage != null){
                // Creating socket as a ressource
                try(Socket socket = new Socket(hostname, nextMessage.getDestinationPort())){
        
                    ObjectOutputStream networkOut = new ObjectOutputStream(socket.getOutputStream());
                    // Send message
                    networkOut.writeObject(nextMessage);
                    networkOut.flush();
                    System.out.println("Sender [" + this.ownPort + "] send a message to " + nextMessage.getDestinationPort());

                } catch(IOException e){
                    e.printStackTrace();
                } finally {
                    // Closing networkOut connection, socket is closed automatically
                    if(networkOut != null ){
                        networkOut.close();
                    }
                }
            }
        }

    }

    public void sendMessage(Message message){
        messageQueue.add(message);
    }
}
