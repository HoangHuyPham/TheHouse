package engine;

import engine.camera.AbstractCamera;
import engine.camera.Camera;
import engine.camera.CameraMiniMap;
import engine.constant.Shaders;
import engine.lifecycle.Mesh;
import engine.lifecycle.Shader;
import engine.lifecycle.ShadowFrameBuffer;
import engine.lighting.Light;
import engine.lighting.Lighting;
import engine.object.*;
import org.joml.Math;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Renderer {
    public static ShadowFrameBuffer shadowFB;

    public static void renderLight(EObject light, AbstractCamera camera) {
        Shader shader = Shaders.LIGHT_SHADER;
        Mesh mesh = light.getMesh();
        bindMesh(mesh);
        shader.use();
        shader.setUniform("model", light.getModelMatrix());
        shader.setUniform("view", camera.getViewMatrix());

        if (!light.isZLimit() && !(camera instanceof CameraMiniMap)) {
            camera.setZFar(Float.POSITIVE_INFINITY);
            camera.setShouldProjectionUpdate(true);
            shader.setUniform("projection", camera.getProjectionMatrix());
            camera.setZFar(Camera.DEFAULT_Z_FAR);
            camera.setShouldProjectionUpdate(true);
        } else {
            shader.setUniform("projection", camera.getProjectionMatrix());
        }


        if (light.getMaterial() != null) {
            if (light.getMaterial().getTexture() != null) {
                shader.setUniform("texture0", 0);
                GL20.glActiveTexture(GL20.GL_TEXTURE0);
                GL20.glBindTexture(GL20.GL_TEXTURE_2D, light.getMaterial().getTexture().getId());
            }
        }

        if (light instanceof Light l) {
            shader.setUniform("intensity", l.getIntensity());
        } else {
            shader.setUniform("intensity", 1f);
        }

        if (mesh.getIndices() != null) {
            GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, mesh.getIbo());
            GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        }

        unbindMesh();
    }

    public static void renderScreen(Screen screen) {
        Shader shader;
        if (screen.getData() != null) {
            shader = Shaders.MINIMAP_SHADER;
            shader.use();
        } else if (screen.getShadow() != null) {
            shader = Shaders.DEPTH_DEBUG_SHADER;
            shader.use();
        } else {
            return;
        }
        Mesh mesh = screen.getMesh();
        bindMesh(mesh);

        shader.setUniform("model", screen.getModelMatrix());

        shader.setUniform("texture0", 0);
        shader.setUniform("depthMap", 0);
        shader.setUniform("texture1", 1);


        if (screen.getData() != null) {
            GL30.glActiveTexture(GL30.GL_TEXTURE0);
            GL30.glBindTexture(GL11.GL_TEXTURE_2D, screen.getData().getColorTexture());
        }


        if (screen.getShadow() != null) {
            GL30.glActiveTexture(GL30.GL_TEXTURE0);
            GL30.glBindTexture(GL11.GL_TEXTURE_2D, screen.getShadow().getDepthCompTexture());
        }

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


    public static void renderBasic(BasicObject object, AbstractCamera camera, Matrix4f lightSpaceMatrix) {
        renderBasic(object, camera, Shaders.CORE_SHADER, lightSpaceMatrix);
    }

    public static void renderBasic(BasicObject object, AbstractCamera camera, Shader shader, Matrix4f lightSpaceMatrix) {
        Mesh mesh = object.getMesh();
        bindMesh(mesh);

        shader.use();

        shader.setUniform("texture0", 0);
        shader.setUniform("texture1", 1);
        shader.setUniform("depthMap", 2);

        if (shadowFB != null) {
            GL30.glActiveTexture(GL30.GL_TEXTURE2);
            GL30.glBindTexture(GL30.GL_TEXTURE_2D, shadowFB.getDepthCompTexture());
        }

        if (lightSpaceMatrix != null)
            shader.setUniform("lightSpaceMatrix", lightSpaceMatrix);

        if (Lighting.DIRECTIONAL_LIGHT != null) {
            shader.setUniform("dLight.position", (camera instanceof CameraMiniMap) ? camera.getPosition() : Lighting.DIRECTIONAL_LIGHT.getPosition());
            shader.setUniform("dLight.ambient", Lighting.DIRECTIONAL_LIGHT.getAmbient());
            shader.setUniform("dLight.diffuse", Lighting.DIRECTIONAL_LIGHT.getDiffuse());
            shader.setUniform("dLight.specular", Lighting.DIRECTIONAL_LIGHT.getSpecular());
            shader.setUniform("dLight.color", Lighting.DIRECTIONAL_LIGHT.getColor());
            shader.setUniform("dLight.intensity", Lighting.DIRECTIONAL_LIGHT.getIntensity());
        }

        for (int i = 0; i < Lighting.POINT_LIGHTS.size(); i++) {
            shader.setUniform("pLights[%d].isActive".formatted(i), !(camera instanceof CameraMiniMap) && Lighting.POINT_LIGHTS.get(i).isActive());
            shader.setUniform("pLights[%d].position".formatted(i), Lighting.POINT_LIGHTS.get(i).getPosition());
            shader.setUniform("pLights[%d].ambient".formatted(i), Lighting.POINT_LIGHTS.get(i).getAmbient());
            shader.setUniform("pLights[%d].diffuse".formatted(i), Lighting.POINT_LIGHTS.get(i).getDiffuse());
            shader.setUniform("pLights[%d].specular".formatted(i), Lighting.POINT_LIGHTS.get(i).getSpecular());
            shader.setUniform("pLights[%d].color".formatted(i), Lighting.POINT_LIGHTS.get(i).getColor());
            shader.setUniform("pLights[%d].intensity".formatted(i), Lighting.POINT_LIGHTS.get(i).getIntensity());
            shader.setUniform("pLights[%d].constant".formatted(i), Lighting.POINT_LIGHTS.get(i).getConstant());
            shader.setUniform("pLights[%d].linear".formatted(i), Lighting.POINT_LIGHTS.get(i).getLinear());
            shader.setUniform("pLights[%d].quadratic".formatted(i), Lighting.POINT_LIGHTS.get(i).getQuadratic());
        }

        for (int i = 0; i < Lighting.SPOT_LIGHTS.size(); i++) {
            shader.setUniform("sLights[%d].isActive".formatted(i), !(camera instanceof CameraMiniMap) && Lighting.SPOT_LIGHTS.get(i).isActive());
            shader.setUniform("sLights[%d].position".formatted(i), Lighting.SPOT_LIGHTS.get(i).getPosition());
            shader.setUniform("sLights[%d].ambient".formatted(i), Lighting.SPOT_LIGHTS.get(i).getAmbient());
            shader.setUniform("sLights[%d].diffuse".formatted(i), Lighting.SPOT_LIGHTS.get(i).getDiffuse());
            shader.setUniform("sLights[%d].specular".formatted(i), Lighting.SPOT_LIGHTS.get(i).getSpecular());
            shader.setUniform("sLights[%d].color".formatted(i), Lighting.SPOT_LIGHTS.get(i).getColor());
            shader.setUniform("sLights[%d].cutOff".formatted(i), Math.cos(Math.toRadians(Lighting.SPOT_LIGHTS.get(i).getCutOff())));
            shader.setUniform("sLights[%d].outerCutOff".formatted(i), Math.cos(Math.toRadians(Lighting.SPOT_LIGHTS.get(i).getOuterCutOff())));
            shader.setUniform("sLights[%d].direction".formatted(i), Lighting.SPOT_LIGHTS.get(i).getForward());
        }

        shader.setUniform("material.shininess", object.getMaterial().getShininess());
        shader.setUniform("material.ambient", object.getMaterial().getAmbient());
        shader.setUniform("material.diffuse", object.getMaterial().getDiffuse());
        shader.setUniform("material.specular", object.getMaterial().getSpecular());

        shader.setUniform("camera.position", camera.getPosition());

        shader.setUniform("model", object.getModelMatrix());
        shader.setUniform("view", camera.getViewMatrix());

        if (!object.isZLimit()) {
            camera.setZFar(Float.POSITIVE_INFINITY);
            camera.setShouldProjectionUpdate(true);
            shader.setUniform("projection", camera.getProjectionMatrix());
            camera.setZFar(Camera.DEFAULT_Z_FAR);
            camera.setShouldProjectionUpdate(true);
        } else {
            shader.setUniform("projection", camera.getProjectionMatrix());
        }

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
