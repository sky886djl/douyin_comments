package com.douyin.comment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("video")
@Data
public class VideoInfo {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String url;
    private String commentUrl;
    private String requestObj;
}
