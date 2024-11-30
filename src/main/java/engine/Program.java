package engine;

import engine.camera.Camera;
import engine.camera.CameraMiniMap;
import engine.camera.CameraShadow;
import engine.constant.Materials;
import engine.constant.Meshes;
import engine.constant.Shaders;
import engine.constant.Textures;
import engine.input.CameraInput;
import engine.lifecycle.MiniMapFrameBuffer;
import engine.lifecycle.ShadowFrameBuffer;
import engine.lighting.Lighting;
import engine.lighting.PointLight;
import engine.object.*;
import engine.scene.EScene;
import engine.tick.Ticker;
import lombok.Getter;
import org.joml.Math;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

@Getter
public class Program implements Runnable {
    final int WINDOW_WIDTH = 1280;
    final int WINDOW_HEIGHT = 720;
    long time;
    int frameCount;
    Vector3f lightPos1 = new Vector3f(2.254E+1f,  1.638E+2f, -1.357E+2f);

    Window window = Window.builder().width(WINDOW_WIDTH).height(WINDOW_HEIGHT).title("The House").build();

    Camera camera = Camera.builder().position(new Vector3f(1.744E+2f,  2.048E+2f,  3.776E+2f)).yaw(-120).pitch(-20).build();
    CameraInput cameraInput = CameraInput.builder().camera(camera).window(window).build();

    CameraShadow cameraShadow = CameraShadow.builder().up(new Vector3f(0, 1f, 0f)).position(lightPos1).forward(new Vector3f(2.230E+1f -2.448E+0f -1.358E+1f)).build();

    CameraMiniMap cameraMiniMap = CameraMiniMap.builder().zFar(2000f).zNear(0.1f).followOject(camera).build();
    CallbackManager callbackManager = CallbackManager.builder().program(this).build();
    MiniMapFrameBuffer minimapFB = MiniMapFrameBuffer.builder().width(WINDOW_WIDTH).height(WINDOW_HEIGHT).build();
    ShadowFrameBuffer shadowFB = ShadowFrameBuffer.builder().width(4096).height(4096).build();
    Ticker ticker = new Ticker();

    BasicObject land = BasicObject.builder().scale(new Vector3f(6f, 1f, 6f)).material(Materials.GRASS).position(new Vector3f(0f, -0.0025f, 0f)).mesh(Meshes.LAND_FROM_OBJ).build();
    BasicObject mountain = BasicObject.builder().material(Materials.DIRT).position(new Vector3f(-200f, -5f, 0f)).scale(new Vector3f(100f,200f,300f)).mesh(Meshes.MOUNTAIN_FROM_OBJ).build();
    BasicObject house = BasicObject.builder().material(Material.builder().texture(Textures.HOUSE).build()).position(new Vector3f(0, 0f, 0f)).rotation(new Vector3f(0)).scale(new Vector3f(2)).mesh(Meshes.HOUSE_FROM_OBJ).build();
    Sun sun = Sun.builder().zLimit(false).material(Materials.SUN).position(new Vector3f(0f, 0f, 0f)).scale(new Vector3f(50_000f, 50_000f, 50_000f)).mesh(Meshes.SUN_FROM_OBJ).build();
    Screen screen = Screen.builder().scale(new Vector3f(1f * WINDOW_HEIGHT/WINDOW_WIDTH,1f,0)).position(new Vector3f(-1f,1,0)).mesh(Meshes.SCREEN_FROM_OBJ).build();
    Lamp lamp = Lamp.builder().material(null).position(lightPos1).forward(new Vector3f(0, -1,0)).pointLight(PointLight.builder().isActive(true).position(lightPos1).build()).mesh(Meshes.SUN_FROM_OBJ).build();
    BasicObject cylinder = BasicObject.builder().material(Material.builder().texture(Textures.HOUSE).build()).scale(new Vector3f(4f, 3f, 4f)).position(new Vector3f(50, 0f, 0f)).mesh(Meshes.CYLINDER_FROM_OBJ).build();
    Spin spin = Spin.builder().material(Material.builder().texture(Textures.HOUSE).build()).position(new Vector3f(50, 80f, 5f)).mesh(Meshes.SPIN_FROM_OBJ).build();


    EScene mainScene = EScene.builder().sceneAction(()->{
        Renderer.renderLight(sun, camera);
        Renderer.renderBasic(land, camera, cameraShadow.getSpaceMatrix());
        Renderer.renderBasic(mountain, camera, cameraShadow.getSpaceMatrix());
        Renderer.renderBasic(house, camera, cameraShadow.getSpaceMatrix());
        Renderer.renderBasic(cylinder, camera, cameraShadow.getSpaceMatrix());
        Renderer.renderBasic(spin, camera, cameraShadow.getSpaceMatrix());
        Renderer.renderLight(lamp, camera);
    }).build();

    EScene minimapScene = EScene.builder().sceneAction(()->{
        Renderer.renderLight(sun, cameraMiniMap);
        Renderer.renderBasic(land, cameraMiniMap, null);
        Renderer.renderBasic(mountain, cameraMiniMap, null);
        Renderer.renderBasic(house, cameraMiniMap, null);
        Renderer.renderBasic(cylinder, cameraMiniMap, null);
        Renderer.renderBasic(spin, cameraMiniMap, null);
        Renderer.renderLight(lamp, cameraMiniMap);
    }).build();

    EScene shadowScene = EScene.builder().sceneAction(()->{
        Renderer.renderBasic(land, cameraShadow, Shaders.DEPTH_FETCH_SHADER, null);
        Renderer.renderBasic(mountain, cameraShadow, Shaders.DEPTH_FETCH_SHADER, null);
        Renderer.renderBasic(house, cameraShadow, Shaders.DEPTH_FETCH_SHADER, null);
        Renderer.renderBasic(cylinder, cameraShadow, Shaders.DEPTH_FETCH_SHADER, null);
        Renderer.renderBasic(spin, cameraShadow, Shaders.DEPTH_FETCH_SHADER, null);
    }).build();

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
        shadowFB.create();

        // Set up camera
        camera.setAspect((float) window.getWidth() / window.getHeight());

        // Set up ticker
        ticker.registerTickable(sun);
        ticker.registerTickable(spin);

        // Set up lighting
        Lighting.DIRECTIONAL_LIGHT = sun.getSunLight();
        Lighting.POINT_LIGHTS.add(lamp.getPointLight());

        // For calc FPS
        time = System.currentTimeMillis();
    }

    public void start() {
        System.out.println("Program started");
        new Thread(this, "programThread").start();
    }

    void render() {
        GL30.glEnable(GL30.GL_DEPTH_TEST);
        // Mirror render
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, minimapFB.getId());
        GL30.glClear(GL30.GL_DEPTH_BUFFER_BIT | GL30.GL_COLOR_BUFFER_BIT | GL30.GL_STENCIL_BUFFER_BIT);
        minimapScene.render();
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);

        GL30.glViewport(0, 0, 4096, 4096);
        // Shadow render
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, shadowFB.getId());
        GL30.glClear(GL30.GL_DEPTH_BUFFER_BIT);
        GL30.glCullFace(GL30.GL_BACK);
        shadowScene.render();
        GL30.glCullFace(GL30.GL_FRONT);
        GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);

        // Main render
        GL30.glViewport(0, 0, window.getWidth(), window.getHeight());
        GL30.glClear(GL30.GL_DEPTH_BUFFER_BIT | GL30.GL_COLOR_BUFFER_BIT | GL30.GL_STENCIL_BUFFER_BIT);
        Renderer.shadowFB = shadowFB;
        mainScene.render();

        // Show on mini screen
        GL30.glDisable(GL30.GL_DEPTH_TEST);
        screen.attachFrameBuffer(minimapFB);
        Renderer.renderScreen(screen);

        catchGLError();
        window.swapBuffer();
        calcFPS();
    }

    void update() {
        window.update();
        calcSkyColor();
        // handle input
        cameraInput.handle();
    }

    void calcSkyColor(){
        float sinSun = Math.sin(Math.toRadians(sun.getAngleXY()));
        float attention = Math.clamp(0.1f,1,sinSun);
        GL20.glClearColor(0.902f*attention, attention, 0.984f*attention, 1f);
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
