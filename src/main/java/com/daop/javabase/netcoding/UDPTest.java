package com.daop.javabase.netcoding;

import com.daop.javabase.utils.CloseUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.*;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.netcoding
 * @Description: UDP测试
 * @DATE: 2020-10-14
 * @AUTHOR: Administrator
 **/
public class UDPTest {
    /**
     * 发送端
     */
    @Test
    public void sender() throws IOException {
        DatagramSocket socket = new DatagramSocket();

        String str = "Use UDP Type send message.....";
        byte[] msgByte = str.getBytes();

        DatagramPacket packet = new DatagramPacket(msgByte, 0, msgByte.length, InetAddress.getLocalHost(), 8899);
        socket.send(packet);

        CloseUtil.close(socket);

    }

    /**
     * 接收端
     */
    @Test
    public void receiver() throws IOException {

        DatagramSocket socket = new DatagramSocket(8899);
        byte[] buffer = new byte[100];

        DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
        socket.receive(packet);

        System.out.println(new String(packet.getData(), 0, packet.getLength()));
    }
}
