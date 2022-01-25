package Server.Model;

public class PlayerData {
    private int xPos, yPos;
    private boolean isConnected, isAlive;
    private int numberOfBombs;

    public PlayerData(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.isConnected = false;
        this.isAlive = false;
        this.numberOfBombs = 1;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public boolean getIsConnected() {
        return isConnected;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public int getNumberOfBombs() {
        return numberOfBombs;
    }

    public void setXPos(int x) {
        this.xPos = x;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    public void setIsConnected(boolean connected) {
        this.isConnected = connected;
    }

    public void setIsAlive(boolean alive) {
        this.isAlive = alive;
    }

    public void setNumberOfBombs(int numberOfBombs) {
        this.numberOfBombs = numberOfBombs;
    }
}

