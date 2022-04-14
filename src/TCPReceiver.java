import java.io.*;
import java.net.*;

public class TCPReceiver implements Runnable{
    
    private int nodeID;
    private int port;

    public TCPReceiver(int nodeID){
        this.nodeID = nodeID;
        this.port = 7700 + nodeID;
    }

    @Override
    public void run() {
        System.out.println("Receiver of Node [" + nodeID + "] is running.");

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
