package com.douyin.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.douyin.comment.model.VideoInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VideoMapper extends BaseMapper<VideoInfo> {
}
