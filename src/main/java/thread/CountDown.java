package thread;

import java.util.concurrent.*;

public class CountDown {
    public static void main(String[] args) throws InterruptedException {
        CountDown countDown = new CountDown();
        countDown.countDownLatchTest();
    }

    private void cyclicBarrierTest() {
        ExecutorService pool = Executors.newCachedThreadPool();
        int size = 3;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(size, () -> {
            System.out.println(size + "位运动员都准备好了，可以起跑！");
            pool.shutdownNow();
        });

        for (int i = 0; i < size; i++) {
            int index = i;
            pool.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("第" + index + "位运动员准备好了");
                try {
                    cyclicBarrier.await();
                    System.out.println("第" + index + "开始跑了");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void countDownLatchTest() throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        CountDownLatch countDownLatch = new CountDownLatch(3);
        int size = 3;

        for (int i = 0; i < size; i++) {
            int index = i;
            pool.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(index);
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("第" + index + "位运动员准备好了");
            });
        }
        countDownLatch.await();
        System.out.println(size + "位运动员都准备好了，可以起跑!");
    }
}
