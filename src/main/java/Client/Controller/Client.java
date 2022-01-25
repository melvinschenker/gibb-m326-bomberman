package Client.Controller;

import Client.Connection.ReceiverHandler;
import Commons.Coordinate;
import Commons.iConstants;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private Socket socket;
    public static PrintStream printStreamOut;
    public static Scanner in;
    public static int id;

    public static Coordinate[][] map = new Coordinate[iConstants.ROW][iConstants.COLUMN];

    public static Coordinate[] spawn = new Coordinate[iConstants.AMOUNT_OF_PLAYERS];
    public static boolean[] isAlive = new boolean[iConstants.AMOUNT_OF_PLAYERS];

    public Client(String host, int port) {
        try {
            System.out.print("Establishing connection with server ");
            this.socket = new Socket(host, port);
            printStreamOut = new PrintStream(socket.getOutputStream(), true);  //send to server
            in = new Scanner(socket.getInputStream()); //receive from server
        }
        catch (UnknownHostException exception) {
            System.out.println("Error: " + exception);
            System.exit(1);
        }
        catch (IOException exception) {
            System.out.println("Error: " + exception);
            System.exit(1);
        }
        System.out.println("ok");

        receiveInitialSettings();
        new ReceiverHandler().start();
    }

    // initial state of the game
    private void receiveInitialSettings() {
        id = in.nextInt();

        // board
        for (int i = 0; i < iConstants.ROW; i++) {
            for (int j = 0; j < iConstants.COLUMN; j++) {
                map[i][j] = new Coordinate(iConstants.SIZE_SPRITE * j, iConstants.SIZE_SPRITE * i, in.next());
            }
        }

        // player alive or dead
        for (int i = 0; i < iConstants.AMOUNT_OF_PLAYERS; i++) {
            Client.isAlive[i] = in.nextBoolean();
        }

        // coordinates where players are starting the game
        for (int i = 0; i < iConstants.AMOUNT_OF_PLAYERS; i++) {
            Client.spawn[i] = new Coordinate(in.nextInt(), in.nextInt());
        }
    }
}
