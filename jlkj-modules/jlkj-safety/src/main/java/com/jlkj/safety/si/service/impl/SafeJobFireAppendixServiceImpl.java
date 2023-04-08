package com.jlkj.safety.si.service.impl;

import cn.hutool.core.util.IdUtil;
import com.jlkj.safety.si.entity.SafeSiJobFireAppendix;
import com.jlkj.safety.si.mapper.SafeJobFireAppendixMapper;
import com.jlkj.safety.si.service.SafeJobFireAppendixService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 动火作业票-附件表 服务实现类
 * </p>
 *
 * @author su
 * @since 2022-06-17
 */
@Service
public class SafeJobFireAppendixServiceImpl extends ServiceImpl<SafeJobFireAppendixMapper, SafeSiJobFireAppendix> implements SafeJobFireAppendixService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobFireFile(Map<String, Object> params) {
        SafeSiJobFireAppendix safeSiJobFireAppendix = new SafeSiJobFireAppendix();
        safeSiJobFireAppendix.setId(IdUtil.randomUUID());
        safeSiJobFireAppendix.setJobId(params.get("job_id").toString());
        safeSiJobFireAppendix.setAppendixId(params.get("appendix_id").toString());
        return save(safeSiJobFireAppendix);
    }
}
