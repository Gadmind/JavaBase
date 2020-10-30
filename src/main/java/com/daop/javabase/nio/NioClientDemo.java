package com.daop.javabase.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: nio
 * @BelongsPackage: com.daop.io
 * @Description:
 * @DATE: 2020-10-30
 * @AUTHOR: Administrator
 **/
public class NioClientDemo {
    static ExecutorService threadPool = new ThreadPoolExecutor(10,
            20,
            3L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(10),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void main(String[] args) {
        NioClient nioClient = new NioClient("127.0.0.1", 8888);
        threadPool.execute(() -> {
            nioClient.read();
            try{
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String msg = sc.nextLine();
            nioClient.write(msg);
        }
    }

    public static class NioClient {
        private String host;
        private int port;
        private SocketChannel socketChannel;
        private Selector selector;
        private String username;


        NioClient(String host, int port) {
            this.host = host;
            this.port = port;
            try {
                //打开一个通道，通道的另一头是这个服务端
                //服务端的Selector中配置了服务端的ServerSocketChannel，与该ServerSocketChannel有关联的所有SocketChannel通道发送数据，都会默认把自己（当前通道）给放入Selector中。
                socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
                //设置通道为非阻塞通道
                socketChannel.configureBlocking(false);
                //打开一个选择器
                selector = Selector.open();
                //这个selector选择器监听该通道的读方法，这个通道为接收服务端消息的通道
                socketChannel.register(selector, SelectionKey.OP_READ);
                //得到username，ip地址:端口号
                username = socketChannel.getLocalAddress().toString().substring(1);
                System.out.println(username + " is ok...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void read() {
            try {
                //等待监听的事件发生，register(selector, SelectionKey.OP_READ);
                selector.select();
                //得到监听到的事件通道
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    //这个通道为服务端发送数据的通道
                    SocketChannel channel = (SocketChannel) key.channel();
                    System.out.println("本地地址：" + channel.getLocalAddress());
                    ByteBuffer buffer = ByteBuffer.allocate(1024 * 16);
                    int read = channel.read(buffer);
                    if (read > 0) {
                        System.out.println(new String(buffer.array()));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public void write(String msg) {
            msg = username + " 说：" + msg;
            try {
                //这个socketChannel就是当前通道，write直接发送给服务端的ServerSocketChannel，ServerSocketChannel收到通道后，会默认直接连接在服务端的selector中。
                socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
