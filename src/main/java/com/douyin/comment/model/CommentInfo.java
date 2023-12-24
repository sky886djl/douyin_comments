package com.douyin.comment.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName("comment")
@Data
public class CommentInfo implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String text;
    private int videoId;

    public String getText() {
        text = text.replaceAll("\\[.*?\\]", "");
        return parseAt(text);
    }

    private String parseAt(String text) {
        StringBuilder sb = new StringBuilder();
        char[] chars = text.toCharArray();
        boolean tag = false;
        for (int i = 0; i < chars.length; i++) {
            if(tag) {
                if(chars[i] == ' ') {
                    tag = false;
                }
                continue;
            }
            if(chars[i] == '@') {
                tag = true;
            } else {
                sb.append(chars[i]);
            }
        }
        return sb.toString();
    }
}
