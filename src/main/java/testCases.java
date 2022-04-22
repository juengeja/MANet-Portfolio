public class testCases {
    private int KNOTENZAHL;
    private Node[] nodes;

    public static void main(String[] args) {
        
    }

    public void initNet(int totalNrNodes){
        this.KNOTENZAHL = totalNrNodes;
        this.nodes = new Node[totalNrNodes];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(i, totalNrNodes);
            nodes[i].init();
        }
    }

}
