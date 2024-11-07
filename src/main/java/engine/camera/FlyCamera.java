package engine.camera;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@Setter
@Getter
@SuperBuilder
public class FlyCamera extends ECamera{
    @Builder.Default float high = 500f;

    public void attachTo(ECamera mainCamera){
        mainCamera.attach(this);
    }

    @Override
    public Matrix4f getView() {
        if (isShouldViewUpdate()){
            synchronized (getLockView()){
                updateDirection();
                setAView(new Matrix4f().identity().lookAt(getPosition().add(new Vector3f(0,high,0), new Vector3f()), getPosition().add(getForward(), new Vector3f()), getUp()));
                setShouldViewUpdate(false);
            }
        }
        return getAView();
    }

    @Override
    public Matrix4f getProjection() {
        if (isShouldProjectionUpdate()){
            synchronized (getLockProjection()){
                setAProjection(new Matrix4f().identity().ortho(-500f,500f, -500f,500f, 0, 1000f));
                setShouldProjectionUpdate(false);
            }
        }
        return getAProjection();
    }
}
