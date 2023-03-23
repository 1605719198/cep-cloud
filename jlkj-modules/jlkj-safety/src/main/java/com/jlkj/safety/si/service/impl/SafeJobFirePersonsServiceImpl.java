package com.jlkj.safety.si.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiJobFirePersons;
import com.jlkj.safety.si.mapper.SafeJobFirePersonsMapper;
import com.jlkj.safety.si.service.SafeJobFirePersonsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * 动火安全作业证-动火人 服务实现类
 * </p>
 *
 * @author su
 * @since 2020-12-28
 */
@Service
public class SafeJobFirePersonsServiceImpl extends ServiceImpl<SafeJobFirePersonsMapper, SafeSiJobFirePersons> implements SafeJobFirePersonsService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertSafeJobFirePersons(Map<String, Object> params) {
        SafeSiJobFirePersons safeSiJobFirePersons = new SafeSiJobFirePersons();
        safeSiJobFirePersons.setId(IdUtil.randomUUID());
        safeSiJobFirePersons.setJobId(params.get("job_id").toString());
        safeSiJobFirePersons.setConstructionUnitId(params.get("construction_unit_id").toString());
        safeSiJobFirePersons.setConstructionUnitName(params.get("construction_unit_name").toString());
        safeSiJobFirePersons.setType(Integer.parseInt(params.get("type").toString()));
        safeSiJobFirePersons.setPersonId(params.get("person_id").toString());
        safeSiJobFirePersons.setPersonName(params.get("person_name").toString());
        safeSiJobFirePersons.setWorkTypeName(params.get("work_type_name").toString());
        safeSiJobFirePersons.setWorkCardCode(params.get("work_card_code").toString());
        safeSiJobFirePersons.setOutFireUserName("2".equals(params.get("type").toString()) ? params.get("person_name").toString() : "");
        return save(safeSiJobFirePersons);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSafeJobFirePersons(Map<String, Object> params) {
        return removeById(params.get("id").toString());
    }

}
