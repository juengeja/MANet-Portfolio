import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StationTest {

    @Test
    @DisplayName("Funktioniert die Erstellung von Stationen?")
    void punktErstellen() {
        assertNotEquals(new Station(0, 0, 200), Station.punktErstellen(2, 0, 200));
        assertNotEquals(new Station(0, 0, 200), Station.punktErstellen(0, 5, 200));
        assertNotEquals(new Station(0, 0, 200), Station.punktErstellen(0, 0, 210));
    }

}
