package engine;

import engine.camera.Camera;
import engine.camera.CameraMap;
import engine.constant.Materials;
import engine.constant.Meshes;
import engine.constant.Shaders;
import engine.constant.Textures;
import engine.input.CameraInput;
import engine.lifecycle.FrameBuffer;
import engine.object.*;
import engine.tick.Ticker;
import lombok.Getter;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.Optional;

@Getter
public class Program implements Runnable {
    final int WINDOW_WIDTH = 1280;
    final int WINDOW_HEIGHT = 720;
    long time;
    int frameCount;
    Window window = Window.builder().width(WINDOW_WIDTH).height(WINDOW_HEIGHT).title("The House").build();
    Camera camera = Camera.builder().position(new Vector3f(0, 25f, 25f)).build();
    CameraInput cameraInput = CameraInput.builder().camera(camera).window(window).build();
    CameraMap cameraMap = CameraMap.builder().zFar(2000f).zNear(0.1f).followOject(Optional.ofNullable(camera)).build();
    CallbackManager callbackManager = CallbackManager.builder().program(this).build();
    FrameBuffer minimapFB = FrameBuffer.builder().width(WINDOW_WIDTH).height(WINDOW_HEIGHT).build();
    Ticker ticker = new Ticker();

    BasicObject basicObject = BasicObject.builder().position(new Vector3f(0f, 0f, 0f)).mesh(Meshes.BOX_FROM_OBJ).build();
    BasicObject land = BasicObject.builder().scale(new Vector3f(6f,1f,6f)).material(Materials.GRASS).position(new Vector3f(0f, -0.0025f, 0f)).mesh(Meshes.LAND_FROM_OBJ).build();
    BasicObject mountain = BasicObject.builder().material(Materials.DIRT).position(new Vector3f(-200f, -5f, 0f)).scale(new Vector3f(100f,200f,300f)).mesh(Meshes.MOUNTAIN_FROM_OBJ).build();
    BasicObject house = BasicObject.builder().material(Material.builder().texture(Textures.HOUSE).build()).position(new Vector3f(0, 0f, 100f)).rotation(new Vector3f(0,90,0)).scale(new Vector3f(5,3,5)).mesh(Meshes.HOUSE_FROM_OBJ).build();
    Sun sun = Sun.builder().disPlayColor(new Vector3f(1,0.8f,0)).position(new Vector3f(0f, 0f, 0f)).scale(new Vector3f(100000f, 100000f, 100000f)).mesh(Meshes.SPHRERE_FROM_OBJ).build();
    Screen screen = Screen.builder().scale(new Vector3f(1f * WINDOW_HEIGHT/WINDOW_WIDTH,1f,0)).position(new Vector3f(-1f,1,0)).mesh(Meshes.SCREEN_FROM_OBJ).build();

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

        ticker.registerTickable(sun);

        // For calc FPS
        time = System.currentTimeMillis();
    }

    public void start() {
        System.out.println("Program started");
        new Thread(this, "programThread").start();
    }

    void render() {
        GL30.glEnable(GL30.GL_DEPTH_TEST);
        // Main render
        Renderer.renderLight(sun, camera);
        Renderer.renderBasic(land, camera, sun);
        Renderer.renderBasic(mountain, camera, sun);
        Renderer.renderBasic(house, camera, sun);
        Renderer.renderBasic(basicObject, camera, sun);

        // Mirror render
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, minimapFB.getId());
        GL20.glClearColor(0.1f, 0.3f, 0.5f, 1f);
        GL30.glClear(GL30.GL_DEPTH_BUFFER_BIT | GL30.GL_COLOR_BUFFER_BIT | GL30.GL_STENCIL_BUFFER_BIT);

        Renderer.renderLight(sun, cameraMap);
        Renderer.renderBasic(land, cameraMap, sun);
        Renderer.renderBasic(mountain, cameraMap, sun);
        Renderer.renderBasic(house, cameraMap, sun);
        Renderer.renderBasic(basicObject, cameraMap, sun);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);

        // Show on mini screen
        GL30.glDisable(GL30.GL_DEPTH_TEST);
        screen.attachFrameBuffer(minimapFB);
        Renderer.renderScreen(screen);

        catchGLError();
        window.swapBuffer();
        calcFPS();
    }

    void update() {
        Vector3f ambient = sun.getCurrentAmbient();
        GL20.glClearColor(0.557f * ambient.x, 0.851f * ambient.y, 0.988f * ambient.z, 0f);
        window.update();

        // handle input
        cameraInput.handle();
    }

    void destroy() {
        cameraInput.destroy();
        minimapFB.destroy();
        ticker.destroy();
        Shaders.destroyAll();
        Textures.destroyAll();
        Meshes.destroyAll();
        callbackManager.destroy();
        window.destroy();
    }

    void onReshape(long w, int width, int height){
        window.reshape(w, width, height);
        System.out.println("Window size has changed");

        // Update camera (matrix)
        camera.setAspect((float) width / height);
        camera.setShouldProjectionUpdate(true);

    }

    void onScale(long w, double deltaX, double deltaY){
        // Update camera (zoom)
        float fov = camera.getFov();
        fov -= (float) deltaY;

        if (fov < 1.0f)
            camera.setFov(1.0f);
        else if (fov > 45.0f)
            camera.setFov(45.0f);
        else
            camera.setFov(fov);

        camera.setAspect((float) window.getWidth()/ window.getHeight());
        camera.setShouldProjectionUpdate(true);
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
