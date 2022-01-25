package Server.Controller;

import Commons.Coordinate;
import Commons.iConstants;
import Server.Model.PlayerData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static PlayerData[] players = new PlayerData[iConstants.AMOUNT_OF_PLAYERS];
    public static Coordinate[][] map = new Coordinate[iConstants.ROW][iConstants.COLUMN];

    public Server(int port) {
        ServerSocket socket;

        setMap();
        setPlayerData();

        try {
            System.out.print("Waiting on port: " + port);
            socket = new ServerSocket(port);
            int id = 0;

            while (!connectionIsFull()) {
                if (!players[id].getIsConnected()) {
                    Socket clientSocket = socket.accept();
                    new ClientManager(clientSocket, id).start();
                }
                id = (id+1) % iConstants.AMOUNT_OF_PLAYERS;
            }

        } catch (IOException e) {
            System.out.println("Error: " + e);
            System.exit(1);
        }
    }

    private boolean connectionIsFull() {
        for (int i = 0; i < iConstants.AMOUNT_OF_PLAYERS; i++) {
            if (players[i].getIsConnected() == false) {
                return false;
            }
        }
        return true;
    }

    private void setMap() {
        // blocks
        for (int i = 0; i < iConstants.ROW; i++) {
            for (int j = 0; j < iConstants.COLUMN; j++) {
                map[i][j] = new Coordinate(iConstants.SIZE_SPRITE * j, iConstants.SIZE_SPRITE * i, "block");
            }
        }

        // border
        for (int i = 1; i < iConstants.COLUMN - 1; i++) {
            map[0][i].setImage("wall_center");
            map[iConstants.ROW - 1][i].setImage("wall_center");
        }

        for (int i = 1; i < iConstants.ROW - 1; i++) {
            map[i][0].setImage("wall_center");
            map[i][iConstants.COLUMN - 1].setImage("wall_center");
        }

        // corners
        map[0][0].setImage("wall_top_left");
        map[0][iConstants.COLUMN - 1].setImage("wall_top_right");
        map[iConstants.ROW - 1][0].setImage("wall_down_left");
        map[iConstants.ROW - 1][iConstants.COLUMN - 1].setImage("wall_down_right");

        // walls
        for (int i = 2; i < iConstants.ROW - 2; i++) {
            for (int j = 2; j < iConstants.COLUMN - 2; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    map[i][j].setImage("wall_center");
                }
            }
        }

        // floor
        map[1][1].setImage("floor_1");
        map[1][2].setImage("floor_1");
        map[2][1].setImage("floor_1");
        map[iConstants.ROW - 2][iConstants.COLUMN - 2].setImage("floor_1");
        map[iConstants.ROW - 3][iConstants.COLUMN - 2].setImage("floor_1");
        map[iConstants.ROW - 2][iConstants.COLUMN - 3].setImage("floor_1");
        map[iConstants.ROW - 2][1].setImage("floor_1");
        map[iConstants.ROW - 3][1].setImage("floor_1");
        map[iConstants.ROW - 2][2].setImage("floor_1");
        map[1][iConstants.COLUMN - 2].setImage("floor_1");
        map[2][iConstants.COLUMN - 2].setImage("floor_1");
        map[1][iConstants.COLUMN - 3].setImage("floor_1");
    }

    private void setPlayerData() {
        players[0] = new PlayerData(
                map[1][1].getX() - iConstants.X_SPRITES,
                map[1][1].getY() - iConstants.Y_SPRITES
        );

        players[1] = new PlayerData(
                map[iConstants.ROW - 2][iConstants.COLUMN - 2].getX() - iConstants.X_SPRITES,
                map[iConstants.ROW - 2][iConstants.COLUMN - 2].getY() - iConstants.Y_SPRITES
        );
        players[2] = new PlayerData(
                map[iConstants.ROW - 2][1].getX() - iConstants.X_SPRITES,
                map[iConstants.ROW - 2][1].getY() - iConstants.Y_SPRITES
        );
        players[3] = new PlayerData(
                map[1][iConstants.COLUMN - 2].getX() - iConstants.X_SPRITES,
                map[1][iConstants.COLUMN - 2].getY() - iConstants.Y_SPRITES
        );
    }
}
