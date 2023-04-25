package arief.belajar.java.thread;

public class SynchronyzedCounter {

    private Long value = 0L;

    public void increment() {
        synchronized (this) {
            value++;
        }
    }

    public Long getValue() {
        return value;
    }
}
