package com.daop.javabase.netcoding;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daop.javabase.utils.CloseUtil;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: javabase
 * @BelongsPackage: com.daop.javabase.netcoding
 * @Description: 爬虫
 * @DATE: 2020-10-14
 * @AUTHOR: Administrator
 **/
public class MyCrawler {


    public static void main(String[] args) {

        Map<Object, Object> types = new HashMap<>(32);

        types.put(5, new String[]{"游戏", ""});
        types.put(6, new String[]{"女生", ""});
        types.put(7, new String[]{"影视", ""});
        types.put(9, new String[]{"风景", ""});
        types.put(10, new String[]{"时尚", ""});
        types.put(11, new String[]{"明星", ""});
        types.put(12, new String[]{"汽车", ""});
        types.put(13, new String[]{"节日", ""});
        types.put(14, new String[]{"萌宠", ""});
        types.put(15, new String[]{"小清新", ""});
        types.put(16, new String[]{"体育", ""});
        types.put(18, new String[]{"BABY秀", ""});
        types.put(22, new String[]{"军事", ""});
        types.put(26, new String[]{"动漫", ""});
        types.put(29, new String[]{"月历", ""});
        types.put(30, new String[]{"爱情", ""});
        types.put(35, new String[]{"文字控", ""});
        types.put(36, new String[]{"4K", ""});

        //下载路径
        String downloadPath = "E:" + File.separator + "Crawler Img" + File.separator;
        for (Object o : types.keySet()) {
            new Thread(() -> {
                // 总条数
                int count = 20000;
                //每次采取条数
                int everyCount = 200;
                //循环次数=(总条数/每次采取条数)+1
//        int cycles = (count / everyCount) + 1;
                int cycles = 3;
                //操作次数
                int accessTime = 0;

                String cid = o.toString();
                String[] typesArray = (String[]) (types.get(o));
                String type = typesArray[0];
                String tag = typesArray[1];

                for (; accessTime < cycles; accessTime++) {
                    System.out.println("开始第" + (accessTime + 1) + "次访问" + type + "类型壁纸");
                    int start = everyCount * accessTime;
                    String imgurl = "http://wallpaper.apc.360.cn/index.php?c=WallPaper&a=getAppsByCategory&cid=" + cid + "&start=" + start + "&count=" + everyCount;
                    System.out.println(imgurl);
                    JSONObject urlMessage = getUrlMessage(imgurl, StandardCharsets.UTF_8);
                    try {
                        List<JSONObject> data = JSONArray.parseArray(urlMessage.get("data").toString(), JSONObject.class);
                        System.out.println(data.size());
                        if (data.size() == 0) {
                            System.out.println("没有数据了");
                            break;
                        }
                        for (JSONObject datum : data) {
                            if (tag.length() != 0) {
                                String utag = (String) datum.get("utag");
                                if (utag != null) {
                                    if (utag.matches(".*" + tag + ".*")) {
                                        downloadImg(datum.get("url").toString(), downloadPath + type + File.separator + tag + File.separator);
                                    }
                                }
                            } else {
                                downloadImg(datum.get("url").toString(), downloadPath + type + File.separator);
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("第" + (accessTime + 1) + "次访问失败，重新访问");
                        accessTime = accessTime - 1;
                    }

                }
            }).start();
        }
    }

    public static JSONObject getUrlMessage(String imgUrl, Charset charsets) {

        URL url;
        HttpURLConnection urlConnection = null;
        StringBuilder sb = null;
        BufferedReader br = null;
        try {
            url = new URL(imgUrl);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("user-agent", " Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.75 Safari/537.36");
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
        return JSONObject.parseObject(sb.toString());
    }

    public static void downloadImg(String imgUrl, String filePath) {

        if (imgUrl != null) {
            imgUrl = imgUrl.replaceAll("\\d*_\\d*_\\d*", "2560_1600_100");

            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("文件目录不存在,创建目录");
                if (file.mkdirs()) {
                    System.out.println("文件目录创建成功！");
                }
            }

            String fileName = System.currentTimeMillis() + "_" + imgUrl.substring(imgUrl.lastIndexOf("/") + 1);
            HttpURLConnection urlConnection = null;
            InputStream is = null;
            FileOutputStream fos = null;
            System.out.println("开始下载: " + imgUrl + "\t文件名 " + fileName);
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

    @Test
    public void getTypes() {
        String imgurl = "http://cdn.apc.360.cn/index.php?c=WallPaper&a=getAllCategoriesV2";
        JSONObject jsonObject = getUrlMessage(imgurl, StandardCharsets.UTF_8);
        List<JSONObject> data = JSONArray.parseArray(jsonObject.get("data").toString(), JSONObject.class);

        for (JSONObject datum : data) {
            Object cid = datum.get("id");
            String url = "http://wallpaper.apc.360.cn/index.php?c=WallPaper&a=getAppsByCategory&cid=" + cid;
            JSONObject urlJson = getUrlMessage(url, StandardCharsets.UTF_8);
            System.out.println("类别ID为: " + datum.get("id") + " " + datum.get("name") + "壁纸共有" + urlJson.get("total") + "张");
        }
    }
}