package Commons;

public interface iConstants {
    int AMOUNT_OF_PLAYERS = 4; // max 4 players

    int ROW = 9, COLUMN = 9;
    int SIZE = 4; // pixel size

    int SIZE_SPRITE = 16 * SIZE;
    int WIDTH_PLAYER_IN_SPRITE_SHEET = 22 * SIZE;
    int HEIGHT_PLAYER_IN_SPRITE_SHEET = 33 * SIZE;

    // difference in pixels between board and player sprite
    int X_SPRITES = 3 * SIZE;
    int Y_SPRITES = 16 * SIZE;

    int UPDATE_BOMB = 90;
    int UPDATE_BLOCK = 100;
    int UPDATE_FIRE = 35;
    int UPDATE_STATUS_OF_PLAYER = 90;
    int UPDATE_COORDS = 27;

    String plantedBombs_ID[] = {
            "1", "2", "3", "2", "1", "2", "3", "2", "1", "2", "3", "2", "1", "2",
            "red_3", "red_2", "red_1", "red_2", "red_3", "red_2", "red_3", "red_2", "red_3", "red_2", "red_3"
    };
    String explosions_ID[] = {
            "1", "2", "3", "4", "5", "4", "3", "4", "5", "4", "3", "4", "5", "4", "3", "4", "5", "4", "3", "2", "1"
    };
    String blockOnFire_ID[] = {
            "1", "2", "1", "2", "1", "2", "3", "4", "5", "6"
    };
}
