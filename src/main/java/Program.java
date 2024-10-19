import engine.Mesh;
import engine.Renderer;
import engine.Vertex;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import utils.file.File;

public class Program implements Runnable {
    Window window;
    Renderer renderer;
    Camera camera;
    long time;
    int frameCount;
    public Mesh mesh = new Mesh(new Vertex[] {
            new Vertex(new Vector3f(-0.5f,  0.5f, -1f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, -1f)),
            new Vertex(new Vector3f( 0.5f, -0.5f, -1f)),
            new Vertex(new Vector3f( 0.5f,  0.5f, -1f))
    }, new int[] {
            0, 1, 2,
            0, 3, 2
    });

    public Mesh box = Mesh.parseMesh(File.getObj("objs/box.obj"));


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

        time = System.currentTimeMillis();
        window = new Window(1024, 768, "The House"); // 4:3 ratio
        window.create();
        renderer = new Renderer();
        box.create();

        camera = new Camera();

        for (int i = 0; i < box.getIndices().length; i++) {
            System.out.println(box.getIndices()[i]);
        }


        GL20.glEnable(GL20.GL_DEPTH_TEST | GL20.GL_SMOOTH);
        GL20.glViewport(0, 0, window.width, window.height);


        GL20.glMatrixMode(GL11.GL_PROJECTION);
        GL20.glLoadIdentity();
        GL20.glLoadMatrixf(Camera.floatBuffer);
        GL20.glMatrixMode(GL11.GL_MODELVIEW);

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
    }
    float angle = 0;
    boolean up;
    void start() {
        System.out.println("Program started");
        new Thread(this, "program").start();
    }

    void render() {
        GL20.glTranslated(0,0,-10f);

        if (up){
            GL20.glRotated(angle, 1, 0, 0);
        }else{
            GL20.glRotated(-angle, 0, 1, 0);
        }


        renderer.renderMesh(box);

        camera.render();
        window.swapBuffer();
        calcFPS();
    }

    void update() {
        window.update();
    }

    void destroy() {
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
