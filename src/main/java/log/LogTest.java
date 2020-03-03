package log;

import java.io.*;

public class LogTest {
    public static void main(String[] args) throws IOException {
        Thread thread1 = new Thread(new WriterMy());
        Thread thread2 = new Thread(new WriterMy());
        Thread thread3 = new Thread(new WriterMy());

        thread1.start();
        thread2.start();
        thread3.start();
    }

    static class WriterMy implements Runnable{

        @Override
        public void run() {
            File f = new File("d://log.txt");
            if (!f.exists()) {
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(f, true);
                OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8");

                for (long i = 0; i < 200; i++) {
                    writer.write(i + " 2020-01-10 14:22:53 [localhost-startStop-1] INFO o.s.c.b.c.PropertySourceBootstrapConfiguration - Located property source: ConfigMapPropertySource");
                    writer.write("\r\n");
                    Thread.sleep(2);
                }

                writer.close();
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
