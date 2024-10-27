package engine;

import lombok.Builder;
import org.joml.Math;
import org.joml.Vector3f;
import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;

@Builder
public class CallbackManager {
    private Window window;
    private ECamera camera;

    public void create() {
        GLFWErrorCallback.createPrint(System.err).set();

        GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE) {
                glfwSetWindowShouldClose(window, true);
            }
        }).set(window.getWindowId());

        GLFWFramebufferSizeCallback.create((window, width, height) -> {
            this.window.setWidth(width);
            this.window.setHeight(height);
            this.window.updateViewPort();
            camera.updateProjectionMatrix((float) width / height);
            System.out.println("Window size has changed");
        }).set(window.getWindowId());

        GLFWKeyCallback.create((window, key, scancode, action, mods) -> {

            if (action == GLFW_RELEASE || action == GLFW_REPEAT)
                switch (key) {
                    case GLFW_KEY_ESCAPE -> {
                        glfwSetWindowShouldClose(window, true);
                    }

                    case GLFW_KEY_W -> {
                        camera.moveUp();
                    }

                    case GLFW_KEY_S -> {
                        camera.moveDown();
                    }

                    case GLFW_KEY_D -> {
                        camera.moveRight();
                    }

                    case GLFW_KEY_A -> {
                        camera.moveLeft();
                    }
                }
        }).set(window.getWindowId());

        GLFWCursorPosCallback.create((w, xpos, ypos) -> {
            float xoffset = (float) xpos - window.getLastX();
            float yoffset = window.getLastY() - (float) ypos;

            if (window.isFirstCursor()){
                window.setFirstCursor(false);
                window.setLastX((float) xpos);
                window.setLastY((float) ypos);
                xoffset = 0;
                yoffset = 0;
            }

            window.setLastX((float) xpos);
            window.setLastY((float) ypos);

            camera.updateForwardByPointer(xoffset, yoffset);
        }).set(window.getWindowId());

        GLFWScrollCallback.create((w, deltaX, deltaY) -> {
            float fov = camera.getFov();
            fov -= (float) deltaY;

            if (fov < 1.0f)
                camera.setFov(1.0f);
            else if (fov > 45.0f)
                camera.setFov(45.0f);
            else
                camera.setFov(fov);

            camera.updateProjectionMatrix((float) window.getWidth()/ window.getHeight());

        }).set(window.getWindowId());
    }



    public void destroy() {
        Callbacks.glfwFreeCallbacks(window.getWindowId());
    }
}
