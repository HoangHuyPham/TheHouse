package engine.object;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.joml.Vector3f;

@Setter
@Getter
@SuperBuilder
public abstract class Light extends EObject{
    @Builder.Default protected Vector3f disPlayColor = new Vector3f(1f);
    @Builder.Default protected Vector3f color = new Vector3f(1f);
    @Builder.Default protected Vector3f ambient = new Vector3f(1f);
    @Builder.Default protected Vector3f diffuse = new Vector3f(1f);
    @Builder.Default protected Vector3f specular = new Vector3f(1f);
}