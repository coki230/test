package io.JavaSocketIO;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IOServer {
    public static void main(String[] args) throws IOException {
        int port = 8888;
        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("wait until the client come.");
        Socket accept = null;
        InputStream inputStream = null;
        try {
            while (true) {
                accept = serverSocket.accept();
                // get the input
                inputStream = accept.getInputStream();
                byte[] bytes = new byte[1024];
                int len;
                StringBuilder stringBuilder = new StringBuilder();
                while ((len = inputStream.read(bytes)) != -1) {
                    stringBuilder.append(new String(bytes, 0, len, "UTF-8"));
                }

                System.out.println("get info the info is " + stringBuilder);
            }
        } finally {
            inputStream.close();
            accept.close();
            serverSocket.close();
        }
    }
}
