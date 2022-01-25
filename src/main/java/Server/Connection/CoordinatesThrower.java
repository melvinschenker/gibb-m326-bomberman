package Server.Connection;

import Commons.iConstants;
import Server.Controller.ClientManager;
import Server.Controller.Server;

import java.awt.event.KeyEvent;

public class CoordinatesThrower extends Thread {
    private boolean up, right, left, down;
    private int id;

    public CoordinatesThrower(int id) {
        this.id = id;
        up = false;
        down = false;
        right = false;
        left = false;
    }

    public void run() {
        int newX = Server.players[id].getXPos();
        int newY = Server.players[id].getYPos();

        while (true) {
            if (up || down || right || left) {
                if (up) {
                    newY = Server.players[id].getYPos() - iConstants.SIZE;
                }
                else if (down) {
                    newY = Server.players[id].getYPos() + iConstants.SIZE;
                }
                else if (right) {
                    newX = Server.players[id].getXPos() + iConstants.SIZE;
                }
                else if (left) {
                    newX = Server.players[id].getXPos() - iConstants.SIZE;
                }

                if (Server.players[id].getIsAlive() && (playerDies(newX, newY) || coordinateIsValid(newX, newY))) {
                    ClientManager.sendToAllClients(id + " new_coordinates " + newX + " " + newY);

                    Server.players[id].setXPos(newX);
                    Server.players[id].setYPos(newY);
                } else {
                    newX = Server.players[id].getXPos();
                    newY = Server.players[id].getYPos();
                }
                try {
                    sleep(iConstants.UPDATE_COORDS);
                } catch (InterruptedException e) {}
            }

            try {sleep(0);} catch (InterruptedException e) {}
        }
    }

    public void keyCodePressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W:
                up = true;
                down = false;
                right = false;
                left = false;
                ClientManager.sendToAllClients(this.id + " new_status player_walking_top");
                break;
            case KeyEvent.VK_S:
                down = true;
                up = false;
                right = false;
                left = false;
                ClientManager.sendToAllClients(this.id + " new_status player_walking_down");
                break;
            case KeyEvent.VK_D:
                right = true;
                up = false;
                down = false;
                left = false;
                ClientManager.sendToAllClients(this.id + " new_status player_walking_right");
                break;
            case KeyEvent.VK_A:
                left = true;
                up = false;
                down = false;
                right = false;
                ClientManager.sendToAllClients(this.id + " new_status player_walking_left");
                break;
        }
    }

    public void keyCodeReleased(int keyCode) {
        if (keyCode != KeyEvent.VK_W && keyCode != KeyEvent.VK_S && keyCode != KeyEvent.VK_D && keyCode != KeyEvent.VK_A) {
            return;
        }

        ClientManager.sendToAllClients(this.id + " stop_status");
        switch (keyCode) {
            case KeyEvent.VK_W:
                up = false;
                break;
            case KeyEvent.VK_S:
                down = false;
                break;
            case KeyEvent.VK_D:
                right = false;
                break;
            case KeyEvent.VK_A:
                left = false;
                break;
        }
    }

    private boolean playerDies(int newX, int newY) {
        int xBody = newX + iConstants.WIDTH_PLAYER_IN_SPRITE_SHEET/2;
        int yBody = newY + 2*iConstants.HEIGHT_PLAYER_IN_SPRITE_SHEET/3;

        if (Server.map[getLineOfMap(yBody)][getColumnOfMap(xBody)].getImage().contains("explosion")) {
            Server.players[id].setIsAlive(false);
            ClientManager.sendToAllClients(id + " new_status player_is_dying");
            return true;
        }
        return false;
    }

    private boolean coordinateIsValid(int newX, int newY) {
        int[] x = new int[4];
        int[] y = new int[4];
        int[] col = new int[4];
        int[] row = new int[4];

        //new coordinate

        //up left corner
        x[0] = iConstants.X_SPRITES + newX + iConstants.SIZE;
        y[0] = iConstants.Y_SPRITES + newY + iConstants.SIZE;
        //up right corner
        x[1] = iConstants.X_SPRITES + newX + iConstants.SIZE_SPRITE - 2 * iConstants.SIZE;
        y[1] = iConstants.Y_SPRITES + newY + iConstants.SIZE;
        //down left corner
        x[2] = iConstants.X_SPRITES + newX + iConstants.SIZE;
        y[2] = iConstants.Y_SPRITES + newY + iConstants.SIZE_SPRITE - 2 * iConstants.SIZE;
        //down right corner
        x[3] = iConstants.X_SPRITES + newX + iConstants.SIZE_SPRITE - 2 * iConstants.SIZE;
        y[3] = iConstants.Y_SPRITES + newY + iConstants.SIZE_SPRITE - 2 * iConstants.SIZE;

        for (int i = 0; i < 4; i++) {
            col[i] = getColumnOfMap(x[i]);
            row[i] = getLineOfMap(y[i]);
        }

        //valid coordinate
        if ((Server.map[row[0]][col[0]].getImage().equals("floor_1") || Server.map[row[0]][col[0]].getImage().contains("explosion")) &&
                (Server.map[row[1]][col[1]].getImage().equals("floor_1") || Server.map[row[1]][col[1]].getImage().contains("explosion")) &&
                (Server.map[row[2]][col[2]].getImage().equals("floor_1") || Server.map[row[2]][col[2]].getImage().contains("explosion")) &&
                (Server.map[row[3]][col[3]].getImage().equals("floor_1") || Server.map[row[3]][col[3]].getImage().contains("explosion"))) {
            return true;
        }

        //wall
        if ((Server.map[row[0]][col[0]].getImage().contains("block") || Server.map[row[0]][col[0]].getImage().contains("wall")) ||
                (Server.map[row[1]][col[1]].getImage().contains("block") || Server.map[row[1]][col[1]].getImage().contains("wall")) ||
                (Server.map[row[2]][col[2]].getImage().contains("block") || Server.map[row[2]][col[2]].getImage().contains("wall")) ||
                (Server.map[row[3]][col[3]].getImage().contains("block") || Server.map[row[3]][col[3]].getImage().contains("wall"))) {
            return false;
        }
        //previous coordinate for bomb

        //up left corner
        x[0] = iConstants.X_SPRITES + Server.players[id].getXPos() + iConstants.SIZE;
        y[0] = iConstants.Y_SPRITES + Server.players[id].getYPos() + iConstants.SIZE;
        //up right corner
        x[1] = iConstants.X_SPRITES + Server.players[id].getXPos() + iConstants.SIZE_SPRITE - 2 * iConstants.SIZE;
        y[1] = iConstants.Y_SPRITES + Server.players[id].getYPos() + iConstants.SIZE;
        //down left corner
        x[2] = iConstants.X_SPRITES + Server.players[id].getXPos() + iConstants.SIZE;
        y[2] = iConstants.Y_SPRITES + Server.players[id].getYPos() + iConstants.SIZE_SPRITE - 2 * iConstants.SIZE;
        //down right corner
        x[3] = iConstants.X_SPRITES + Server.players[id].getXPos() + iConstants.SIZE_SPRITE - 2 * iConstants.SIZE;
        y[3] = iConstants.Y_SPRITES + Server.players[id].getYPos() + iConstants.SIZE_SPRITE - 2 * iConstants.SIZE;

        for (int i = 0; i < 4; i++) {
            col[i] = getColumnOfMap(x[i]);
            row[i] = getLineOfMap(y[i]);
        }

        //bomb valid
        if (Server.map[row[0]][col[0]].getImage().contains("bomb_planted") ||
                Server.map[row[1]][col[1]].getImage().contains("bomb_planted") ||
                Server.map[row[2]][col[2]].getImage().contains("bomb_planted") ||
                Server.map[row[3]][col[3]].getImage().contains("bomb_planted")) {
            return true;
        }

        return false;
    }

    private int getColumnOfMap(int x) {
        return x/iConstants.SIZE_SPRITE;
    }

    private int getLineOfMap(int y) {
        return y/iConstants.SIZE_SPRITE;
    }
}
