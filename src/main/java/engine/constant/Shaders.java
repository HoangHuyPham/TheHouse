package engine.constant;

import engine.lifecycle.Shader;

public class Shaders {
    public final static Shader CORE_SHADER = Shader.builder().vertexShaderSource("shader/core.vert").fragmentShaderSource("shader/core.frag").build();
    public final static Shader LIGHT_SHADER = Shader.builder().vertexShaderSource("shader/light.vert").fragmentShaderSource("shader/light.frag").build();
    public final static Shader MINIMAP_SHADER = Shader.builder().vertexShaderSource("shader/minimap.vert").fragmentShaderSource("shader/minimap.frag").build();


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
