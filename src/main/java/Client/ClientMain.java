package Client;

import Client.Controller.Client;
import Client.View.Window;

public class ClientMain {
    public static void main(String[] args) {
        new Client("127.0.0.1", 8383);
        new Window();
    }
}
