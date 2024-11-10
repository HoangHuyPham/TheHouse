package engine;

import lombok.Builder;
import org.lwjgl.glfw.*;

@Builder
public class CallbackManager {
    private Program program;

    public void create() {
        GLFWErrorCallback.createPrint(System.err).set();

        GLFWFramebufferSizeCallback reShapeCallback = GLFWFramebufferSizeCallback.create(program::onReshape);
        reShapeCallback.set(program.getWindow().getWindowId());

        GLFWScrollCallback scaleCallback = GLFWScrollCallback.create(program::onScale);
        scaleCallback.set(program.getWindow().getWindowId());
    }



    public void destroy() {
        Callbacks.glfwFreeCallbacks(program.getWindow().getWindowId());
    }
}
