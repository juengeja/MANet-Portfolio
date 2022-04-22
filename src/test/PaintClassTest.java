
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaintClassTest {


    @Test
    @DisplayName("Sind zwei Stationen in Reichweite?")
    void testErreichbarkeit() {
        assertEquals(true, PaintClass.erreichbarkeit(new Station(0, 0, 100), new Station(0, 0, 100))); //Aufeinander liegende Stationen
        assertEquals(true, PaintClass.erreichbarkeit(new Station(0, 0, 100), new Station(50, 50, 100))); //In Reichweite liegende Stationen
        assertEquals(false, PaintClass.erreichbarkeit(new Station(0, 0, 100), new Station(1000, 1000, 100))); //Außerhalb Reichweite liegende Stationen
    }
}
