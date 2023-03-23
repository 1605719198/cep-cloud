package com.jlkj.safety.si.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobSpacePersons;
import com.jlkj.safety.si.mapper.SafeJobSpacePersonsMapper;
import com.jlkj.safety.si.service.SafeJobSpacePersonsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 受限空间安全作业证-作业人员 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-26
 */
@Service
public class SafeJobSpacePersonsServiceImpl extends ServiceImpl<SafeJobSpacePersonsMapper, SafeSiJobSpacePersons> implements SafeJobSpacePersonsService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobSpacePersons(Map<String, Object> params) {
        SafeSiJobSpacePersons safeSiJobSpacePersons = new SafeSiJobSpacePersons();
        safeSiJobSpacePersons.setId(IdUtil.randomUUID());
        safeSiJobSpacePersons.setJobId(params.get("job_id").toString());
        safeSiJobSpacePersons.setConstructionUnitId(params.get("construction_unit_id").toString());
        safeSiJobSpacePersons.setConstructionUnitName(params.get("construction_unit_name").toString());
        safeSiJobSpacePersons.setType(Integer.parseInt(params.get("type").toString()));
        safeSiJobSpacePersons.setPersonId(params.get("person_id").toString());
        safeSiJobSpacePersons.setPersonName(params.get("person_name").toString());
        safeSiJobSpacePersons.setWorkTypeName(params.get("work_type_name").toString());
        safeSiJobSpacePersons.setWorkCardCode(params.get("work_card_code").toString());
        return save(safeSiJobSpacePersons);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobSpacePersons(Map<String, Object> params) {
        return removeById(params.get("id").toString());
    }
}
