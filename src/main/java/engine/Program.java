package engine;

import engine.constant.Meshes;
import engine.constant.Shaders;
import engine.constant.Textures;
import engine.lifecycle.Mesh;
import engine.object.*;
import engine.tick.Ticker;
import lombok.Getter;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;
import utils.file.File;

@Getter
public class Program implements Runnable {
    Window window = Window.builder().width(1024).height(768).title("The House").build();
    ECamera camera = ECamera.builder().position(new Vector3f(0, 0, 5f)).build();
    CallbackManager callbackManager = CallbackManager.builder().window(window).camera(camera).build();
    Ticker ticker = new Ticker();
    long time;
    int frameCount;

    public BasicObject basicObject = BasicObject.builder().position(new Vector3f(0f, 0f, 0f)).rotation(new Vector3f(0, 0, 0)).mesh(Meshes.BOX_NO_INDICES).build();
    public Light sun = Light.builder().color(new Vector3f(1, 0.961f, 0.698f)).position(new Vector3f(0f, 0f, 0f)).scale(new Vector3f(100000f, 100000f, 100000f)).mesh(Meshes.SPHRERE_FROM_OBJ).build();

    @Override
    public void run() {
        init();
        subInit();
        loop();
    }

    void loop() {
        while (!window.shouldClose()) {
            update();
            render();
        }

        destroy();
    }

    void subInit(){
        ticker.start();
    }

    void init() {
        window.create(); // 4:3 ratio
        callbackManager.create();
        Shaders.create();
        Textures.create();
        Meshes.create();

        // Set up camera
        camera.setAspect((float) window.getWidth() / window.getHeight());
        camera.setShouldProjectionUpdate(true);
        camera.setShouldViewUpdate(true);

        // Ticker register
        ticker.registerTickable(sun);
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
        Textures.destroyAll();
        Meshes.destroyAll();
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
