import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Test1 {
    public static void main(String[] args) throws ParseException, InterruptedException, IOException {
//        String s = "Sat 14 Mar 2020 04:09:20 PM CST";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy hh:mm:ss aa Z", Locale.US);
//        Date parse = simpleDateFormat.parse(s);
//        System.out.println(parse);
//
//
//        String date = "Wed Aug 01 00:00:00 CST 2012";
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",Locale.US);
//        Date d=sdf.parse(date);
//        sdf=new SimpleDateFormat("yyyyMMdd");
//        System.out.println(sdf.format(d));

        Test1 test1 = new Test1();
//        String exec = test1.exec("helm lint /data/iomp_data/repo/temp/jdk-1.0.1.tgz1592559541355");
//        String exec = test1.exec("ipconfig");
//        System.out.println(exec);
        test1.showCertInfo();
    }


    public void test() {
        List<String> aa = new ArrayList<>();
        aa.add("aa");
        aa.add("bb");
        aa.add("vv");
        String join = StringUtils.join(aa, ";");
        System.out.println(join);

    }



    public void showCertInfo() {
        try {
            //读取证书文件

            String aa = "-----BEGIN CERTIFICATE-----\n" +
                    "MIIDczCCAlugAwIBAgIBRTANBgkqhkiG9w0BAQsFADBtMQswCQYDVQQGEwJDTjEQ\n" +
                    "MA4GA1UECAwHSmlhbmdTdTENMAsGA1UECgwEaW9tcDENMAsGA1UECwwEaW9tcDEN\n" +
                    "MAsGA1UEAwwEaW9tcDEfMB0GCSqGSIb3DQEJARYQaW9tcEBrZWRhY29tLmNvbTAe\n" +
                    "Fw0yMDA2MDgxMTE2MzBaFw0zMDA2MDYxMTE2MzBaMH4xCzAJBgNVBAYTAkNOMQsw\n" +
                    "CQYDVQQIDAJKUzEQMA4GA1UECgwHa2VkYWNvbTENMAsGA1UECwwEcHJvZDEbMBkG\n" +
                    "A1UEAwwSZG9ja2VyLmtlZGFjb20uY29tMSQwIgYJKoZIhvcNAQkBFhVzZWN1cmVA\n" +
                    "Z2xvYmFsc2lnbi5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDV\n" +
                    "QWflJAGCyViCbFTwyF46TqZoVXNAUToYYZDVc1ffCajU7TiFu5u81V/3WO8fjaKq\n" +
                    "+yNHzESFmVRK+cTZchRlPiDxqB0E7HCE1QDupEd/8HGVICKJ076SbchDbC9Ix1Rr\n" +
                    "TS2KfM9vDNOjtJXy0zOwSL7nicLN5jRxI8NSbfn7atccSa/bng10KvfMG2xAgDhD\n" +
                    "x7/aCkdfB83O75GtXrLe0Q6rjRaTuxHm7DCo5KjVR4cNDPXwk0KLVB/suQG4cgAU\n" +
                    "76zJRoV6KTWtfptwUpxugmZ5M2ZIBdHXO7fkLP4eP6s1aannVE3Oj6hvNL/9T0Rk\n" +
                    "tTWwOQ7DDwFp2xO+xcL/AgMBAAGjDTALMAkGA1UdEwQCMAAwDQYJKoZIhvcNAQEL\n" +
                    "BQADggEBADz7JerTv0J8BTTUHQ41C5P2ZtH6UsvqFzDHsm09mtDgoUPUSGpVvrF/\n" +
                    "0oo6IF3L+n3ZXbvpYOrBwQmNlFEn9HvAi8eUlUagGZtj5CkvGew2rpzCmOOdCir2\n" +
                    "XxzT84Kn+fyyqEfgTtHXP4WrG6UB2HfD8ZCl/HiHkkkEWeex/PjU3K8p0JEk1G0S\n" +
                    "VMTi2ttx7F3OWvEXclaQieMG7Ithf3MF7bbc/xN8WNbVaZQ/FDe7KZ9YWAC1Oluv\n" +
                    "8gYF934MEeWwLO6DCgj7bOGFBPuE/tjV0S6XKfGkiFCI+0dfS8lIqU5Ae/Bnv1BF\n" +
                    "37nvH1MeMg1W9yyJln1zDIVHsO6wK2g=\n" +
                    "-----END CERTIFICATE-----\n";

//            File file = new File("D://cert/aaa");
//            InputStream inStream = new FileInputStream(file);


            InputStream inStream = new ByteArrayInputStream(aa.getBytes());

            //创建X509工厂类
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            //创建证书对象
            X509Certificate oCert = (X509Certificate) cf.generateCertificate(inStream);
            inStream.close();
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
            String info = null;
            //获得证书版本
            info = String.valueOf(oCert.getVersion());
            System.out.println("证书版本:" + info);
            //获得证书序列号
            info = oCert.getSerialNumber().toString(16);
            System.out.println("证书序列号:" + info);
            //获得证书有效期
            Date beforedate = oCert.getNotBefore();
            info = dateformat.format(beforedate);
            System.out.println("证书生效日期:" + info);
            Date afterdate = oCert.getNotAfter();
            info = dateformat.format(afterdate);
            System.out.println("证书失效日期:" + info);
            //获得证书主体信息
            info = oCert.getSubjectDN().getName();
            System.out.println("证书拥有者:" + info);
            //获得证书颁发者信息
            info = oCert.getIssuerDN().getName();
            System.out.println("证书颁发者:" + info);
            //获得证书签名算法名称
            info = oCert.getSigAlgName();
            System.out.println("证书签名算法:" + info);


//            byte[] byt = oCert.getExtensionValue("2.5.29.19");
//            String strExt = new String(byt);
//            System.out.println("证书扩展域:" + strExt);
//            byt = oCert.getExtensionValue("1.2.86.11.7.1.8");
//            String strExt2 = new String(byt);
//            System.out.println("证书扩展域2:" + strExt2);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("解析证书出错！");
        }
    }


    public String exec(String command) {
        String returnString = "";
        Process process;
        BufferedReader input;
        try {
            if (System.getProperties().getProperty("os.name").matches("Windows.*$")) {
                process = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", command}); //bat
                process.waitFor();
                input = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("GBK")));
            } else {
                process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", command}); //shell
                process.waitFor();
                input = new BufferedReader(new InputStreamReader(process.getInputStream(), Charset.forName("UTF-8")));
            }
            String line;
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return returnString;
    }
}
