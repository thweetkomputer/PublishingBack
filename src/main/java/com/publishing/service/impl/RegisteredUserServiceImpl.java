package com.publishing.service.impl;

import com.publishing.entity.RegisteredUser;
import com.publishing.mapper.RegisteredUserMapper;
import com.publishing.service.RegisteredUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JerryZhao
 * @since 2021-05-09
 */
@Service
public class RegisteredUserServiceImpl extends ServiceImpl<RegisteredUserMapper, RegisteredUser> implements RegisteredUserService {

}
