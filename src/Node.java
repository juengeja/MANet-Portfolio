import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Node implements Runnable{

    private int nodeID;
    private int x;
    private int y;
    private final int DEFAULT_PORT = 5777;
    final static int MAX_PACKET_SIZE = 65507;

    // main Methode evtl noch auslagern in eigene Controller-Klasse?
    public static void main(String[] args) {
        Node node = new Node(1);
        node.init();
        node.run();
    }

    public Node(int nodeID){
        this.nodeID = nodeID;
    }

    private void init(){
        x = Math.round((float)Math.random()*100);
        y = Math.round((float)Math.random()*100);

    }

    @Override
    public void run(){
        System.out.println("Node [" + this.nodeID + "] is online.");
        while(true){
            // Hier wird ein UDP-Socket als Ressource erstellt, wird nach try-Block automatisch wieder geschlossen
            try(DatagramSocket socket = new DatagramSocket(DEFAULT_PORT)){

                // Erstellen eines Datagram Packets, warten auf eingehende Nachrichten
                DatagramPacket data = new DatagramPacket(new byte[MAX_PACKET_SIZE], MAX_PACKET_SIZE);
                socket.receive(data);

            } catch(IOException e){
                e.printStackTrace();
            }
            return;
        }
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}