package engine.object;

import lombok.*;
import org.joml.Vector2f;
import org.joml.Vector3f;

@Builder()
@Setter
public class Vertex {
    @NonNull public Vector3f position;
    @Builder.Default public Vector3f normal = new Vector3f(0f);
    @Builder.Default public Vector2f texture = new Vector2f(-1f);
}
