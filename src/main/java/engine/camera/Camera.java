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
public class Camera extends AbstractCamera{
    public static final float DEFAULT_Z_FAR = Float.POSITIVE_INFINITY;
    @Builder.Default protected float fov = 45f;
    @Builder.Default protected float sensitivity = 0.05f, speed = 100f, aspect = 1.0f;

    @Override
    public Matrix4f getViewMatrix() {
        if (shouldViewUpdate){
            synchronized (lockView){
                updateDirection();
                view.setLookAt(position, position.add(forward, new Vector3f()), up);
                shouldViewUpdate = false;
            }
        }
        return view;
    }

    @Override
    public Matrix4f getProjectionMatrix() {
        if (shouldProjectionUpdate){
            synchronized (lockProjection){
                projection.setPerspective(Math.toRadians(fov), aspect, zNear, zFar);
                shouldProjectionUpdate = false;
            }
        }
        return projection;
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
        this.forward = direction.normalize();
    }

    @Override
    public void onTick() {}
}
