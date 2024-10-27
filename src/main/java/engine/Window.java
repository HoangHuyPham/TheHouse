package engine;

import lombok.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

@Builder
@Getter
@Setter
public class Window {
    private int width, height;
    private String title;
    private long windowId;
    private CallbackManager callbackManager;
    private float lastX, lastY;

    public static float deltaTime, lastFrame;

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
        glfwSetInputMode(windowId, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
        updateViewPort();
    }

    void updateViewPort(){
        GL20.glViewport(0, 0, width, height);
    }

    void swapBuffer(){
        updateDeltaTime();
        glfwSwapBuffers(windowId);
    }

    void update(){
        GL20.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL20.glClearColor(0f, 0f, 0f, 0f);
        GL20.glLoadIdentity();
        glfwPollEvents();
    }

    void destroy() {
        glfwDestroyWindow(windowId);
        glfwTerminate();
    }

    boolean shouldClose(){
        return glfwWindowShouldClose(windowId);
    }

    void showFPS(int fpsCount) {
        glfwSetWindowTitle(windowId, title.concat(" | "+"FPS: "+fpsCount));
    }

    /**
     * update delta time on per current frame
     */
    void updateDeltaTime(){
        float currentFrame = (float)glfwGetTime();
        deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;
    }
}
