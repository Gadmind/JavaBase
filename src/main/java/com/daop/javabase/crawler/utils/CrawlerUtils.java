package com.daop.javabase.crawler.utils;

import com.alibaba.fastjson.JSONObject;
import com.daop.javabase.crawler.site.DownloadResult;
import com.daop.javabase.utils.CloseUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.crawler
 * @Description: 爬虫工具
 * @DATE: 2020-10-16
 * @AUTHOR: Administrator
 **/
public class CrawlerUtils {

    /**
     * 获取URL请求链接
     *
     * @param url 请求地址
     * @return URL连接
     */
    public static URLConnection getUrlConnection(String url) throws IOException {
        URLConnection urlConnection = new URL(url).openConnection();
        urlConnection.setRequestProperty("user-agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36");
        return urlConnection;
    }

    /**
     * 获取接口请求响应信息
     *
     * @param url      接口地址
     * @param charsets 编码集
     * @return String类型
     */
    public static String getUrlResponseToString(String url, Charset charsets) {
        HttpURLConnection urlConnection = null;
        StringBuilder sb = null;
        BufferedReader br = null;
        try {
            urlConnection = (HttpURLConnection) getUrlConnection(url);
            urlConnection.connect();
            sb = new StringBuilder();
            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), charsets));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(br);
            assert urlConnection != null;
            urlConnection.disconnect();

        }
        assert sb != null;
        return sb.toString();
    }

    /**
     * 获取接口请求响应信息
     *
     * @param url      接口地址
     * @param charsets 编码集
     * @return JSONObject <K,V>类型
     */
    public static JSONObject getUrlResponseToJsonObject(String url, Charset charsets) {
        String sb = getUrlResponseToString(url, charsets);
        return JSONObject.parseObject(sb);
    }


    /**
     * 下载图片
     *
     * @param imgUrl   图片路径
     * @param filePath 下载地址：盘符 + File.separator + 文件目录 + File.separator;
     *                 E:\\file\\
     */
    public static DownloadResult downloadImg(String imgUrl, String filePath) {
        DownloadResult df = null;
        if (imgUrl != null) {
            //创建文件夹
            createFileDir(filePath);
            //设置文件名称格式
            String fileName = System.currentTimeMillis() + "_" + imgUrl.substring(imgUrl.lastIndexOf("/") + 1);

            HttpURLConnection urlConnection = null;
            InputStream is = null;
            FileOutputStream fos = null;
            System.out.println("开始下载: " + imgUrl + "\t文件名 " + fileName);
            try {
                urlConnection = (HttpURLConnection) getUrlConnection(imgUrl);
                is = urlConnection.getInputStream();
                fos = new FileOutputStream(new File(filePath + fileName));
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                System.out.println("下载成功!");
            } catch (IOException e) {
                if (e instanceof MalformedURLException) {
                    df = new DownloadResult(imgUrl, fileName, "协议错误");
                } else if (e instanceof FileNotFoundException) {
                    df = new DownloadResult(imgUrl, fileName, "找不到文件");
                } else {
                    df = new DownloadResult(imgUrl, fileName, e.toString());
                }
                System.err.println("下载失败\t失败原因：" + df.getFailReason());
            } finally {
                CloseUtil.close(fos, is);
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
        return df;
    }

    /**
     * 创建文件目录
     *
     * @param filePath 文件目录路径
     */
    private static void createFileDir(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("文件目录不存在,创建目录");
            if (file.mkdirs()) {
                System.out.println("文件目录创建成功！");
            }
        }
    }
}