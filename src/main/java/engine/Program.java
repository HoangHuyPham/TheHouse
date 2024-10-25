package engine;

import lombok.Getter;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;
import utils.file.File;

@Getter
public class Program implements Runnable {
    Window window = new Window.WindowBuilder().width(1024).height(768).title("The House").build();
    ECamera camera = new ECamera.ECameraBuilder().position(new Vector3f(0,0,5f)).build();
    CallbackManager callbackManager = new CallbackManager(window, camera);
    Renderer renderer;
    Shader shader = new Shader("shader/vertex.glsl", "shader/fragment.glsl");
    long time;
    int frameCount;
    public Mesh mesh = new Mesh(new Vertex[] {
            new Vertex.VertexBuilder().position(new Vector3f(0.5f,  0.5f, 0.0f)).texture(new Vector2f(1.0f, 1.0f)).build(),
            new Vertex.VertexBuilder().position(new Vector3f( 0.5f, -0.5f, 0.0f)).texture(new Vector2f(1.0f, 0.0f)).build(),
            new Vertex.VertexBuilder().position(new Vector3f(-0.5f, -0.5f, 0.0f)).texture(new Vector2f(0.0f, 0.0f)).build(),
            new Vertex.VertexBuilder().position(new Vector3f(-0.5f,  0.5f, 0.0f)).texture(new Vector2f(0.0f, 1.0f)).build(),
    }, new int[] {
            0, 1, 3,
            1, 2, 3
    });
    public EObject eObject1 = new EObject.EObjectBuilder().position(new Vector3f(0f,0f,0f)).rotation(new Vector3f(0, 15, 45)).mesh(mesh).build();

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
        window.create(); // 4:3 ratio
        callbackManager.create();
        shader.create();
        mesh.addTexture("texture/wall.png").create();
        box.create();
        renderer = new Renderer.RendererBuilder().shader(shader).build();

        camera.updateViewMatrix();
        camera.updateProjectionMatrix((float) window.getWidth()/ window.getHeight());

        GL20.glEnable(GL20.GL_DEPTH_TEST);

        // For calc FPS
        time = System.currentTimeMillis();
    }

    public void start() {
        System.out.println("Program started");
        new Thread(this, "programThread").start();
    }

    void render() {
        renderer.renderMesh(eObject1, camera);
        window.swapBuffer();
        calcFPS();
    }

    void update() {
        window.update();
    }

    void destroy() {
        shader.destroy();
        mesh.destroy();
        callbackManager.destroy();
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
