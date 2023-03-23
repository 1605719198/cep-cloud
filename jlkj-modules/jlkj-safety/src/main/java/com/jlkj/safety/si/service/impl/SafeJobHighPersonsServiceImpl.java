package com.jlkj.safety.si.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobHighPersons;
import com.jlkj.safety.si.mapper.SafeJobHighPersonsMapper;
import com.jlkj.safety.si.service.SafeJobHighPersonsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 高处安全作业证-作业人员 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-02-01
 */
@Service
public class SafeJobHighPersonsServiceImpl extends ServiceImpl<SafeJobHighPersonsMapper, SafeSiJobHighPersons> implements SafeJobHighPersonsService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobHighPersons(Map<String, Object> params) {
        SafeSiJobHighPersons safeSiJobHighPersons = new SafeSiJobHighPersons();
        safeSiJobHighPersons.setId(IdUtil.randomUUID());
        safeSiJobHighPersons.setJobId(params.get("job_id").toString());
        safeSiJobHighPersons.setConstructionUnitId(params.get("construction_unit_id").toString());
        safeSiJobHighPersons.setConstructionUnitName(params.get("construction_unit_name").toString());
        safeSiJobHighPersons.setType(Integer.parseInt(params.get("type").toString()));
        safeSiJobHighPersons.setPersonId(params.get("person_id").toString());
        safeSiJobHighPersons.setPersonName(params.get("person_name").toString());
        safeSiJobHighPersons.setWorkTypeName(params.get("work_type_name").toString());
        safeSiJobHighPersons.setWorkCardCode(params.get("work_card_code").toString());
        return save(safeSiJobHighPersons);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobHighPersons(Map<String, Object> params) {
        return removeById(params.get("id").toString());
    }
}
