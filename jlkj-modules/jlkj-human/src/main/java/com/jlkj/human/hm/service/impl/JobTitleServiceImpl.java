package com.jlkj.human.hm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.human.hm.domain.JobTitle;
import com.jlkj.human.hm.mapper.JobTitleMapper;
import com.jlkj.human.hm.service.IJobTitleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 职位名称数据 服务实现类
 * </p>
 *
 * @author xin
 * @since 2022-05-20
 */
@Service
public class JobTitleServiceImpl extends ServiceImpl<JobTitleMapper, JobTitle> implements IJobTitleService {

}
