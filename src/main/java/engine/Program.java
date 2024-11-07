package engine;

import engine.camera.ECamera;
import engine.camera.FlyCamera;
import engine.constant.Materials;
import engine.constant.Meshes;
import engine.constant.Shaders;
import engine.constant.Textures;
import engine.lifecycle.FrameBuffer;
import engine.object.*;
import engine.tick.Ticker;
import lombok.Getter;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

@Getter
public class Program implements Runnable {
    final int WINDOW_WIDTH = 1280;
    final int WINDOW_HEIGHT = 720;
    long time;
    int frameCount;
    Window window = Window.builder().width(WINDOW_WIDTH).height(WINDOW_HEIGHT).title("The House").build();
    ECamera camera = ECamera.builder().position(new Vector3f(0, 25f, 25f)).build();
    FlyCamera flyCamera = FlyCamera.builder().pitch(-90f).build();
    CallbackManager callbackManager = CallbackManager.builder().window(window).camera(camera).build();
    FrameBuffer minimapFB = FrameBuffer.builder().width(WINDOW_WIDTH).height(WINDOW_HEIGHT).build();
    Ticker ticker = new Ticker();

    BasicObject basicObject = BasicObject.builder().position(new Vector3f(0f, 0f, 0f)).mesh(Meshes.BOX_FROM_OBJ).build();
    BasicObject land = BasicObject.builder().scale(new Vector3f(6f,1f,6f)).material(Materials.GRASS).position(new Vector3f(0f, -0.0025f, 0f)).mesh(Meshes.LAND_FROM_OBJ).build();
    BasicObject mountain = BasicObject.builder().material(Materials.DIRT).position(new Vector3f(-200f, -5f, 0f)).scale(new Vector3f(100f,200f,300f)).mesh(Meshes.MOUNTAIN_FROM_OBJ).build();
    BasicObject house = BasicObject.builder().material(Material.builder().texture(Textures.HOUSE).build()).position(new Vector3f(0, 0f, 100f)).rotation(new Vector3f(0,90,0)).scale(new Vector3f(5,3,5)).mesh(Meshes.HOUSE_FROM_OBJ).build();
    Sun sun = Sun.builder().disPlayColor(new Vector3f(1,0.8f,0)).position(new Vector3f(0f, 0f, 0f)).scale(new Vector3f(100000f, 100000f, 100000f)).mesh(Meshes.SPHRERE_FROM_OBJ).build();
    Screen screen = Screen.builder().scale(new Vector3f(0.5f, 1f ,0)).position(new Vector3f(0.5f, 1f, 0)).texture(Textures.GRASS).mesh(Meshes.SCREEN_FROM_OBJ).build();

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
        minimapFB.create();

        // Set up camera
        camera.setAspect((float) window.getWidth() / window.getHeight());
        // Set up fly camera
        flyCamera.setAspect((float) window.getWidth() / window.getHeight());
        flyCamera.attachTo(camera);

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
        // Mirror render
        GL30.glEnable(GL30.GL_DEPTH_TEST);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, minimapFB.getId());
        GL30.glClear(GL30.GL_DEPTH_BUFFER_BIT | GL30.GL_COLOR_BUFFER_BIT | GL30.GL_STENCIL_BUFFER_BIT);
        Renderer.renderLight(sun, flyCamera);
        Renderer.renderBasic(land, flyCamera, sun);
        Renderer.renderBasic(mountain, flyCamera, sun);
        Renderer.renderBasic(house, flyCamera, sun);
        Renderer.renderBasic(basicObject, flyCamera, sun);

        // Main render
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        Renderer.renderLight(sun, camera);
        Renderer.renderBasic(land, camera, sun);
        Renderer.renderBasic(mountain, camera, sun);
        Renderer.renderBasic(house, camera, sun);
        Renderer.renderBasic(basicObject, camera, sun);

        // Show on mini screen
        GL30.glDisable(GL30.GL_DEPTH_TEST);
        Renderer.renderScreen(screen, minimapFB);

        catchGLError();
        window.swapBuffer();
        calcFPS();
    }

    void update() {
        Vector3f ambient = sun.getCurrentAmbient();
        GL20.glClearColor(0.557f * ambient.x, 0.851f * ambient.y, 0.988f * ambient.z, 0f);
        window.update();
    }

    void destroy() {
        minimapFB.destroy();
        ticker.destroy();
        Shaders.destroyAll();
        Textures.destroyAll();
        Meshes.destroyAll();
        callbackManager.destroy();
        window.destroy();
    }

    void catchGLError(){
        int error = GL20.glGetError();
        if (error != GL20.GL_NO_ERROR) {
            System.out.println("OpenGL error: " + error);
        }
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
