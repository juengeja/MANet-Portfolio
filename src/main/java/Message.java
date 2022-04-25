import java.io.Serializable;

public class Message implements Serializable{
    
    private int senderID;
    private int seq;
    private int destinationPort;
    private Type type;
    private Serializable typeData;
    private int sourcePort;
    private int lastSenderPort;
  
 
   
    public Message(Type type, Serializable typeData) {
        this.type = type;
        this.typeData = typeData;
    }
  
    public Message( Type type, int destinationPort, Serializable typeData, int senderID){
        this.type = type;
        this.setDestinationPort(destinationPort);
        this.typeData = typeData;
        this.senderID = senderID;  
    }

    public Message(Type type, int senderID, Serializable typeData) {
        this.type = type;
        this.senderID = senderID;
        this.typeData = typeData;
    }


    public int getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(int destinationPort) {
        this.destinationPort = destinationPort;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }


    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public Serializable getTypeData() {
        return typeData;
    }

    public void setTypeData(Serializable dataLoad) {
        this.typeData = dataLoad;
    }

    public int getSourcePort() {
        return sourcePort;
    }

    public void setSourcePort(int sourcePort) {
        this.sourcePort = sourcePort;
    }

    public int getLastSenderPort() {
        return lastSenderPort;
    }

    public void setLastSenderPort(int lastSenderPort) {
        this.lastSenderPort = lastSenderPort;
    }

    public Type getType(){
        return this.type;
    }

    

    public enum Type{
        HELLO, TC, CONTENT
    }
}
