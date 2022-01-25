package Client.Model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class Animation {
    public static final String[] playersColor = {
            "black", "white", "green", "red"
    };

    public static Hashtable<String, Image> hashtable = new Hashtable<>();
    public static Hashtable<String, Integer> amountOfLooping = new Hashtable<>();

    /*
    *   set all keys for every single graphics (board elements) since they should be accessible through this defined keywords
    */
    private static final String[] setKeysOfBoard = {
            "background",
            "floor_1", "floor_2",
            "block", "block_is_on_fire_1", "block_is_on_fire_2", "block_is_on_fire_3", "block_is_on_fire_4", "block_is_on_fire_5", "block_is_on_fire_6",

            // bomb
            "bomb_1", "bomb_2",
            "bomb_red_1", "bomb_red_2",
            "bomb_planted_1", "bomb_planted_2", "bomb_planted_3",
            "bomb_planted_red_1", "bomb_planted_red_2", "bomb_planted_red_3",

            // explosion
            "explosion_center_1", "explosion_center_2", "explosion_center_3", "explosion_center_4", "explosion_center_5",
            "explosion_down_1", "explosion_down_2", "explosion_down_3", "explosion_down_4", "explosion_down_5",
            "explosion_top_1", "explosion_top_2", "explosion_top_3", "explosion_top_4", "explosion_top_5",
            "explosion_left_1", "explosion_left_2","explosion_left_3","explosion_left_4","explosion_left_5",
            "explosion_right_1", "explosion_right_2", "explosion_right_3", "explosion_right_4", "explosion_right_5",
            "explosion_horizontal_1", "explosion_horizontal_2", "explosion_horizontal_3", "explosion_horizontal_4", "explosion_horizontal_5",
            "explosion_vertical_1", "explosion_vertical_2", "explosion_vertical_3", "explosion_vertical_4", "explosion_vertical_5",

            // destruction
            "destruction_1",  "destruction_2",  "destruction_3",  "destruction_4",  "destruction_5",  "destruction_6",  "destruction_7",

            // wall
            "wall_center", "wall_down_left", "wall_down_right","wall_top_left", "wall_top_right"
    };

    /*
    *   set all keys for every single graphics (players) since they should be accessible through this defined keywords
    */
    public static final String[] setKeysOfPlayer ={
            // walking
            "player_walking_top_0", "player_walking_top_1", "player_walking_top_2", "player_walking_top_3", "player_walking_top_4", "player_walking_top_5", "player_walking_top_6", "player_walking_top_7",
            "player_walking_down_0",  "player_walking_down_1",  "player_walking_down_2",  "player_walking_down_3",  "player_walking_down_4", "player_walking_down_5", "player_walking_down_6", "player_walking_down_7",
            "player_walking_left_0",  "player_walking_left_1",  "player_walking_left_2",  "player_walking_left_3",  "player_walking_left_4",  "player_walking_left_5",  "player_walking_left_6",  "player_walking_left_7",
            "player_walking_right_0", "player_walking_right_1", "player_walking_right_2", "player_walking_right_3", "player_walking_right_4", "player_walking_right_5", "player_walking_right_6", "player_walking_right_7",

            // dead
            "player_is_dying_0",  "player_is_dying_1", "player_is_dying_2", "player_is_dying_3", "player_is_dying_4",

            // waiting
            "player_is_waiting_0",  "player_is_waiting_1", "player_is_waiting_2",  "player_is_waiting_3",  "player_is_waiting_4",
    };

    /*
     *  defines how many graphics of an 'element' exists -> loop-max
     */
    public static void howManyLoops() {
        amountOfLooping.put("player_walking_top", 8);
        amountOfLooping.put("player_walking_down", 8);
        amountOfLooping.put("player_walking_left", 8);
        amountOfLooping.put("player_walking_right", 8);
        amountOfLooping.put("player_is_dying", 5);
        amountOfLooping.put("player_is_waiting", 5);
    }

    /*
    *  loads game-graphics using for-loops and hashtable
    */
    public static void loadAnimation() {
        try{
            for(String key : setKeysOfBoard) {
                hashtable.put(key, ImageIO.read(new File("src/main/resources/images/map/" + key + ".png")));
            }
            for (String color: playersColor ) {
                for (String key: setKeysOfPlayer){
                  hashtable.put(color +"/"+key, ImageIO.read((new File("src/main/resources/images/player/"+color +"/" + key+".png"))));
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(1);
        }
        System.out.println("Animation worked!");
    }

    /*
    *   getter and setter to make them available withing the code
    */
    public static Hashtable<String, Image> getHashtable() {
        return hashtable;
    }
}