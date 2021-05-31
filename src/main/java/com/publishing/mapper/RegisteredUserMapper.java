package com.publishing.mapper;

import com.publishing.entity.RegisteredUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author JerryZhao
 * @since 2021-05-11
 */

@Repository
public interface RegisteredUserMapper extends BaseMapper<RegisteredUser> {

    public RegisteredUser getByName(String name);
}
