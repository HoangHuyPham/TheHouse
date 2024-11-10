package engine.camera;

import engine.object.EObject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Optional;

@SuperBuilder
@Getter
@Setter
public class CameraMap extends AbstractCamera{
    @Builder.Default private float viewSize = 400f;
    @Builder.Default private float high = 1999f;
    private Optional<EObject> followOject;

    @Override
    public Matrix4f getViewMatrix() {
        if (shouldViewUpdate || followOject.map(EObject::isShouldUpdate).orElse(false)){
            synchronized (lockView){

                if (followOject.isPresent()){
                    if (followOject.get() instanceof AbstractCamera camera){
                        followOject.ifPresent(obj -> {
                            Vector3f objPosition = obj.getPosition();
                            position.x = objPosition.x;
                            position.z = objPosition.z;
                        });

                        position.y = high;
                        forward = new Vector3f(0, -1, 0).normalize();
                        up = new Vector3f(camera.getForward().x, 0, camera.getForward().z).negate().normalize();

                        view.setLookAt(position, position.add(forward, new Vector3f()), up);
                        shouldViewUpdate = false;
                    };

                }

            }
        }
        return view;
    }

    @Override
    public Matrix4f getProjectionMatrix() {
        if (shouldProjectionUpdate){
            synchronized (lockProjection){
                projection.identity().ortho(-viewSize, viewSize, -viewSize, viewSize, zNear, zFar);
                shouldProjectionUpdate = false;
            }
        }
        return projection;
    }

    @Override
    public void onTick() {}
}
