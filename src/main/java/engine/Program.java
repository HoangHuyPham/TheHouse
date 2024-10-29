package engine;

import engine.object.BasicObject;
import engine.object.Light;
import engine.tick.Ticker;
import lombok.Getter;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;
import utils.file.File;

@Getter
public class Program implements Runnable {
    Window window = Window.builder().width(1024).height(768).title("The House").build();
    ECamera camera = ECamera.builder().position(new Vector3f(0,0,5f)).build();
    CallbackManager callbackManager = new CallbackManager(window, camera);
    Ticker ticker = new Ticker();
    long time;
    int frameCount;
    public Mesh mesh = new Mesh(new Vertex[]{
            // Front face
            new Vertex.VertexBuilder()
                    .position(new Vector3f(-0.5f, -0.5f,  0.5f))
                    .normal(new Vector3f(0.0f,  0.0f,  1.0f)).build(),
            new Vertex.VertexBuilder()
                    .position(new Vector3f( 0.5f, -0.5f,  0.5f))
                    .normal(new Vector3f(0.0f,  0.0f,  1.0f)).build(),
            new Vertex.VertexBuilder()
                    .position(new Vector3f( 0.5f,  0.5f,  0.5f))
                    .normal(new Vector3f(0.0f,  0.0f,  1.0f)).build(),
            new Vertex.VertexBuilder()
                    .position(new Vector3f(-0.5f,  0.5f,  0.5f))
                    .normal(new Vector3f(0.0f,  0.0f,  1.0f)).build(),

            // Back face
            new Vertex.VertexBuilder()
                    .position(new Vector3f(-0.5f, -0.5f, -0.5f))
                    .normal(new Vector3f(0.0f,  0.0f, -1.0f)).build(),
            new Vertex.VertexBuilder()
                    .position(new Vector3f( 0.5f, -0.5f, -0.5f))
                    .normal(new Vector3f(0.0f,  0.0f, -1.0f)).build(),
            new Vertex.VertexBuilder()
                    .position(new Vector3f( 0.5f,  0.5f, -0.5f))
                    .normal(new Vector3f(0.0f,  0.0f, -1.0f)).build(),
            new Vertex.VertexBuilder()
                    .position(new Vector3f(-0.5f,  0.5f, -0.5f))
                    .normal(new Vector3f(0.0f,  0.0f, -1.0f)).build(),

            // Left face
            new Vertex.VertexBuilder()
                    .position(new Vector3f(-0.5f, -0.5f, -0.5f))
                    .normal(new Vector3f(-1.0f, 0.0f, 0.0f)).build(),
            new Vertex.VertexBuilder()
                    .position(new Vector3f(-0.5f, -0.5f,  0.5f))
                    .normal(new Vector3f(-1.0f, 0.0f, 0.0f)).build(),
            new Vertex.VertexBuilder()
                    .position(new Vector3f(-0.5f,  0.5f,  0.5f))
                    .normal(new Vector3f(-1.0f, 0.0f, 0.0f)).build(),
            new Vertex.VertexBuilder()
                    .position(new Vector3f(-0.5f,  0.5f, -0.5f))
                    .normal(new Vector3f(-1.0f, 0.0f, 0.0f)).build(),

            // Right face
            new Vertex.VertexBuilder()
                    .position(new Vector3f(0.5f, -0.5f, -0.5f))
                    .normal(new Vector3f(1.0f, 0.0f, 0.0f)).build(),
            new Vertex.VertexBuilder()
                    .position(new Vector3f(0.5f, -0.5f,  0.5f))
                    .normal(new Vector3f(1.0f, 0.0f, 0.0f)).build(),
            new Vertex.VertexBuilder()
                    .position(new Vector3f(0.5f,  0.5f,  0.5f))
                    .normal(new Vector3f(1.0f, 0.0f, 0.0f)).build(),
            new Vertex.VertexBuilder()
                    .position(new Vector3f(0.5f,  0.5f, -0.5f))
                    .normal(new Vector3f(1.0f, 0.0f, 0.0f)).build(),

            // Top face
            new Vertex.VertexBuilder()
                    .position(new Vector3f(-0.5f,  0.5f, -0.5f))
                    .normal(new Vector3f(0.0f,  1.0f, 0.0f)).build(),
            new Vertex.VertexBuilder()
                    .position(new Vector3f( 0.5f,  0.5f, -0.5f))
                    .normal(new Vector3f(0.0f,  1.0f, 0.0f)).build(),
            new Vertex.VertexBuilder()
                    .position(new Vector3f( 0.5f,  0.5f,  0.5f))
                    .normal(new Vector3f(0.0f,  1.0f, 0.0f)).build(),
            new Vertex.VertexBuilder()
                    .position(new Vector3f(-0.5f,  0.5f,  0.5f))
                    .normal(new Vector3f(0.0f,  1.0f, 0.0f)).build(),

            // Bottom face
            new Vertex.VertexBuilder()
                    .position(new Vector3f(-0.5f, -0.5f, -0.5f))
                    .normal(new Vector3f(0.0f, -1.0f, 0.0f)).build(),
            new Vertex.VertexBuilder()
                    .position(new Vector3f( 0.5f, -0.5f, -0.5f))
                    .normal(new Vector3f(0.0f, -1.0f, 0.0f)).build(),
            new Vertex.VertexBuilder()
                    .position(new Vector3f( 0.5f, -0.5f,  0.5f))
                    .normal(new Vector3f(0.0f, -1.0f, 0.0f)).build(),
            new Vertex.VertexBuilder()
                    .position(new Vector3f(-0.5f, -0.5f,  0.5f))
                    .normal(new Vector3f(0.0f, -1.0f, 0.0f)).build()
    }, new int[]{
            // Front face
            0, 1, 2, 2, 3, 0,
            // Back face
            4, 5, 6, 6, 7, 4,
            // Left face
            8, 9, 10, 10, 11, 8,
            // Right face
            12, 13, 14, 14, 15, 12,
            // Top face
            16, 17, 18, 18, 19, 16,
            // Bottom face
            20, 21, 22, 22, 23, 20
    });


    public Mesh box = Mesh.parseMesh(File.getObj("obj/box.obj", true));
    public BasicObject basicObject = BasicObject.builder().position(new Vector3f(0f,0f,0f)).rotation(new Vector3f(0, 0, 0)).mesh(mesh).build();
    public Light sun = Light.builder().position(new Vector3f(0f,0f,0f)).scale(new Vector3f(100000f,100000f,100000f)).mesh(mesh).build();
    @Override
    public void run() {
        init();
        ticker.start();
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
        Shaders.create();
        mesh.create();
        box.create();

        camera.setAspect((float) window.getWidth()/ window.getHeight());
        camera.setShouldProjectionUpdate(true);
        camera.setShouldViewUpdate(true);

        ticker.registerTickable(sun);

        GL20.glEnable(GL20.GL_DEPTH_TEST);
        // For calc FPS
        time = System.currentTimeMillis();
    }

    public void start() {
        System.out.println("Program started");
        new Thread(this, "programThread").start();
    }

    void render() {
        Renderer.renderLight(sun, camera);
        Renderer.renderBasic(basicObject, camera, sun);
        window.swapBuffer();
        calcFPS();
    }

    void update() {
        window.update();
    }

    void destroy() {
        ticker.destroy();
        Shaders.destroyAll();
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
