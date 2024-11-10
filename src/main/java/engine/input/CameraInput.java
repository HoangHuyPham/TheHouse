package engine.input;

import engine.camera.Camera;
import lombok.experimental.SuperBuilder;
import org.lwjgl.system.MemoryUtil;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;

@SuperBuilder
public class CameraInput extends Input {
    private Camera camera;
    private final DoubleBuffer xPos = MemoryUtil.memAllocDouble(1), yPos = MemoryUtil.memAllocDouble(1);
    private final FloatBuffer xScale = MemoryUtil.memAllocFloat(1), yScale = MemoryUtil.memAllocFloat(1);


    @Override
    public void handle() {
        movement();
        rotation();
    }

    private void rotation(){
        glfwGetCursorPos(window.getWindowId(), xPos, yPos);
        float xOffset = (float) xPos.get(0) - window.getLastX();
        float yOffset = window.getLastY() - (float) yPos.get(0);

        if (window.isFirstCursor()){
            window.setFirstCursor(false);
            xOffset = 0;
            yOffset = 0;
        }

        window.setLastX((float) xPos.get(0));
        window.setLastY((float) yPos.get(0));

        camera.setPitch((camera.getPitch() + yOffset * camera.getSensitivity()) % 360);
        camera.setYaw((camera.getYaw() + xOffset * camera.getSensitivity()) % 360);

        // Limit euler angle
        if(camera.getPitch() >= 89.0f)
            camera.setPitch(89.0f);
        if(camera.getPitch() < -89.0f)
            camera.setPitch(-89.0f);
        camera.updateDirection();

        updateCamera();
    }

    private void movement() {
        if (glfwGetKey(window.getWindowId(), GLFW_KEY_ESCAPE) == GLFW_PRESS) {
            window.shouldClose(true);
        }

        if (glfwGetKey(window.getWindowId(), GLFW_KEY_W) == GLFW_PRESS) {
            camera.moveUp();
            updateCamera();
        }

        if (glfwGetKey(window.getWindowId(), GLFW_KEY_A) == GLFW_PRESS) {
            camera.moveLeft();
            updateCamera();
        }

        if (glfwGetKey(window.getWindowId(), GLFW_KEY_D) == GLFW_PRESS) {
            camera.moveRight();
            updateCamera();
        }

        if (glfwGetKey(window.getWindowId(), GLFW_KEY_S) == GLFW_PRESS) {
            camera.moveDown();
            updateCamera();
        }

        if (glfwGetKey(window.getWindowId(), GLFW_KEY_SPACE) == GLFW_PRESS) {
            camera.flyUp();
            updateCamera();
        }

        if (glfwGetKey(window.getWindowId(), GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
            camera.flyDown();
            updateCamera();
        }

    }

    @Override
    public void destroy() {
        MemoryUtil.memFree(xPos);
        MemoryUtil.memFree(yPos);
    }

    private void updateCamera() {
        camera.setShouldViewUpdate(true);
    }
}
