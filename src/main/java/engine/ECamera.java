package engine;

import lombok.Builder;
import lombok.Getter;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;


@Builder
@Getter
public class ECamera {
    @Builder.Default private float fov = 45f;
    @Builder.Default private Vector3f position = new Vector3f(0, 0, 0);
    @Builder.Default private Vector3f target = new Vector3f(0, 0, 0);
    @Builder.Default  Vector3f up = new Vector3f(0, 1, 0);
    private Matrix4f projection;
    private Matrix4f view;

    /**
     * Calculate View Matrix again
     */
    public void updateViewMatrix(){
        view = new Matrix4f().lookAt(position, target, up);
    }

    /**
     * Calculate Projection Matrix again
     */
    public void updateProjectionMatrix(float aspect){
        projection = new Matrix4f().perspective(Math.toRadians(fov), aspect, 1.0f, 100.0f);
    }

}
