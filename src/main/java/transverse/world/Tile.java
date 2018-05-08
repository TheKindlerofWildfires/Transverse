package transverse.world;

public class Tile {

    public static Tile tiles[] = new Tile[16]; //Max tiles in game
    public static byte NOT = 0;

    public static final Tile testTile = new Tile("test");
    public static final Tile test2Tile = new Tile("check");

    private byte id;

    private String texture;

    public Tile(String texture){
        this.id = NOT;
        NOT++;
        this.texture = texture;
        if(tiles[id]!=null){
            throw new IllegalStateException("Tiles at : " + id + " is in use");
        }
        tiles[id] = this;//This doesn't look right but it is

    }
    public byte getId() {
        return id;
    }
    public String getTexture() {
        return texture;
    }
    public void setId(byte id) {
        this.id = id;
    }
    public void setTexture(String texture) {
        this.texture = texture;
    }
}
