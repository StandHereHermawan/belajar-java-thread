package arief.belajar.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadpoolTest {

    @Test
    void createTP() {

        var minThread = 10;
        var maxThread = 100;
        var alive = 1;
        var aliveTime = TimeUnit.MINUTES;
        var queue = new ArrayBlockingQueue<Runnable>(100);

        var executor = new ThreadPoolExecutor(
                minThread,
                maxThread,
                alive,
                aliveTime,
                queue
        );
    }

    @Test
    void executeRunnable() throws InterruptedException {

        var minThread = 10;
        var maxThread = 100;
        var alive = 1;
        var aliveTime = TimeUnit.MINUTES;
        var queue = new ArrayBlockingQueue<Runnable>(100);

        var executor = new ThreadPoolExecutor(
                minThread,
                maxThread,
                alive,
                aliveTime,
                queue
        );

        Runnable runnable = () -> {
            try {
                Thread.sleep(5000);
                System.out.println("Runnable from thread : " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        executor.execute(runnable);

        Thread.sleep(6000);
    }

    @Test
    void shutdown() throws InterruptedException {

        var minThread = 10;
        var maxThread = 100;
        var alive = 1;
        var aliveTime = TimeUnit.MINUTES;
        var queue = new ArrayBlockingQueue<Runnable>(1000);

        var executor = new ThreadPoolExecutor(
                minThread,
                maxThread,
                alive,
                aliveTime,
                queue
        );

        for (int iterate = 0; iterate < 500; iterate++) {
            final var task = iterate;
            Runnable runnable = () -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("Task : " + task + " from thread : " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            executor.execute(runnable);

        }

//        executor.shutdown();
//        executor.shutdownNow();
        executor.awaitTermination(1, TimeUnit.MINUTES);
    }

    @Test
    void rejected() throws InterruptedException {

        var minThread = 10;
        var maxThread = 100;
        var alive = 1;
        var aliveTime = TimeUnit.MINUTES;
        var queue = new ArrayBlockingQueue<Runnable>(100);
        var logRejectedExecutionHandler = new LogRejectedExecutionHandler();

        var executor = new ThreadPoolExecutor(
                minThread,
                maxThread,
                alive,
                aliveTime,
                queue,
                logRejectedExecutionHandler
        );

        for (int iterate = 0; iterate < 500; iterate++) {
            final var task = iterate;
            Runnable runnable = () -> {
                try {
                    Thread.sleep(500);
                    System.out.println("Task : " + task + " from thread : " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            executor.execute(runnable);

        }

//        executor.shutdown();
//        executor.shutdownNow();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    public static class LogRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
            System.out.println("Task: " + runnable + " is rejected ");
        }
    }

}