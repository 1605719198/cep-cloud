package com.jlkj.safety.si.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobCutRoadPersons;
import com.jlkj.safety.si.mapper.SafeJobCutRoadPersonsMapper;
import com.jlkj.safety.si.service.SafeJobCutRoadPersonsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 断路安全作业证-作业人员 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-02-01
 */
@Service
public class SafeJobCutRoadPersonsServiceImpl extends ServiceImpl<SafeJobCutRoadPersonsMapper, SafeSiJobCutRoadPersons> implements SafeJobCutRoadPersonsService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobCutRoadPersons(Map<String, Object> params) {
        SafeSiJobCutRoadPersons safeSiJobCutRoadPersons = new SafeSiJobCutRoadPersons();
        safeSiJobCutRoadPersons.setId(IdUtil.randomUUID());
        safeSiJobCutRoadPersons.setJobId(params.get("job_id").toString());
        safeSiJobCutRoadPersons.setConstructionUnitId(params.get("construction_unit_id").toString());
        safeSiJobCutRoadPersons.setConstructionUnitName(params.get("construction_unit_name").toString());
        safeSiJobCutRoadPersons.setType(Integer.parseInt(params.get("type").toString()));
        safeSiJobCutRoadPersons.setPersonId(params.get("person_id").toString());
        safeSiJobCutRoadPersons.setPersonName(params.get("person_name").toString());
        safeSiJobCutRoadPersons.setWorkTypeName(params.get("work_type_name").toString());
        safeSiJobCutRoadPersons.setWorkCardCode(params.get("work_card_code").toString());
        return save(safeSiJobCutRoadPersons);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobCutRoadPersons(Map<String, Object> params) {
        return removeById(params.get("id").toString());
    }
}
