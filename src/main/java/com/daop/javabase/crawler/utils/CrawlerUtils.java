package com.daop.javabase.crawler.utils;

import com.alibaba.fastjson.JSONObject;
import com.daop.javabase.crawler.enums.ErrorEnum;
import com.daop.javabase.crawler.site.DownloadResult;
import com.daop.javabase.utils.CloseUtil;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    public static URLConnection getUrlConnection(String url) {
        URLConnection urlConnection = null;
        try {
            urlConnection = new URL(url).openConnection();
            urlConnection.setRequestProperty("user-agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36");
            urlConnection.setRequestProperty("cookie", " __cfduid=d39c9ce8f5d1eca81dacbd986b4302e7e1602762248; remember_web_59ba36addc2b2f9401580f014c7f58ea4e30989d=eyJpdiI6IlpZclRGOTk0Nm1qR1dsQ00xTDRZR2c9PSIsInZhbHVlIjoidmViMk1HR0I5N3M4T1F1Q3lXSldYXC9MR2NoWGk5VmVhUUZIbU5zcHJkajhERlhOT1wvT3c4SHR1YlpXS1R4UnJnZ21HNHBxN1ZYMlc0MTJFcTVTZzI0VTlzU2ttXC9mK0F3U2krSXFRZ3A2V2ZHWTBadXRcL0RQdGIyRVhhS1NTR2RXZE5NZERiT1J6dFh3TFRyc2RhU2xROEIrTnNLMFRzN080cDVzYjdJQWlaSzlcL3lqMFwvMmJhR1wvYkdET0oyZG16YyIsIm1hYyI6IjgzMGI1NjQwYzU3NjMyYzdhMjNmNTAxNzcwYmFjYmViYjdmYjc4MDc5MDA4OTljZTVmZjkzOTQxOTUzYmE0ZGMifQ%3D%3D; _pk_ses.1.01b8=1; XSRF-TOKEN=eyJpdiI6IjN5MnJTZXd2aGZJaEhqQ1orYWdxMEE9PSIsInZhbHVlIjoiYjRPaTZtdFhQMkJ5QUpxaEQxZDJzKzdqUDlXa1pDMGJjRTl4N1pXd2N0akRvRVlweG5URVZsZGxpd0hDa0J6WiIsIm1hYyI6IjI2N2Y3YWRmNTE4Y2FlMmY2NDc5NGE0ZTI5YWRkMDVkOTdjZDkzMjQ0ZmY1ZDQ2ZWUxMDdiOTUzYmU1YmEyZWUifQ%3D%3D; wallhaven_session=eyJpdiI6IlkzeW9MV3pZdHpKR29qUjB1ZW1wRXc9PSIsInZhbHVlIjoiNHJDMitxWDhqa3g1YUF3cWhYNFR3VXBwMVZWbUFvbXJjNkFra3R6UHBQTTZFKzFCaGxZUVBleDVCQmJTZDB6bSIsIm1hYyI6IjcwMWI2Y2NjOTZjMDAwNGY3ZDU3OWVmNjI4MDBmZGJiZTg3NGE4MzBjYjc3Y2RmNWVmMjE1NTc1ZTRmMzU4ZTQifQ%3D%3D; _pk_id.1.01b8=da33600227865f77.1602762263.9.1602914617.1602901937.");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * @param url      图片路径
     * @param filePath 下载地址：盘符 + File.separator + 文件目录 + File.separator;
     *                 E:\\file\\
     * @param fileName 文件名称
     * @return 下载结果
     */
    public static DownloadResult downloadImg(String url, String filePath, String fileName) {
        return download(url, filePath, fileName);
    }

    /**
     * 下载图片
     *
     * @param imgUrl   图片路径
     * @param filePath 下载地址：盘符 + File.separator + 文件目录 + File.separator;
     *                 E:\\file\\
     * @return 下载结果
     */
    public static DownloadResult downloadImg(String imgUrl, String filePath) {
        return download(imgUrl, filePath, "");
    }

    /**
     * 下载
     *
     * @param url
     * @param filePath
     * @param fileName
     * @return
     */
    private static DownloadResult download(String url, String filePath, String fileName) {
        DownloadResult df = null;
        if (url != null) {
            //创建文件夹
            createFileDir(filePath);
            //设置文件名称格式
            if (fileName.length() == 0) {
                fileName = System.currentTimeMillis() + "_" + url.substring(url.lastIndexOf("/") + 1);
            }

            HttpURLConnection urlConnection = null;
            InputStream is = null;
            FileOutputStream fos = null;
            System.out.println("开始下载: " + url + "\t文件名 " + fileName);
            try {
                urlConnection = (HttpURLConnection) getUrlConnection(url);
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
                    df = new DownloadResult(url, fileName, ErrorEnum.PROTOCOL_ERROR);
                } else if (e instanceof FileNotFoundException) {
                    df = new DownloadResult(url, fileName, ErrorEnum.FILE_NOT_FOUND);
                } else {
                    df = new DownloadResult(url, fileName, ErrorEnum.OTHER, e.toString());
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
     * 多线程下载
     *
     * @return
     */
    public static DownloadResult downloadByMulThread(int threadCount, String url, String filePath) {
        ExecutorService threadPool = new ThreadPoolExecutor(30,
                40,
                3L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(30),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        File file = new File(filePath);
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) getUrlConnection(url);
            Map<String, Object> fileInfo = getFileSize(url);
            url = (String) fileInfo.get("url");
            long fileSize = (long) fileInfo.get("fileSize");
            RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
            accessFile.setLength(fileSize);
            accessFile.close();
            long pieceSize = fileSize % threadCount == 0 ? fileSize / threadCount : fileSize / threadCount + 1;
            for (int i = 0; i < threadCount; i++) {
                threadPool.execute(
                        new DownloadThread(i, pieceSize, url, file)
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    private static Map<String, Object> getFileSize(String url) {
        return getFileSize(url, 0);
    }

    private static Map<String, Object> getFileSize(String url, int next) {
        Map<String, Object> res = new HashMap<>(2);
        HttpURLConnection urlConnection = null;
        try {
            String suffix = url.substring(url.lastIndexOf("."));

            String[] suffixs = {".jpg", ".jpeg", ".gif", ".psd", ".tiff", ".tga", ".png", ".eps"};
            for (String s : suffixs) {
                String nexturl = url.replaceFirst(suffix, s);
                urlConnection = (HttpURLConnection) new URL(nexturl).openConnection();
                urlConnection.setRequestProperty("user-agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36");
                long fileSize = urlConnection.getContentLengthLong();
                if (fileSize > -1) {
                    res.put("url", nexturl);
                    res.put("fileSize", fileSize);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
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

    private static class DownloadThread implements Runnable {
        private int threadNum;
        private long pieceSize;
        private String url;
        private File file;

        public DownloadThread(int threadNum, long pieceSize, String url, File file) {
            this.threadNum = threadNum;
            this.pieceSize = pieceSize;
            this.url = url;
            this.file =file;
        }

        @Override
        public void run() {
            long start = threadNum * pieceSize;
            long end = (threadNum + 1) * pieceSize;
            InputStream is = null;
            RandomAccessFile accessFile = null;
            try {
                accessFile = new RandomAccessFile(file, "rwd");
                accessFile.seek(start);
                URLConnection urlConnection = getUrlConnection(url);
                urlConnection.setRequestProperty("Range", "bytes=" + start + "-" + end);
                is = urlConnection.getInputStream();
                System.out.println(start + "===" + end);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    accessFile.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    assert accessFile != null;
                    accessFile.close();
                    assert is != null;
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
