import java.io.*;
import java.net.*;

public class TCPReceiver implements Runnable{
    
    private final Node ownNode;
    private final int port;

    public TCPReceiver(Node ownNode){
        this.ownNode = ownNode;
        this.port = 7700 + ownNode.getNodeID();
    }

    @Override
    public void run() {
        System.out.println("Receiver of Node [" + ownNode.getNodeID() + "] is running.");

        Socket connection = null;
        ObjectInputStream in = null;

        try(ServerSocket server = new ServerSocket(this.port)){

            while(true){
                // Warten auf Nachrichten
                connection = server.accept();
                // Eingaberoutine
                in = new ObjectInputStream(connection.getInputStream());
                //Nachricht lesen
                Message message = (Message) in.readObject();

                if(ownNode.getRange() >= Math.sqrt(Math.pow((ownNode.getX()-message.getSourceX()),2) + Math.pow((ownNode.getY()-message.getSourceY()), 2))){

                    // Verarbeiten der Nachricht
                    switch (message.getType()) {
                        case HELLO:
                            // TODO
                            System.out.println("Receiver [" + this.port + "] received a " + message.getType() + "-Message.");
                            break;
                        case TC:
                            // TODO
                            System.out.println("Receiver [" + this.port + "] received a " + message.getType() + "-Message.");
                            break;
                        default:
                            // TODO
                            System.out.println("Receiver [" + this.port + "] received something strange.");
                    }
                } else {
                    System.out.println("Receiver der Node " + ownNode.getNodeID() + " ist außer Reichweite für diese Nachricht!");
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        } finally {
            // Verbindungen schließen
            try {
                if (connection != null && in != null) {
                    connection.close();
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
