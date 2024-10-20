import engine.Mesh;
import engine.Renderer;
import engine.Shader;
import engine.Vertex;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL20;
import utils.file.File;
import lombok.*;

public class Program implements Runnable {
    Window window;
    Renderer renderer;
    Camera camera;
    Shader shader;
    long time;
    int frameCount;
    public Mesh mesh = new Mesh(new Vertex[] {
            new Vertex.VertexBuilder().position(new Vector3f(-0.5f,  0.5f, -1f)).build(),
            new Vertex.VertexBuilder().position(new Vector3f(-0.5f, -0.5f, -1f)).build(),
            new Vertex.VertexBuilder().position(new Vector3f(0.5f, -0.5f, -1f)).build(),
            new Vertex.VertexBuilder().position(new Vector3f(0.5f,  0.5f, -1f)).build(),
    }, new int[] {
            0, 1, 2,
            0, 3, 2
    });

    public Mesh box = Mesh.parseMesh(File.getObj("obj/box.obj", true));


    @Override
    public void run() {
        init();
        loop();
    }

    void loop() {
        while (!window.shouldClose()) {
            update();
            render();
        }

        destroy();
    }

    void init() {
        window = new Window(1024, 768, "The House"); // 4:3 ratio
        window.create();
        shader = new Shader("shader/vertex.glsl", "shader/fragment.glsl");
        shader.create();
        renderer = new Renderer(shader);
        mesh.create();
        box.create();

        camera = new Camera();
        Camera.isUpdating = true; // init projectile matrix in first


        GL20.glEnable(GL20.GL_DEPTH_TEST);
        GL20.glViewport(0, 0, window.width, window.height);

        GLFWKeyCallback.create((window, key, scancode, action, mods) -> {
            if (key == GLFW.GLFW_KEY_RIGHT){
                up = false;
                angle+=15;
            }
            if (key == GLFW.GLFW_KEY_UP){
                up = true;
                angle+=15;
            }
        }).set(window.windowId);

        time = System.currentTimeMillis();
    }

    float angle = 0;
    boolean up;

    void start() {
        System.out.println("Program started");
        new Thread(this, "program").start();
    }

    void render() {
//        GL20.glTranslated(0,0,-5f);

        if (up){
            GL20.glRotated(angle, 1, 0, 0);
        }else{
            GL20.glRotated(-angle, 0, 1, 0);
        }


        renderer.renderMesh(mesh);

//        if (Camera.isUpdating){
//            Camera.isUpdating = false;
//            camera.render();
//        }

        window.swapBuffer();
        calcFPS();
    }

    void update() {
        window.update();
    }

    void destroy() {
        shader.destroy();
        mesh.destroy();
        window.destroy();
    }

    void calcFPS() {
        frameCount++;
        if (System.currentTimeMillis() - time > 1000) {
            window.showFPS(frameCount);
            time = System.currentTimeMillis();
            frameCount = 0;
        }
    }
}
