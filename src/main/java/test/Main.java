package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws ParseException {
//        A a = new A();
//        a.setName("lili");
//
//        B b = (B) a;
//        b.setAge(2);
//
//        System.out.println(b.getName());
        String aa = "2019-05-31T00:00:00.000Z";
        aa = aa.replace("T", " ");
        aa = aa.substring(0, aa.length() - 5);
        System.out.println(aa);


        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
        Date date = sdf.parse("2019-01-02");
        System.out.println(date);
    }
}
