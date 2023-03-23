package com.jlkj.safety.si.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeJobHoistingAppendix;
import com.jlkj.safety.si.mapper.SafeJobHoistingAppendixMapper;
import com.jlkj.safety.si.service.SafeJobHoistingAppendixService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 吊装作业票-附件表 服务实现类
 * </p>
 *
 * @author su
 * @since 2022-06-14
 */
@Service
public class SafeJobHoistingAppendixServiceImpl extends ServiceImpl<SafeJobHoistingAppendixMapper, SafeJobHoistingAppendix> implements SafeJobHoistingAppendixService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobHoistingFile(Map<String, Object> params) {
        SafeJobHoistingAppendix safeJobHoistingAppendix = new SafeJobHoistingAppendix();
        safeJobHoistingAppendix.setId(IdUtil.randomUUID());
        safeJobHoistingAppendix.setJobId(params.get("job_id").toString());
        safeJobHoistingAppendix.setAppendixId(params.get("appendix_id").toString());
        return save(safeJobHoistingAppendix);
    }
}
