package thread;

import java.util.Date;
import java.util.Hashtable;
import java.util.concurrent.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class ThreadTest {
    public static void main(String[] args) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                new RunnableWithExceptionProtection(() -> doSome(), t -> System.out.println("error")), 1, 3, TimeUnit.SECONDS
        );

    }

    private static void doSome() {
        Hashtable hashtable;
        System.out.println(new Date());
        System.out.println(1 / 0);
    }
}
