package engine;

import lombok.Builder;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

@Builder
public class Renderer {
    final private Shader shader;
    private int texture;

    public void renderMesh(Mesh mesh, int texture){
        this.texture = texture;
        renderMesh(mesh);
    }

    public void renderMesh(Mesh mesh) {
        GL30.glBindVertexArray(mesh.getVao());
        GL30.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIbo());
        GL30.glUseProgram(shader.program);
        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        GL30.glUseProgram(0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(2);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(0);
        GL30.glBindTexture(GL11.GL_TEXTURE_2D, 0);
        GL30.glBindVertexArray(0);
    }

    public static class RendererBuilder{
        public RendererBuilder(){}
    }
}
