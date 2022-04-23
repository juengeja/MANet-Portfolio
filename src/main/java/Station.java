public class Station {
    private int x, y, groesse;

    public static final int schrittgroesse = 40;

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
        /*
        Wahrscheinlichkeiten:
        ----------------------------
        Stehen bleiben: 50%         /
        Links: 7%                   /
        Rechts: 7%                  /
        Oben: 7%                    /
        Unten: 7%                   /
        Links-Unten: 5.5%           /
        Links-Oben: 5.5%            /
        Rechts-Unten: 5.5%          /
        Rechts-Oben: 5.5%           /
                                    /
        Total: 100%                 /
        ----------------------------
         */


        double zufalllszahl = Math.random(); //Diese Zufallszahl soll zufällig entscheiden, wohin sich die Stationen bewegen

        if(zufalllszahl >= 0 && zufalllszahl < 0.5) {
            //Zu 50% bewegen sich die Stationen nicht
        }
        if(zufalllszahl >= 0.5 && zufalllszahl < 0.57) {
            //Zu 7% bewegen sich die Stationen nach links. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
            if(this.getX() > schrittgroesse) {
                this.setX(this.getX() - schrittgroesse);
            }
        }
        if(zufalllszahl >= 0.57 && zufalllszahl < 0.64) {
            //Zu 7% bewegen sich die Stationen nach rechts. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
            if(this.getX() + schrittgroesse < PaintClass.breite) {
                this.setX(this.getX() + schrittgroesse);
            }
        }

        if(zufalllszahl >= 0.64 && zufalllszahl < 0.71) {
            //Zu 7% bewegen sich die Stationen nach oben. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
            if(this.getY() + schrittgroesse < PaintClass.hoehe) {
                this.setY(this.getY() + schrittgroesse);
            }
        }

        if(zufalllszahl >= 0.71 && zufalllszahl < 0.78) {
            //Zu 7% bewegen sich die Stationen nach unten. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
            if(this.getY() > schrittgroesse) {
                this.setY(this.getY() - schrittgroesse);
            }
        }

        if(zufalllszahl >= 0.78 && zufalllszahl < 0.835) {
            //Zu 5.5% bewegen sich die Stationen nach links oben. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
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

        if(zufalllszahl >= 0.835 && zufalllszahl < 0.89) {
            //Zu 5.5% bewegen sich die Stationen nach links unten. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
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

        if(zufalllszahl >= 0.89 && zufalllszahl < 0.945) {
            //Zu 5.5% bewegen sich die Stationen nach rechts oben. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
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

        if(zufalllszahl >= 0.945 && zufalllszahl < 1) {
            //Zu 5.5% bewegen sich die Stationen nach rechts unten. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
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
