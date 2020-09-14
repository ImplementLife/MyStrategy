package map;

public enum TypeTile {
    WATER(0),

    GRASS(20);

    //=======================================//

    public final byte Type;
    TypeTile(int type) {
        Type = (byte) type;
    }
}
