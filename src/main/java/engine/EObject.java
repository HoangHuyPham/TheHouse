package engine;

import lombok.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@Builder
@Getter
public class EObject {
    private Vector3f position;
    @Builder.Default private Vector3f rotation = new Vector3f(0f, 0f, 0f);
    @Builder.Default private Vector3f scale = new Vector3f(1.0f, 1.0f, 1.0f);
    private Mesh mesh;

    public static class EObjectBuilder{
        public EObjectBuilder(){}
    }

    /**
     * A current transform matrix
     * @return Matrix4f
     */
    public Matrix4f getMatrix(){
        Matrix4f matrix = new Matrix4f();
        matrix.translate(position);
        matrix.rotateX(rotation.x);
        matrix.rotateY(rotation.y);
        matrix.rotateZ(rotation.z);
        matrix.scale(scale);
        return matrix;
    }
}
