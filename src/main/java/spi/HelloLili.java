package spi;

public class HelloLili implements Hello {
    @Override
    public void sayHi() {
        System.out.println("Lili Hi.");
    }
}
