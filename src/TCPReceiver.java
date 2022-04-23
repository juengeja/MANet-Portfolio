import java.io.*;
import java.net.*;

public class TCPReceiver implements Runnable{
    
    private Node ownNode;
    private int port;

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
                HelloMsg helloMsg = (HelloMsg) in.readObject();

                if(ownNode.getRange() >= Math.sqrt(Math.pow((ownNode.getX()-helloMsg.getSourceX()),2) + Math.pow((ownNode.getY()-helloMsg.getSourceY()), 2))){

                    // Verarbeiten der Nachricht
                    switch (helloMsg.getType()) {
                        case HELLO:
                            // TODO
                            ownNode.prcssHelloMsg(helloMsg);
                            System.out.println("Receiver [" + this.port + "] received a " + helloMsg.getType() + "-Message.");
                            break;
                        case TC:
                            // TODO
                            System.out.println("Receiver [" + this.port + "] received a " + helloMsg.getType() + "-Message.");
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
