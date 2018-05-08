package transverse.world;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import transverse.render.Camera;
import transverse.render.Shader;

public class World {
    private byte[] tiles;
    private int width;
    private int height;
    private Matrix4f world;

    public World(){
        width = 16;
        height = 16;

        tiles = new byte[width *height];
        world = new Matrix4f().setTranslation(new Vector3f(0));
        world.scale(16);//TODO: Sets tile size, might be diff from tutorial

    }
    public void render(TileRenderer render, Shader shader, Camera camera){
        for (int i= 0; i<height; i++){
            for (int j= 0; j<width; j++){
                render.renderTile(tiles[j+i*width],j, -i, shader, world, camera );
            }
        }
    }
}
