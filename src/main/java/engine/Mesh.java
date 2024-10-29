package engine;

import engine.object.Textures;
import lombok.Getter;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;

@Getter
public class Mesh{
    final private Vertex[] vertices;
    final private int[] indices;
    private int vao, vbo, ibo;
    private int textureId;
    private byte hasTexture = Textures.NO_TEXTURE;

    public Mesh(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public Mesh(Vertex[] vertices, int[] indices, int textureId) {
        this(vertices, indices);
        this.textureId = textureId;
        if (this.textureId != MemoryUtil.NULL) {
            this.hasTexture = Textures.HAS_TEXTURE;
        }
    }

    public Mesh(Vertex[] vertices, int[] indices, String texture) {
        this(vertices, indices);
        addTexture(texture);
    }

    public static Mesh parseMesh(Obj obj){
        return new Mesh(obj.getVertices(), obj.getIndices());
    }

    /**
     * Only addTexture when OpenGL is ready
     * @param fileName
     * @return
     */
    public Mesh addTexture(String fileName){
        this.textureId = TextureLoader.loadTexture(fileName);
        if (this.textureId != MemoryUtil.NULL) {
            this.hasTexture = Textures.HAS_TEXTURE;
        }
        return this;
    }

    public void create(){
        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        int VAOSize = 11;
        float[] data = new float[vertices.length * VAOSize];
        for (int i = 0; i < vertices.length; i++) {
            data[i*VAOSize] = vertices[i].position.x;
            data[i*VAOSize+1] = vertices[i].position.y;
            data[i*VAOSize+2] = vertices[i].position.z;

            data[i*VAOSize+3] = vertices[i].color.x;
            data[i*VAOSize+4] = vertices[i].color.y;
            data[i*VAOSize+5] = vertices[i].color.z;

            data[i*VAOSize+6] = vertices[i].normal.x;
            data[i*VAOSize+7] = vertices[i].normal.y;
            data[i*VAOSize+8] = vertices[i].normal.z;

            data[i*VAOSize+9] = vertices[i].texture.x;
            data[i*VAOSize+10] = vertices[i].texture.y;
        }

        vbo = GL30.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
        GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, Float.BYTES * 11, 0);
        GL30.glVertexAttribPointer(1, 3, GL30.GL_FLOAT, false, Float.BYTES * 11, Float.BYTES * 3);
        GL30.glVertexAttribPointer(2, 3, GL30.GL_FLOAT, false, Float.BYTES * 11, Float.BYTES * 6);
        GL30.glVertexAttribPointer(3, 2, GL30.GL_FLOAT, false, Float.BYTES * 11, Float.BYTES * 9);
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
