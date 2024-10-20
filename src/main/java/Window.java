import org.joml.Math;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.glfw.GLFW.*;

public class Window {
    static int width, height;
    String title;
    long windowId;
    boolean isWindowSizeChange;


    public Window(int width_, int height_, String title) {
        width = width_;
        height = height_;
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

        GLFWErrorCallback.createPrint(System.err).set();

        GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE){
                glfwSetWindowShouldClose(windowId, true);
            }
        }).set(windowId);

        GLFWWindowSizeCallback.create((window, w, h) -> {
            isWindowSizeChange = true;
            width = w;
            height = h;
        }).set(windowId);

        GL.createCapabilities();
    }
    void swapBuffer(){
        glfwSwapBuffers(windowId);
    }

    void update(){
        if (isWindowSizeChange){
            System.out.println("window change");
            GL11.glViewport(0, 0, width, height);
            Camera.updateProjection();
            isWindowSizeChange = false;
        }
        GL20.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL20.glClearColor(0f, 0f, 0f, 0f);
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
