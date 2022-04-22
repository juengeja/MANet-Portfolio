import javax.swing.*;
import java.awt.*;

public class Controller {

    public static final int TOTAL_NUMBER_OF_NODES = 10;
    public static final Node[] nodes = new Node[TOTAL_NUMBER_OF_NODES];

    public static final int hoehe = 1000; //1m = 10px
    public static final int breite = 1000;
    public static final int sendeReichweite = 100;
    public static Station[] stationen = new Station[Controller.TOTAL_NUMBER_OF_NODES];
    public static final int schrittgroesse = 10;


    public static void main(String[] args) {
        for (int i = 0; i < nodes.length; i++) {
            Node node = new Node(i, TOTAL_NUMBER_OF_NODES);
            nodes[i] = node;
            node.init();
        }
        for (Node node : nodes) {
            node.start();
        }
        SwingUtilities.invokeLater(Controller::new);
    }

    public Controller() {
        JFrame frame = new JFrame("Mobiles Ad-Hoc Netz") {
            public void paint(Graphics g) {
                for (int i = 0; i < Controller.TOTAL_NUMBER_OF_NODES; i++) {
                    //int x = (int)(Math.random() * hoehe);
                    //int y = (int)(Math.random() * breite);
                    int x = Controller.nodes[i].getX();
                    int y = Controller.nodes[i].getY();
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.drawOval(x, y, sendeReichweite, sendeReichweite);
                    stationen[i] = Station.punktErstellen(x, y, sendeReichweite);
                }
                int j = 20;
                while (j > 2) {
                    Graphics2D g2d = (Graphics2D) g;
                    for (int z = 0; z < stationen.length; z++) {
                        Station neuerStationStandort = stationen[z].bewegung();
                        g2d.drawOval(stationen[z].getX() - (sendeReichweite / 2), stationen[z].getY() - (sendeReichweite / 2), sendeReichweite, sendeReichweite);
                        stationen[z] = neuerStationStandort;
                    }
                    for (int x = 0; x < nodes.length; x++) {
                        for (int y = 0; y < nodes.length; y++) {
                            if (erreichbarkeit(stationen[x], stationen[y])) {
                                g2d.setColor(Color.RED);
                                g2d.drawLine(stationen[x].getX(), stationen[x].getY(), stationen[y].getX(), stationen[y].getY());
                            }
                        }
                    }
                    g2d.setColor(Color.BLACK);
                    try {

                        Thread.sleep(130);
                        this.getGraphics().clearRect(0, 0, 1000, 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    j++;
                }
            }
        };
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(breite, hoehe);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Controller(Node[] netNodes){
        JFrame frame = new JFrame("Mobiles Ad-Hoc Netz") {
            public void paint(Graphics g) {
                for (Node node : netNodes) {
                    
                    int x = node.getX();
                    int y = node.getY();
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.drawOval(x, y, sendeReichweite, sendeReichweite);
                }
                Graphics2D g2d = (Graphics2D) g;
                for (int x = 0; x < netNodes.length; x++) {
                    for (int y = 0; y < netNodes.length; y++) {
                        if (erreichbarkeit(netNodes[x], netNodes[y])) {
                            g2d.setColor(Color.RED);
                            g2d.drawLine(netNodes[x].getX(), netNodes[x].getY(), netNodes[y].getX(), netNodes[y].getY());
                        }
                    }
                }
                g2d.setColor(Color.BLACK);
            }
            
        };
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(breite, hoehe);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static boolean erreichbarkeit(Station a, Station b) {
        return sendeReichweite >= Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
    }

    public static boolean erreichbarkeit(Node a, Node b) {
        return sendeReichweite >= Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
    }
}
