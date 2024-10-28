package engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;
import utils.file.File;

import java.nio.FloatBuffer;

public class Shader {
    public String vertexShaderSource;
    public String fragmentShaderSource;
    public int program, vertexShader, fragmentShader;
    public Shader(String vertexShaderSource, String fragmentShaderSource) {
        this.vertexShaderSource = vertexShaderSource;
        this.fragmentShaderSource = fragmentShaderSource;
    }

    public void setUniform(String name, int value){
        GL30.glUniform1i(GL30.glGetUniformLocation(this.program, name), value);
    }

    public void setUniform(String name, float value){
        GL30.glUniform1f(GL30.glGetUniformLocation(this.program, name), value);
    }

    public void setUniform(String name, Vector2f value){
        GL30.glUniform2f(GL30.glGetUniformLocation(this.program, name), value.x, value.y);
    }

    public void setUniform(String name, Vector3f value){
        GL30.glUniform3f(GL30.glGetUniformLocation(this.program, name), value.x, value.y, value.z);
    }

    public void setUniform(String name, Matrix4f value){
        FloatBuffer buffer = MemoryUtil.memAllocFloat(4*4);
        float[] data = new float[4*4];
        value.get(data);
        buffer.put(data).flip();
        GL30.glUniformMatrix4fv(GL30.glGetUniformLocation(this.program, name),false, buffer);
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
            System.err.println("Could not compile vertex shader: " + vertexShaderSource);
        }
    }

    void loadFragmentShader() {
        fragmentShader = GL30.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShader, File.readAllLines(fragmentShaderSource));
        GL20.glCompileShader(fragmentShader);

        if (GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS) == 0) {
            System.err.println("Could not compile fragment shader: " + fragmentShaderSource);
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
