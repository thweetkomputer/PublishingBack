package com.publishing.service.impl;

import com.publishing.entity.Comment;
import com.publishing.mapper.CommentMapper;
import com.publishing.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 聊天记录 服务实现类
 * </p>
 *
 * @author JerryZhao
 * @since 2021-05-09
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
