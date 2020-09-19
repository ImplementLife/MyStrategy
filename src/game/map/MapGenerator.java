package game.map;

import lib.math.Vec2D;

import java.io.*;
import java.util.ArrayList;

public final class MapGenerator /*implements Serializable*/ {
//    private static final long serialVersionUID = 1019931704136383438L;

    /**
     * Не статические поля нужны для сериализации
     */
    private ArrayList<ArrayList<Tile>> tileMap;

    private static long seed;
    private static Vec2D size;
    private static ArrayList<Vec2D> sub = new ArrayList<>();

    private MapGenerator(long seed, Vec2D size) {
        MapGenerator.seed = seed;
        MapGenerator.size = new Vec2D(size);
        tileMap = new ArrayList<>();
        for (ArrayList<Double> d : PerlinNoise2D.getHeightMap(size, seed)){
            ArrayList<Tile> tiles = new ArrayList<>();
            for (Double d2 : d) tiles.add(new Tile(d2));
            tileMap.add(tiles);
        }
    }

    private Map sub() {
        for (int X = 0; X <= size.getX(); X++) {
            for (int Y = 0; Y <= size.getY(); Y++) {
                if (X > 0 && Y > 0 && (X < size.getX() && Y < size.getY())) {
                    typeTile(new Vec2D(X, Y));
                }
            }
        }

        for (int i = 0; i < sub.size(); i++) {
            Vec2D vec = sub.get(i);
            if (vec.getX() > 0 && vec.getY() > 0 && (vec.getX() < size.getX() && vec.getY() < size.getY())) {
                typeTile(vec);
            }
            sub.remove(i);
            i--;
        }

        for (ArrayList<Tile> d : tileMap) for (Tile d2 : d) d2.resetTypeTile();

        return new Map(tileMap);
    }

    private static void typeTile(Vec2D pos) {
        TypeTile type[] = new TypeTile[9];
        Vec2D tiles[] = new Vec2D[9];

        Tile tile = map.getTile(pos);
        tile.removeSubTile();
        type[4] = tile.getType();

        int i = 0;
        for (int X = pos.getIntX()-1; X <= pos.getIntX() + 1; X++) {
            for (int Y = pos.getIntY()-1; Y <= pos.getIntY() + 1; Y++) {
                if ((X != pos.getIntX() || Y != pos.getIntY())) {
                    tiles[i] = new Vec2D(X, Y);
                    type[i] = map.getType(tiles[i]);
                }
                i++;
            }
        }

        byte b = 0;
        String stbTile = "00";
        if (tile.getType().Type > type[1].Type) {
            tile.setTypeTile(type[1]);
            stbTile = "04";
            b++;
        }
        if (tile.getType().Type > type[3].Type) {
            tile.setTypeTile(type[3]);
            stbTile = "01";
            b++;
        }
        if (tile.getType().Type > type[5].Type) {
            tile.setTypeTile(type[5]);
            stbTile = "03";
            b++;
        }
        if (tile.getType().Type > type[7].Type) {
            tile.setTypeTile(type[7]);
            stbTile = "02";
            b++;
        }

        if (tile.getType().Type > type[1].Type && tile.getType().Type > type[3].Type) stbTile = "05";
        if (tile.getType().Type > type[3].Type && tile.getType().Type > type[7].Type) stbTile = "06";
        if (tile.getType().Type > type[5].Type && tile.getType().Type > type[1].Type) stbTile = "08";
        if (tile.getType().Type > type[7].Type && tile.getType().Type > type[5].Type) stbTile = "07";

        if (stbTile.equals("00")) {
            if (tile.getType().Type > type[0].Type) {
                tile.setTypeTile(type[0]);
                stbTile = "12";
            }
            if (tile.getType().Type > type[6].Type) {
                tile.setTypeTile(type[6]);
                stbTile = "11";
            }
            if (tile.getType().Type > type[2].Type) {
                tile.setTypeTile(type[2]);
                stbTile = "10";
            }
            if (tile.getType().Type > type[8].Type) {
                tile.setTypeTile(type[8]);
                stbTile = "09";
            }
        }

        if (b >= 3) {
            for (Vec2D vec : tiles) if (vec != null) sub.add(vec);
            stbTile = "";
            tile.resetTypeTile();
        }

        if (!stbTile.equals("00")) tile.setSubTile(stbTile);
    }

    //======================== Другие методы ========================//

    public static MapGenerator map;

    /**
     * Метод для создания новой карты
     */
    public static Map newMap(Vec2D size, long seed) {
        map = new MapGenerator(seed, size);
        return map.sub();
    }

    public static Tile getTile(Vec2D pos) {
        return map.tileMap.get(pos.getIntX()).get(pos.getIntY());
    }
    public static TypeTile getType(Vec2D pos) {
        return map.tileMap.get(pos.getIntX()).get(pos.getIntY()).getType();
    }
    public static double getHeight(Vec2D pos) {
        return map.tileMap.get(pos.getIntX()).get(pos.getIntY()).getHeight();
    }

    public static Vec2D getSize() {
        return size;
    }

    /**
     * Метод для сохранения карты в файл
     */
    public static void save(String path) throws IOException {
        ObjectOutputStream saveObject = new ObjectOutputStream(new FileOutputStream(path));
        saveObject.writeObject(map);
        saveObject.close();
    }

    /**
     * Метод для загрузки карты из файла
     */
    public static void load(String path) throws IOException, ClassNotFoundException {
        ObjectInputStream loadObject = new ObjectInputStream(new FileInputStream(path));
        map = (MapGenerator) loadObject.readObject();
        loadObject.close();
    }
}
