package engine.camera;

import engine.Window;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;


@Getter
@Setter
@SuperBuilder
public class Camera {
    public static float DEFAULT_ZFAR = Float.POSITIVE_INFINITY;
    @Builder.Default protected float fov = 45f;
    @Builder.Default protected Vector3f position = new Vector3f(0, 0, 0);
    @Builder.Default protected Vector3f forward = new Vector3f(0, 0, -1f);
    @Builder.Default protected Vector3f up = new Vector3f(0, 1, 0);
    @Builder.Default protected float pitch = 0, yaw = -180f, roll = 0;
    @Builder.Default protected float sensitivity = 0.05f, speed = 100f, aspect = 1.0f, zNear = 1.0f, zfar = DEFAULT_ZFAR;
    @Builder.Default protected Matrix4f aProjection = new Matrix4f().identity();
    @Builder.Default protected Matrix4f aView = new Matrix4f().identity();
    @Builder.Default protected boolean shouldViewUpdate = true;
    @Builder.Default protected boolean shouldProjectionUpdate = true;

    @Builder.Default private final Object lockView = new Object(), lockProjection = new Object();

    /**
     * If {@systemProperty shouldViewUpdate} is not set to {@code true} before call {@code getView()}, it will return cache Matrix
     * @return {@link Matrix4f}
     */
    public Matrix4f getView(){
        if (shouldViewUpdate){
            synchronized (lockView){
                updateDirection();
                aView.identity().lookAt(position, position.add(forward, new Vector3f()), up);
                shouldViewUpdate = false;
            }
        }
        return aView;
    }

    /**
     * If {@systemProperty shouldProjectionUpdate} is not set to {@code true} before call {@code getProjection()}, it will return cache Matrix
     * @return {@link Matrix4f}
     */
    public Matrix4f getProjection(){
        if (shouldProjectionUpdate){
            synchronized (lockProjection){
              aProjection.identity().perspective(Math.toRadians(fov), aspect, zNear, zfar);
                shouldProjectionUpdate = false;
            }
        }
        return aProjection;
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
        if(getPitch() >= 89.0f)
            setPitch(89.0f);
        if(getPitch() < -89.0f)
            setPitch(-89.0f);

        updateDirection();
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

    public void updateDirection(){
        Vector3f direction = new Vector3f();
        direction.x = Math.cos(Math.toRadians(getYaw())) * Math.cos(Math.toRadians(getPitch()));
        direction.y = Math.sin(Math.toRadians(getPitch()));
        direction.z = Math.sin(Math.toRadians(getYaw())) * Math.cos(Math.toRadians(getPitch()));
        setForward(direction.normalize());
    }
}