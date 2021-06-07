package com.publishing.mapper;

import com.publishing.entity.Passage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author JerryZhao
 * @since 2021-05-09
 */

@Repository
public interface PassageMapper extends BaseMapper<Passage> {
    // 分页查询已发布的文章
    List<Passage> selectByPage(Integer startPage, Integer endPage);
    // 查询已发布的文章的总数
    Long selectCount();

    // 查询已审阅的未出版的文章
    List<Passage> selectReviewedUnpublishedByPage(Integer startPage, Integer endPage);

    // 查询已审阅的未出版的文章的总数
    Long selectCountReviewedUnpublished();

    // 查询已审阅的未出版的文章
    List<Passage> selectUnreviewedByPage(Integer startPage, Integer endPage);

    // 查询已审阅的未出版的文章的总数
    Long selectCountUnreviewed();

}
