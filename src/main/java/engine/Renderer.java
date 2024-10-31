package engine;

import engine.constant.Shaders;
import engine.constant.Textures;
import engine.lifecycle.Mesh;
import engine.lifecycle.Shader;
import engine.object.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Renderer {

    public static void renderLight(Light light, ECamera camera){
        Shader shader = Shaders.SIMPLE_SHADER;
        Mesh mesh = light.getMesh();
        bindMesh(mesh);
        GL30.glUseProgram(shader.program);

        shader.setUniform("color", light.getColor());
        shader.setUniform("model", light.getModelMatrix());
        shader.setUniform("view", camera.getView());
        camera.setZfar(Float.POSITIVE_INFINITY);
        camera.setShouldProjectionUpdate(true);
        shader.setUniform("projection", camera.getProjection());
        camera.setZfar(ECamera.DEFAULT_ZFAR);
        camera.setShouldProjectionUpdate(true);

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

        shader.setUniform("texture0", 0);
        shader.setUniform("texture1", 1);

        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL11.GL_TEXTURE_2D, Textures.WALL.getTextureId());
//
//        GL30.glActiveTexture(GL30.GL_TEXTURE1);
//        GL30.glBindTexture(GL11.GL_TEXTURE_2D, Textures.CONTAINER_TEX1.getTextureId());

        if (mesh.getIndices() != null)
            GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        else
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 36);

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
        GL30.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
    }
}
