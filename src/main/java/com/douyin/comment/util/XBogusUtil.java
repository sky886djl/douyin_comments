package com.douyin.comment.util;

import com.douyin.comment.model.XBogus;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class XBogusUtil {
    private static Gson gson = new Gson();

    public static String getXBogusLink(String commentUrl) throws IOException {
        commentUrl = commentUrl.split("X-Bogus")[0];
        String url = "http://127.0.0.1:8787/X-Bogus"; // 替换为您要发送POST请求的URL
        String requestBody = "{\"url\":\"" + commentUrl + "\",\"user_agent\":\"你的userAgent\"}"; // 替换为您要发送的请求体

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // 设置请求方法为POST
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Content-Length", requestBody.length() + "");
        con.setDoOutput(true);

        // 发送请求体
        OutputStream os = con.getOutputStream();
        os.write(requestBody.getBytes(StandardCharsets.UTF_8));
        os.flush();
        os.close();

        // 获取响应码和响应体
        String responseBody = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
        XBogus xBogus = gson.fromJson(responseBody, XBogus.class);
        return xBogus.getNewLink();
    }
}
