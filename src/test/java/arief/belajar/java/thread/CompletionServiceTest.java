package arief.belajar.java.thread;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.*;

public class CompletionServiceTest {

    private Random random = new Random();

    @Test
    void testCompletionService() throws InterruptedException {

        var executor = Executors.newFixedThreadPool(10);
        var completionService = new ExecutorCompletionService<String>(executor);

        //submit task
        Executors.newSingleThreadExecutor().execute(() -> {
            for (int iterate = 0; iterate < 100; iterate++) {
                final var index = iterate;
                completionService.submit(() -> {
                    Thread.sleep(random.nextInt(2000));
                    return "Task-" + index;
                });
            }
        });

        //poll task
        Executors.newSingleThreadExecutor().execute(() -> {
            while (true) {
                try {
                    Future<String> future = completionService.poll(5, TimeUnit.SECONDS);
                    if (future == null) {
                        break;
                    } else {
                        System.out.println(future.get());
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    break;
                }
            }
        });

        //executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);
    }
}
