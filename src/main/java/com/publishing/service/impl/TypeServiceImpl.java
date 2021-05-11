package com.publishing.service.impl;

import com.publishing.entity.Type;
import com.publishing.mapper.TypeMapper;
import com.publishing.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author JerryZhao
 * @since 2021-05-11
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

}
