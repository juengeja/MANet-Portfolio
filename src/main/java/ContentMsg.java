import java.io.Serializable;

public class ContentMsg implements Serializable{
    
    private String content;
    private int receiverID;

    public ContentMsg(String content, int receiverID) {
        this.content = content;
        this.receiverID = receiverID;
    }
    
    
    public String getContent() {
        return content;
    }
    public int getReceiverID() {
        return receiverID;
    }
    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }
    public void setContent(String content) {
        this.content = content;
    }

}
