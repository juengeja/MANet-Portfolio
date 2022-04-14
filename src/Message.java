import java.io.Serializable;

public class Message implements Serializable{
    
    private int sequenceNumber;
    private int destinationPort;
    private Type type;

    public Message(int sequenceNumber, Type type, int destinationPort){
        this.sequenceNumber = sequenceNumber;
        this.type = type;
        this.destinationPort = destinationPort;
    }

    public Type getType(){
        return this.type;
    }

    public int getDestinationPort(){
        return this.destinationPort;
    }

    public enum Type{
        HELLO, TC
    }
}
