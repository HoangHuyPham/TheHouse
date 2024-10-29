package engine.object;

import lombok.Builder;
import lombok.Getter;
import org.joml.Vector3f;

@Getter
@Builder
public class Material {
    private int textureId;
    private Vector3f ambient, diffuse, specular;
    private float shininess; //0-1
}
