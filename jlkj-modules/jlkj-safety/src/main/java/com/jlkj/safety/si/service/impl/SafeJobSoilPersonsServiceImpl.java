package com.jlkj.safety.si.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobSoilPersons;
import com.jlkj.safety.si.mapper.SafeJobSoilPersonsMapper;
import com.jlkj.safety.si.service.SafeJobSoilPersonsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 动土安全作业证-作业人员 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-02-01
 */
@Service
public class SafeJobSoilPersonsServiceImpl extends ServiceImpl<SafeJobSoilPersonsMapper, SafeSiJobSoilPersons> implements SafeJobSoilPersonsService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobSoilPersons(Map<String, Object> params) {
        SafeSiJobSoilPersons safeSiJobSoilPersons = new SafeSiJobSoilPersons();
        safeSiJobSoilPersons.setId(IdUtil.randomUUID());
        safeSiJobSoilPersons.setJobId(params.get("job_id").toString());
        safeSiJobSoilPersons.setConstructionUnitId(params.get("construction_unit_id").toString());
        safeSiJobSoilPersons.setConstructionUnitName(params.get("construction_unit_name").toString());
        safeSiJobSoilPersons.setType(Integer.parseInt(params.get("type").toString()));
        safeSiJobSoilPersons.setPersonId(params.get("person_id").toString());
        safeSiJobSoilPersons.setPersonName(params.get("person_name").toString());
        safeSiJobSoilPersons.setWorkTypeName(params.get("work_type_name").toString());
        safeSiJobSoilPersons.setWorkCardCode(params.get("work_card_code").toString());
        return save(safeSiJobSoilPersons);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobSoilPersons(Map<String, Object> params) {
        return removeById(params.get("id").toString());
    }
}
