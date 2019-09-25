package jvm;

/**
 * -XX:+PrintCompilation 打印即时编译为本地代码的方法名
 */
public class PrintCompilation {
    public static final int NUM = 15000;

    public static int doubleValue(int i) {
        for (int j = 0; j < 10000; j++);
        return i * 2;
    }

    public static long calcSum() {
        long sum = 0;
        for (int i = 1; i<=100; i++) {
            sum += doubleValue(i);
        }
        return sum;
    }

    public static void main(String[] args) {
        for (int i = 0; i < NUM; i++) {
            calcSum();
        }
    }
}
