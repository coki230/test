package jvm;

public class TestClass {
    private int m;
    public int inc() {
        return m + 1;
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("os.name").toLowerCase().contains("windows"));
    }
}
