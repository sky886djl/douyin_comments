package com.douyin.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.douyin.comment.model.CommentInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper extends BaseMapper<CommentInfo> {
}
