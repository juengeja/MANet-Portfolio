public class Node extends Thread{

    private final int nodeID;
    private int x;
    private int y;
    private final int totalNodes;
    private final TCPReceiver receiver;
    private Thread receiverThread;
    private final TCPSender sender;
    private Thread senderThread;
    private static final int RANGE = Controller.RANGE;

    public static final int STEPSIZE = Controller.STEPSIZE; //Bei Bewegung: wie weit gehen die Nodes?

    public Node(int nodeID, int totalNumberOfNodes){
        this.nodeID = nodeID;
        this.totalNodes = totalNumberOfNodes;
        this.receiver = new TCPReceiver(this);
        this.sender = new TCPSender(this);
    }

    public void init(){
        x = Math.round((float)Math.random()*1000);
        y = Math.round((float)Math.random()*1000);
        System.out.println("Initialisierung mit Koordinaten: " + x + ", " + y);
        this.receiverThread = new Thread(receiver);
        receiverThread.start();
        this.senderThread = new Thread(sender);
        senderThread.start();
    }

    @Override
    public void run(){
        System.out.println("Node [" + this.nodeID + "] is running.");
        if(this.nodeID == 0){
            int destinationPort = 7700 + totalNodes - 1;
            this.sender.sendMessage(new Message(1, Message.Type.HELLO, destinationPort, this.x, this.y));
        }
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRange(){
        return RANGE;
    }
    public int getNodeID(){
        return nodeID;
    }


    //Stationen zufällig bewegen lassen
    public Node bewegung() {
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


        double zufallszahl = Math.random(); //Diese Zufallszahl soll zufällig entscheiden, wohin sich die Stationen bewegen

        if (zufallszahl >= 0 && zufallszahl < 0.5) {
            //Zu 50% bewegen sich die Stationen nicht
        }
        if (zufallszahl >= 0.5 && zufallszahl < 0.57) {
            //Zu 7% bewegen sich die Stationen nach links. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
            if (this.getX() > STEPSIZE) {
                this.setX(this.getX() - STEPSIZE);
            }
        }
        if (zufallszahl >= 0.57 && zufallszahl < 0.64) {
            //Zu 7% bewegen sich die Stationen nach rechts. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
            if (this.getX() + STEPSIZE < Controller.WIDTH) {
                this.setX(this.getX() + STEPSIZE);
            }
        }

        if (zufallszahl >= 0.64 && zufallszahl < 0.71) {
            //Zu 7% bewegen sich die Stationen nach oben. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
            if (this.getY() + STEPSIZE < Controller.HEIGHT) {
                this.setY(this.getY() + STEPSIZE);
            }
        }

        if (zufallszahl >= 0.71 && zufallszahl < 0.78) {
            //Zu 7% bewegen sich die Stationen nach unten. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
            if (this.getY() > STEPSIZE) {
                this.setY(this.getY() - STEPSIZE);
            }
        }

        if (zufallszahl >= 0.78 && zufallszahl < 0.835) {
            //Zu 5.5% bewegen sich die Stationen nach links oben. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
            if (this.getX() > STEPSIZE) {
                this.setX(this.getX() - STEPSIZE);
            } else {
                this.setX(this.getX() + STEPSIZE);
            }
            if (this.getY() > STEPSIZE) {
                this.setY(this.getY() - STEPSIZE);
            } else {
                this.setY(this.getY() + STEPSIZE);
            }
        }

        if (zufallszahl >= 0.835 && zufallszahl < 0.89) {
            //Zu 5.5% bewegen sich die Stationen nach links unten. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
            if (this.getX() > STEPSIZE) {
                this.setX(this.getX() - STEPSIZE);
            } else {
                this.setX(this.getX() + STEPSIZE);
            }
            if (this.getY() < Controller.HEIGHT - STEPSIZE) {
                this.setY(this.getY() + STEPSIZE);
            } else {
                this.setY(this.getY() - STEPSIZE);
            }
        }

        if (zufallszahl >= 0.89 && zufallszahl < 0.945) {
            //Zu 5.5% bewegen sich die Stationen nach rechts oben. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
            if (this.getX() < Controller.WIDTH - STEPSIZE) {
                this.setX(this.getX() + STEPSIZE);
            } else {
                this.setX(this.getX() - STEPSIZE);
            }
            if (this.getY() > STEPSIZE) {
                this.setY(this.getY() - STEPSIZE);
            } else {
                this.setY(this.getY() + STEPSIZE);
            }
        }

        if (zufallszahl >= 0.945 && zufallszahl < 1) {
            //Zu 5.5% bewegen sich die Stationen nach rechts unten. Voraussetzung: Innerhalb des Spielfelds nach der Bewegung.
            if (this.getX() < Controller.WIDTH - STEPSIZE) {
                this.setX(this.getX() + STEPSIZE);
            } else {
                this.setX(this.getX() - STEPSIZE);
            }
            if (this.getY() < Controller.HEIGHT - STEPSIZE) {
                this.setY(this.getY() + STEPSIZE);
            } else {
                this.setY(this.getY() - STEPSIZE);
            }
        }
        return this;
    }
}
