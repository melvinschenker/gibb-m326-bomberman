package Client.Connection;

import Client.Controller.Client;
import Client.View.Game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// listens to window while in focus
// checks changes and sends corresponding msg to receiver (since the manipulation will be handled there)
public class SenderHandler extends KeyAdapter {
    private int keyPressed;

    // new
    public boolean newKeyCodeDetected(int newCode) {
         if(newCode != keyPressed) {
             keyPressed = newCode;
             return true;
         } else {
             keyPressed = newCode;
             return false;
         }
    }

    // key pressed
    public void keyPressed(KeyEvent e)  {
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            Client.printStreamOut.println("space_pressed " + Game.playerIsYou.getX() + " " + Game.playerIsYou.getY());
        } else if(newKeyCodeDetected(e.getKeyCode())) {
            Client.printStreamOut.println("key_pressed " + e.getKeyCode());

        }
    }

    // key is released
    public void keyReleased(KeyEvent e) {
        Client.printStreamOut.println("key_released " + e.getKeyCode());
        keyPressed = -1;
    }
}
