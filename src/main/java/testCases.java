import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class TestCases {
    private static final int KNOTENZAHL = 500;
    private MockNode[] mockNodes;
    
    public static void main(String[] args) {
        TestCases testcase = new TestCases();
        //testcase.initNet();
        int nodesFor90 = 0;
        for (int i = 1600; i < 2500; i += 10) {
            if(testcase.completeNetProb(i) >= 90){
                nodesFor90 = i;
                break;
            }
        }
        System.out.println("Wahrscheinlichkeit für ein vollständig verbundenes Netz >= 90% ab " + nodesFor90 + " Nodes.");
    }
    
    public void initNet(){
        this.mockNodes = new MockNode[KNOTENZAHL];
        for (int i = 0; i < mockNodes.length; i++) {
            mockNodes[i] = new MockNode();
            mockNodes[i].init();
        }
        
        new MockController(mockNodes);
    }

    public static boolean reachable(MockNode a, MockNode b) {
        return Controller.RANGE / 2 >= Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
    }

    public static boolean isComplete(MockNode[] mockNodeNet){
        boolean[] isComplete = new boolean[mockNodeNet.length];
        Arrays.fill(isComplete, false);
        for (int a = 0; a < mockNodeNet.length; a++) {
            for (int b = 0; b < mockNodeNet.length; b++) {
                if(a != b && reachable(mockNodeNet[a], mockNodeNet[b])){
                    isComplete[a] = true;
                }
            }
        }
        for(boolean value: isComplete){
            if(value == false){ return false;}
          }
        return true;
    }

    public int completeNetProb(int numberOfNodes){
        int lapCount = 0;
        int probability = 0;
        while(lapCount < 1000){
            MockNode[] mockNodesNet = new MockNode[numberOfNodes];
            for(int i = 0; i < numberOfNodes; i++){
                mockNodesNet[i] = new MockNode();
                mockNodesNet[i].init();
            }
            if(isComplete(mockNodesNet)){
                probability += 1;
            }
            lapCount++;
        }
        return probability/10;
    }
    
    public class MockNode {
        private int x;
        private int y;

        public void init(){
            this.x = Math.round((float)Math.random()*Controller.HEIGHT);
            this.y = Math.round((float)Math.random()*Controller.WIDTH);
        }
        public int getX(){
            return x;
        }
        public int getY(){
            return y;
        }
    }
    public class MockController{
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
            return Controller.RANGE / 2 >= Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
        }
    }
}
