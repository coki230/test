package es;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class TestJava8 {
    public static void main(String[] args) {
        final List<String> friends = Arrays.asList("Brian", "Nate", "Neal", "Raju", "Sara", "Scott");
        // method 1
//        friends.forEach(new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(s);
//            }
//        });
        // method 2
//        friends.forEach((String name) -> {
//            System.out.println(name);
//        });
        // method 3
//        friends.forEach((name) -> {
//            System.out.println(name);
//        });
        // method 4
//        friends.forEach(name -> {
//            System.out.println(name);
//        });
        // method 5
        friends.forEach(System.out::println);
    }
}
