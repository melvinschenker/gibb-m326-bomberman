package Client.Connection;

import Client.Controller.Client;
import Client.View.Game;
import Client.Model.Player;
import Commons.iConstants;

public class ReceiverHandler extends Thread {
    private Player player;

    // detects which player it is
    public Player whoIsWho(int playerID) {
        if (playerID == Client.id) {
            return Game.playerIsYou;
            // Modulo number of playing players results in check if current playerId is enemy.
        } else if (playerID == (Client.id + 1)% iConstants.AMOUNT_OF_PLAYERS) {
            return Game.enemy1;
        } else if (playerID == (Client.id + 2)% iConstants.AMOUNT_OF_PLAYERS) {
            return Game.enemy2;
        }else if (playerID == (Client.id + 3)% iConstants.AMOUNT_OF_PLAYERS) {
            return Game.enemy3;
        }
        return null;
    }

    public void run() {
        String text;
        while(Client.in.hasNextLine()) {
            this.player = whoIsWho(Client.in.nextInt()); // gets id from client
            text = Client.in.next();

            switch(text) {
                case("board_update"):
                    Game.setClientMap(Client.in.next(), Client.in.nextInt(), Client.in.nextInt());
                    Game.playerIsYou.getPanel().repaint();
                    break;
                case("new_coordinates"):
                    player.setX(Client.in.nextInt());
                    player.setY(Client.in.nextInt());
                    Game.playerIsYou.getPanel().repaint();
                    break;
                case("new_status"):
                    player.getStatusChanger().setLoopStatus(Client.in.next());
                    break;
                case("stop_status"):
                    player.getStatusChanger().stopLoopStatus();
                    break;
                case("player_has_joined"):
                    player.setIsAlive(true);
                    break;
            }
        }
    }
}

