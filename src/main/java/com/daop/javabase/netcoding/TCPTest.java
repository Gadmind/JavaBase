package com.daop.javabase.netcoding;

import com.daop.javabase.utils.CloseUtil;
import com.daop.javabase.PathConstants;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.netcoding
 * @Description: TCP编程
 * @DATE: 2020-10-14
 * @AUTHOR: Administrator
 **/
public class TCPTest {
    /**
     * 客户端
     */
    @Test
    public void client() {
        Socket socket = null;
        OutputStream os = null;
        InputStream is = null;
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            //创建Socket对象，指明服务端IP和端口号
            InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
            socket = new Socket(inetAddress, 8899);
            //获取一个输出流，用于输出数据
            os = socket.getOutputStream();
            //通过输入流读取文件 并发送文件
            fis = new FileInputStream(new File(PathConstants.PIC_PATH + "CopyImg.jpg"));
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            //关闭数据的输出，阻塞
            socket.shutdownOutput();
            //接受服务器反馈消息
            is = socket.getInputStream();
            baos = new ByteArrayOutputStream();
            byte[] bufferr = new byte[1024];
            int len1;
            while ((len1 = is.read(bufferr)) != -1) {
                baos.write(bufferr, 0, len1);
            }
            System.out.println(baos.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //资源关闭
            CloseUtil.close(baos, os, fis, os, socket);
        }
    }

    /**
     * 服务端
     */
    @Test
    public void server() {
        ServerSocket ss = null;
        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        FileOutputStream fos = null;
        try {
            //创建服务端的ServerSocket，指明自己的端口号
            ss = new ServerSocket(8899);
            //调用accept()表示接受来自客户端的socket
            socket = ss.accept();
            System.out.println("收到：" + socket.getInetAddress().getHostAddress());
            //获取输入流
            is = socket.getInputStream();
            //获取输入流中的数据
            fos = new FileOutputStream(new File(PathConstants.PIC_PATH + "AcceptImg.jpg"));

            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            System.out.println("File Print Success!!!");

            //服务器反馈客户端
            os = socket.getOutputStream();
            os.write("Accept success!".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //资源关闭
            CloseUtil.close(os,fos, is, ss, socket);
        }

    }
}
