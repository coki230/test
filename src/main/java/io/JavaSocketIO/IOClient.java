package io.JavaSocketIO;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class IOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",8888);
        OutputStream outputStream = socket.getOutputStream();
        String hello = "你好，just a test.";
        outputStream.write(hello.getBytes("UTF-8"));

        outputStream.close();
        socket.close();
    }
}
