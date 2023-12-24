package com.douyin.comment.model;

import lombok.Data;

import java.util.List;

@Data
public class Result {
    private int status_code;
    private List<CommentInfo> comments;
}
