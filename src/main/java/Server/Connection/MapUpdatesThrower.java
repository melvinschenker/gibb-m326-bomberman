package Server.Connection;

import Commons.iConstants;
import Server.Controller.ClientManager;
import Server.Controller.Server;

public class MapUpdatesThrower extends Thread {
    private boolean bombPlanted;
    private int id, row, col;

    public MapUpdatesThrower(int id) {
        this.id = id;
        this.bombPlanted = false;
    }

    public void setBombPlanted(int x, int y) {
        x += iConstants.WIDTH_PLAYER_IN_SPRITE_SHEET / 2;
        y += 2 * iConstants.HEIGHT_PLAYER_IN_SPRITE_SHEET / 3;

        this.col = x/iConstants.SIZE_SPRITE;
        this.row = y/iConstants.SIZE_SPRITE;

        this.bombPlanted = true;
    }

    public void run() {
        while (true) {
            if (bombPlanted) {
                bombPlanted = false;

                for (String index: iConstants.plantedBombs_ID) {
                    changeMap("bomb_planted_" + index, row, col);
                    try {
                        sleep(iConstants.UPDATE_BOMB);
                    } catch (InterruptedException e) {}
                }

                //explosion
                //explosion center
                new Thrower("explosion_center", iConstants.explosions_ID, iConstants.UPDATE_FIRE, row, col).start();
                checkIfExplosionKilled(row, col);

                //explosion down
                if (Server.map[row +1][col].getImage().equals("floor_1")) {
                    new Thrower("explosion_down", iConstants.explosions_ID, iConstants.UPDATE_FIRE, row +1, col).start();
                    checkIfExplosionKilled(row +1, col);
                }
                else if (Server.map[row +1][col].getImage().contains("block"))
                    new Thrower("block_is_on_fire", iConstants.blockOnFire_ID, iConstants.UPDATE_BLOCK, row +1, col).start();

                //explosion right
                if (Server.map[row][col +1].getImage().equals("floor_1")) {
                    new Thrower("explosion_right", iConstants.explosions_ID, iConstants.UPDATE_FIRE, row, col +1).start();
                    checkIfExplosionKilled(row, col +1);
                }
                else if (Server.map[row][col +1].getImage().contains("block"))
                    new Thrower("block_is_on_fire", iConstants.blockOnFire_ID, iConstants.UPDATE_BLOCK, row, col +1).start();

                //explosion up
                if (Server.map[row -1][col].getImage().equals("floor_1")) {
                    new Thrower("explosion_top", iConstants.explosions_ID, iConstants.UPDATE_FIRE, row -1, col).start();
                    checkIfExplosionKilled(row -1, col);
                }
                else if (Server.map[row -1][col].getImage().contains("block"))
                    new Thrower("block_is_on_fire", iConstants.blockOnFire_ID, iConstants.UPDATE_BLOCK, row -1, col).start();

                //explosion left
                if (Server.map[row][col -1].getImage().equals("floor_1")) {
                    new Thrower("explosion_left", iConstants.explosions_ID, iConstants.UPDATE_FIRE, row, col -1).start();
                    checkIfExplosionKilled(row, col -1);
                }
                else if (Server.map[row][col -1].getImage().contains("block"))
                    new Thrower("block_on_fire", iConstants.blockOnFire_ID, iConstants.UPDATE_BLOCK, row, col -1).start();

                Server.players[id].setNumberOfBombs(1);
            }
            try {sleep(0);} catch (InterruptedException e) {}
        }
    }

    public static void changeMap(String keyWord, int row, int col) {
        Server.map[row][col].setImage(keyWord);
        ClientManager.sendToAllClients("-1 board_update " + keyWord + " " + row + " " + col);
    }

    private void checkIfExplosionKilled(int rowSprite, int colSprite) {
        int rowPlayer, colPlayer, x, y;

        for (int id = 0; id < iConstants.AMOUNT_OF_PLAYERS; id++)
            if (Server.players[id].getIsAlive()) {
                x = Server.players[id].getXPos() + iConstants.WIDTH_PLAYER_IN_SPRITE_SHEET / 2;
                y = Server.players[id].getYPos() + 2 * iConstants.HEIGHT_PLAYER_IN_SPRITE_SHEET / 3;

                colPlayer = getColumnOfMap(x);
                rowPlayer = getLineOfMap(y);

                if (rowSprite == rowPlayer && colSprite == colPlayer) {
                    Server.players[id].setIsAlive(false);
                    ClientManager.sendToAllClients(id + " new_status player_is_dying");
                }
            }
    }

    private int getColumnOfMap(int x) {
        return x / iConstants.SIZE_SPRITE;
    }

    private int getLineOfMap(int y) {
        return y / iConstants.SIZE_SPRITE;
    }
}
