import java.io.Serializable;

public class Message implements Serializable{
    
    private int seq;
    private int destinationPort;
    private Type type;
    private String dataLoad;
    private int sourcePort;
    private int lastSenderPort;
    private int sourceX;
    private int sourceY;

 
   

    public Message(int sequenceNumber, Type type, int destinationPort, int sourceX, int sourceY){
        this.setSeq(sequenceNumber);
        this.type = type;
        this.destinationPort = destinationPort;
        this.sourceX = sourceX;
        this.sourceY = sourceY;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getDataLoad() {
        return dataLoad;
    }

    public void setDataLoad(String dataLoad) {
        this.dataLoad = dataLoad;
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

    public int getDestinationPort(){
        return this.destinationPort;
    }

    public int getSourceX(){
        return sourceX;
    }

    public int getSourceY(){
        return sourceY;
    }

    public enum Type{
        HELLO, TC, DATA
    }
}
