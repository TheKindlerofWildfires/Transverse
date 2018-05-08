package transverse;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;

public class Window {
    private long window;

    private int width, height;

    private boolean fullscreen;

    public static void setCallbacks(){
        glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
    }
    public Window(){
        setSize(640, 480);

    }
    public void createWindow(String title){
        window = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor():0,0);
        if (window == 0){
            throw new IllegalStateException("Failed to create window!");
        }
        if(!fullscreen) {
            GLFWVidMode vid = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window, (vid.width() - width) / 2, (vid.height() - height) / 2);
            glfwShowWindow(window);


        }
        glfwMakeContextCurrent(window);

    }
    public void setSize(int width, int height){
        this.width = width;
        this.height = height;

    }

    public boolean shouldClose(){
        return glfwWindowShouldClose(window);//TODO: Freestyled here a little
    }
    public void swapBuffers(){
        glfwSwapBuffers(window);
    }
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    public boolean getFullscreen(){return fullscreen;}
    public void setFullscreen(boolean fullscreen){this.fullscreen = fullscreen;}
    public long getWindow(){
        return window;
    }

}
