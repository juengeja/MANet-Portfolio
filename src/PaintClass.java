import java.awt.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PaintClass {

    public static final int hoehe = 1000; //1m = 10px
    public static final int breite = 1000;
    public final int anzahl = 100;
    public final int sendeReichweite = 100;
    public static ArrayList<Station> stationen = new ArrayList<>();

    public PaintClass() {
        JFrame frame = new JFrame("Mobiles Ad-Hoc Netz") {
            public void paint(Graphics g) {
                for(int i = 0; i < anzahl; i++) {
                    int x = (int)(Math.random() * hoehe);
                    int y = (int)(Math.random() * breite);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.drawOval(x, y, sendeReichweite, sendeReichweite);
                    stationen.add(Station.punktErstellen(x, y, sendeReichweite));
                }
                int j = 20;
                while(j > 2) {
                    Graphics2D g2d = (Graphics2D) g;
                    for(int z = 0; z < stationen.size(); z++) {
                        stationen.get(z).bewegung();
                        g2d.drawOval(stationen.get(z).getX(), stationen.get(z).getY(), sendeReichweite, sendeReichweite);
                        Station neuerStationStandort = Station.punktErstellen(stationen.get(z).getX(), stationen.get(z).getY(), sendeReichweite);
                        stationen.add(z, neuerStationStandort);
                        stationen.remove(z);
                    }
                    try {

                        Thread.sleep(50);
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
}