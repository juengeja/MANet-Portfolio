import javax.swing.*;
import java.awt.*;

public class TestCases {
    private static final int KNOTENZAHL = 50;
    private MockNode[] mockNodes;
    
    public static void main(String[] args) {
        TestCases testcase = new TestCases();
        testcase.initNet();
    }
    
    public void initNet(){
        this.mockNodes = new MockNode[KNOTENZAHL];
        for (int i = 0; i < mockNodes.length; i++) {
            mockNodes[i] = new MockNode();
            mockNodes[i].init();
        }
        
        new MockController(mockNodes);
    }
    
    private class MockNode {
        private int x;
        private int y;

        public void init(){
            x = Math.round((float)Math.random()*Controller.HEIGHT);
            y = Math.round((float)Math.random()*Controller.WIDTH);
        }
        public int getX(){
            return x;
        }
        public int getY(){
            return y;
        }
    }
    private class MockController{
        public MockController(MockNode[] mockNet) {
            JFrame frame = new JFrame("Mobiles Ad-Hoc Netz") {
                public void paint(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g;
                    for (MockNode mockNode : mockNet) {
                        g2d.setColor(Color.BLACK);
                        g2d.drawOval(mockNode.getX() - (Controller.RANGE / 2), mockNode.getY() - (Controller.RANGE / 2), Controller.RANGE, Controller.RANGE);
                        for (MockNode secondLayerMockNode : mockNet) {
                            if (reachable(mockNode, secondLayerMockNode)) {
                                g2d.setColor(Color.RED);
                                g2d.drawLine(mockNode.getX(), mockNode.getY(), secondLayerMockNode.getX(), secondLayerMockNode.getY());
                            }
                        }
                    }
                }
            };
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(Controller.WIDTH, Controller.HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    
        public boolean reachable(MockNode a, MockNode b) {
            return Controller.RANGE >= Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
        }
    }
}
