package com.daop.javabase.netcoding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.daop.javabase.PathConstants;
import com.daop.javabase.utils.CloseUtil;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.netcoding
 * @Description: 爬虫
 * @DATE: 2020-10-14
 * @AUTHOR: Administrator
 **/
public class MyCrawler {

    /**
     * 图片：https://www.mpyit.com/html/ritu/api.php?cid=6&start=0&count=100
     * 风景大片、小清新 动漫
     */
    public static void main(String[] args) {
        // 总条数 7237
        int count = 7237;
        //每次采取条数
        int everyCount = 200;
        //图片分类
        int cid = 9;
        //循环次数=(总条数/每次采取条数)+1
//        int cycles = (count / everyCount) + 1;
        int cycles = 3;

        String tag = "";

        HttpURLConnection urlConnection = null;
        FileWriter fw = null;
        BufferedReader br = null;
        try {
            //1.File实例化，指明写出的文件
            File file1 = new File(PathConstants.FILE_PATH + "Pic.html");
            //2.提供FileWriter对象，用于数据写出
            fw = new FileWriter(file1);
            //3.写出的操作
            int accessTime = 0;

            for (; accessTime < cycles; accessTime++) {
                System.out.println("开始第" + (accessTime + 1) + "次访问");
                int start = everyCount * accessTime;
                String imgurl = "http://wallpaper.apc.360.cn/index.php?c=WallPaper&a=getAppsByCategory&cid=" + cid + "&start=" + start + "&count=" + everyCount;
                System.out.println(imgurl);
                URL url = new URL(imgurl);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("user-agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36");
                urlConnection.connect();
                StringBuilder sb = new StringBuilder();
                br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.US_ASCII));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                JSONObject jsonObject = null;
                try {
                    jsonObject = JSONObject.parseObject(sb.toString());
                    List<JSONObject> data = JSONArray.parseArray(jsonObject.get("data").toString(), JSONObject.class);

                    for (JSONObject datum : data) {
//                        fw.write("<img src='" + datum.get("img_1600_900") + "' height='50px' width='50px' alt=''/>");
                        if (!tag.equals("")) {
                            if (datum.get("utag").equals(tag)) {
                                downloadImg(datum.get("img_1600_900").toString(), "E:\\Crawler Img\\Landscape");
                            }
                            continue;
                        }
                        downloadImg(datum.get("img_1600_900").toString(), "E:\\Crawler Img\\Landscape");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("第" + (accessTime + 1) + "次访问失败，重新访问");
                    accessTime = accessTime - 1;
                    continue;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                assert fw != null;
                fw.write("</body>\n" +
                        "</html>");
            } catch (IOException e) {
                e.printStackTrace();
            }

            CloseUtil.close(br, fw);
            assert urlConnection != null;
            urlConnection.disconnect();
        }
    }


    public static void downloadImg(String imgUrl, String filePath) {
        imgUrl = imgUrl.replaceAll("\\d*_\\d*_\\d*", "2560_1600_100");

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("文件目录不存在,创建目录");
            if (file.mkdirs()) {
                System.out.println("文件目录创建成功！");
            }
        }

        System.out.println("开始下载: " + imgUrl);
        String fileName = imgUrl.substring(imgUrl.lastIndexOf("/"));
        HttpURLConnection urlConnection = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(imgUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            is = urlConnection.getInputStream();
            fos = new FileOutputStream(new File(filePath + fileName));
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtil.close(fos, is);
            assert urlConnection != null;
            urlConnection.disconnect();
        }
        System.out.println("下载成功!");
    }
}

class CrawlerURL {
    public static void main(String[] args) throws IOException {

        //1.File实例化，指明写出的文件
        File file1 = new File(PathConstants.FILE_PATH + "Pic.html");
        //2.提供FileWriter对象，用于数据写出


        String imgurl = "http://cdn.apc.360.cn/index.php?c=WallPaper&a=getAllCategoriesV2";
        System.out.println(imgurl);
        URL url = new URL(imgurl);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestProperty("user-agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36");
        urlConnection.connect();
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.US_ASCII));
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }

        JSONObject jsonObject = JSONObject.parseObject(sb.toString());
        List<JSONObject> data = JSONArray.parseArray(jsonObject.get("data").toString(), JSONObject.class);

        for (JSONObject datum : data) {
            System.out.println(datum.get("id") + "    " + datum.get("name"));
        }

    }
}
