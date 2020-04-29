package io.JavaSocketNIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer {
    public static void main(String[] args) throws IOException {
        NIOServer nioServer = new NIOServer();
        nioServer.select();
    }

    private void select() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress(8888));
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
            while (selectionKeys.hasNext()) {
                SelectionKey next = selectionKeys.next();
                if (next.isAcceptable()) {
                    accept(next);
                } else if (next.isReadable()) {
                    read(next);
                } else if (next.isWritable()) {
                    write(next);
                } else if (next.isConnectable()) {
                    System.out.println("connected.");
                }
                selectionKeys.remove();
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel socketChannel = (ServerSocketChannel)key.channel();
        SocketChannel channel = socketChannel.accept();
        channel.configureBlocking(false);
        channel.register(key.selector(), SelectionKey.OP_ACCEPT, ByteBuffer.allocate(1024));
    }
    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer)key.attachment();
        int read = channel.read(buffer);
        while (read > 0) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.print((char) buffer.get());
            }
            System.out.println();
            buffer.clear();
            read = channel.read(buffer);
        }
        if (read == -1) {
            channel.close();
        }
    }
    private void write(SelectionKey key) throws IOException {
        ByteBuffer buffer = (ByteBuffer)key.attachment();
        SocketChannel channel = (SocketChannel) key.channel();
        buffer.flip();
        while (buffer.hasRemaining()){
            channel.write(buffer);
        }
        buffer.compact();
    }
}
