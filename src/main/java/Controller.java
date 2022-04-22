import javax.swing.*;
import java.awt.*;

public class Controller {

    public static final int TOTAL_NUMBER_OF_NODES = 10;
    public static Node[] nodes = new Node[TOTAL_NUMBER_OF_NODES];

    public static final int HEIGHT = 1000; //1m = 10px
    public static final int WIDTH = 1000;
    public static final int RANGE = 100;
    public static final int STEPSIZE = 10; //Bei Bewegung: wie weit gehen die Nodes?

    private static final boolean SHOULD_THE_NODES_MOVE = true;


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
                int j = 20;
                while (j > 2) {
                    Graphics2D g2d = (Graphics2D) g;
                    for (int z = 0; z < nodes.length; z++) {
                        if(SHOULD_THE_NODES_MOVE) {
                            //Mit Bewegen
                            Node newNode = nodes[z].bewegung();
                            g2d.drawOval(nodes[z].getX() - (RANGE / 2), nodes[z].getY() - (RANGE / 2), RANGE, RANGE);
                            nodes[z] = newNode;
                        } else {
                            g2d.drawOval(nodes[z].getX() - (RANGE / 2), nodes[z].getY() - (RANGE / 2), RANGE, RANGE);
                        }
                    }
                    for (int x = 0; x < nodes.length; x++) {
                        for (int y = 0; y < nodes.length; y++) {
                            if (reachable(nodes[x], nodes[y])) {
                                g2d.setColor(Color.RED);
                                g2d.drawLine(nodes[x].getX(), nodes[x].getY(), nodes[y].getX(), nodes[y].getY());
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
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static boolean reachable(Node a, Node b) {
        return RANGE >= Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
    }
}
