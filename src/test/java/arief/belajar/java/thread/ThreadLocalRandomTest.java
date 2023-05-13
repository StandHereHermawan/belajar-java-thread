package arief.belajar.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ThreadLocalRandomTest {

    @Test
    void test() throws InterruptedException {

        final var executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 100; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(1000);
                    var number = ThreadLocalRandom.current().nextInt();
                    System.out.println(number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.awaitTermination(10, TimeUnit.SECONDS);

    }

    @Test
    void stream() throws InterruptedException {
        final var executor = Executors.newFixedThreadPool(10);

        executor.execute(() -> {
            ThreadLocalRandom
                    .current()
                    .ints(1000,0,1000)
                    .forEach(System.out::println);
        });

        executor.shutdown();
        executor.awaitTermination(20,TimeUnit.SECONDS);
    }
}
