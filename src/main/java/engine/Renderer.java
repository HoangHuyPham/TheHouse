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
        shader.use();
        shader.setUniform("displayColor", light.getDisPlayColor());
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
        shader.use();
        shader.setUniform("material.ambient", object.getMaterial().getAmbient());
        shader.setUniform("material.diffuse", object.getMaterial().getDiffuse());
        shader.setUniform("material.specular", object.getMaterial().getSpecular());
        shader.setUniform("material.shininess", object.getMaterial().getShininess());

        if (light instanceof Sun sun){
            shader.setUniform("light.ambient", sun.getCurrentAmbient());
        }else{
            shader.setUniform("light.ambient", light.getAmbient());
        }

        shader.setUniform("light.diffuse", light.getDiffuse());
        shader.setUniform("light.specular", light.getSpecular());
        shader.setUniform("light.position", light.getPosition());
        shader.setUniform("light.color", light.getColor());

        shader.setUniform("camera.position", camera.getPosition());

        shader.setUniform("model", object.getModelMatrix());
        shader.setUniform("view", camera.getView());
        shader.setUniform("projection", camera.getProjection());

        if (object.getMaterial().getTexture() != null){
            shader.setUniform("texture0", 0);
            GL30.glActiveTexture(GL30.GL_TEXTURE0);
            GL30.glBindTexture(GL11.GL_TEXTURE_2D, object.getMaterial().getTexture().getTextureId());
        }

        if (mesh.getIndices() != null) {
            bindIndices(mesh.getIbo());
            GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        }else
            GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 36);

        unbindMesh();
    }

    private static void bindMesh(Mesh mesh) {
        GL30.glBindVertexArray(mesh.getVao());
        enableVAO();
        bindIndices(mesh.getIbo());
    }

    private static void unbindMesh() {
        GL30.glUseProgram(0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(2);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        GL30.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    private static void bindIndices(int indice) {
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indice);
    }

    private static void enableVAO() {
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
    }
}
