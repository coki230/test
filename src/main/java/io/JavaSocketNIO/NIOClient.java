package io.JavaSocketNIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress(8888));
        try {
            if (channel.finishConnect()) {
                int i = 0;
                while (true) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(50);
                    String info = " \ni am the " + i++ + " information of the client";
                    byteBuffer.put(info.getBytes("UTF-8"));
                    byteBuffer.flip();
                    if (i < 8) {
                        System.out.println(byteBuffer);
                        channel.write(byteBuffer);
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            channel.close();
        }

    }
}
