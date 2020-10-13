package com.daop.javabase.io;

import com.daop.javabase.PathConstants;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @BelongsProject: JavaBase
 * @BelongsPackage: com.daop.javabase.io
 * @Description: 转换流的使用
 * @DATE: 2020-10-13
 * @AUTHOR: Administrator
 * 1.转换流
 * InputStreamReader：将一个字节的输入流转换为字符的输入流
 * OutputStreamWriter：将一个字符的输出流转换为字节的输出流
 * <p>
 * 2.作用 提供字节流与字符流之间的转换
 * <p>
 * 3.解码：字节、字节数组-->字符数组、字符串
 * 编码：字符数组、字符串-->字节、字节数组
 * <p>
 * 4.字符集
 * ASCII:       美国标准信息交换码 用一个字节的7位可以表示
 * ISO8859-1:   拉丁码表。欧洲码表 用一个字节的8为表示
 * GB2312:      中国中文编码表，最多两个字节码所有字符
 * GBK:         中国的中文编码表升级，融合了更多的中文文字。最多两个字节编码
 * Unicode:     国际标准码，融合了目前人类使用的所有字符。为每个字符分配唯一的字符码，所有文字都用两个字节表示
 * UTF-8:       变长的编码方式，可用1-4个字节来表示一个字符
 **/
public class InputStreamReaderTest {
    /**
     * @throws IOException
     */
    @Test
    public void inputStreamTest() throws IOException {
        FileInputStream fis = new FileInputStream(PathConstants.FILE_PATH + "OtherThesaurus.txt");
        //参数2不设置的时候默认为系统默认的字符集
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);

        char[] cbuf = new char[20];
        int len;
        while ((len = isr.read(cbuf)) != -1) {
            String str = new String(cbuf, 0, len);
            System.out.println(str);
        }

        isr.close();
    }

    /**
     * 综合使用InputStreamReader和OutputStream
     */
    @Test
    public void inputAndOutputStreamTest() throws IOException {
        //造流，造文件
        FileInputStream fis = new FileInputStream(PathConstants.FILE_PATH + "OtherThesaurus.txt");
        FileOutputStream fos = new FileOutputStream(PathConstants.FILE_PATH + "OtherThesaurus_gbk.txt");

        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "GBK");
        //读写过程
        char[] cbuf = new char[20];
        int len;
        while ((len = isr.read(cbuf)) != -1) {
            osw.write(cbuf, 0, len);
        }
        //关闭资源
        isr.close();
        osw.close();
    }
}

