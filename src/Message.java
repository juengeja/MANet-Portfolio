import java.io.Serializable;

public class Message implements Serializable{
    
    private int sequenceNumber;
    private Type type;

    public Message(int sequenceNumber, Type type){
        this.sequenceNumber = sequenceNumber;
        this.type = type;
    }

    private enum Type{
        HELLO, TC
    }
}
