package arief.belajar.java.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class ReactiveStreamTest {

    @Test
    void publish() throws InterruptedException {
        var publisher = new SubmissionPublisher<String>();
        var subscriber1 = new PrintSubscriber("A", 100L);
        var subscriber2 = new PrintSubscriber("B", 50L);
        var subscriber3 = new PrintSubscriber("C", 150L);
        publisher.subscribe(subscriber1);
        publisher.subscribe(subscriber2);
        publisher.subscribe(subscriber3);

        var executor = Executors.newFixedThreadPool(10);
        executor.execute(() -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    Thread.sleep(10L);
                    publisher.submit("Konten-" + i);
                    System.out.println(Thread.currentThread().getName() + " : Send Konten-" + i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        executor.shutdown();
        executor.awaitTermination(10 * 100, TimeUnit.SECONDS);

        Thread.sleep(10 * 1000);
    }

    @Test
    void buffer() throws InterruptedException {
        var publisher = new SubmissionPublisher<String>(Executors.newWorkStealingPool(), 15);
        var subscriber1 = new PrintSubscriber("A", 100L);
        var subscriber2 = new PrintSubscriber("B", 50L);
        var subscriber3 = new PrintSubscriber("C", 150L);
        publisher.subscribe(subscriber1);
        publisher.subscribe(subscriber2);
        publisher.subscribe(subscriber3);

        var executor = Executors.newFixedThreadPool(10);
        executor.execute(() -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    Thread.sleep(10L);
                    publisher.submit("Konten-" + i);
                    System.out.println(Thread.currentThread().getName() + " : Send Konten-" + i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        executor.shutdown();
        executor.awaitTermination(10 * 100, TimeUnit.SECONDS);

        Thread.sleep(10 * 1000);
    }

    @Test
    void processor() throws InterruptedException {

        var publisher = new SubmissionPublisher<String>();
        var processor = new HelloProcessor();
        publisher.subscribe(processor);
        var subscriber = new PrintSubscriber("A", 500L);
        processor.subscribe(subscriber);

        var executor = Executors.newFixedThreadPool(10);
        executor.execute(() -> {
            for (int i = 0; i < 1000; i++) {
                publisher.submit("Konten-" + i);
                System.out.println(Thread.currentThread().getName() + " : Send Konten-" + i);

            }
        });

        Thread.sleep(10*1000);

    }

    public static class PrintSubscriber implements Flow.Subscriber<String> {

        private Flow.Subscription subscription;

        private String name;

        private Long sleep;

        public PrintSubscriber(String name, Long sleep) {
            this.name = name;
            this.sleep = sleep;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            this.subscription.request(1);
        }

        @Override
        public void onNext(String item) {
            try {
                Thread.sleep(sleep);
                System.out.println(Thread.currentThread().getName() + " : " + name + " : " + item);
                this.subscription.request(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
        }

        @Override
        public void onComplete() {
            System.out.println(Thread.currentThread().getName() + " : DONE");
        }
    }

    public static class HelloProcessor extends SubmissionPublisher<String> implements Flow.Processor<String, String> {

        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            this.subscription.request(1);
        }

        @Override
        public void onNext(String item) {
            var value = "Kirim " + item;
            submit(value);
            this.subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {
            throwable.printStackTrace();
        }

        @Override
        public void onComplete() {
            close();
        }
    }

}
