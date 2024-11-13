package engine.camera;

import engine.object.EObject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@SuperBuilder
@Getter
@Setter
public abstract class AbstractCamera extends EObject{
    @Builder.Default protected float zNear = 1.0f, zFar = 100.0f;
    @Builder.Default protected float pitch = 0, yaw = 0, roll = 0;

    @Builder.Default protected Vector3f up = new Vector3f(0, 1, 0);

    @Builder.Default protected boolean shouldViewUpdate = true;
    @Builder.Default protected boolean shouldProjectionUpdate = true;

    protected final Object lockView = new Object(), lockProjection = new Object();

    @Builder.Default protected final Matrix4f projection = new Matrix4f().identity();
    @Builder.Default protected final Matrix4f view = new Matrix4f().identity();

    /**
     * If {@systemProperty shouldViewUpdate} is {@code false} before call {@code getViewMatrix()}, it will return cache Matrix
     * @return {@link Matrix4f}
     */
    public abstract Matrix4f getViewMatrix();

    /**
     * If {@systemProperty shouldProjectionUpdate} is {@code false} before call {@code getProjectionMatrix()}, it will return cache Matrix
     * @return {@link Matrix4f}
     */
    public abstract Matrix4f getProjectionMatrix();
}