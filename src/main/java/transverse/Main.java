package transverse;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

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
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable
        win.createWindow("Transverse");


    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        Camera camera = new Camera(640, 480);
        glEnable(GL_TEXTURE_2D);
        // Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);



        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        float[] vertices = new float[]{
                -.5f, .5f, 0,
                .5f, .5f, 0,
                .5f, -.5f, 0,
                -.5f, -.5f, 0,
        };
        float[] texture = new float[]{
                0, 0,
                1, 0,
                1, 1,
                0, 1

        };
        int[] indices = new int[]{
                0, 1, 2, 2, 3, 0
        };
        Model model = new Model(vertices, texture, indices);
        Shader shader = new Shader("shader");


        Texture tex = new Texture("/lines.png"); //TODO: TUTORIAL STUFF



        Matrix4f scale = new Matrix4f().translate(100,0,0).scale(32);
        Matrix4f target = new Matrix4f();

        camera.setPosition(new Vector3f(-100,0,0));

        double frame_cap = 1.0/60.0;

        double frame_time = 0;
        int frames = 1;

        double time = Timer.getTime();

        double unprocessed = 0;
        float x = 0f; //TODO: TUTORIAL STUFF

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
                target = scale;
                /*glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
                    if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                        glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
                });*/
                glfwPollEvents();
                if(frame_time>=1.0){
                    frame_time=0;
                    System.out.println("FPS: " +frames);
                    frames = 0;
                }
            }
            if(can_render) {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer


                shader.bind();
                shader.setUniform("sampler", 0);
                shader.setUniform("projection", camera.getProjection().mul(target));
                tex.bind(0);
                model.render();

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