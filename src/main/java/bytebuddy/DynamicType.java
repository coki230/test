package bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.util.function.Function;

public class DynamicType {
    public static class GreetingInterceptor {
        public Object greet(Object arg) {
            return "hello from " + arg;
        }
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(java.util.function.Function.class)
                .method(ElementMatchers.named("apply"))
                .intercept(MethodDelegation.to(new GreetingInterceptor()))
                .make()
                .load(DynamicType.class.getClassLoader())
                .getLoaded();

        Function function = (Function) dynamicType.newInstance();
        Object haha = function.apply(123);
        System.out.println(haha.toString());

    }


}
