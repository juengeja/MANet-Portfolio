public class Station {
    private int x, y, groesse;

    public Station(int x, int y, int groesse) {
        this.x = x;
        this.y = y;
        this.groesse = groesse;
    }

    public static Station punktErstellen(int x, int y, int groesse) {
        return new Station(x, y, groesse);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Station bewegung() {
        double zufalllszahl = Math.random();
        int schrittgroesse = 10;

        if(zufalllszahl >= 0 && zufalllszahl < 0.25) {  //nach links oben
            if(this.getX() > schrittgroesse) {
                this.setX(this.getX() - schrittgroesse);
            } else {
                this.setX(this.getX() + schrittgroesse);
            }
            if(this.getY() > schrittgroesse) {
                this.setY(this.getY()-schrittgroesse);
            } else {
                this.setY(this.getY() + schrittgroesse);
            }
        }

        if(zufalllszahl >= 0.25 && zufalllszahl < 0.5) { //nach links unten
            if(this.getX() > schrittgroesse) {
                this.setX(this.getX() - schrittgroesse);
            } else {
                this.setX(this.getX() + schrittgroesse);
            }
            if(this.getY() < PaintClass.hoehe-schrittgroesse) {
                this.setY(this.getY() + schrittgroesse);
            } else {
                this.setY(this.getY() - schrittgroesse);
            }
        }

        if(zufalllszahl >= 0.5 && zufalllszahl < 0.75) { //nach rechts oben
            if(this.getX() < PaintClass.breite-schrittgroesse) {
                this.setX(this.getX() + schrittgroesse);
            } else {
                this.setX(this.getX() - schrittgroesse);
            }
            if(this.getY() > schrittgroesse) {
                this.setY(this.getY()-schrittgroesse);
            } else {
                this.setY(this.getY() + schrittgroesse);
            }
        }

        if(zufalllszahl >= 0.75 && zufalllszahl < 1) { //nach rechts unten
            if(this.getX() < PaintClass.breite-schrittgroesse) {
                this.setX(this.getX() + schrittgroesse);
            } else {
                this.setX(this.getX() - schrittgroesse);
            }
            if(this.getY() < PaintClass.hoehe-schrittgroesse) {
                this.setY(this.getY() + schrittgroesse);
            } else {
                this.setY(this.getY() - schrittgroesse);
            }
        }

        return this;
    }
}