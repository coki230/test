package bytebuddy;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;

public class Interceptor {
    @RuntimeType
    public static Object intercept(@RuntimeType Object value) {
        System.out.println("Invoked method with: " + value);
        return value;
    }
}