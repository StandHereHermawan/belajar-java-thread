package arief.belajar.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {

    @Test
    void testExecutorService() throws InterruptedException {

        var executor = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 25; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(100);
                    System.out.println("Runnable run in thread : " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.awaitTermination(30, TimeUnit.SECONDS);
    }

    @Test
    void testExecutorServiceFix() throws InterruptedException {

        var executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 250; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(100);
                    System.out.println("Iterate : " + executor + " Runnable run in thread : " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);
    }
}
