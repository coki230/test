package spi;

public class HelloLucy implements Hello {
    @Override
    public void sayHi() {
        System.out.println("Lucy Hi.");
    }
}
