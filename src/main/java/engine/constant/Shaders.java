package engine.constant;

import engine.lifecycle.Shader;

public class Shaders {
    public final static Shader CORE_SHADER = Shader.builder().vertexShaderSource("shader/core.vert").fragmentShaderSource("shader/core.frag").build();
    public final static Shader LIGHT_SHADER = Shader.builder().vertexShaderSource("shader/light.vert").fragmentShaderSource("shader/light.frag").build();
    public final static Shader MINIMAP_SHADER = Shader.builder().vertexShaderSource("shader/minimap.vert").fragmentShaderSource("shader/minimap.frag").build();
    public final static Shader DEPTH_FETCH_SHADER = Shader.builder().vertexShaderSource("shader/depth.vert").fragmentShaderSource("shader/depth.frag").build();
    public final static Shader DEPTH_DEBUG_SHADER = Shader.builder().vertexShaderSource("shader/debugDepth.vert").fragmentShaderSource("shader/debugDepth.frag").build();


    public static void create(){
        CORE_SHADER.create();
        LIGHT_SHADER.create();
        MINIMAP_SHADER.create();
        DEPTH_FETCH_SHADER.create();
        DEPTH_DEBUG_SHADER.create();
    }

    public static void destroyAll(){
        CORE_SHADER.destroy();
        LIGHT_SHADER.destroy();
        MINIMAP_SHADER.destroy();
        DEPTH_FETCH_SHADER.destroy();
        DEPTH_DEBUG_SHADER.destroy();
    }
}
