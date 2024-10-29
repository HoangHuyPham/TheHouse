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

            camera.setAspect((float) width / height);
            camera.setShouldProjectionUpdate(true);

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
                        camera.setShouldViewUpdate(true);
                    }

                    case GLFW_KEY_S -> {
                        camera.moveDown();
                        camera.setShouldViewUpdate(true);
                    }

                    case GLFW_KEY_D -> {
                        camera.moveRight();
                        camera.setShouldViewUpdate(true);
                    }

                    case GLFW_KEY_A -> {
                        camera.moveLeft();
                        camera.setShouldViewUpdate(true);
                    }

                    case GLFW_KEY_SPACE -> {
                        camera.flyUp();
                        camera.setShouldViewUpdate(true);
                    }

                    case GLFW_KEY_LEFT_SHIFT -> {
                        camera.flyDown();
                        camera.setShouldViewUpdate(true);
                    }
                }
        }).set(window.getWindowId());

        GLFWCursorPosCallback.create((w, xpos, ypos) -> {
            float xoffset = (float) xpos - window.getLastX();
            float yoffset = window.getLastY() - (float) ypos;

            if (window.isFirstCursor()){
                window.setFirstCursor(false);
                xoffset = 0;
                yoffset = 0;
            }

            window.setLastX((float) xpos);
            window.setLastY((float) ypos);

            camera.updateForwardByPointer(xoffset, yoffset);
            camera.setShouldViewUpdate(true);

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

            camera.setAspect((float) window.getWidth()/ window.getHeight());
            camera.setShouldProjectionUpdate(true);

        }).set(window.getWindowId());
    }



    public void destroy() {
        Callbacks.glfwFreeCallbacks(window.getWindowId());
    }
}
