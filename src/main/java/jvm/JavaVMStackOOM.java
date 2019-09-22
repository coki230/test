package jvm;

/**
 * 直接假死，不要测试
 * vm ages: -Xss2M
 */
public class JavaVMStackOOM {
    private void dontStop() {
        while (true) {

        }
    }
    public void stackLeakByThread() {
        while (true) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOM javaVMStackOOM = new JavaVMStackOOM();
        javaVMStackOOM.stackLeakByThread();
    }
}
