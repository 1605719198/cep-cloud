package com.jlkj.safety.si.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobTemporaryElectricityPersons;
import com.jlkj.safety.si.mapper.SafeJobTemporaryElectricityPersonsMapper;
import com.jlkj.safety.si.service.SafeJobTemporaryElectricityPersonsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 临时用电安全作业证-作业人员 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-02-01
 */
@Service
public class SafeJobTemporaryElectricityPersonsServiceImpl extends ServiceImpl<SafeJobTemporaryElectricityPersonsMapper, SafeSiJobTemporaryElectricityPersons> implements SafeJobTemporaryElectricityPersonsService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobTemporaryElectricityPersons(Map<String, Object> params) {
        SafeSiJobTemporaryElectricityPersons safeSiJobTemporaryElectricityPersons = new SafeSiJobTemporaryElectricityPersons();
        safeSiJobTemporaryElectricityPersons.setId(IdUtil.randomUUID());
        safeSiJobTemporaryElectricityPersons.setJobId(params.get("job_id").toString());
        safeSiJobTemporaryElectricityPersons.setConstructionUnitId(params.get("construction_unit_id").toString());
        safeSiJobTemporaryElectricityPersons.setConstructionUnitName(params.get("construction_unit_name").toString());
        safeSiJobTemporaryElectricityPersons.setType(Integer.parseInt(params.get("type").toString()));
        safeSiJobTemporaryElectricityPersons.setPersonId(params.get("person_id").toString());
        safeSiJobTemporaryElectricityPersons.setPersonName(params.get("person_name").toString());
        safeSiJobTemporaryElectricityPersons.setWorkTypeName(params.get("work_type_name").toString());
        safeSiJobTemporaryElectricityPersons.setWorkCardCode(params.get("work_card_code").toString());
        return save(safeSiJobTemporaryElectricityPersons);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobTemporaryElectricityPersons(Map<String, Object> params) {
        return removeById(params.get("id").toString());
    }
}
