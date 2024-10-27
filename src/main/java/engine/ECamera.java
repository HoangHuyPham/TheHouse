package engine;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;



@Getter
@Setter
@Builder
public class ECamera {
    @Builder.Default private float fov = 45f;
    @Builder.Default private Vector3f position = new Vector3f(0, 0, 0);
    @Builder.Default private Vector3f forward = new Vector3f(0, 0, -1);
    @Builder.Default private Vector3f up = new Vector3f(0, 1, 0);
    @Builder.Default private float speed = 2.5f;
    @Builder.Default private float pitch = 0, yaw = -90f, roll = 0;
    private Matrix4f projection;
    private Matrix4f view;

    /**
     * Calculate View Matrix again
     */
    public void updateViewMatrix(){
        view = new Matrix4f().lookAt(position, position.add(forward, new Vector3f()), up);
    }

    /**
     * Calculate Projection Matrix again
     */
    public void updateProjectionMatrix(float aspect){
        System.out.println(fov);
        projection = new Matrix4f().perspective(Math.toRadians(fov), aspect, 1.0f, 100.0f);
    }

    public void moveUp(){
        position.add(forward.mul(speed * Window.deltaTime, new Vector3f()));
    }

    public void moveDown(){
        position.sub(forward.mul(speed * Window.deltaTime, new Vector3f()));
    }

    public void moveLeft(){
        position.sub(forward.cross(up, new Vector3f()).normalize().mul(speed * Window.deltaTime));
    }

    public void moveRight(){
        position.add(forward.cross(up, new Vector3f()).normalize().mul(speed * Window.deltaTime));
    }

    public void setForward(Vector3f forward){
        this.forward.set(forward);
    }
}
