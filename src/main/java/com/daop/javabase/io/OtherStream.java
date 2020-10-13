package com.daop.javabase.io;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @BelongsProject: JavaBase
 * @BelongsPackage: com.daop.javabase.io
 * @Description: 其他流的使用 仅作了解
 * @DATE: 2020-10-13
 * @AUTHOR: Administrator
 * 1.标准输入/出流
 * 2.打印流
 * 3.数据流
 **/
public class OtherStream {
    /**
     * 1.标准输入/出流
     * 1.1
     * System.in: 标准输入流，默认从键盘输入
     * System.out: 标准输出流，默认从控制台输出
     * 1.2
     * System类的setIn(InputStream is)/setOut(PrintStream ps) 方式重新指定输入和输出的流
     */
    public static void main(String[] args) {

        BufferedReader br = null;
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            br = new BufferedReader(isr);

            while (true) {
                System.out.println("请输入......");
                String data = br.readLine();
                if ("e".equalsIgnoreCase(data) || "exit".equalsIgnoreCase(data)) {
                    System.out.println("程序结束");
                    break;
                }
                System.out.println(data.toUpperCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert br != null;
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     *
     * 打印流：PrintStream和PrintWriter
     * 数据流：用于数据读取或写出基本数据类型的变量或字符串
     *        DataInputStream和DataOutputStream
     *        数据读取和数据写入的数据顺序一致
     *
     * 对象流
     */
}
