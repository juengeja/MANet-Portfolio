import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TestCases {
    private static int numberOfNodes;
    private MockNode[] mockNodes;
    private static final int RANGE = 100;
    private static final int HEIGHT = 1000;
    private static final int WIDTH = 1000;
    
    public static void main(String[] args) {
        numberOfNodes = 1630;
        TestCases testcase = new TestCases();
        testcase.drawNormal();
        //testcase.drawCloseTogether();
        //testcase.printProbability();
        //System.out.println("Wahrscheinlichkeit: " + testcase.completeNetProb(numberOfNodes) + "%.");
    }
    
    public void drawNormal(){
        this.mockNodes = new MockNode[numberOfNodes];
        for (int i = 0; i < mockNodes.length; i++) {
            mockNodes[i] = new MockNode();
            mockNodes[i].init(i);
        }
        
        new MockController(mockNodes);
    }

    public static boolean reachable(MockNode a, MockNode b) {
        return RANGE / 2 >= Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
    }
    
    public static boolean isComplete(HashMap<Integer, MockNode> neighboursOf0, int totalNodes){
        int presize = neighboursOf0.size();
        HashMap<Integer, MockNode> neighboursCopy = new HashMap<>(neighboursOf0);
        for (Object key : neighboursCopy.keySet()) {
            MockNode neighbour = neighboursOf0.get(key);
            HashMap<Integer, MockNode> neighbourNeighbours = neighbour.getNeighbours();
            if(neighbourNeighbours.size() != 0){
                Map<Integer, MockNode> tmp = new HashMap<Integer, MockNode>(neighbourNeighbours);
                tmp.keySet().removeAll(neighboursOf0.keySet());
                neighboursOf0.putAll(tmp);
            }
        }
        if (totalNodes == neighboursOf0.size()) {
            return true;
        }
        if (presize == neighboursOf0.size()) {
            return false;
        }
        return isComplete(neighboursOf0, totalNodes);
    }

    public int completeNetProb(int numberOfNodes){
        int lapCount = 0;
        int probability = 0;
        while(lapCount < 100){
            MockNode[] mockNodesNet = new MockNode[numberOfNodes];
            for(int i = 0; i < numberOfNodes; i++){
                mockNodesNet[i] = new MockNode();
                mockNodesNet[i].init(i);
            }
            for (MockNode mockNode : mockNodesNet) {
                mockNode.setNeighbors(mockNodesNet);
            }
            HashMap<Integer, MockNode> neighboursOf0 = new HashMap<>();
            neighboursOf0.put(mockNodesNet[0].getID(), mockNodesNet[0]);
            if(isComplete(neighboursOf0, numberOfNodes)){
                probability += 1;
            }
            lapCount++;
        }
        return probability;
    }

    public void printProbability(){
        int nodesFor90 = 0;
        for (int i = 1500; i < 2000; i += 10) {
            if(completeNetProb(i) >= 90){
                nodesFor90 = i;
                break;
            }
        }
        System.out.println("Wahrscheinlichkeit für ein vollständig verbundenes Netz >= 90% ab " + nodesFor90 + " Nodes.");
    }

    public void drawCloseTogether(){
        this.mockNodes = new MockNode[numberOfNodes];
        for (int i = 0; i < mockNodes.length; i++) {
            mockNodes[i] = new MockNode();
            mockNodes[i].initCloserTogether(i);
        }
        HashMap<Integer, MockNode> newNodes = new HashMap<>();
        for (MockNode mockNode : mockNodes) {
            mockNode.setNeighbors(mockNodes);
        }
        newNodes.put(mockNodes[0].getID(), mockNodes[0]);
        if(isComplete(newNodes, numberOfNodes)){
            new MockController(mockNodes);
        } else {
            drawCloseTogether();
        }
    }
    
    public class MockNode {
        private int x;
        private int y;
        private int id;
        private HashMap<Integer, MockNode> directNeighbours;

        public void init(int id){
            this.x = Math.round((float)Math.random()*HEIGHT);
            this.y = Math.round((float)Math.random()*WIDTH);
            this.id = id;
        }
        public void initCloserTogether(int id){
            this.x = Math.round((float)Math.random()*(HEIGHT/4)) + 250;
            this.y = Math.round((float)Math.random()*(WIDTH/4)) + 250;
            this.id = id;
        }
        public void setNeighbors(MockNode[] allNodes){
            directNeighbours = new HashMap<Integer, MockNode>();
            for (MockNode mockNode : allNodes) {
                if(this != mockNode && reachable(mockNode, this)){
                    directNeighbours.put(mockNode.getID(), mockNode);
                }
            }
        }
        public HashMap<Integer, MockNode> getNeighbours(){
            return directNeighbours;
        }
        public int getX(){
            return x;
        }
        public int getY(){
            return y;
        }
        public int getID(){
            return id;
        }
    }
    public class MockController{
        public MockController(MockNode[] mockNet) {
            JFrame frame = new JFrame("Mobiles Ad-Hoc Netz") {
                public void paint(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g;
                    for (MockNode mockNode : mockNet) {
                        g2d.setColor(Color.BLACK);
                        g2d.drawOval(mockNode.getX() - (RANGE / 2), mockNode.getY() - (RANGE / 2), RANGE, RANGE);
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
            frame.setSize(WIDTH, HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    
        public boolean reachable(MockNode a, MockNode b) {
            return RANGE / 2 >= Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
        }
    }
}
