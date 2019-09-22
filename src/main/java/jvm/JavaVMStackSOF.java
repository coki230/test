package jvm;

/**
 * 栈内存容量
 * vm args: -Xss128k
 */
public class JavaVMStackSOF {
    private int stackLen = 1;
    private void stackLeak() {
        stackLen++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOF javaVMStackSOF = new JavaVMStackSOF();
        javaVMStackSOF.stackLeak();
    }
}
