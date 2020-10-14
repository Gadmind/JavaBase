package com.daop.javabase.netcoding;

import com.daop.javabase.PathConstants;
import com.daop.javabase.utils.CloseUtil;
import org.junit.jupiter.api.condition.OS;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.netcoding
 * @Description: URL网编测试
 * @DATE: 2020-10-14
 * @AUTHOR: Administrator
 * <p>
 * URL：统一资源定位符，对应着互联网的某一资源地址
 * 格式：<传输协议>://<主机名>:<端口号>/<文件名>#<片段名>?<参数列表>
 * http://localhost:8080/test/index.html#a?name=tom
 * 协议    主机名    端口   资源地址       片段  参数列表
 **/
public class URLTest {
    public static void main(String[] args) {
        URL url = null;
        HttpURLConnection urlConnection = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            url = new URL("http://www.lcread.com/bookpage/246404/5004446rc.html");

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            is = urlConnection.getInputStream();
            fos = new FileOutputStream(PathConstants.FILE_PATH + "File.html");

            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(is, fos);
            assert urlConnection != null;
            urlConnection.disconnect();
        }
        System.out.println("协议名：" + url.getProtocol());
        System.out.println("主机名：" + url.getHost());
        System.out.println("端口号：" + url.getPort());
        System.out.println("文件路径：" + url.getPath());
        System.out.println("文件名：" + url.getFile());
        System.out.println("查询名：" + url.getQuery());


    }
}
