package docker;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class Testa {
    public static void main(String[] args) {
        try {
            Process p = Runtime.getRuntime().exec("curl -k --cert ./client.cert --key ./client.key  https://10.68.7.151:15000/v2/_catalog");
            InputStreamReader ir = new InputStreamReader(p.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);      //创建IO管道，准备输出命令执行后的显示内容
            String line;
            while ((line = input.readLine()) != null) {     //按行打印输出内容
                System.out.println(line);
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
