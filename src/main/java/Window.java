import org.lwjgl.glfw.Callbacks;

import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.glfw.GLFW.*;

public class Window {
    int width, height;
    String title;
    long windowId;


    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    void create(){
        if (!glfwInit()){
            System.err.println("GLFW failed to initialize");
            System.exit(1);
        }

        windowId = glfwCreateWindow(width, height, title, NULL, NULL);

        if (windowId == NULL){
            System.err.println("Failed to create the GLFW window");
            System.exit(1);
        }

        glfwMakeContextCurrent(windowId);
        glfwSwapInterval(1);
    }
    void render(){
        glfwSwapBuffers(windowId);
    }

    void update(){
        glfwPollEvents();
    }

    void destroy() {
        Callbacks.glfwFreeCallbacks(windowId);
        glfwDestroyWindow(windowId);
        glfwTerminate();
    }

    boolean shouldClose(){
        return glfwWindowShouldClose(windowId);
    }

    void showFPS(int fpsCount) {
        glfwSetWindowTitle(windowId, title.concat(" | "+"FPS: "+fpsCount));
    }
}
