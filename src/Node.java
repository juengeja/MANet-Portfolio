

public class Node implements Runnable{

    private int nodeID;
    private int x;
    private int y;
    private int totalNodes;
    private Receiver[] receivers;
    private Sender[] senders;

    public Node(int nodeID, int totalNumberOfNodes){
        this.nodeID = nodeID;
        this.totalNodes = totalNumberOfNodes;
        this.receivers = new Receiver[totalNumberOfNodes];
        this.senders = new Sender[totalNumberOfNodes];
    }

    public void init(){
        x = Math.round((float)Math.random()*100);
        y = Math.round((float)Math.random()*100);

        for (int i = 0; i < totalNodes; i++) {
            receivers[i] = new Receiver(this.nodeID, i + 1);
            senders[i] = new Sender(this.nodeID, i + 1);
        }
        for (Receiver receiver : receivers) {
            receiver.run();
        }
        for (Sender sender : senders) {
            sender.run();
        }
    }

    @Override
    public void run(){
        System.out.println("Node [" + this.nodeID + "] is running.");
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}