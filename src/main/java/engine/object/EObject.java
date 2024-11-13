package engine.object;

import engine.constant.Materials;
import engine.lifecycle.Mesh;
import engine.tick.Tickable;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.joml.Matrix4f;
import org.joml.Vector3f;

@SuperBuilder
@Getter
@Setter
public abstract class EObject implements Tickable {
    @Builder.Default protected Vector3f position = new Vector3f();
    @Builder.Default protected Vector3f forward = new Vector3f();
    @Builder.Default protected Vector3f rotation = new Vector3f();
    @Builder.Default protected Vector3f scale = new Vector3f(1f);
    @Builder.Default protected Matrix4f matrix = new Matrix4f().identity();
    @Builder.Default protected boolean shouldUpdate = true;
    @Builder.Default protected boolean zLimit = true;
    private Mesh mesh;
    @Builder.Default
    protected Material material = Materials.WHITE_PLASTIC;

    /**
     * If {@systemProperty shouldUpdate} is not set to {@code true} before call {@code getModelMatrix()}, it will return cache Matrix
     * @return {@link Matrix4f}
     */
    public Matrix4f getModelMatrix() {
        if (shouldUpdate){
            synchronized (EObject.class){
                matrix.identity();
                matrix.translate(position);
                matrix.scale(scale);
                matrix.rotateX(rotation.x).rotateY(rotation.y).rotateZ(rotation.z);
                shouldUpdate = false;
            }
        }
        return matrix;
    }

    public float distance(EObject object) {
        return object.getPosition().distance(position);
    }

    // Set forward vector follow target's position
    public void followTarget(EObject object) {
        forward.set(object.getPosition().sub(position, new Vector3f()).normalize());
    }

    // Set forward vector follow position
    public void followTarget(Vector3f position) {
        forward.set(position.sub(this.position, new Vector3f()).normalize());
    }
}
