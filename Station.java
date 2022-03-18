public class Station {
    private int x, y, groesse;

    public Station(int x, int y, int groesse) {
        this.x = x; this.y = y; this.groesse = groesse;
    }

    public static Station punktErstellen(int x, int y, int groesse) {
        return new Station(x, y, groesse);
    }


}