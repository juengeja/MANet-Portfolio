import java.util.HashSet;

public class HelloMsg extends Message{

    private int senderID;
    private HashSet<Integer> oneHopNbrs;
    private HashSet<Integer> mprs;

    public HelloMsg(int sequenceNumber, Message.Type type, int destinationPort, int sourceX, int sourceY,HashSet<Integer> oneHopNbrs, HashSet<Integer> mprs, int senderID) {
        super(sequenceNumber, type, destinationPort, sourceX, sourceY);
        this.setMprs(mprs);
        this.setOneHopNbrs(oneHopNbrs);
        this.setSenderID(senderID);
        //TODO Auto-generated constructor stub
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public HashSet<Integer> getMprs() {
        return mprs;
    }

    public void setMprs(HashSet<Integer> mprs) {
        this.mprs = mprs;
    }

    public HashSet<Integer> getOneHopNbrs() {
        return oneHopNbrs;
    }

    public void setOneHopNbrs(HashSet<Integer> oneHopNbrs) {
        this.oneHopNbrs = oneHopNbrs;
    }

}