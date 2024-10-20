import org.joml.Math;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

public class Camera {
    static Matrix4f projection = new Matrix4f().perspective(Math.toRadians(45), (float) Window.width / Window.height, 0.1f, 100.0f).lookAt(0,0,0, 0, 0, -100f, 0, 1, 0);
    static FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(16);
    static boolean isUpdating;

    public Camera() {
        projection.get(floatBuffer);
    }

    public static void updateProjection() {
        isUpdating = true;
        projection = new Matrix4f().perspective(Math.toRadians(45), (float) Window.width / Window.height, 0.1f, 100.0f).lookAt(0,0,0, 0, 0, -100f, 0, 1, 0);
        projection.get(floatBuffer);
    }

    public void rotateX(float angle) {
        projection.rotateX(Math.toRadians(angle)).get(floatBuffer);
    }

    public void rotateY(float angle) {
        projection.rotateY(Math.toRadians(angle)).get(floatBuffer);
    }

    public void rotateZ(float angle) {
        projection.rotateZ(Math.toRadians(angle)).get(floatBuffer);
    }

    public void translate(float x, float y, float z) {
        projection.translate(x, y, z).get(floatBuffer);
    }

    public void render() {
        GL20.glMatrixMode(GL20.GL_PROJECTION);
        GL20.glLoadIdentity();
        GL20.glLoadMatrixf(floatBuffer);
        GL20.glMatrixMode(GL20.GL_MODELVIEW);
    }
}
