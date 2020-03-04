package reference;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class TestWeakReference {


    public static void main(String[] args) {

        Car car = new Car(22000,"silver");
        SoftReference<Car> weakCar = new SoftReference<Car>(car);
        int i=0;
        while(true){
            if(weakCar.get()!=null){
                i++;
                System.out.println("Object is alive for "+i+" loops - "+weakCar);
            }else{
                System.out.println("Object has been collected.");
                break;
            }
        }
    }
}