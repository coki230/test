import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.locks.ReentrantLock;

public class Test1 {
    public static void main(String[] args) throws ParseException {
        String s = "Sat 14 Mar 2020 04:09:20 PM CST";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy hh:mm:ss aa Z", Locale.US);
        Date parse = simpleDateFormat.parse(s);
        System.out.println(parse);


        String date = "Wed Aug 01 00:00:00 CST 2012";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",Locale.US);
        Date d=sdf.parse(date);
        sdf=new SimpleDateFormat("yyyyMMdd");
        System.out.println(sdf.format(d));
    }
}
