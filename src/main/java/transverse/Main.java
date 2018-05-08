package transverse;

import org.joml.Matrix4f;
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
    private long window;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
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

        // Create the window
        window = glfwCreateWindow(640, 480, "Transverse", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
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


        Matrix4f projection = new Matrix4f().ortho2D(-640/2, 640/2, -480/2, 480/2);
        Matrix4f scale = new Matrix4f().scale(32);
        Matrix4f target = new Matrix4f();

        projection.mul(scale, target);
        target = target.translate(1,0,0);

        model.render();
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            float x = 0f; //TODO: TUTORIAL STUFF

            if (GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_1) == GL11.GL_TRUE) {
                x += 1;//TODO: TUTORIAL STUFF
            }
            shader.bind();
            shader.setUniform("sampler", 0);
            shader.setUniform("projection", target);
            tex.bind(0);
            model.render();

            /*

            GL11.glBegin(GL11.GL_QUADS);
            glTexCoord2f(0,0);
            GL11.glVertex2f(-0.5f, 0.5f + x);
            glTexCoord2f(1,0);
            GL11.glVertex2f(0.5f, 0.5f + x);
            glTexCoord2f(1,1);
            GL11.glVertex2f(0.5f, -0.5f + x);
            glTexCoord2f(0,1);
            GL11.glVertex2f(-0.5f, -0.5f + x);
            GL11.glEnd();
            */
            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }

}