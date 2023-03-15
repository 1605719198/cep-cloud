package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.human.hm.domain.Post;
import com.jlkj.human.hm.mapper.PostMapper;
import com.jlkj.human.hm.service.IPostService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 岗位数据 服务实现类
 * </p>
 *
 * @author Xin
 * @since 2022-04-29
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {

}
