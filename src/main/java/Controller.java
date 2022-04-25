import java.util.HashSet;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class Controller {
    public static final int TOTAL_NUMBER_OF_NODES = 10;
    public static final int HEIGHT = 1000; //1m = 10px
    public static final int WIDTH = 1000;
    public static final int RANGE = 100;
    public static final int STEPSIZE = 5; //Bei Bewegung: wie weit gehen die Nodes?
    private static final boolean SHOULD_THE_NODES_MOVE = false;
    public static HashSet<Node> nodes = new HashSet<Node>();


    public static void main(String[] args) {

           
            Node node1 = new Node(1, 500, 500);
            Node node2 = new Node(2, 400, 500);
            Node node3 = new Node(3, 300, 500);
            Node node4 = new Node(4, 480, 420);
            Node node5 = new Node(5, 520, 420);
            Node node6 = new Node(6, 600, 500);


           
            nodes.add(node1);
            nodes.add(node2);
            nodes.add(node3);
            nodes.add(node4);
            nodes.add(node5);
            nodes.add(node6);

            node1.init(nodes);
            node2.init(nodes);
            node3.init(nodes);
            node4.init(nodes);
            node5.init(nodes);
            node6.init(nodes);

            
            node1.start();
            node2.start();
            node3.start();
            node4.start();
            node5.start();
            node6.start();
            
            SwingUtilities.invokeLater(Controller::new);
            
            Scanner sc = new Scanner(System.in);

            String content = sc.nextLine();
            int receiver = sc.nextInt();
        
            ContentMsg typeData = new ContentMsg(
            content, receiver
        );
            node2.forwardContentMsg(new Message(Message.Type.CONTENT,typeData));
        }


        public Controller() {
            JFrame frame = new JFrame("Mobiles Ad-Hoc Netz") {
    
                public void paint(Graphics g) {
                    int j = 20;
                    int diameter = (RANGE * 2);
                    while (j > 2) {
                        Graphics2D g2d = (Graphics2D) g;
                        for (Node node : nodes) {
                            if(SHOULD_THE_NODES_MOVE) {
                                //Mit Bewegen
                                Node newNode = node.bewegung();
                                
                                g2d.drawOval(node.getX() - (RANGE), node.getY() - (RANGE), diameter, diameter);
                                node = newNode;
                            } else {
                                g2d.drawOval(node.getX() - (RANGE), node.getY() - (RANGE), diameter, diameter);
                            }
                        }
                        for (Node node1 : nodes) {
                            for (Node node2 : nodes) {
                                if (reachable(node1, node2)) {
                                    g2d.setColor(Color.RED);
                                    g2d.drawLine(node1.getX(), node1.getY(), node2.getX(), node2.getY());
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

