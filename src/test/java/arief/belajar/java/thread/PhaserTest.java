package arief.belajar.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PhaserTest {

    @Test
    void countDownLatch() throws InterruptedException {

        final var phaser = new Phaser();
        final var executor = Executors.newFixedThreadPool(10);

        phaser.bulkRegister(5);

        for (int i = 0; i < 5; i++) {
            executor.execute(() -> {
                try {
                    System.out.println("Start Task");
                    Thread.sleep(1000L);
                    System.out.println("End Task");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    phaser.arrive();
                }
            });
        }

        executor.execute(() -> {
            phaser.awaitAdvance(0);
            System.out.println("All task Finished");
        });

        executor.awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    void cyclicBarrier() throws InterruptedException {

        final var phaser = new Phaser();
        final var executor = Executors.newFixedThreadPool(10);

        phaser.bulkRegister(5);

        for (int iterate = 0; iterate < 5; iterate++) {
            executor.execute(() -> {
                System.out.println("Await");
                phaser.arriveAndAwaitAdvance();
                System.out.println("DONE");
            });
        }

        executor.awaitTermination(10,TimeUnit.SECONDS);
        System.out.println("Finished All Task");
    }
}
