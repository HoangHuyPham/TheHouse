import org.lwjgl.glfw.Callbacks;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

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
        GL.createCapabilities();

    }
    void swapBuffer(){
        glfwSwapBuffers(windowId);
    }

    void update(){
        GL20.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL20.glClearColor(1f, 0f, 0f, 0f);
        GL20.glLoadIdentity();
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
