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

    public static final int TIMEOUT = 1000;
    public static final int BUFFER_SIZE = 1024;

    static ExecutorService threadPool = new ThreadPoolExecutor(10,
            20,
            3L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(10),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void main(String[] args) {
        NioClient nioClient = new NioClient("127.0.0.1", 8888);
        threadPool.execute(nioClient);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String msg = sc.nextLine();
            nioClient.sendMsg(msg);
            nioClient.readMsg();
        }
    }

    public static class NioClient implements Runnable {
        private String host;
        private int port;

        private Selector selector;
        private SocketChannel socketChannel;

        private volatile boolean stop;

        public NioClient(String host, int port) {
            this.host = host;
            this.port = port;
            try {
                //打开SocketChannel，创建客户端链接
                socketChannel = SocketChannel.open();
                //设置SocketChannel为非阻塞模式
                socketChannel.configureBlocking(false);
                //创建多路复用器
                selector = Selector.open();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                //socketChannel发起连接
                if (socketChannel.connect(new InetSocketAddress(host, port))) {
                    //连接成功，注册到多路复用器上
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else {
                    socketChannel.register(selector, SelectionKey.OP_CONNECT);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                readMsg();
            }
            // 多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
           /* if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/
        }

        public void sendMsg(String msg) {
            try {
                //发送消息，读应答
                byte[] req = msg.getBytes();
                ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
                writeBuffer.put(req);
                writeBuffer.flip();
                socketChannel.write(writeBuffer);
                if (!writeBuffer.hasRemaining()) {
                    System.out.println("Send order 2 server succeed.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void readMsg() {
            try {
                //多路复用器轮询准备就绪的Key
                selector.select(TIMEOUT);
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                SelectionKey key = null;
                while (iter.hasNext()) {
                    key = iter.next();
                    iter.remove();
                    try {
                        if (key.isValid()) {
                            //将链接成功的Channel注册到多路复用器上
                            //判断连接是否成功
                            SocketChannel sc = (SocketChannel) key.channel();
                            if (key.isConnectable()) {
                                if (sc.finishConnect()) {
                                    sc.register(selector, SelectionKey.OP_READ);
                                }
                            }
                            //监听读操作，读取服务器的网络信息
                            try {
                                if (key.isReadable()) {
                                    ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
                                    int readBytes = sc.read(readBuffer);
                                    if (readBytes > 0) {
                                        readBuffer.flip();
                                        byte[] bytes = new byte[readBuffer.remaining()];
                                        readBuffer.get(bytes);
                                        String body = new String(bytes, "UTF-8");
                                        System.out.println("Now is : " + body);
                                    } else if (readBytes < 0) {
                                        System.out.println("close ");
                                        // 对端链路关闭
                                        key.cancel();
                                        sc.close();
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("Server close connection......");
                                key.cancel();
                                sc.close();
                            }

                        }
                    } catch (IOException e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }


                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
