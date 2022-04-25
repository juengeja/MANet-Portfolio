import java.io.Serializable;
import java.util.HashSet;

public class HelloMsg implements Serializable{

    private HashSet<Integer> oneHopNbrs;
    private HashSet<Integer> mprs;
    private int sourceX;
    private int sourceY;

    public HelloMsg(int sourceX, int sourceY, HashSet<Integer> oneHopNbrs, HashSet<Integer> mprs) {
        this.setSourceX(sourceX);
        this.setSourceY(sourceY);
        this.setMprs(mprs);
        this.setOneHopNbrs(oneHopNbrs);
       
        //TODO Auto-generated constructor stub
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

    public int getSourceX(){
        return sourceX;
    }

    public int getSourceY(){
        return sourceY;
    }

    public void setSourceX(int sourceX){
        this.sourceX = sourceX;
    }

    public void setSourceY(int sourceY){
        this.sourceY = sourceY;
    }
}