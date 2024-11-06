package engine;

import engine.constant.Materials;
import engine.constant.Meshes;
import engine.constant.Shaders;
import engine.constant.Textures;
import engine.object.*;
import engine.tick.Ticker;
import lombok.Getter;
import org.joml.Vector3f;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;
import org.lwjgl.opengl.GL20;

@Getter
public class Program implements Runnable {
    Window window = Window.builder().width(1024).height(768).title("The House").build();
    ECamera camera = ECamera.builder().position(new Vector3f(0, 25, 25f)).build();
    CallbackManager callbackManager = CallbackManager.builder().window(window).camera(camera).build();
    Ticker ticker = new Ticker();
    long time;
    int frameCount;

    public BasicObject basicObject = BasicObject.builder().position(new Vector3f(0f, 0f, 0f)).mesh(Meshes.BOX_FROM_OBJ).build();
    public BasicObject basicObject1 = BasicObject.builder().position(new Vector3f(2f, 0f, 0f)).mesh(Meshes.BOX_FROM_OBJ).build();
    public BasicObject basicObject2 = BasicObject.builder().position(new Vector3f(-2f, 0f, 0f)).mesh(Meshes.BOX_FROM_OBJ).build();
    public BasicObject land = BasicObject.builder().scale(new Vector3f(6f,1f,6f)).material(Materials.GRASS).position(new Vector3f(0f, -0.0025f, 0f)).mesh(Meshes.LAND_FROM_OBJ).build();
    public BasicObject mountain = BasicObject.builder().material(Materials.DIRT).position(new Vector3f(-200f, -5f, 0f)).scale(new Vector3f(100f,200f,300f)).mesh(Meshes.MOUNTAIN_FROM_OBJ).build();
    public BasicObject house = BasicObject.builder().material(Material.builder().texture(Textures.HOUSE).build()).position(new Vector3f(0, 0f, 100f)).rotation(new Vector3f(0,90,0)).scale(new Vector3f(5,3,5)).mesh(Meshes.HOUSE_FROM_OBJ).build();


    public Sun sun = Sun.builder().disPlayColor(new Vector3f(1,0.8f,0)).position(new Vector3f(0f, 0f, 0f)).scale(new Vector3f(100000f, 100000f, 100000f)).mesh(Meshes.SPHRERE_FROM_OBJ).build();

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
        Renderer.renderBasic(land, camera, sun);
        Renderer.renderBasic(mountain, camera, sun);
        Renderer.renderBasic(house, camera, sun);
        Renderer.renderBasic(basicObject, camera, sun);
        Renderer.renderBasic(basicObject1, camera, sun);
        Renderer.renderBasic(basicObject2, camera, sun);

        window.swapBuffer();
        calcFPS();
    }

    void update() {
        Vector3f ambient = sun.getCurrentAmbient();

        GL20.glClearColor(0.557f * ambient.x, 0.851f * ambient.y, 0.988f * ambient.z, 0f);
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
