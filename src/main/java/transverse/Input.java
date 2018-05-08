package transverse;

import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetMouseButton;

public class Input {
    private long window;

    public Input(long window){
        this.window = window;
    }

    public boolean isKeyDown(int key){
        return glfwGetKey(window, key) == 1;
    }

    public boolean isMouseButtonDown(int key){
        return glfwGetMouseButton(window, key) == 1;
    }
}
