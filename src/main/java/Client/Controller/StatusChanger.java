package Client.Controller;

import Client.View.Game;
import Client.Model.Animation;
import Client.Model.Player;
import Commons.iConstants;

public class StatusChanger extends Thread {
    private Player player;
    private String status;
    private int index;
    private boolean playerInMotion;

    public StatusChanger(Player player, String initialStatus) {
        this.player = player;
        this.status = initialStatus;
        index = 0;
        playerInMotion = true;
    }

    public void run() {
        while (true) {
            player.setStatus(status + "_" + index);
            if (playerInMotion) {
                index = (++index) % Animation.amountOfLooping.get(status);
                player.getPanel().repaint();
            }

            try {
                Thread.sleep(iConstants.UPDATE_STATUS_OF_PLAYER);
            } catch (InterruptedException e) {
            }

            if (player.getStatus().equals("player_is_dying_4")) {
                player.setIsAlive(false);

                // if your player is dead -> exit the game / disconnect
                if (Game.playerIsYou == player) {
                    System.exit(1);
                }
            }
        }
    }

    public void setLoopStatus(String status) {
        this.status = status;
        index = 1;
        playerInMotion = true;
    }

    public void stopLoopStatus() {
        playerInMotion = false;
        index = 0;
    }
}