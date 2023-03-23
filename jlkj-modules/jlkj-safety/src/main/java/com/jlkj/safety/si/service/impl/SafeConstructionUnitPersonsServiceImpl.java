package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiConstructionUnitPersons;
import com.jlkj.safety.si.mapper.SafeConstructionUnitPersonsMapper;
import com.jlkj.safety.si.service.SafeConstructionUnitPersonsService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.jlkj.safety.si.constants.Constant.TYPE;

/**
 * <p>
 * 施工单位-施工人员 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-01-06
 */
@Service
public class SafeConstructionUnitPersonsServiceImpl extends ServiceImpl<SafeConstructionUnitPersonsMapper, SafeSiConstructionUnitPersons> implements SafeConstructionUnitPersonsService {

    @Resource
    private SafeConstructionUnitPersonsMapper safeConstructionUnitPersonsMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeConstructionUnitPersonsPageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeConstructionUnitPersonsMapper.getSafeConstructionUnitPersonsPageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeConstructionUnitPersons(Map<String, Object> params) {
        SafeSiConstructionUnitPersons safeSiConstructionUnitPersons = new SafeSiConstructionUnitPersons();
        safeSiConstructionUnitPersons.setId(IdUtil.randomUUID());
        safeSiConstructionUnitPersons.setConstructionUnitId(params.get("construction_unit_id").toString());
        safeSiConstructionUnitPersons.setConstructionUnitName(params.get("construction_unit_name").toString());
        safeSiConstructionUnitPersons.setType(Integer.parseInt(params.get("type").toString()));
        if (Integer.parseInt(params.get(TYPE).toString()) == 1) {
            safeSiConstructionUnitPersons.setPersonId(params.get("person_id").toString());
        }
        else {
            safeSiConstructionUnitPersons.setPersonId("");
        }
        safeSiConstructionUnitPersons.setPersonName(params.get("person_name").toString());
        safeSiConstructionUnitPersons.setWorkTypeName(params.get("work_type_name").toString());
        safeSiConstructionUnitPersons.setWorkCardCode(params.get("work_card_code").toString());
        safeSiConstructionUnitPersons.setIsLeader(Integer.parseInt(params.get("is_leader").toString()));
        safeSiConstructionUnitPersons.setCreateUserId(params.get("create_user_id").toString());
        safeSiConstructionUnitPersons.setCreateUserName(params.get("create_user_name").toString());
        safeSiConstructionUnitPersons.setCeateTime(DateUtil.parseLocalDateTime(DateUtil.now()));
        if (save(safeSiConstructionUnitPersons)) {
            return ResponseUtil.toResult(params, "施工人员保存成功");
        } else {
            return ResponseUtil.toError(params, "施工人员保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeConstructionUnitPersons(Map<String, Object> params) {
        SafeSiConstructionUnitPersons safeSiConstructionUnitPersons = getById(params.get("id").toString());
        safeSiConstructionUnitPersons.setConstructionUnitId(params.get("construction_unit_id").toString());
        safeSiConstructionUnitPersons.setConstructionUnitName(params.get("construction_unit_name").toString());
        safeSiConstructionUnitPersons.setType(Integer.parseInt(params.get("type").toString()));
        if (Integer.parseInt(params.get(TYPE).toString()) == 1) {
            safeSiConstructionUnitPersons.setPersonId(params.get("person_id").toString());
        }
        else {
            safeSiConstructionUnitPersons.setPersonId("");
        }
        safeSiConstructionUnitPersons.setPersonName(params.get("person_name").toString());
        safeSiConstructionUnitPersons.setWorkTypeName(params.get("work_type_name").toString());
        safeSiConstructionUnitPersons.setWorkCardCode(params.get("work_card_code").toString());
        safeSiConstructionUnitPersons.setIsLeader(Integer.parseInt(params.get("is_leader").toString()));
        if (updateById(safeSiConstructionUnitPersons)) {
            return ResponseUtil.toResult(params, "施工人员保存成功");
        } else {
            return ResponseUtil.toError(params, "施工人员保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeConstructionUnitPersons(Map<String, Object> params) {
        SafeSiConstructionUnitPersons safeSiConstructionUnitPersons = getById(params.get("id").toString());
        if (null != safeSiConstructionUnitPersons) {
            if (removeById(safeSiConstructionUnitPersons.getId())) {
                return ResponseUtil.toResult(params, "施工人员删除成功");
            } else {
                return ResponseUtil.toError(params, "施工人员删除失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "施工人员不存在");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsConstructionUnitPersonsById(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("construction_unit_id", params.get("id").toString());
        query.put("type", 2);
        if (listByMap(query).size() == 0) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSafeConstructionUnitPersonsConstructionUnit(Map<String, Object> params) {
        safeConstructionUnitPersonsMapper.updateSafeConstructionUnitPersonsConstructionUnit(params);
        return true;
    }
}
