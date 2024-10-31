package engine.tick;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ticker implements Runnable {
    private long lastTime = System.currentTimeMillis();
    private final CopyOnWriteArrayList<Tickable> tickables = new CopyOnWriteArrayList<>();
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private volatile boolean isStop = false;
    private static long tickTime = 0l;

    @Override
    public void run() {
        while (!isStop) {
            try {
                long now = System.currentTimeMillis();
                float deltaTime = (now - lastTime) / 1000.0f;
                Thread.sleep(99); // 10 ticks per second

                for (Tickable tickable : tickables) {
                    executor.submit(tickable::onTick);
                }

                tickTime ++;
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
