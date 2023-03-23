package com.jlkj.safety.si.service.impl;

import cn.hutool.core.util.IdUtil;
import com.jlkj.safety.si.entity.SafeJobSpaceAppendix;
import com.jlkj.safety.si.mapper.SafeJobSpaceAppendixMapper;
import com.jlkj.safety.si.service.SafeJobSpaceAppendixService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 有限空间作业票-附件表 服务实现类
 * </p>
 *
 * @author su
 * @since 2022-06-15
 */
@Service
public class SafeJobSpaceAppendixServiceImpl extends ServiceImpl<SafeJobSpaceAppendixMapper, SafeJobSpaceAppendix> implements SafeJobSpaceAppendixService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobSpaceFile(Map<String, Object> params) {
        SafeJobSpaceAppendix safeJobSpaceAppendix = new SafeJobSpaceAppendix();
        safeJobSpaceAppendix.setId(IdUtil.randomUUID());
        safeJobSpaceAppendix.setJobId(params.get("job_id").toString());
        safeJobSpaceAppendix.setAppendixId(params.get("appendix_id").toString());
        return save(safeJobSpaceAppendix);
    }
}
