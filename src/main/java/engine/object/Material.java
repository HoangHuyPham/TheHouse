package engine.object;

import engine.lifecycle.Texture;
import lombok.Builder;
import lombok.Getter;
import org.joml.Vector3f;

@Getter
@Builder
public class Material {
    private Texture texture;
    private Vector3f ambient, diffuse, specular;
    private float shininess; //0-1
}
