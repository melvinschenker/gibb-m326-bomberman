package Client.View;

import Client.Controller.Client;
import Client.Model.Animation;
import Client.Model.Player;
import Commons.iConstants;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class Game extends JPanel {
    public static final long serialVersionUID = 1L;
    public static Player playerIsYou, enemy1, enemy2, enemy3;

    public Game(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        try {
            System.out.print("Starting players...");
            playerIsYou = new Player(Client.id, this);
            enemy1 = new Player((Client.id+1)% iConstants.AMOUNT_OF_PLAYERS, this);
            enemy2 = new Player((Client.id+2)% iConstants.AMOUNT_OF_PLAYERS, this);
            enemy3 = new Player((Client.id+3)% iConstants.AMOUNT_OF_PLAYERS, this);
        } catch (InterruptedException e) {
            System.out.println(" Error: " + e);
            System.exit(1);
        }
        System.out.println(" ok");

        System.out.println("New player: " + Animation.playersColor[Client.id]);
    }

    //draws the components, called by paint() and repaint()
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMap(g);
        enemy1.draw(g);
        enemy2.draw(g);
        enemy3.draw(g);
        playerIsYou.draw(g);

        // System.out.format("%s: %s [%04d, %04d]\n", Game.you.color, Game.you.status, Game.you.x, Game.you.y);;
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawMap(Graphics g) {
        for (int i = 0; i < iConstants.ROW; i++)
            for (int j = 0; j < iConstants.COLUMN; j++)
                g.drawImage(
                        Animation.getHashtable().get(Client.map[i][j].getImage()),
                        Client.map[i][j].getX(), Client.map[i][j].getY(),
                        iConstants.SIZE_SPRITE, iConstants.SIZE_SPRITE, null
                );
    }

    public static void setClientMap(String key, int x, int y) {
        Client.map[x][y].setImage(key);
    }
}
