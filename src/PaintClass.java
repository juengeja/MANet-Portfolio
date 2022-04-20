import java.awt.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PaintClass {

    public static final int hoehe = 1000; //1m = 10px
    public static final int breite = 1000;
    public static final int anzahl = 90;
    public final int sendeReichweite = 100;
    public static Station[] stationen = new Station[anzahl];

    public PaintClass() {
        JFrame frame = new JFrame("Mobiles Ad-Hoc Netz") {
            public void paint(Graphics g) {
                for(int i = 0; i < anzahl; i++) {
                    int x = (int)(Math.random() * hoehe);
                    int y = (int)(Math.random() * breite);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.drawOval(x, y, sendeReichweite, sendeReichweite);
                    stationen[i] = Station.punktErstellen(x, y, sendeReichweite);
                }
                int j = 20;
                while(j > 2) {
                    Graphics2D g2d = (Graphics2D) g;
                    for(int z = 0; z < stationen.length; z++) {
                        Station neuerStationStandort = stationen[z].bewegung();
                        g2d.drawOval(stationen[z].getX()-(sendeReichweite/2), stationen[z].getY()-(sendeReichweite/2), sendeReichweite, sendeReichweite);
                        //Station neuerStationStandort = Station.punktErstellen(stationen[z].getX(), stationen[z].getY(), sendeReichweite);
                        stationen[z] = neuerStationStandort;
                    }
                    for(int x = 0; x < anzahl; x++) {
                        for(int y = 0; y < anzahl; y++) {
                            if(erreichbarkeit(stationen[x], stationen[y])){
                                g2d.setColor(Color.RED);
                                g2d.drawLine(stationen[x].getX(), stationen[x].getY(), stationen[y].getX(), stationen[y].getY());
                            }
                        }
                    }
                    g2d.setColor(Color.BLACK);
                    try {

                        Thread.sleep(130);
                        this.getGraphics().clearRect(0, 0, 1000, 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    j++;
                }
            }
        };
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(breite, hoehe);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaintClass());
    }

    public boolean erreichbarkeit(Station a, Station b) {
        if(sendeReichweite >= Math.sqrt(Math.pow((a.getX()-b.getX()),2) + Math.pow((a.getY()-b.getY()), 2))) {
            return true;
        }
        return false;
    }

}
