package com.douyin.comment.service;

import com.douyin.comment.mapper.CommentMapper;
import com.douyin.comment.mapper.VideoMapper;
import com.douyin.comment.model.CommentInfo;
import com.douyin.comment.model.Result;
import com.douyin.comment.model.VideoInfo;
import com.douyin.comment.util.XBogusUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class VideoService {
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private CommentMapper commentMapper;
    static Random random = new Random();
    static Gson gson = new Gson();
    public Integer addVideo(String url, String commentUrl) {
        VideoInfo videoInfo = new VideoInfo();
        try {
            videoInfo.setUrl(url);
            videoInfo.setCommentUrl(commentUrl);
            int insert = videoMapper.insert(videoInfo);
            System.err.println(insert == 1 ? "插入视频链接成功！" : "插入视频失败");
        } catch (Exception e) {

        }
        return videoInfo.getId();
    }

    public void start(String url, String commentUrl, int pageNo) {
        Integer videoId = addVideo(url, commentUrl);
        if(videoId == null || videoId == 0L) {
            Map<String, Object> columnMap = new HashMap<>();
            columnMap.put("url", url);
            List<VideoInfo> list = videoMapper.selectByMap(columnMap);
            if(CollectionUtils.isEmpty(list)) {
                return;
            }
            videoId = list.get(0).getId();
        }

        String preUrl = commentUrl.substring(0, commentUrl.indexOf("&cursor="));
        String suffixUrl = commentUrl.substring(commentUrl.indexOf("&cursor=")+2);
        suffixUrl = suffixUrl.substring(suffixUrl.indexOf("&"));
        String charset = "UTF-8"; // 网页编码
        try {
            for (int i=pageNo;;i++) {
                String link = preUrl + "&cursor=" + (i*20) + suffixUrl;
                URL obj = new URL(link);
//                URL obj = new URL(XBogusUtil.getXBogusLink(link));
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                setParams(con);
                con.connect();
                if (con.getResponseCode() == 200) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    if(content.toString().equals("blocked")) {
                        System.err.println("blocked, exit");
                        break;
                    }
                    if(content.toString().length() <=0) {
                        System.err.println("length=0, exit");
                        break;
                    }
                    try {
                        Result result = gson.fromJson(content.toString(), Result.class);
                        if(result == null || CollectionUtils.isEmpty(result.getComments())) {
                            System.err.println("result=null or comments is empty, exit");
                            break;
                        }
                        for (CommentInfo comment : result.getComments()) {
                            comment.setVideoId(videoId);
                            if(StringUtils.isEmpty(comment.getText()))  {
                                continue;
                            }
                            int insert = commentMapper.insert(comment);
                            if(insert!=1) {
                                throw new Exception("插入comment失败");
                            }
                        }
                    } catch (Exception e) {
//                        break;
                    }
                } else {
                    System.err.println("Failed to connect to the website.");
                }
                con.disconnect();
                Thread.sleep(random.nextInt(500) + 1000);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void setParams(HttpURLConnection con) throws ProtocolException {
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept-Charset", "UTF-8");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setRequestProperty("Sec-Ch-Ua-Platform", "Android");
        con.setRequestProperty("Sec-Ch-Ua", "Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120");
        con.setRequestProperty("Sec-Ch-Ua-Mobile", "?1");
        con.setRequestProperty("Sec-Fetch-Mode", "cors");
        con.setRequestProperty("Sec-Fetch-Site", "same-origin");
        con.setRequestProperty("Sec-Fetch-Dest", "empty");
        con.setRequestProperty("authority", "www.douyin.com");
        con.setRequestProperty("Referer", "https://www.douyin.com/");
        // todo
        con.setRequestProperty("User-Agent", "替换为你的 userAgent");
        // todo
        con.setRequestProperty("Cookie", "替换为你的Cookie");
        con.setDoOutput(true);
    }

}
