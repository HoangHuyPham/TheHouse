package engine;

import java.util.Arrays;

public class Obj {
    Vertex[] vertices;
    Vertex[] normals;
    Vertex[] texCoords;
    Integer[] faces;

    private Obj(Vertex[] vertices, Vertex[] normals, Vertex[] texCoords, Integer[] faces) {
        this.vertices = vertices;
        this.normals = normals;
        this.texCoords = texCoords;
        this.faces = faces;
    }

    public static Obj create(Vertex[] vertices, Vertex[] normals, Vertex[] texCoords, Integer[] faces) {
        return new Obj(vertices, normals, texCoords, faces);
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public Vertex[] getNormals() {
        return normals;
    }

    public Vertex[] getTexCoords() {
        return texCoords;
    }

    public Integer[] getFaces() {
        return faces;
    }

    public int[] getIndices() {
        int[] indices = new int[faces.length];
        for (int i = 0; i < faces.length; i++) {
            indices[i] = faces[i];
        }
        return indices;
    }

    @Override
    public String toString() {
        return "Obj{" +
                "vertices=" + Arrays.toString(vertices) +
                ", normals=" + Arrays.toString(normals) +
                ", texCoords=" + Arrays.toString(texCoords) +
                ", faces=" + Arrays.toString(faces) +
                '}';
    }
}
