package com.douyin.comment.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Request implements Serializable {
    private String url;
    private String commentUrl;
    private int pageNo;
}
