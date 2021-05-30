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
    // 分页查询
    List<Passage> selectByPage(Integer startPage, Integer endPage);
    // 查询文章总数
    Integer selectCount();
}
