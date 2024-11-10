package engine;

import lombok.*;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

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
    @Builder.Default
    private boolean isFirstCursor = true;
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
        GL20.glEnable(GL20.GL_DEPTH_TEST);
        GL20.glClearDepth(1);
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
        glfwPollEvents();
        GL30.glClear(GL30.GL_DEPTH_BUFFER_BIT | GL30.GL_COLOR_BUFFER_BIT | GL30.GL_STENCIL_BUFFER_BIT);
        GL20.glLoadIdentity();
    }

    void destroy() {
        glfwDestroyWindow(windowId);
        glfwTerminate();
    }

    void reshape(long id, int width, int height){
        this.width = width;
        this.height = height;
        updateViewPort();
    }

    boolean shouldClose(){
        return glfwWindowShouldClose(windowId);
    }

    public void shouldClose(boolean value){
        glfwSetWindowShouldClose(windowId, value);
    }

    void showFPS(int fpsCount) {
        glfwSetWindowTitle(windowId, title.concat(" | "+"FPS: "+fpsCount));
    }


    // Update delta time on per current frame
    void updateDeltaTime(){
        float currentFrame = (float)glfwGetTime();
        deltaTime = currentFrame - lastFrame;
        lastFrame = currentFrame;
    }
}
