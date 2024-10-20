package engine;

import org.lwjgl.opengl.*;

public class Mesh{
    final private Vertex[] vertices;
    final private int[] indices;
    private int vao, vbo, ibo;

    public Mesh(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public static Mesh parseMesh(Obj obj){
        return new Mesh(obj.getVertices(), obj.getIndices());
    }

    public void create(){
        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        float[] data = new float[vertices.length * 6];
        for (int i = 0; i < vertices.length; i++) {
            data[i*6] = vertices[i].position.x;
            data[i*6+1] = vertices[i].position.y;
            data[i*6+2] = vertices[i].position.y;

            data[i*6+3] = vertices[i].color.x;
            data[i*6+4] = vertices[i].color.y;
            data[i*6+5] = vertices[i].color.z;
        }

        vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
        GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, Float.BYTES * 6, 0);
        GL30.glVertexAttribPointer(1, 3, GL30.GL_FLOAT, false, Float.BYTES * 6, Float.BYTES * 3);

        ibo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        GL30.glBindVertexArray(0);
    }

    public int getVao() {
        return vao;
    }

    public int getIbo() {
        return ibo;
    }

    public int[] getIndices() {
        return indices;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public void destroy(){
        GL30.glDeleteVertexArrays(vao);
        GL30.glDeleteBuffers(vbo);
        GL30.glDeleteBuffers(ibo);
    }
}
