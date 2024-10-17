import org.lwjgl.glfw.Callbacks;

import static org.lwjgl.glfw.GLFW.*;
public class Program implements Runnable{
    Window window;
    long time;

    @Override
    public void run() {
        init();
        loop();
    }

    void loop(){
        while(!window.shouldClose()){
            update();
            render();
        }

        destroy();
    }

    void init(){
        window = new Window(1024, 768, "The House"); // 4:3 ratio
        window.create();
    }

    void start(){
        System.out.println("Program started");
        new Thread(this, "program").start();
    }

    void render(){
        window.render();
    }

    void update(){
        window.update();
    }

    void destroy(){
        window.destroy();
    }
}
