package com.daop.javabase.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: nio
 * @BelongsPackage: com.daop.io
 * @Description: Nio服务端
 * @DATE: 2020-10-30
 * @AUTHOR: Administrator
 **/
public class NioServerDemo {
    public static final int PORT = 8888;
    public static final int TIMEOUT = 0;
    public static final int BUFFER_SIZE = 1024;

    static ExecutorService threadPool = new ThreadPoolExecutor(10,
            20,
            3L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(10),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void main(String[] args) {
        threadPool.execute(new NioServer());

    }


    public static class NioServer implements Runnable {

        @Override
        public void run() {
            while (true) {
                selector();
            }
        }
    }

    public static void selector() {
        Selector selector = null;
        ServerSocketChannel ssc = null;
        try {

            selector = Selector.open();
            //开启ServerSocket通道
            ssc = ServerSocketChannel.open();
            ssc.socket().bind(new InetSocketAddress(PORT));
            ssc.configureBlocking(false);
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                if (selector.select(TIMEOUT) == 0) {
                    System.out.println("==");
                    continue;
                }
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();

                    if (key.isAcceptable()) {
                        handleAccept(key);
                    }
                    if (key.isReadable()) {
                        handleRead(key);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (selector != null) {
                    selector.close();
                }
                if (ssc != null) {
                    ssc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void handleCancel(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        System.out.println("RemoteAddress: " + sc.getRemoteAddress() + " cancel connection......");
        key.cancel();
    }

    private static void handleRead(SelectionKey key) throws IOException {
        try {
            SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer bf = (ByteBuffer) key.attachment();
            long bytesRead = sc.read(bf);
            while (bytesRead > 0) {
                bf.flip();
                while (bf.hasRemaining()) {
                    System.out.print((char) bf.get());
                }
                System.out.println();
                bf.clear();
                bytesRead = sc.read(bf);
                handleWrite(key, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(System.currentTimeMillis()));
            }
            if (bytesRead == -1) {
                sc.close();
            }
        } catch (IOException e) {
            handleCancel(key);
//            e.printStackTrace();
        }
    }

    private static void handleWrite(SelectionKey key, Object obj) throws IOException {
        ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
        SocketChannel sc = ssChannel.accept();
        ByteBuffer bf = ByteBuffer.allocateDirect(BUFFER_SIZE);
        bf.put(obj.toString().getBytes());
        bf.flip();
        sc.write(bf);
    }

    private static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
        SocketChannel sc = ssChannel.accept();
        System.out.println("Remote Address: " + sc.getRemoteAddress() + " connection......");
        sc.configureBlocking(false);
        sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUFFER_SIZE));
    }
}
