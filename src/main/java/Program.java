import engine.Mesh;
import engine.Renderer;
import engine.Vertex;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Program implements Runnable {
    Window window;
    Renderer renderer;
    long time;
    int frameCount;

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
    }

    void start() {
        System.out.println("Program started");
        new Thread(this, "program").start();
    }

    void render() {
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
