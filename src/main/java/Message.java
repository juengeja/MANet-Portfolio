import java.io.Serializable;

public class Message implements Serializable{
    
    private final int sequenceNumber;
    private final int destinationPort;
    private final Type type;
    private String dataLoad;
    private int sourcePort;
    private int lastSenderPort;
    private final int sourceX;
    private final int sourceY;

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
