import java.util.concurrent.CountDownLatch;

/**
 * Created by Java Student on 8/25/2014.
 */
public class ThreadFail {
    private static final CountDownLatch start = new CountDownLatch(1);
    public static void main(String[] args) {
        Counter c = new Counter();
        Thread t0 = new Thread(new Runner(c));
        Thread t1 = new Thread(new Runner(c));
        Thread t2 = new Thread(new Runner(c));
        Thread t3 = new Thread(new Runner(c));
        Thread t4 = new Thread(new Runner(c));
        t0.start();
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        start.countDown();
        try {
            t0.join();
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main thread count = " + c.getCount());
    }

    static class Counter {
        private long count;

        public void inc() {
            ++count;
        }

        public long getCount() {
            return count;
        }
    }

    static class Runner implements Runnable {
        private Counter c;
        Runner(Counter c) {
            this.c = c;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 1_000_000; i++) {
                c.inc();
            }
            System.out.println(Thread.currentThread().getName() + " count = " + c.getCount());
        }
    }
}
