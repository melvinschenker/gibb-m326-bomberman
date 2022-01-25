package Client.Model;

import Client.Controller.Client;
import Client.Controller.StatusChanger;
import Commons.iConstants;

import java.awt.Graphics;
import javax.swing.JPanel;

// for me and enemy players
public class Player {
    private int x, y;
    private String status, color;
    private JPanel panel;
    private boolean isAlive;

    private StatusChanger statusChanger;

    public Player(int id, JPanel panel) throws InterruptedException {
        this.x = Client.spawn[id].getX();
        this.y = Client.spawn[id].getY();
        this.color = Animation.playersColor[id];
        this.panel = panel;
        this.isAlive = Client.isAlive[id];

        (statusChanger = new StatusChanger(this, "player_is_waiting")).start();
    }

    public void draw(Graphics g) {
        if (isAlive)
            g.drawImage(Animation.getHashtable().get(color + "/" + status), x, y, iConstants.WIDTH_PLAYER_IN_SPRITE_SHEET, iConstants.HEIGHT_PLAYER_IN_SPRITE_SHEET, null);
    }

    /*
     *   getter and setter to make them available withing the code
     */
    public void setIsAlive(boolean alive) {
        isAlive = alive;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JPanel getPanel() {
        return panel;
    }

    public StatusChanger getStatusChanger() {
        return statusChanger;
    }
}