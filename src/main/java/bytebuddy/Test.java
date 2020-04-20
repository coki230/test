package bytebuddy;

import bytebuddy.foo.*;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.pool.TypePool;

import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.*;

public class Test {
    public static void main(String[] args) throws NoSuchFieldException, InstantiationException, IllegalAccessException {
        Test test = new Test();
        test.superCall();
    }


    /**
     * SuperCall
     * 可以调用执行被拦截的方法
     */
    private void superCall() throws IllegalAccessException, InstantiationException {
        MemoryDatabase loggingDatabase = new ByteBuddy()
                .subclass(MemoryDatabase.class)
                .method(named("load")).intercept(MethodDelegation.to(LoggerInterceptor.class))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance();
        List<String> aaa = loggingDatabase.load("aaa");
        System.out.println(aaa);
    }

    /**
     * 方法替换
     */
    private void modifyMothod() throws IllegalAccessException, InstantiationException {
        String m = new ByteBuddy()
                .subclass(Bar.class)
                .method(named("m")).intercept(MethodDelegation.to(BarMethod.class))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .m();
        System.out.println(m);

    }



    private void multipleMethodFoo() throws IllegalAccessException, InstantiationException {
        MultipleMethodFoo multipleMethodFoo = new ByteBuddy()
                .subclass(MultipleMethodFoo.class)
                .method(isDeclaredBy(MultipleMethodFoo.class)).intercept(FixedValue.value("one"))
                .method(named("foo")).intercept(FixedValue.value("tow"))
                .method(named("foo").and(takesArguments(1))).intercept(FixedValue.value("three"))
                .method(named("num")).intercept(FixedValue.value(2))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance();
        System.out.println(multipleMethodFoo.num());
    }

    private void modifyClass() {
        ByteBuddyAgent.install();
        Foo foo = new Foo();
        System.out.println(Foo.class.getName());
        new ByteBuddy()
                .redefine(Bar.class)
                .name(Foo.class.getName())
                .make()
                .load(Foo.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        System.out.println(foo.m());
    }

    private void overrideToString() throws IllegalAccessException, InstantiationException {
        String toString = new ByteBuddy()
                .subclass(Object.class)
                .name("example.Type")
                .method(named("toString").and(returns(String.class)).and(takesArguments(0))).intercept(FixedValue.value("Hello World"))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded()
                .newInstance()
                .toString()
                ;
        System.out.println(toString);
    }



    private void loadClass() throws NoSuchFieldException {
        // 加载不存在的类
//        TypePool typePool = new TypePool.ClassLoading(new TypePool.CacheProvider.Simple(), );
        TypePool typePool = TypePool.Default.ofPlatformLoader();
        new ByteBuddy()
                .redefine(typePool.describe("foo.Bar").resolve(), ClassFileLocator.ForClassLoader.ofPlatformLoader())
                .defineField("qux", String.class)
                .make()
                .load(ClassLoader.getSystemClassLoader());
        System.out.println(Bar.class.getDeclaredField("qux"));

    }
}
