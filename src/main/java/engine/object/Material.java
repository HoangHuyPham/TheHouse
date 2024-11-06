package engine.object;

import engine.lifecycle.Texture;
import lombok.Builder;
import lombok.Getter;
import org.joml.Vector3f;

@Getter
@Builder
public class Material {
    private Texture texture;
    @Builder.Default private Vector3f ambient = new Vector3f(0.2f), diffuse = new Vector3f(1f), specular = new Vector3f(1f);
    @Builder.Default private Vector3f baseAmbient = new Vector3f(0.5f, 0.5f, 0.5f);
    @Builder.Default private float shininess = 0.5f; //0-1
}
