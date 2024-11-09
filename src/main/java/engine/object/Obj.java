package engine.object;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Builder
@Getter
public class Obj {
    @NonNull
    private Vector3f[] positions;
    private Vector3f[] normals;
    private Vector2f[] texCoords;
    private IntBuffer indices;

    public Vertex[] getVertexArray(){
        List<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {
            Vertex v = Vertex.builder().position(positions[i]).build();
            if (normals != null && normals.length > 0){
                v.setNormal(normals[i]);
            }
            if (texCoords != null && texCoords.length > 0){
                v.setTexture(texCoords[i]);
            }
            vertices.add(v);
        }
        return vertices.toArray(new Vertex[0]);
    }

    public int[] getIndices() {
        int[] res = new int[indices.limit()];
        for (int i = 0; i < indices.limit(); i++) {
            res[i] = indices.get(i);
        }
        return res;
    }

    @Override
    public String toString() {
        return "Obj{" +
                "position=" + Arrays.toString(positions) +
                ", normals=" + Arrays.toString(normals) +
                ", texCoords=" + Arrays.toString(texCoords) +
                ", indices=" + Arrays.toString(getIndices()) +
                '}';
    }
}
