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
    @Builder.Default private float pitch = 0, yaw = -90f, roll = 0;
    @Builder.Default private float sensitivity = 0.05f, speed = 5f, aspect = 1.0f;
    @Builder.Default private Matrix4f projection = new Matrix4f().identity();
    @Builder.Default private Matrix4f view = new Matrix4f().identity();
    @Builder.Default private boolean shouldViewUpdate = true;
    @Builder.Default private boolean shouldProjectionUpdate = true;

    @Builder.Default private final Object lockView = new Object(), lockProjection = new Object();

    /**
     * If {@systemProperty shouldViewUpdate} is not set to {@code true} before call {@code getView()}, it will return cache Matrix
     * @return {@link Matrix4f}
     */
    public Matrix4f getView(){
        if (shouldViewUpdate){
            synchronized (lockView){
                view.identity().lookAt(position, position.add(forward, new Vector3f()), up);
                shouldViewUpdate = false;
            }
        }
        return view;
    }

    /**
     * If {@systemProperty shouldProjectionUpdate} is not set to {@code true} before call {@code getProjection()}, it will return cache Matrix
     * @return {@link Matrix4f}
     */
    public Matrix4f getProjection(){
        if (shouldProjectionUpdate){
            synchronized (lockProjection){
                projection.identity().perspective(Math.toRadians(fov), aspect, 1.0f, 100.0f);;
                shouldProjectionUpdate = false;
            }
        }
        return projection;
    }

    /**
     * Update {@systemProperty forward} follow current x, y offset mouse
     * @param xOffset
     * @param yOffset
     */
    public void updateForwardByPointer(float xOffset, float yOffset){
        setPitch(getPitch() + yOffset * this.sensitivity);
        setYaw(getYaw() + xOffset * this.sensitivity);

        // Limit euler angle
        if(getPitch() >= 90.0f)
            setPitch(90.0f);
        if(getPitch() < -90.0f)
            setPitch(-90.0f);

        Vector3f direction = new Vector3f();
        direction.x = Math.cos(Math.toRadians(getYaw())) * Math.cos(Math.toRadians(getPitch()));
        direction.y = Math.sin(Math.toRadians(getPitch()));
        direction.z = Math.sin(Math.toRadians(getYaw())) * Math.cos(Math.toRadians(getPitch()));

        setForward(direction.normalize());
    }

    public void flyUp(){
        position.add(up.mul(speed * Window.deltaTime, new Vector3f()));
    }

    public void flyDown(){
        position.sub(up.mul(speed * Window.deltaTime, new Vector3f()));
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
