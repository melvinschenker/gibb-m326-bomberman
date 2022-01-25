package Client.View;

import Client.Model.Animation;
import Client.Connection.SenderHandler;
import Commons.iConstants;

import javax.swing.*;

public class Window extends JFrame {
    private static final long serialVersionUID = 1L;

    public Window() {
        Animation.loadAnimation();
        Animation.howManyLoops();

        add(new Game(iConstants.COLUMN * iConstants.SIZE_SPRITE, iConstants.ROW * iConstants.SIZE_SPRITE));
        setTitle("bomberman");
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        addKeyListener(new SenderHandler());
    }
}