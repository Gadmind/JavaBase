package com.daop.javabase.netcoding;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.netcoding
 * @Description: 网络编程
 * @DATE: 2020-10-14
 * @AUTHOR: Administrator
 * <p>
 * 网络编程中有两个主要的问题
 * 1.如何准确地定位网络上一台或多台主机。定位主机上特定的应用。
 * 2.找到主机后如何可靠高效的进行数据传输
 * <p>
 * 网络编程要考虑的两个要素
 * IP和端口号
 * 提供网络通信协议：TCP/IP参考模型（应用层、传输层、网络层、物理+数据链路层）
 * |——————————————————————————————————————————————————————|
 * | SIO参考模型  |  TCP/IP参考模型   | TCP/IP参考模型对应协议 |
 * |————————————|——————————————————|——————————————————————|
 * |   应用层    |                  |                      |
 * |————————————|                  |    HTTP FTP Telnet   |
 * |   表示层    |      应用层       |         DNS...       |
 * |————————————|                  |                      |
 * |   会话层    |                  |                      |
 * |————————————|——————————————————|——————————————————————|
 * |   传输层    |       传输层      |       TCP UDP ...    |
 * |————————————|——————————————————|——————————————————————|
 * |   网络层    |       网络层      |     IP ICMP ARP ...  |
 * |————————————|——————————————————|——————————————————————|
 * | 数据链路层   |                  |                      |
 * |————————————|   物理+数据链路层   |     Link            |
 * |   物理层    |                  |                      |
 * |————————————|——————————————————|——————————————————————|
 * <p>
 * 通信要素：IP和端口号
 * IP：唯一的标识Internet上的计算机（通信实体）
 * 在java中使用InteAddress类代表IP
 * IP分类：IPv4和IPv6；万维网和局域网
 *
 * TCP协议
 * 使用TCP协议前，须建立TCP连接，形成传输数据通道
 * 传输前，采用“三次握手”方式，点对点通信，是可靠的
 * TCP协议进行通信的两个应用进程：客户端、服务端
 * 在连接中可进行大数据量的传输
 * 传输完毕，需释放已建立的连接，效率低
 *
 * UDP协议
 * 将数据、源、目的地封装成数据包，不需要建立连接
 * 每个数据报的大小限制在64K内
 * 发送不管对方是否准备好，接收方收到也不确认，是不可好的
 * 可以广播发送
 * 发送数据结束时无需释放资源，开销小、速度快
 *
 **/
public class InterAddressTest {
    public static void main(String[] args) {
        try {
            InetAddress inet1 = InetAddress.getByName("192.168.1.108");
            System.out.println(inet1);

            InetAddress inet2 = InetAddress.getByName("www.mi.com");
            System.out.println(inet2);

            //获取本机回路地址
            InetAddress inet3 = InetAddress.getLoopbackAddress();
            System.out.println(inet3);

            //获取本机IP
            InetAddress inet4 = InetAddress.getLocalHost();
            System.out.println(inet4);



        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
