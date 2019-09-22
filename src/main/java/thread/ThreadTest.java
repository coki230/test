package thread;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadTest {
    public static void main(String[] args) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                new RunnableWithExceptionProtection(() -> doSome(), t -> System.out.println("error")), 1, 3, TimeUnit.SECONDS
        );

    }

    private static void doSome() {
        System.out.println(new Date());
        System.out.println(1 / 0);
    }
}
