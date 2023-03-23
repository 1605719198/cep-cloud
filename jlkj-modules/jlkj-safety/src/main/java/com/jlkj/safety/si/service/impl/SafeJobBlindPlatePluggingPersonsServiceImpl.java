package com.jlkj.safety.si.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobBlindPlatePluggingPersons;
import com.jlkj.safety.si.mapper.SafeJobBlindPlatePluggingPersonsMapper;
import com.jlkj.safety.si.service.SafeJobBlindPlatePluggingPersonsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 盲板抽堵安全作业证-作业人员 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-02-01
 */
@Service
public class SafeJobBlindPlatePluggingPersonsServiceImpl extends ServiceImpl<SafeJobBlindPlatePluggingPersonsMapper, SafeSiJobBlindPlatePluggingPersons> implements SafeJobBlindPlatePluggingPersonsService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobBlindPlatePluggingPersons(Map<String, Object> params) {
        SafeSiJobBlindPlatePluggingPersons safeSiJobBlindPlatePluggingPersons = new SafeSiJobBlindPlatePluggingPersons();
        safeSiJobBlindPlatePluggingPersons.setId(IdUtil.randomUUID());
        safeSiJobBlindPlatePluggingPersons.setJobId(params.get("job_id").toString());
        safeSiJobBlindPlatePluggingPersons.setConstructionUnitId(params.get("construction_unit_id").toString());
        safeSiJobBlindPlatePluggingPersons.setConstructionUnitName(params.get("construction_unit_name").toString());
        safeSiJobBlindPlatePluggingPersons.setType(Integer.parseInt(params.get("type").toString()));
        safeSiJobBlindPlatePluggingPersons.setPersonId(params.get("person_id").toString());
        safeSiJobBlindPlatePluggingPersons.setPersonName(params.get("person_name").toString());
        safeSiJobBlindPlatePluggingPersons.setWorkTypeName(params.get("work_type_name").toString());
        safeSiJobBlindPlatePluggingPersons.setWorkCardCode(params.get("work_card_code").toString());
        return save(safeSiJobBlindPlatePluggingPersons);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobBlindPlatePluggingPersons(Map<String, Object> params) {
        return remove(new QueryWrapper<SafeSiJobBlindPlatePluggingPersons>().lambda()
                .eq(SafeSiJobBlindPlatePluggingPersons::getJobId, params.get("id").toString()));
    }
}
