public class Controller {

    private static final int TOTAL_NUMBER_OF_NODES = 5;

    public static void main(String[] args) {
        Node[] nodes = new Node[TOTAL_NUMBER_OF_NODES];
        for (int i = 0; i < nodes.length; i++) {
            Node node = new Node(i + 1, TOTAL_NUMBER_OF_NODES);
            nodes[i] = node;
            node.init();
        }
        for (Node node : nodes) {
            node.run();
        }
    }
}
