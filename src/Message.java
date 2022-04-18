import java.io.Serializable;

public class Message implements Serializable{
    
    private int sequenceNumber;
    private int destinationPort;
    private Type type;
    private String dataLoad;
    private int sourcePort;
    private int lastSenderPort;
    private int sourceX;
    private int sourceY;

    public Message(int sequenceNumber, Type type, int destinationPort, int sourceX, int sourceY){
        this.sequenceNumber = sequenceNumber;
        this.type = type;
        this.destinationPort = destinationPort;
        this.sourceX = sourceX;
        this.sourceY = sourceY;
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
