package engine;

import lombok.*;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@Builder
@Getter
public class EObject{
    private Vector3f position;
    @Builder.Default private Vector3f rotation = new Vector3f(0f, 0f, 0f);
    @Builder.Default private Vector3f scale = new Vector3f(1.0f, 1.0f, 1.0f);
    @Builder.Default private Matrix4f matrix =new Matrix4f().identity();;
    private Mesh mesh;

    public static class EObjectBuilder{
        public EObjectBuilder(){}
    }

    /**
     * A current transform matrix
     * @return Matrix4f
     */
    public synchronized Matrix4f getMatrix(){
        matrix.translate(position);
        matrix.rotateX(rotation.x);
        matrix.rotateY(rotation.y);
        matrix.rotateZ(rotation.z);
        matrix.scale(scale);
        return matrix;
    }
}
