package engine.camera;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.joml.Math;
import org.joml.Matrix4f;


@Getter
@Setter
@SuperBuilder
public class CameraShadow extends AbstractCamera{
    public static final float DEFAULT_Z_FAR = Float.POSITIVE_INFINITY;
    @Builder.Default protected final Matrix4f space = new Matrix4f().identity();

    @Override
    public Matrix4f getViewMatrix() {
        if (shouldViewUpdate){
            synchronized (lockView){
                view.setLookAt(position, forward, up);
                shouldViewUpdate = false;
            }
        }

        return view;
    }

    @Override
    public Matrix4f getProjectionMatrix() {
        if (shouldProjectionUpdate){
            synchronized (lockProjection){
                projection.setPerspective(Math.toRadians(89f), 1f, 5f, DEFAULT_Z_FAR);
                shouldProjectionUpdate = false;
            }
        }
        return projection;
    }

    public Matrix4f getSpaceMatrix() {
        return getProjectionMatrix().mul(getViewMatrix(), getSpace());
    }

    @Override
    public void onTick() {}
}
