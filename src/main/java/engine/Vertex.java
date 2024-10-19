package engine;

import org.joml.Vector3f;

public class Vertex {
    Vector3f position;

    public Vertex(Vector3f position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Vertex [position=" + position + "]";
    }
}
