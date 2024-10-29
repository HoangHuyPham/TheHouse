package engine;

import engine.object.BasicObject;
import engine.object.EObject;
import engine.object.Light;
import engine.object.Textures;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Renderer {

    public static void renderLight(Light light, ECamera camera){
        Shader shader = Shaders.SIMPLE_SHADER;
        Mesh mesh = light.getMesh();
        bindMesh(mesh);
        GL30.glUseProgram(shader.program);

        shader.setUniform("model", light.getModelMatrix());
        shader.setUniform("view", camera.getView());
        shader.setUniform("projection", camera.getProjection());

        if (mesh.getHasTexture() == Textures.HAS_TEXTURE) {
            bindTexture(mesh.getTextureId());
        }

        GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);

        unbindMesh();
    }

    public static void renderBasic(BasicObject object, ECamera camera, Light light) {
        Shader shader;
        Mesh mesh = object.getMesh();
        bindMesh(mesh);

        shader = Shaders.CORE_SHADER;
        GL30.glUseProgram(shader.program);
        shader.setUniform("material.ambient", object.getMaterial().getAmbient());
        shader.setUniform("material.diffuse", object.getMaterial().getDiffuse());
        shader.setUniform("material.specular", object.getMaterial().getSpecular());
        shader.setUniform("material.shininess", object.getMaterial().getShininess());

        shader.setUniform("light.ambient", light.getAmbient());
        shader.setUniform("light.diffuse", light.getDiffuse());
        shader.setUniform("light.specular", light.getSpecular());
        shader.setUniform("light.position", light.getPosition());
        shader.setUniform("light.color", light.getColor());

        shader.setUniform("camera.position", camera.getPosition());

        shader.setUniform("model", object.getModelMatrix());
        shader.setUniform("view", camera.getView());
        shader.setUniform("projection", camera.getProjection());

        if (mesh.getHasTexture() == Textures.HAS_TEXTURE) {
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
        Shaders.CORE_SHADER.setUniform("textureUnit", 0); //GL_TEXTURE0
        GL30.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        GL30.glUniform1i(GL30.glGetUniformLocation(Shaders.CORE_SHADER.program, "useTexture"), 1);
    }
}
