package engine;

import org.joml.Vector3f;

public class Vertex {
    Vector3f position;
    Vector3f color;

    public Vertex(Vector3f position) {
        this.position = position;
    }

    public Vertex(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Vertex [position=" + position + ", color=" + color + "]";
    }
}
