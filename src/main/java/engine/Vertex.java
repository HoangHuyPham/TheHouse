package engine;

import lombok.*;
import org.joml.Vector3f;

@Builder(access = AccessLevel.PUBLIC)
public class Vertex {
    @NonNull public final Vector3f position;
    @Builder.Default public final Vector3f color = new Vector3f(1f, 1f, 1f); // white default color

    public static class VertexBuilder {
        public VertexBuilder() {} // Public constructor
    }
}
