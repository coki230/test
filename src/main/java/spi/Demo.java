package spi;

import java.util.Iterator;
import java.util.ServiceLoader;

public class Demo {
    public static void main(String[] args) {
        ServiceLoader<Hello> hellos = ServiceLoader.load(Hello.class);
        Iterator<Hello> iterator = hellos.iterator();
        while (iterator.hasNext()) {
            Hello next = iterator.next();
            next.sayHi();
        }
    }
}
