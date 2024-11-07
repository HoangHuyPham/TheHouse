package engine.constant;

import engine.lifecycle.Shader;

public class Shaders {
    public final static Shader CORE_SHADER = Shader.builder().vertexShaderSource("shader/core_vertex.glsl").fragmentShaderSource("shader/core_fragment.glsl").build();
    public final static Shader SIMPLE_SHADER = Shader.builder().vertexShaderSource("shader/simple_vertex.glsl").fragmentShaderSource("shader/simple_fragment.glsl").build();
    public final static Shader MINIMAP_SHADER = Shader.builder().vertexShaderSource("shader/minimap_vertex.glsl").fragmentShaderSource("shader/minimap_fragment.glsl").build();


    public static void create(){
        CORE_SHADER.create();
        SIMPLE_SHADER.create();
        MINIMAP_SHADER.create();
    }

    public static void destroyAll(){
        CORE_SHADER.destroy();
        SIMPLE_SHADER.destroy();
        MINIMAP_SHADER.destroy();
    }
}
