package draw.game.drawers;

import draw.ImageLoader;
import draw.drawer.GameDrawer;
import lib.math.Vec2D;
import map.MapGenerator;
import map.Tile;
import map.TypeTile;

import java.awt.*;
import java.util.HashMap;

public class DrawerMap implements Drawer {
    private GameDrawer drawer;

    public DrawerMap(GameDrawer drawer) {
        this.drawer = drawer;
        this.sizeTile = 128;
        this.sizeMap = MapGenerator.getSize();
        init(new Vec2D(128, 128));
    }

    private int sizeTile;
    private Vec2D sizeMap;
    private static HashMap<String, Image> tiles;

    private static void init(Vec2D sizeTile) {
        if (tiles == null) {
            tiles = new HashMap<>();
            tiles.put("00", ImageLoader.resize(sizeTile, "resource/tiles/myTiles/00.png"));
            tiles.put("01", ImageLoader.resize(sizeTile, "resource/tiles/myTiles/01.png"));
            tiles.put("02", ImageLoader.resize(sizeTile, "resource/tiles/myTiles/02.png"));
            tiles.put("03", ImageLoader.resize(sizeTile, "resource/tiles/myTiles/03.png"));
            tiles.put("04", ImageLoader.resize(sizeTile, "resource/tiles/myTiles/04.png"));
            tiles.put("05", ImageLoader.resize(sizeTile, "resource/tiles/myTiles/05.png"));
            tiles.put("06", ImageLoader.resize(sizeTile, "resource/tiles/myTiles/06.png"));
            tiles.put("07", ImageLoader.resize(sizeTile, "resource/tiles/myTiles/07.png"));
            tiles.put("08", ImageLoader.resize(sizeTile, "resource/tiles/myTiles/08.png"));
            tiles.put("09", ImageLoader.resize(sizeTile, "resource/tiles/myTiles/09.png"));
            tiles.put("10", ImageLoader.resize(sizeTile, "resource/tiles/myTiles/10.png"));
            tiles.put("11", ImageLoader.resize(sizeTile, "resource/tiles/myTiles/11.png"));
            tiles.put("12", ImageLoader.resize(sizeTile, "resource/tiles/myTiles/12.png"));

            tiles.put("water", ImageLoader.resize(sizeTile, "resource/tiles/water/00.png"));
        }
    }

    @Override
    public void draw() {
        for (int X = 1; X < sizeMap.getX(); X++) {
            for (int Y = 1; Y < sizeMap.getY(); Y++) {
                Vec2D pos = new Vec2D(X, Y).scalar(sizeTile);
                if (GameDrawer.inCamera(pos, sizeTile)) {
                    Vec2D posInCamera = pos.sub(new Vec2D(sizeTile, sizeTile));

                    Tile tile = MapGenerator.getTile(new Vec2D(X, Y));
                    if (tile.getType() == TypeTile.WATER) drawer.drawImage(posInCamera, tiles.get("water"));
                    else drawer.drawImage(posInCamera, tiles.get("00"));

                    if (tile.getSubTile() != null) {
                        drawer.drawImage(posInCamera, tiles.get(tile.getSubTile()));
                    }

//                    if (pos.getX() - sizeTile < Listener.globalMousePos.getX()) {
//                        if (pos.getY() - sizeTile < Listener.globalMousePos.getY()) {
//                            drawer.drawRect(posInCamera, new Vec2D(sizeTile, sizeTile), Color.BLACK);
//                        }
//                    }
                }
            }
        }
    }
}
