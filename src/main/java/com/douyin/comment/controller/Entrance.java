package com.douyin.comment.controller;

import com.douyin.comment.model.Request;
import com.douyin.comment.service.VideoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/")
public class Entrance {
    @Resource
    private VideoService videoService;
    @PostMapping("/go")
    public void start(@RequestBody Request request) {
        videoService.start(request.getUrl(), request.getCommentUrl(), request.getPageNo());
    }
}
