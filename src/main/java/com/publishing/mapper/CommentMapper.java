package com.publishing.mapper;

import com.publishing.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.publishing.entity.Passage;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 聊天记录 Mapper 接口
 * </p>
 *
 * @author JerryZhao
 * @since 2021-05-09
 */

@Repository
public interface CommentMapper extends BaseMapper<Comment> {
    // 分页查询
    List<Comment> selectByPage(Long startPage, Long endPage, Long articleId);
    // 查询文章总数
    Long selectCount();

}
