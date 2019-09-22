import com.sun.tools.javac.util.ArrayUtils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) {
        String aa ="a,b";
        String[] split = aa.split(",");
        List<String> strings = Arrays.asList(split);
        System.out.println(split.length);
    }
}
