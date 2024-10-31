package engine.tick;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ticker implements Runnable {
    private long lastTime;
    private final CopyOnWriteArrayList<Tickable> tickables = new CopyOnWriteArrayList<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private volatile boolean isStop = false;
    public static long tickTimes = 0l;

    @Override
    public void run() {
        lastTime = System.currentTimeMillis();
        while (!isStop) {
            try {
                Thread.sleep(99); // 10 ticks per second
                long now = System.currentTimeMillis();
                float deltaTime = (now - lastTime) / 1000f;
                for (Tickable tickable : tickables) {
                    executor.submit(tickable::onTick);
                }
                tickTimes ++;
                lastTime = now;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Ticker thread interrupted: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }

    public void registerTickable(Tickable tickable) {
        tickables.add(tickable);
    }

    public void registerTickables(Tickable... tickable) {
        for (Tickable t : tickable) {
            registerTickable(t);
        }
    }

    public void start() {
        System.out.println("Ticker is starting...");
        Thread thread = new Thread(this, "tickingThread");
        thread.start();
    }

    public void destroy() {
        isStop = true;
    }
}
