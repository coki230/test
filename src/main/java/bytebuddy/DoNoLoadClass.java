package bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.pool.TypePool;

public class DoNoLoadClass {
    public static void main(String[] args) throws NoSuchFieldException {
        TypePool typePool = TypePool.Default.ofPlatformLoader();
        new ByteBuddy()
                .redefine(typePool.describe("bytebuddy.foo.Foo").resolve(),
                        ClassFileLocator.ForClassLoader.ofPlatformLoader())
                .defineField("qux", String.class)
                .make()
                .load(ClassLoader.getSystemClassLoader());
        System.out.println(bytebuddy.foo.Foo.class.getDeclaredField("qux"));
    }
}
