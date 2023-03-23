package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeJobSpace;
import com.jlkj.safety.si.entity.SafeJobSpaceAnalysis;
import com.jlkj.safety.si.mapper.SafeJobSpaceAnalysisMapper;
import com.jlkj.safety.si.service.SafeJobSpaceAnalysisService;
import com.jlkj.safety.si.service.SafeJobSpaceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 受限空间安全作业证-分析 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-13
 */
@Service
public class SafeJobSpaceAnalysisServiceImpl extends ServiceImpl<SafeJobSpaceAnalysisMapper, SafeJobSpaceAnalysis> implements SafeJobSpaceAnalysisService {

    @Resource
    @Lazy
    SafeJobSpaceService safeJobSpaceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobSpaceAnalysis(Map<String, Object> params) {
        SafeJobSpaceAnalysis safeJobSpaceAnalysis = new SafeJobSpaceAnalysis();
        safeJobSpaceAnalysis.setId(IdUtil.randomUUID());
        safeJobSpaceAnalysis.setJobId(params.get("job_id").toString());
        safeJobSpaceAnalysis.setAnalysisTime(DateUtil.parseLocalDateTime(params.get("analysis_time").toString()));
        safeJobSpaceAnalysis.setToxicMedium(params.get("toxic_medium").toString());
        safeJobSpaceAnalysis.setCombustibleGas(params.get("combustible_gas").toString());
        safeJobSpaceAnalysis.setOxygenContent(params.get("oxygen_content").toString());
        safeJobSpaceAnalysis.setPosition(params.get("position").toString());
        safeJobSpaceAnalysis.setPersonId(params.get("person_id").toString());
        safeJobSpaceAnalysis.setPersonName(params.get("person_name").toString());
        save(safeJobSpaceAnalysis);
        QueryWrapper<SafeJobSpaceAnalysis> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("job_id", safeJobSpaceAnalysis.getJobId())
                .orderByAsc("analysis_time");
        List<Map<String, Object>> list = listMaps(queryWrapper);
        if (list.size() > 0) {
            Map<String, Object> firstAnalysis = list.get(0);
            SafeJobSpace safeJobSpace = safeJobSpaceService.getById(safeJobSpaceAnalysis.getJobId());
            safeJobSpace.setStartTime(DateUtil.parseLocalDateTime(firstAnalysis.get("analysis_time").toString(), "yyyy-MM-dd HH:mm:ss.S"));
            safeJobSpaceService.updateById(safeJobSpace);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobSpaceAnalysis(Map<String, Object> params) {
        return removeById(params.get("id").toString());
    }
}
