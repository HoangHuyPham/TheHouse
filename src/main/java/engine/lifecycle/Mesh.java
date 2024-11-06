package engine.lifecycle;

import engine.object.Obj;
import engine.object.Vertex;
import lombok.Builder;
import lombok.Getter;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

@Getter
@Builder
public class Mesh implements ELifeCycle {
    final private Vertex[] vertices;
    final private int[] indices;
    private int vao, vbo, ibo;

    public static Mesh parseMesh(Obj obj){
        return Mesh.builder().vertices(obj.getVertexArray()).indices(obj.getIndices()).build();
    }

    @Override
    public void create(){
        vao = GL30.glGenVertexArrays();;
        GL30.glBindVertexArray(vao);

        int VAOSize = 8;
        FloatBuffer data = MemoryUtil.memAllocFloat(vertices.length * VAOSize);
        for (int i = 0; i < vertices.length; i++) {
            data.put(i*VAOSize, vertices[i].position.x);
            data.put(i*VAOSize+1, vertices[i].position.y);
            data.put(i*VAOSize+2, vertices[i].position.z);

            data.put(i*VAOSize+3, vertices[i].normal.x);
            data.put(i*VAOSize+4, vertices[i].normal.y);
            data.put(i*VAOSize+5, vertices[i].normal.z);

            data.put(i*VAOSize+6, vertices[i].texture.x);
            data.put(i*VAOSize+7, vertices[i].texture.y);
        }

        vbo = GL30.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
        GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, Float.BYTES * VAOSize, 0);
        GL30.glVertexAttribPointer(1, 3, GL30.GL_FLOAT, false, Float.BYTES * VAOSize, Float.BYTES * 3);
        GL30.glVertexAttribPointer(2, 2, GL30.GL_FLOAT, false, Float.BYTES * VAOSize, Float.BYTES * 6);
        GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

        if (indices == null)
            return;

        ibo = GL30.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        System.out.println("Mesh created (VAO ID) "+vao);
        MemoryUtil.memFree(data);
    }


    @Override
    public void destroy(){
        GL30.glDeleteVertexArrays(vao);
        GL30.glDeleteBuffers(vbo);
        GL30.glDeleteBuffers(ibo);
    }
}
