package engine;

import engine.camera.AbstractCamera;
import engine.camera.Camera;
import engine.camera.CameraMap;
import engine.constant.Shaders;
import engine.lifecycle.Mesh;
import engine.lifecycle.Shader;
import engine.object.*;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class Renderer {

    public static void renderLight(Light light, AbstractCamera camera) {
        Shader shader = Shaders.SIMPLE_SHADER;
        Mesh mesh = light.getMesh();
        bindMesh(mesh);
        shader.use();
        shader.setUniform("displayColor", light.getDisPlayColor());
        shader.setUniform("model", light.getModelMatrix());
        shader.setUniform("view", camera.getViewMatrix());
        if (!(camera instanceof CameraMap)) {
            camera.setZFar(Float.POSITIVE_INFINITY);
            camera.setShouldProjectionUpdate(true);
            shader.setUniform("projection", camera.getProjectionMatrix());
            camera.setZFar(Camera.DEFAULT_Z_FAR);
            camera.setShouldProjectionUpdate(true);
        } else {
            shader.setUniform("projection", camera.getProjectionMatrix());
        }

        if (mesh.getIndices() != null) {
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIbo());
            GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        }

        unbindMesh();
    }

    public static void renderScreen(Screen screen) {
        if (screen.getData() == null)
            return;

        Shader shader = Shaders.MINIMAP_SHADER;
        Mesh mesh = screen.getMesh();
        bindMesh(mesh);
        shader.use();
        shader.setUniform("model", screen.getModelMatrix());

        shader.setUniform("texture0", 0);
        shader.setUniform("texture1", 1);

        GL30.glActiveTexture(GL30.GL_TEXTURE0);
        GL30.glBindTexture(GL11.GL_TEXTURE_2D, screen.getData().getColorTexture());

        if (screen.getDecorTexture() != null) {
            GL30.glActiveTexture(GL30.GL_TEXTURE1);
            GL30.glBindTexture(GL11.GL_TEXTURE_2D, screen.getDecorTexture().getId());
        }

        if (mesh.getIndices() != null) {
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIbo());
            GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        }

        unbindMesh();
    }

    public static void renderBasic(BasicObject object, AbstractCamera camera, Light light) {
        Shader shader = Shaders.CORE_SHADER;
        Mesh mesh = object.getMesh();
        bindMesh(mesh);

        shader.use();

        if (light instanceof Sun sun) {
            shader.setUniform("light.ambient", sun.getCurrentAmbient());
        } else {
            shader.setUniform("light.ambient", light.getAmbient());
        }
        shader.setUniform("light.diffuse", light.getDiffuse());
        shader.setUniform("light.specular", light.getSpecular());

        shader.setUniform("material.shininess", object.getMaterial().getShininess());
        shader.setUniform("material.ambient", object.getMaterial().getAmbient());
        shader.setUniform("material.diffuse", object.getMaterial().getDiffuse());
        shader.setUniform("material.specular", object.getMaterial().getSpecular());


        shader.setUniform("light.color", light.getColor());
        if (camera instanceof CameraMap){
            shader.setUniform("light.position", new Vector3f(0, 2000f,0));
        }else{
            shader.setUniform("light.position", light.getPosition());
        }

        shader.setUniform("camera.position", camera.getPosition());

        shader.setUniform("model", object.getModelMatrix());
        shader.setUniform("view", camera.getViewMatrix());
        shader.setUniform("projection", camera.getProjectionMatrix());

        if (object.getMaterial().getTexture() != null) {
            shader.setUniform("texture0", 0);
            GL30.glActiveTexture(GL30.GL_TEXTURE0);
            GL30.glBindTexture(GL11.GL_TEXTURE_2D, object.getMaterial().getTexture().getId());
        }

        if (mesh.getIndices() != null) {
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIbo());
            GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        }

        unbindMesh();
    }

    private static void bindMesh(Mesh mesh) {
        GL30.glBindVertexArray(mesh.getVao());
        enableVAO();
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

    private static void enableVAO() {
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
    }
}
