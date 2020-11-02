package com.daop.javabase.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * @BelongsProject: nio
 * @BelongsPackage: com.daop.io
 * @Description: Nio服务端
 * @DATE: 2020-10-30
 * @AUTHOR: Administrator
 **/
public class NioServerDemo {
    /**
     * 服务端口号
     */
    public static final int PORT = 8888;
    /**
     * 主机地址
     */
    public static final String ADDRESS = "localhost";
    /**
     * 超时时间
     */
    public static final int TIMEOUT = 1000;
    /**
     * 缓存空间大小
     */
    public static final int BUFFER_SIZE = 1024;
    /**
     * 用于存放客户端连接
     */
    public static final Map<String, SocketChannel> CHANNEL_MAP = new ConcurrentHashMap<>(8);
    static ExecutorService threadPool = new ThreadPoolExecutor(10,
            20,
            3L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(10),
            new ThreadPoolExecutor.DiscardOldestPolicy());

    public static void main(String[] args) {
        NioServer nioServer = new NioServer();
        threadPool.execute(nioServer);
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String msg = sc.nextLine();
            System.out.println(msg);
            nioServer.sendToList(CHANNEL_MAP.values(), msg);
        }
    }


    public static class NioServer implements Runnable {
        private Selector selector;
        ServerSocketChannel serverSocketChannel;

        public NioServer() {
            try {
                //打开ServerSocketChannel，监听客户端链接，是所有客户端链接的父管道
                ServerSocketChannel ssc = ServerSocketChannel.open();
                //绑定监听端口，并设置为非阻塞模式
                ssc.socket().bind(new InetSocketAddress(InetAddress.getByName(ADDRESS), PORT));
                ssc.configureBlocking(false);
                //创建线程，创建多路复用器并启动线程
                selector = Selector.open();
                //将ServerSocketChannel注册到线程的多路复用器Selector上，监听Accept事件
                ssc.register(selector, SelectionKey.OP_ACCEPT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (true) {

                try {
                    selector.select(TIMEOUT);
                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();

                    SelectionKey key = null;
                    while (iter.hasNext()) {
                        key = iter.next();
                        iter.remove();
                        try {
                            if (key.isValid()) {
                                //处理接入的请求
                                if (key.isAcceptable()) {
                                    handlerAccept(key);
                                }
                                if (key.isReadable()) {
                                    handlerRead(key);
                                }
                            }
                        } catch (IOException e) {
                            if (key != null) {
                                SocketChannel sc = (SocketChannel) key.channel();
                                System.out.println("Client : " + sc.getRemoteAddress() + " close connection.......");
                                CHANNEL_MAP.remove(clientAddr(sc));
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


        /**
         * 接受客户端
         *
         * @param key
         * @throws IOException
         */
        public void handlerAccept(SelectionKey key) throws IOException {
            //多路复用器监听到有新的客户端接入，处理新的接入请求，完成TCP三次握手，建立物理链路
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            serverSocketChannel = ssc;
            SocketChannel sc = ssc.accept();
            System.out.println("Client : " + sc.getRemoteAddress() + " open connection.......");
            CHANNEL_MAP.put(clientAddr(sc), sc);

            //设置客户端链路为非阻塞模式
            sc.configureBlocking(false);
            //设置链接为服用模式
            sc.socket().setReuseAddress(true);
            //注册新接入的客户端链接到多路复用器上，监听读操作，读取信息
            sc.register(selector, SelectionKey.OP_READ);
        }

        /**
         * 读取信息
         *
         * @param key
         * @throws IOException
         */
        public void handlerRead(SelectionKey key) throws IOException {
            //异步读取客户端信息到缓存区
            SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
            int readBytes = sc.read(readBuffer);
            //对ByteBuffer进行解码，如果有半包消息指针reset，继续读取后续报文。
            if (readBytes > 0) {
                readBuffer.flip();
                byte[] bytes = new byte[readBuffer.remaining()];
                readBuffer.get(bytes);
                String msg = new String(bytes, StandardCharsets.UTF_8);
                System.out.println("Receive client Msg: " + msg);
                //写应答
                sendToOne(sc, msg.toUpperCase());
            } else if (readBytes < 0) {
                CHANNEL_MAP.remove(clientAddr(sc));
                System.out.println("Client : " + sc.getRemoteAddress() + " close connection.......");
                //关闭链路
                key.cancel();
                sc.close();
            }
        }

        /**
         * 单一发送
         *
         * @param sc
         * @param msg
         */
        public void sendToOne(SocketChannel sc, Object msg) {
            try {
                String resMsg = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS").format(System.currentTimeMillis()) + "\t" + msg;
                byte[] resMsgBytes = resMsg.getBytes();
                System.out.println(resMsg.length() + "\t" + resMsgBytes.length);
                ByteBuffer writeBuffer = ByteBuffer.allocate(resMsgBytes.length);
                writeBuffer.put(resMsgBytes);
                writeBuffer.flip();
                sc.write(writeBuffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 批量发送
         *
         * @param scList
         * @param msg
         */
        public void sendToList(Collection<SocketChannel> scList, Object msg) {
            for (SocketChannel sc : scList) {
                sendToOne(sc, msg);
            }
        }

        /**
         * 处理客户端IP地址
         *
         * @param sc
         * @return
         */
        public String clientAddr(SocketChannel sc) {
            String clientAddress = "";
            try {
                InetSocketAddress isa = (InetSocketAddress) sc.getRemoteAddress();
                clientAddress = isa.getHostString() + ":" + isa.getPort();
                System.out.println(clientAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return clientAddress;
        }

    }


}
