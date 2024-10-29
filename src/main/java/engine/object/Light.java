package engine.object;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.joml.Vector3f;

@Setter
@Getter
@SuperBuilder
public class Light extends EObject {
    @Builder.Default Vector3f color = new Vector3f(1.0f);
    @Builder.Default Vector3f ambient = new Vector3f(1f);
    @Builder.Default Vector3f diffuse = new Vector3f(1f);
    @Builder.Default Vector3f specular = new Vector3f(1f);
}
