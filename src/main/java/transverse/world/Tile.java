package transverse.world;

public class Tile {

    public static Tile tiles[] = new Tile[16]; //Max tiles in game

    public static final Tile textTile = new Tile((byte)0, "test");

    private byte id;

    private String texture;

    public Tile(byte id, String texture){
        this.id = id;
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
