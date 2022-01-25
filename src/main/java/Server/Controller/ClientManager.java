package Server.Controller;

import Commons.iConstants;
import Server.Connection.CoordinatesThrower;
import Server.Connection.MapUpdatesThrower;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientManager extends Thread {
    public static List<PrintStream> listOutClients = new ArrayList<>();

    private Socket clientSocket;
    private Scanner in;
    private PrintStream printStreamOut;
    private int id;

    private CoordinatesThrower ct;
    private MapUpdatesThrower mt;

    public ClientManager(Socket clientSocket, int id) {
        this.id = id;
        this.clientSocket = clientSocket;
        ct = new CoordinatesThrower(this.id);
        mt = new MapUpdatesThrower(this.id);

        ct.start();
        mt.start();

        try {
            System.out.print("Establishing connection with player " + this.id);
            this.in = new Scanner(clientSocket.getInputStream()); //receive from Client
            this.printStreamOut = new PrintStream(clientSocket.getOutputStream(), true); //send to Client
        } catch (IOException e) {
            System.out.println("Error " + e);
            System.exit(1);
        }
        System.out.println(" ok");

        listOutClients.add(printStreamOut);
        Server.players[id].setIsConnected(true);
        Server.players[id].setIsAlive(true);
        sendInitialSettings();

        //notify clients
        for (PrintStream outClient: listOutClients) {
            if (outClient != this.printStreamOut) {
                outClient.println(id + " player_has_joined");
            }
        }
    }

    public static void sendToAllClients(String outputLine) {
        for (PrintStream outClient : listOutClients)
            outClient.println(outputLine);
    }

    public void run() {
        while (in.hasNextLine()) {
            String str[] = in.nextLine().split(" ");

            if (str[0].equals("key_pressed") && Server.players[id].getIsAlive()) {
                ct.keyCodePressed(Integer.parseInt(str[1]));
            }
            else if (str[0].equals("key_released") && Server.players[id].getIsAlive()) {
                ct.keyCodeReleased(Integer.parseInt(str[1]));
            }
            else if (str[0].equals("space_pressed") && Server.players[id].getNumberOfBombs() > 0) {
                Server.players[id].setNumberOfBombs(0);
                mt.setBombPlanted(Integer.parseInt(str[1]), Integer.parseInt(str[2]));
            }
        }
        clientDisconnected();
    }

    private void sendInitialSettings() {
        printStreamOut.print(id);
        for (int i = 0; i < iConstants.ROW; i++) {
            for (int j = 0; j < iConstants.COLUMN; j++) {
                printStreamOut.print(" " + Server.map[i][j].getImage());
            }
        }

        for (int i = 0; i < iConstants.AMOUNT_OF_PLAYERS; i++) {
            printStreamOut.print(" " + Server.players[i].getIsAlive());
        }

        for (int i = 0; i < iConstants.AMOUNT_OF_PLAYERS; i++)
            printStreamOut.print(" " + Server.players[i].getXPos() + " " + Server.players[i].getYPos());
        printStreamOut.println();
    }

    private void clientDisconnected() {
        listOutClients.remove(printStreamOut);
        Server.players[id].setIsConnected(false);
        try {
            System.out.print("Player " + this.id + " died");
            in.close();
            printStreamOut.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e);
            System.exit(1);
        }
        System.out.println(" ok");
    }
}
