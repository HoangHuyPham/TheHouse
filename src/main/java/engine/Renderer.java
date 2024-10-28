package engine;

import engine.object.EObject;
import engine.object.Light;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Renderer {
    public static void renderMesh(EObject eObject, ECamera eCamera) {
        Shader shader;
        Mesh mesh = eObject.getMesh();
        bindMesh(mesh);

        if (eObject instanceof Light) {
            shader = Shaders.SIMPLE_SHADER;
            GL30.glUseProgram(shader.program);
        } else {
            shader = Shaders.CORE_SHADER;
            GL30.glUseProgram(shader.program);
            shader.setUniform("objectColor", new Vector3f(1.0f, 0.5f, 0.31f));
            shader.setUniform("lightColor", new Vector3f(1.0f, 1.0f, 1.0f));
            shader.setUniform("lightPos", new Vector3f(1.0f, 1.0f, 0.0f));
            shader.setUniform("viewPos", eCamera.getPosition());
        }


        shader.setUniform("model", eObject.getModelMatrix());
        shader.setUniform("view", eCamera.getView());
        shader.setUniform("projection", eCamera.getProjection());

        if (mesh.getTextureId() != MemoryUtil.NULL) {
            bindTexture(mesh.getTextureId());
        }


        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);


        unbindMesh();
    }

    private static void bindMesh(Mesh mesh) {
        GL30.glBindVertexArray(mesh.getVao());
        bindPositionVertex();
        bindColorVertex();
        bindNormal();
        bindTextureCoord();
        bindIndices(mesh.getIbo());
    }

    private static void unbindMesh() {
        GL30.glUseProgram(0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(3);
        GL30.glDisableVertexAttribArray(2);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    private static void bindIndices(int indice) {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indice);
    }

    private static void bindPositionVertex() {
        GL30.glEnableVertexAttribArray(0);
    }

    private static void bindColorVertex() {
        GL30.glEnableVertexAttribArray(1);
    }

    private static void bindNormal() {
        GL30.glEnableVertexAttribArray(2);
    }

    private static void bindTextureCoord() {
        GL30.glEnableVertexAttribArray(3);
    }

    private static void bindTexture(int textureId) {
        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        Shaders.CORE_SHADER.setUniform("texture", 0); //GL_TEXTURE0
        GL30.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        GL30.glUniform1i(GL30.glGetUniformLocation(Shaders.CORE_SHADER.program, "useTexture"), 1);
    }
}
