package arief.belajar.java.thread;

import org.junit.jupiter.api.Test;

public class ThreadTest {
    @Test
    void mainThread() {
        var name = Thread.currentThread().getName();
        System.out.println(name);
    }

    @Test
    void createThread() {
        Runnable runnable = () -> {
            System.out.println("Hello World from thread : "+ Thread.currentThread().getName());
        };

        var thread = new Thread(runnable);
        thread.start();
        
        System.out.println("Program selesai");
    }

    @Test
    void threadSleep() throws InterruptedException {
        Runnable runnable = () -> {
            try {
                Thread.sleep(2_000L);
                System.out.println("Hello World from thread : "+ Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        var thread = new Thread(runnable);
        thread.start();

        System.out.println("Program Selesai.");

        Thread.sleep(3_000L);
    }

    @Test
    void threadJoin() throws InterruptedException {
        Runnable runnable = () -> {
            try {
                Thread.sleep(2_000L);
                System.out.println("Hello World from thread : "+ Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        var thread = new Thread(runnable);
        thread.start();
        System.out.println("Menunggu Selesai ...");
        thread.join();
        System.out.println("Program Selesai.");

    }

    @Test
    void threadInterruptFalse() throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i <= 10; i++) {
                System.out.println("Runnable ke : "+ i);
                try {
                    Thread.sleep(2_000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        var thread = new Thread(runnable);
        thread.start();
        Thread.sleep(5_000);
        thread.interrupt();
        System.out.println("Menunggu Selesai ...");
        thread.join();
        System.out.println("Program Selesai.");
    }

    @Test
    void threadInterruptTrue() throws InterruptedException {
        Runnable runnable = () -> {
            System.out.println("Program dimulai");
            for (int i = 0; i <= 10; i++) {
                if (Thread.interrupted()){
                    return;
                }
                System.out.println("Percobaan ke : "+ i);
                try {
                    Thread.sleep(2_000L);
                } catch (InterruptedException e) {
                    return;
                }
            }
        };

        var thread = new Thread(runnable);
        thread.start();
        Thread.sleep(9_000);
        thread.interrupt();
        System.out.println("Program di Interupsi.");
        thread.join();
        System.out.println("Program Selesai.");
    }

    @Test
    void threadName() {
        var thread = new Thread(() -> {
            System.out.println("Run in Thread : " + Thread.currentThread().getName());
        });
        thread.setName("Nigga");
        thread.start();
    }

    @Test
    void threadState() throws InterruptedException {
        var thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getState());
            System.out.println("Run in Thread : " + Thread.currentThread().getName());
        });
        thread.setName("Nigga");
        System.out.println(thread.getState());

        thread.start();
        thread.join();
        System.out.println(thread.getState());
    }
}
