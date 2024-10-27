package engine;

import lombok.*;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@Builder
@Getter
public class EObject {
    private Vector3f position;
    @Builder.Default
    private Vector3f rotation = new Vector3f(0f, 0f, 0f);
    @Builder.Default
    private Vector3f scale = new Vector3f(1.0f, 1.0f, 1.0f);
    @Builder.Default private Matrix4f matrix = new Matrix4f().identity();
    @Builder.Default private boolean shouldUpdate = true;
    private Mesh mesh;


    public static class EObjectBuilder {
        public EObjectBuilder() {
        }
    }

    /**
     * If {@systemProperty shouldUpdate} is not set to {@code true} before call {@code getModelMatrix()}, it will return cache Matrix
     * @return {@link Matrix4f}
     */
    public Matrix4f getModelMatrix() {
        if (shouldUpdate){
            synchronized (EObject.class){
                matrix.identity();
                matrix.translate(position);
                matrix.scale(scale);
                matrix.rotateX(rotation.x).rotateY(rotation.y).rotateZ(rotation.z);
                shouldUpdate = false;
            }
        }
        return matrix;
    }
}
