package engine;

import lombok.Getter;
import org.lwjgl.opengl.*;

@Getter
public class Mesh{
    final private Vertex[] vertices;
    final private int[] indices;
    private int vao, vbo, ibo;
    private int textureId;

    public Mesh(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public static Mesh parseMesh(Obj obj){
        return new Mesh(obj.getVertices(), obj.getIndices());
    }

    public Mesh addTexture(String fileName){
        this.textureId = TextureLoader.loadTexture(fileName);
        return this;
    }

    public void create(){
        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        float[] data = new float[vertices.length * 8];
        for (int i = 0; i < vertices.length; i++) {
            data[i*8] = vertices[i].position.x;
            data[i*8+1] = vertices[i].position.y;
            data[i*8+2] = vertices[i].position.y;

            data[i*8+3] = vertices[i].color.x;
            data[i*8+4] = vertices[i].color.y;
            data[i*8+5] = vertices[i].color.z;

            data[i*8+6] = vertices[i].texture.x;
            data[i*8+7] = vertices[i].texture.y;
        }

        vbo = GL30.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
        GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, Float.BYTES * 8, 0);
        GL30.glVertexAttribPointer(1, 3, GL30.GL_FLOAT, false, Float.BYTES * 8, Float.BYTES * 3);
        GL30.glVertexAttribPointer(2, 2, GL30.GL_FLOAT, false, Float.BYTES * 8, Float.BYTES * 6);
        GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);


        ibo = GL30.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);


        GL30.glBindVertexArray(0);
    }


    public void destroy(){
        GL30.glDeleteVertexArrays(vao);
        GL30.glDeleteBuffers(vbo);
        GL30.glDeleteBuffers(ibo);
    }
}
