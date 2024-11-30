package engine.lighting;

import engine.camera.CameraShadow;
import engine.object.EObject;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.joml.Vector3f;

@SuperBuilder
@Getter
@Setter
public abstract class Light extends EObject {
    @Builder.Default
    protected Vector3f color = new Vector3f(1f);
    @Builder.Default
    protected Vector3f diffuse = new Vector3f(1f), specular = new Vector3f(1f);
    @Builder.Default
    protected float intensity = 1f; // 0-1
    @Builder.Default
    protected CameraShadow cameraShadow = CameraShadow.builder().build();
}
