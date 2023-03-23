package com.jlkj.safety.si.service.impl;

import cn.hutool.core.util.IdUtil;
import com.jlkj.safety.si.entity.SafeJobHighAppendix;
import com.jlkj.safety.si.mapper.SafeJobHighAppendixMapper;
import com.jlkj.safety.si.service.SafeJobHighAppendixService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 高处作业票-附件表 服务实现类
 * </p>
 *
 * @author su
 * @since 2022-06-14
 */
@Service
public class SafeJobHighAppendixServiceImpl extends ServiceImpl<SafeJobHighAppendixMapper, SafeJobHighAppendix> implements SafeJobHighAppendixService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobHighFile(Map<String, Object> params) {
        SafeJobHighAppendix safeJobHighAppendix = new SafeJobHighAppendix();
        safeJobHighAppendix.setId(IdUtil.randomUUID());
        safeJobHighAppendix.setJobId(params.get("job_id").toString());
        safeJobHighAppendix.setAppendixId(params.get("appendix_id").toString());
        return save(safeJobHighAppendix);
    }
}
