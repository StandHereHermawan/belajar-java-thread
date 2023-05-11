package arief.belajar.java.thread;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentMapTest {

    @Test
    void concurrentMap() throws InterruptedException {

        final var countdown = new CountDownLatch(100);
        final var map = new ConcurrentHashMap<Integer, String>();
        final var executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 100; i++) {
            final var index = i;
            executor.execute(() -> {
                try {
                    Thread.sleep(1000);
                    map.putIfAbsent(index, "Data" + index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countdown.countDown();
                }
            });
        }

        executor.execute(() -> {
            try {
                countdown.await();
                map.forEach((integer, s) -> System.out.println(integer+" : "+s));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.awaitTermination(20, TimeUnit.SECONDS);

    }

    @Test
    void testCollection() {

        List<String> list = List.of("Arief", "Karditya", "Hermawan");
        List<String> synchronizedList = Collections.synchronizedList(list);

        System.out.println(synchronizedList);
    }
}
