import java.io.Serializable;
import java.util.HashSet;

public class TCMsg implements Serializable{

        private HashSet<Integer> mprSelectors;
        private Long mprSelectorsSeq;
    
        TCMsg(HashSet<Integer> mprSelectors, Long mprSelectorsSeq) {
          this.setMprSelectors(mprSelectors);
          this.setMprSelectorsSeq(mprSelectorsSeq);
        }

        public HashSet<Integer> getMprSelectors() {
          return mprSelectors;
        }

        public void setMprSelectors(HashSet<Integer> mprSelectors) {
          this.mprSelectors = mprSelectors;
        }

        public Long getMprSelectorsSeq() {
          return mprSelectorsSeq;
        }

        public void setMprSelectorsSeq(Long mprSelectorsSeq) {
          this.mprSelectorsSeq = mprSelectorsSeq;
        }
    
        
      }

