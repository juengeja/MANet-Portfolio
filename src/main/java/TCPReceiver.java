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
                Message msg = (Message) in.readObject();
                    // Verarbeiten der Nachricht
                    switch (msg.getType()) {
                        case HELLO:
                            // TODO
                            ownNode.prcssHelloMsg(msg);
                            //System.out.println("Receiver [" + this.port + "] received a " + msg.getType() + "-Message.");
                            break;
                        case TC:
                            // TODO
                            ownNode.prcssTCMsg(msg);
                            //System.out.println("Receiver [" + this.port + "] received a " + msg.getType() + "-Message.");
                            break;
                        case CONTENT:
                        // TODO
                        if(msg.getDestinationPort() == (ownNode.getNodeID() + 7700)){
                            ContentMsg typeData = (ContentMsg) msg.getTypeData();
                            System.out.println("Forward received. Sender: " + msg.getSenderID());
                            System.out.println("Message received. Content:  " + typeData.getContent());
                        }
                        else 
                        {ownNode.forwardContentMsg(msg);
                            System.out.println("Forward received. Sender: " + msg.getSenderID());}
                        break;
                        default:
                            // TODO
                            System.out.println("Receiver [" + this.port + "] received something strange.");
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
  

