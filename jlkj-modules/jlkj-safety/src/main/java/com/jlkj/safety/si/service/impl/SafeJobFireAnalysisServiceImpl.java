package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobFireAnalysis;
import com.jlkj.safety.si.mapper.SafeJobFireAnalysisMapper;
import com.jlkj.safety.si.service.SafeJobFireAnalysisService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 动火安全作业证-动火分析 服务实现类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
@Service
public class SafeJobFireAnalysisServiceImpl extends ServiceImpl<SafeJobFireAnalysisMapper, SafeSiJobFireAnalysis> implements SafeJobFireAnalysisService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobFireAnalysis(Map<String, Object> params) {
        SafeSiJobFireAnalysis safeSiJobFireAnalysis = new SafeSiJobFireAnalysis();
        safeSiJobFireAnalysis.setId(IdUtil.randomUUID());
        safeSiJobFireAnalysis.setJobId(params.get("job_id").toString());
        safeSiJobFireAnalysis.setAnalysisTime(DateUtil.parseLocalDateTime(params.get("analysis_time").toString()));
        safeSiJobFireAnalysis.setPersonName(params.get("person_name").toString());
        safeSiJobFireAnalysis.setAnalysisArea(params.get("analysis_area").toString());
        safeSiJobFireAnalysis.setCoDensity(new BigDecimal(params.get("co_density").toString()));
        safeSiJobFireAnalysis.setO2Density(new BigDecimal(params.get("o2_density").toString()));
        safeSiJobFireAnalysis.setOthreDensity(new BigDecimal(params.get("othre_density").toString()));
        safeSiJobFireAnalysis.setPersonId(params.get("person_id").toString());
        return save(safeSiJobFireAnalysis);
    }

    /**
     * 删除动火分析
     * @author 265800
     * @date 2023/7/14 15:37
     * @param params
     * @return boolean
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobFireAnalysis(Map<String, Object> params) {
        return removeById(params.get("id").toString());
    }

}
