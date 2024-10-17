public class Program implements Runnable {
    Window window;
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
    }

    void start() {
        System.out.println("Program started");
        new Thread(this, "program").start();
    }

    void render() {
        window.render();
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
