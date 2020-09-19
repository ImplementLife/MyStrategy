package game.map;

import java.util.ArrayList;

public class Map {
//    private static final long serialVersionUID = 1019931704136383438L;
    private ArrayList<ArrayList<Tile>> tileMap;

    Map(ArrayList<ArrayList<Tile>> tileMap) {
        this.tileMap = tileMap;
    }

}
