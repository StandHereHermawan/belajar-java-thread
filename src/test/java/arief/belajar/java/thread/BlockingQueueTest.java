package arief.belajar.java.thread;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.concurrent.*;

public class BlockingQueueTest {

    @Test
    void arrayBlockingQueue() throws InterruptedException {
        var queue = new ArrayBlockingQueue<>(5);
        var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    queue.put("Data");
                    System.out.println("Data has been putted.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(1000L);
                    var value = queue.take();
                    System.out.println("Receive Data : " + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(15, TimeUnit.SECONDS);
    }

    @Test
    void LinkedBlockingQueue() throws InterruptedException {
        var queue = new LinkedBlockingQueue<String>();
        var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    queue.put("Data");
                    System.out.println("Data has been putted.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(1000L);
                    var value = queue.take();
                    System.out.println("Receive Data : " + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(15, TimeUnit.SECONDS);
    }

    @Test
    void priorityBlockingQueue() throws InterruptedException {
        var queue = new PriorityBlockingQueue<Integer>(10, Comparator.reverseOrder());
        var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            final var index = i;
            executor.execute(() -> {
                queue.put(index);
                System.out.println("Data has been putted.");
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(1000L);
                    var value = queue.take();
                    System.out.println("Receive Data : " + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(15, TimeUnit.SECONDS);
    }

    @Test
    void delayedQueue() throws InterruptedException {
        final var queue = new DelayQueue<ScheduledFuture<String>>();
        final var executor = Executors.newFixedThreadPool(20);
        final var executorScheduled = Executors.newScheduledThreadPool(10);

        for (int i = 1; i <= 10; i++) {
            final var index = i;
            queue.put(executorScheduled.schedule(() -> "Data " + index, i, TimeUnit.SECONDS));
        }

        executor.execute(() -> {
            while (true) {
                try {
                    var value = queue.take();
                    System.out.println("Receive Data : " + value.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(15, TimeUnit.SECONDS);
    }

    @Test
    void synchronousQueue() throws InterruptedException {
        final var queue = new SynchronousQueue<String>();
        final var executors = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            final var index = i;
            executors.execute(() -> {
                try {
                    queue.put("Data-" + index);
                    System.out.println("Data : " + index + " has been inputted");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executors.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(500L);
                    var value = queue.take();
                    System.out.println("Receive Data : " + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        executors.awaitTermination(15, TimeUnit.SECONDS);
    }

    @Test
    void blockingDeque() throws InterruptedException {
        final var queue = new LinkedBlockingDeque<String>();
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            final var index = i;
            try {
                queue.putLast("Data-" + index);
                System.out.println("Finished put data : " + index);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    var value = queue.takeFirst();
                    System.out.println("Receive data : " + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(15, TimeUnit.SECONDS);
    }

    @Test
    void transferQueue() throws InterruptedException {
        final var queue = new LinkedTransferQueue<String>();
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            final var index = i;
            executor.execute(() -> {
                try {
                    queue.transfer("Data-" + index);
                    System.out.println("Finished put data : " + index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    var value = queue.take();
                    System.out.println("Receive data : " + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(15, TimeUnit.SECONDS);
    }
}
