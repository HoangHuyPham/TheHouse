package engine;

import lombok.Builder;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

@Builder
public class Renderer {
    final private Shader shader;

    public void renderMesh(EObject eObject, ECamera eCamera) {
        Mesh mesh = eObject.getMesh();

        bindMesh(mesh);

        GL30.glUseProgram(shader.program);
        GL30.glAttachShader(shader.program, shader.vertexShader);
        shader.setUniform("model", eObject.getMatrix());
        shader.setUniform("view", eCamera.getView());
        shader.setUniform("projection", eCamera.getProjection());

        if (mesh.getTextureId() != MemoryUtil.NULL){
            bindTexture(mesh.getTextureId());
        }

        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);

        unbindMesh();
    }

    private void bindMesh(Mesh mesh){
        GL30.glBindVertexArray(mesh.getVao());
        bindPositionVertex();
        bindColorVertex();
        bindTextureCoord();
        bindIndices(mesh.getIbo());
    }

    private void unbindMesh(){
        GL30.glUseProgram(0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(2);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    private void bindIndices(int indice){
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indice);
    }

    private void bindPositionVertex(){
        GL30.glEnableVertexAttribArray(0);
    }

    private void bindColorVertex(){
        GL30.glEnableVertexAttribArray(1);
    }

    private void bindTextureCoord(){
        GL30.glEnableVertexAttribArray(2);
    }

    private void bindTexture(int textureId){
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        GL30.glUniform1i(GL30.glGetUniformLocation(shader.program,"useTexture"), 1);
    }
}
