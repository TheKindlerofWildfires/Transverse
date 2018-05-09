package transverse.world;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import transverse.io.Window;
import transverse.render.Camera;
import transverse.render.Shader;

import static org.lwjgl.opengl.GL11.glDeleteTextures;

public class World {
    private final int viewX = 120;
    private final int viewY = 60;
    private byte[] tiles;
    private int width;
    private int height;
    private int scale = 16;
    private Matrix4f world;

    public World(){
        width = 120; //Size stuff
        height = 120;

        tiles = new byte[width *height];
        world = new Matrix4f().setTranslation(new Vector3f(0));
        world.scale(scale);//TODO: Sets tile size, might be diff from tutorial

    }
    public void render(TileRenderer render, Shader shader, Camera camera, Window window){
        int posX = (int) camera.getPosition().x / (scale * 2);
        int posY = (int) camera.getPosition().y / (scale * 2);

        for (int i = 0; i < viewX; i++) {
            for (int j = 0; j < viewY; j++) {
                Tile t = getTile(i - posX - (viewX / 2) + 1, j + posY - (viewY / 2));
                if (t != null) render.renderTile(t, i - posX - (viewX / 2) + 1, -j - posY + (viewY / 2), shader, world, camera);
            }
        }
    }


    public void correctCamera(Camera camera, Window window){
        //TODO:There is a bug in here, the camera cuts off part of the world
        Vector3f pos = camera.getPosition();

        int w = -width * scale * 2;
        int h = height * scale * 2;

        if (pos.x > -(window.getWidth() / 2) + scale) pos.x = -(window.getWidth() / 2) + scale;
        if (pos.x < w + (window.getWidth() / 2) + scale) pos.x = w + (window.getWidth() / 2) + scale;

        if (pos.y < (window.getHeight() / 2) - scale) pos.y = (window.getHeight() / 2) - scale;
        if (pos.y > h - (window.getHeight() / 2) - scale) pos.y = h - (window.getHeight() / 2) - scale;
    }
    public void setTile(Tile tile, int x, int y){
        tiles[x +y*width] = tile.getId();
    }

    public Tile getTile(int x, int y){
        try{
            return Tile.tiles[tiles[x +y*width] ];
        }catch(ArrayIndexOutOfBoundsException e){
            return null;
        }
    }
}
