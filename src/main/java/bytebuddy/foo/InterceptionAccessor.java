package bytebuddy.foo;

public interface InterceptionAccessor {
    Interceptor getInterceptor();
    void setInterceptor(Interceptor interceptor);
}
