import com.sun.tools.javac.util.ArrayUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) throws ParseException {
        String aa ="2019-10-09T06:00:00.000Z";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println(df.parse(aa).toLocaleString());

        Float f = new Float(0.7949);
        System.out.println(f * 100);
    }
}
