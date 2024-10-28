package engine;

public class Shaders {
    final static Shader CORE_SHADER = new Shader("shader/core_vertex.glsl", "shader/core_fragment.glsl");
    final static Shader SIMPLE_SHADER = new Shader("shader/simple_vertex.glsl", "shader/simple_fragment.glsl");

    public static void create(){
        CORE_SHADER.create();
        SIMPLE_SHADER.create();
    }

    public static void destroyAll(){
        CORE_SHADER.destroy();
        SIMPLE_SHADER.destroy();
    }
}
