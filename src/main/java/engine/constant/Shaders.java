package engine.constant;

import engine.lifecycle.Shader;

public class Shaders {
    public final static Shader CORE_SHADER = Shader.builder().vertexShaderSource("shader/core_vertex.glsl").fragmentShaderSource("shader/core_fragment.glsl").build();
    public final static Shader LIGHT_SHADER = Shader.builder().vertexShaderSource("shader/light_vertex.glsl").fragmentShaderSource("shader/light_fragment.glsl").build();
    public final static Shader MINIMAP_SHADER = Shader.builder().vertexShaderSource("shader/minimap_vertex.glsl").fragmentShaderSource("shader/minimap_fragment.glsl").build();


    public static void create(){
        CORE_SHADER.create();
        LIGHT_SHADER.create();
        MINIMAP_SHADER.create();
    }

    public static void destroyAll(){
        CORE_SHADER.destroy();
        LIGHT_SHADER.destroy();
        MINIMAP_SHADER.destroy();
    }
}
