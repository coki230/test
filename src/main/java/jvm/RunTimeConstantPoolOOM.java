package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * VM args: -XX:PermSize=10M -XX:MaxPermSize=10M
 */
public class RunTimeConstantPoolOOM {
    public static void main(String[] args) {
        // 使用list保持常量池，避免full gc回收
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}
