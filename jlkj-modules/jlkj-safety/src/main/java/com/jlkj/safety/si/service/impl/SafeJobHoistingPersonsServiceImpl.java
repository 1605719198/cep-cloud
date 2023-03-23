package com.jlkj.safety.si.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobHoistingPersons;
import com.jlkj.safety.si.mapper.SafeJobHoistingPersonsMapper;
import com.jlkj.safety.si.service.SafeJobHoistingPersonsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 吊装安全作业证-作业人员 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-02-01
 */
@Service
public class SafeJobHoistingPersonsServiceImpl extends ServiceImpl<SafeJobHoistingPersonsMapper, SafeSiJobHoistingPersons> implements SafeJobHoistingPersonsService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobHoistingPersons(Map<String, Object> params) {
        SafeSiJobHoistingPersons safeSiJobHoistingPersons = new SafeSiJobHoistingPersons();
        safeSiJobHoistingPersons.setId(IdUtil.randomUUID());
        safeSiJobHoistingPersons.setJobId(params.get("job_id").toString());
        safeSiJobHoistingPersons.setConstructionUnitId(params.get("construction_unit_id").toString());
        safeSiJobHoistingPersons.setConstructionUnitName(params.get("construction_unit_name").toString());
        safeSiJobHoistingPersons.setType(Integer.parseInt(params.get("type").toString()));
        safeSiJobHoistingPersons.setPersonId(params.get("person_id").toString());
        safeSiJobHoistingPersons.setPersonName(params.get("person_name").toString());
        safeSiJobHoistingPersons.setWorkTypeName(params.get("work_type_name").toString());
        safeSiJobHoistingPersons.setWorkCardCode(params.get("work_card_code").toString());
        safeSiJobHoistingPersons.setPersonType(Integer.parseInt(params.get("person_type").toString()));
        return save(safeSiJobHoistingPersons);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobHoistingPersons(Map<String, Object> params) {
        return removeById(params.get("id").toString());
    }
}
