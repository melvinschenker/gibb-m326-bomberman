package Server.Connection;

import Server.Connection.MapUpdatesThrower;

public class Thrower extends Thread {
    private String keyWord, index[];
    private int row, col;
    private int delay;

    public Thrower(String keyWord, String index[], int delay, int row, int col) {
        this.keyWord = keyWord;
        this.index = index;
        this.delay = delay;
        this.row = row;
        this.col = col;
    }

    public void run() {
        for (String i : index) {
            MapUpdatesThrower.changeMap(keyWord + "_" + i, row, col);
            try {
                sleep(delay);
            } catch (InterruptedException e) {}
        }
        //map after explosion
        MapUpdatesThrower.changeMap("floor_1", row, col);
    }
}
