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
        testcase.drawNormalComplete();
        //testcase.drawCloseTogether();
        testcase.drawCloseComplete();
        //testcase.printProbability();
        //System.out.println("Wahrscheinlichkeit: " + testcase.completeNetProb(numberOfNodes) + "%.");
    }
    
    public void drawNormal(){
        // Diese Methode Zeichnet die Mock-Nodes in zufälligen Positionen
        this.mockNodes = new MockNode[numberOfNodes];
        // Erstellen und Initialisieren der MockNodes
        for (int i = 0; i < mockNodes.length; i++) {
            mockNodes[i] = new MockNode();
            // Initialisierung mit zufälligen Koordinaten
            mockNodes[i].init(i);
        }
        // Zeichnen der erstellten Nodes
        new MockController(mockNodes);
    }

    public static boolean reachable(MockNode a, MockNode b) {
        // Berechne, ob zwei MockNodes in Sendereichweite sind --> Kreis
        return RANGE / 2 >= Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
    }
    
    // Methode zum Errechnen, ob Netz vollständig verknüpft ist
    public static boolean isComplete(HashMap<Integer, MockNode> neighboursOf0, int totalNodes){
        // Merken der vorherigen Größe
        int presize = neighboursOf0.size();
        HashMap<Integer, MockNode> neighboursCopy = new HashMap<>(neighboursOf0);
        // Alle vorherigen Nacbarn durchgehen
        for (Object key : neighboursCopy.keySet()) {
            MockNode neighbour = neighboursOf0.get(key);
            // Nachbarn des Nachbarns ermitteln
            HashMap<Integer, MockNode> neighbourNeighbours = neighbour.getNeighbours();
            if(neighbourNeighbours.size() != 0){
                // Wenn dieser Nachbarn Nachbarn hat, die noch nicht gemerkt wurden
                Map<Integer, MockNode> tmp = new HashMap<Integer, MockNode>(neighbourNeighbours);
                tmp.keySet().removeAll(neighboursOf0.keySet());
                neighboursOf0.putAll(tmp);
            }
        }
        // Abbruchbedingung: Nachbarn und Nachbarn der Nachbarn der Startnode sind alle Nodes
        if (totalNodes == neighboursOf0.size()) {
            return true;
        }
        // Abbruchbedingung: Keine neuen Nachbarn hinzugekommen --> Rest nicht erreichbar
        if (presize == neighboursOf0.size()) {
            return false;
        }
        // Rekursiv --> neue Nachbarn durchgehen
        return isComplete(neighboursOf0, totalNodes);
    }

    public int completeNetProb(int numberOfNodes){
        int lapCount = 0;
        int probability = 0;
        // Berechnung der Wahrscheinlichkeit aus 100 Testläufen
        while(lapCount < 100){
            MockNode[] mockNodesNet = new MockNode[numberOfNodes];
            // Initialisierung neuer Nodes
            for(int i = 0; i < numberOfNodes; i++){
                mockNodesNet[i] = new MockNode();
                mockNodesNet[i].init(i);
            }
            // Bekanntmachen der eigenen Nachbarn für alle Nodes
            for (MockNode mockNode : mockNodesNet) {
                mockNode.setNeighbors(mockNodesNet);
            }
            HashMap<Integer, MockNode> neighboursOf0 = new HashMap<>();
            // Start bei einer Ausgangsnode
            neighboursOf0.put(mockNodesNet[0].getID(), mockNodesNet[0]);
            // Check, ob das Netz voll verknüpft ist
            if(isComplete(neighboursOf0, numberOfNodes)){
                // Wenn verknüpft, ein erfolgreicher Testlauf
                probability += 1;
            }
            lapCount++;
        }
        return probability;
    }

    public void printProbability(){
        // Methode probiert Wahrscheinlichkeiten für bestimmte Nodeanzahlen in 10er-Schritten durch
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
        // Erstellen von MockNodes mit geringerem räumlichem Abstand
        this.mockNodes = new MockNode[numberOfNodes];
        // Erstellen und Initialisieren der MockNodes
        for (int i = 0; i < mockNodes.length; i++) {
            mockNodes[i] = new MockNode();
            mockNodes[i].initCloserTogether(i);
        }
        // Bekanntmachen der Nachbarn einer jeden MockNode
        for (MockNode mockNode : mockNodes) {
            mockNode.setNeighbors(mockNodes);
        }
        // Zeichnen der nahen Nodes
        new MockController(mockNodes);
    }

    public void drawCloseComplete(){
        // Erstellen von MockNodes mit geringerem räumlichem Abstand und vollständig verbundn
        this.mockNodes = new MockNode[numberOfNodes];
        // Initialisierung der MockNodes näher zusammen
        for (int i = 0; i < mockNodes.length; i++) {
            mockNodes[i] = new MockNode();
            mockNodes[i].initCloserTogether(i);
        }
        HashMap<Integer, MockNode> newNodes = new HashMap<>();
        // Setzen der Nachbarn jeder Node
        for (MockNode mockNode : mockNodes) {
            mockNode.setNeighbors(mockNodes);
        }
        // Start mit Node 0
        newNodes.put(mockNodes[0].getID(), mockNodes[0]);
        //Check, ob Netz vollständig verbunden
        if(isComplete(newNodes, numberOfNodes)){
            // Zeichnen, wenn vollständig
            new MockController(mockNodes);
        } else {
            // Neues Netz, neuer Check, wenn nicht vollständig
            drawCloseTogether();
        }
    }
    
    public void drawNormalComplete(){
        // Zeichnen eines vollständig verbundenen Netzes mit normalen Abständen
        this.mockNodes = new MockNode[numberOfNodes];
        // Erstellen und Initialisieren der MockNodes
        for (int i = 0; i < mockNodes.length; i++) {
            mockNodes[i] = new MockNode();
            mockNodes[i].init(i);
        }
        HashMap<Integer, MockNode> newNodes = new HashMap<>();
        // Setzen der Nachbarn jeder Node
        for (MockNode mockNode : mockNodes) {
            mockNode.setNeighbors(mockNodes);
        }
        newNodes.put(mockNodes[0].getID(), mockNodes[0]);
        // Check, ob Netz vollständig
        if(isComplete(newNodes, numberOfNodes)){
            // Zeichnen wenn vollständig
            new MockController(mockNodes);
        } else {
            // Sonst nochmal neues Netz, neuer Check
            drawNormalComplete();
        }
    }
    public class MockNode {
        // Klasse MockNodes, simuliert Nodes, aber einfacher und nicht als Thread
        // --> Geringerer Rechenaufwand
        private int x;
        private int y;
        private int id;
        private HashMap<Integer, MockNode> directNeighbours;

        public void init(int id){
            // Initialisierung einer MockNode mit ID und Koordinaten
            this.x = Math.round((float)Math.random()*HEIGHT);
            this.y = Math.round((float)Math.random()*WIDTH);
            this.id = id;
        }
        public void initCloserTogether(int id){
            // Initialisierung einer MockNode in einem engeren Bereich mit ID
            this.x = Math.round((float)Math.random()*(HEIGHT/4)) + 250;
            this.y = Math.round((float)Math.random()*(WIDTH/4)) + 250;
            this.id = id;
        }
        public void setNeighbors(MockNode[] allNodes){
            // Methode zum Erkennen der eigenen Nachbarn (One-Hop)
            directNeighbours = new HashMap<Integer, MockNode>();
            // Iterieren durch alle Nodes
            for (MockNode mockNode : allNodes) {
                // Check, ob aktuelle Node in Reichweite ist
                if(this != mockNode && reachable(mockNode, this)){
                    // Wenn in Reichweite, als eigenen Nachbarn merken
                    directNeighbours.put(mockNode.getID(), mockNode);
                }
            }
        }

        // Getter und Setter für Attribute

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
        // MockController imitiert den Controller zur Visualisierung der MockNodes
        public MockController(MockNode[] mockNet) {
            JFrame frame = new JFrame("Mobiles Ad-Hoc Netz") {
                public void paint(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g;
                    // Iterieren durch alle Nodes
                    for (MockNode mockNode : mockNet) {
                        // Farbe der Kreise
                        g2d.setColor(Color.BLACK);
                        // Zeichnen aller Nodes
                        g2d.drawOval(mockNode.getX() - (RANGE / 2), mockNode.getY() - (RANGE / 2), RANGE, RANGE);
                        for (MockNode secondLayerMockNode : mockNet) {
                            // Check, ob Nodes verbunden
                            if (reachable(mockNode, secondLayerMockNode)) {
                                // Farbe der Routen
                                g2d.setColor(Color.RED);
                                // Route Zeichnen
                                g2d.drawLine(mockNode.getX(), mockNode.getY(), secondLayerMockNode.getX(), secondLayerMockNode.getY());
                            }
                        }
                    }
                }
            };
            // JFrame Einstellungen
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(WIDTH, HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    
        public boolean reachable(MockNode a, MockNode b) {
            // Reachable Methode im MockController
            return RANGE / 2 >= Math.sqrt(Math.pow((a.getX() - b.getX()), 2) + Math.pow((a.getY() - b.getY()), 2));
        }
    }
}
