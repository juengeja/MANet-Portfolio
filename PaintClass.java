import java.awt.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class PaintClass {

    public final int hoehe = 1000; //1m = 10px
    public final int breite = 1000;
    public final int anzahl = 80;
    public final int sendeReichweite = 100;
    public ArrayList<Station> stationen = new ArrayList<>();

    public PaintClass() {
        JFrame frame = new JFrame("Mobiles Ad-Hoc Netz") {
            public void paint(Graphics g) {
                for(int i = 0; i < anzahl; i++) {
                    int x = (int)(Math.random() * hoehe);
                    int y = (int)(Math.random() * breite);
                    stationen.add(Station.punktErstellen(x, y, sendeReichweite));
                    g.drawOval(x, y, sendeReichweite, sendeReichweite);
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