package engine;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import utils.file.File;

public class Shader {
    public String vertexShaderSource;
    public String fragmentShaderSource;
    public int program, vertexShader, fragmentShader;
    public Shader(String vertexShaderSource, String fragmentShaderSource) {
        this.vertexShaderSource = vertexShaderSource;
        this.fragmentShaderSource = fragmentShaderSource;
    }
    public void create() {
        loadVertexShader();
        loadFragmentShader();
        program = GL30.glCreateProgram();
        GL30.glAttachShader(program, vertexShader);
        GL30.glAttachShader(program, fragmentShader);
        GL30.glLinkProgram(program);
        cleanUp();
    }

    void loadVertexShader() {
        vertexShader = GL30.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShader, File.readAllLines(vertexShaderSource));
        GL20.glCompileShader(vertexShader);

        if (GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS) == 0) {
            System.err.println("Could not compile vertex shader: " + vertexShader);
        }
    }

    void loadFragmentShader() {
        fragmentShader = GL30.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShader, File.readAllLines(fragmentShaderSource));
        GL20.glCompileShader(fragmentShader);

        if (GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS) == 0) {
            System.err.println("Could not compile fragment shader: " + vertexShader);
        }
    }

    void cleanUp(){
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
    }

    public void destroy(){
        GL30.glDeleteProgram(program);
    }
}
