package bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.pool.TypePool;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        Main main = new Main();
        main.createNewClassWithClass();
    }

    /**
     * 重新加载类
     */
    private void reloadClass() {
        Foo foo = new Foo();
        ByteBuddyAgent.install();
        new ByteBuddy()
                // 用参数里面的对象重新定义类
                .redefine(Bar.class)
                // 重新定义的类名，类加载器通过名称加载类，也就是重新改写了Foo类
                .name(Foo.class.getName())
                // 生成二进制文件
                .make()
                // 如果不重新指定Foo类的加载策略，Foo类无法感知新生成的对象。因为
                // Foo是被AppClassLoader加载的，而新生成的类需要自己实现的类加载器，而且是AppClassLoader的
                // 子类，所以无法加载，必须把Foo对象加载到Foo对象的加载器里
                .load(Foo.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
        System.out.println(foo.m());
    }

    private void createNewClassWithClass() throws ClassNotFoundException {
        TypePool typePool = TypePool.Default.ofSystemLoader();
        new ByteBuddy()
                .redefine(typePool.describe("foo.Bar").resolve(), ClassFileLocator.ForClassLoader.ofSystemLoader())
                .defineField("qux", String.class)
                .make()
                .load(ClassLoader.getSystemClassLoader());

        System.out.println(Class.forName("foo.Bar").getFields());
    }
}
