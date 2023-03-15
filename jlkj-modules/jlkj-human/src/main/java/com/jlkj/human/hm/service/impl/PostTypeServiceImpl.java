package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.human.hm.domain.PostType;
import com.jlkj.human.hm.mapper.PostTypeMapper;
import com.jlkj.human.hm.service.IPostTypeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 岗位类别数据 服务实现类
 * </p>
 *
 * @author xin
 * @since 2022-05-19
 */
@Service
public class PostTypeServiceImpl extends ServiceImpl<PostTypeMapper, PostType> implements IPostTypeService {

}
