package transverse.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;
import transverse.render.Camera;
import transverse.render.Shader;
import transverse.render.Texture;
import transverse.io.Timer;
import transverse.io.Window;
import transverse.world.Tile;
import transverse.world.TileRenderer;
import transverse.world.World;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {

    // The window handle
    Window win = new Window();
    public void run() {
        System.out.println("Begining to Transverse");

        init();
        loop();



    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        win.setCallbacks();//TODO: Slightly freestyled

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        win.setSize(1920, 1080);
        win.setFullscreen(false);
        win.createWindow("Transverse");


    }

    private void loop() {
        GL.createCapabilities();

        Camera camera = new Camera(win.getWidth(), win.getHeight());
        glEnable(GL_TEXTURE_2D);

        TileRenderer tiles = new TileRenderer();
        glClearColor(1.0f, 1.0f, 0.0f, 0.0f);

        Shader shader = new Shader("shader");

        Texture tex = new Texture("lines.png"); //TODO: TUTORIAL STUFF

        World world = new World();

        world.setTile(Tile.test2Tile,0,0);
        world.setTile(Tile.test2Tile,63,63);

        camera.setPosition(new Vector3f(0,0,0));

        double frame_cap = 1.0/60.0;

        double frame_time = 0;
        int frames = 1;

        double time = Timer.getTime();

        double unprocessed = 0;

        while (!win.shouldClose()) {
            boolean can_render = false;
            double time_2 = Timer.getTime();
            double passed = time_2-time;
            unprocessed+=passed;
            frame_time += passed;

            time = time_2;

            while(unprocessed>=frame_cap){
                unprocessed-=frame_cap;
                can_render = true;
                if(win.getInput().isKeyDown(GLFW_KEY_ESCAPE)){
                    glfwSetWindowShouldClose(win.getWindow(), true);
                }
                if(win.getInput().isKeyDown(GLFW_KEY_A)){
                    camera.getPosition().sub(new Vector3f(-5,0,0));
                }
                if(win.getInput().isKeyDown(GLFW_KEY_D)){
                    camera.getPosition().sub(new Vector3f(5,0,0));
                }
                if(win.getInput().isKeyDown(GLFW_KEY_W)){
                    camera.getPosition().sub(new Vector3f(0,5,0));
                }if(win.getInput().isKeyDown(GLFW_KEY_S)){
                    camera.getPosition().sub(new Vector3f(0,-5,0));
                }
                world.correctCamera(camera, win);
                win.update();
                if(frame_time>=1.0){
                    frame_time=0;
                    System.out.println("FPS: " +frames);
                    frames = 0;
                }
            }
            if(can_render) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer


               /* shader.bind();
                shader.setUniform("sampler", 0);
                shader.setUniform("projection", camera.getProjection().mul(target));
                tex.bind(0);
                model.render();*/

               world.render(tiles, shader, camera);

                win.swapBuffers();
                frames++;
            }
            // Poll for window events. The key callback above will only be
            // invoked during this call.

        }
    }

    public static void main(String[] args) {
        new Main().run();
    }

}