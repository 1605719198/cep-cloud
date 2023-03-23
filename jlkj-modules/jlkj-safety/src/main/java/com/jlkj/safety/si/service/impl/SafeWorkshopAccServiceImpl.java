package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeSiWorkshopAcc;
import com.jlkj.safety.si.mapper.SafeWorkshopAccMapper;
import com.jlkj.safety.si.service.SafeWorkshopAccService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jlkj.common.core.constant.RabbitConstant.SAFETY_EXCHANGE;
import static com.jlkj.common.core.constant.RabbitConstant.SAFETY_WORKSHOPACC_ROUTE_KEY;
import static com.jlkj.safety.si.constants.Constant.ID;

/**
 * <p>
 * 车间明细台账 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-05-20
 */
@Service
public class SafeWorkshopAccServiceImpl extends ServiceImpl<SafeWorkshopAccMapper, SafeSiWorkshopAcc> implements SafeWorkshopAccService {
    @Resource
    private SafeWorkshopAccMapper safeWorkshopAccMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeWorkshopAccPageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeWorkshopAccMapper.getSafeWorkshopAccPageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeWorkshopAccPageListByProcedure(Map<String, Object> params) {
        StringBuilder sqlSectionCol = new StringBuilder();
        StringBuilder sqlSectionMax = new StringBuilder();
        List<Map<String, Object>> listCol = safeWorkshopAccMapper.getSafeworkshopAccSectionColList(params);
        for (int i = 0; i < listCol.size(); i++) {
            sqlSectionCol.append(", case when position = '" + listCol.get(i).get("section_name").toString() + "' then concat(id,'_',convert(amount, char)) else '' end as `" + listCol.get(i).get("id").toString() + "`");
            sqlSectionMax.append(", max(`" + listCol.get(i).get("id").toString() + "`) as `" + listCol.get(i).get("id").toString() + "`");
        }
        params.put("sql_section_col", sqlSectionCol.toString());
        params.put("sql_section_max", sqlSectionMax.toString());
        return ResponseUtil.toResult(params, safeWorkshopAccMapper.getSafeWorkshopAccPageListByProcedure(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeWorkshopAcc(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("workshop_name", params.get("workshop_name").toString());
        query.put("position", params.get("position").toString());
        query.put("material_name", params.get("material_name").toString());
        query.put("specification", params.get("specification").toString());
        if (listByMap(query).size() == 0) {
            SafeSiWorkshopAcc safeSiWorkshopAcc = new SafeSiWorkshopAcc();
            safeSiWorkshopAcc.setId(IdUtil.randomUUID());
            safeSiWorkshopAcc.setWorkshopName(params.get("workshop_name").toString());
            safeSiWorkshopAcc.setMaterialName(params.get("material_name").toString());
            safeSiWorkshopAcc.setType(params.get("type").toString());
            safeSiWorkshopAcc.setSpecification(params.get("specification").toString());
            safeSiWorkshopAcc.setAmount(Float.valueOf(params.get("amount").toString()));
            safeSiWorkshopAcc.setPosition(params.get("position").toString());
            safeSiWorkshopAcc.setCreateUserId(params.get("create_user_id").toString());
            safeSiWorkshopAcc.setCreateUserNo(params.get("create_user_no").toString());
            safeSiWorkshopAcc.setCreateUser(params.get("create_user").toString());
            safeSiWorkshopAcc.setCreateTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            safeSiWorkshopAcc.setModifyUserId(params.get("create_user_id").toString());
            safeSiWorkshopAcc.setModifyUserNo(params.get("create_user_no").toString());
            safeSiWorkshopAcc.setModifyUser(params.get("create_user").toString());
            safeSiWorkshopAcc.setModifyTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            safeSiWorkshopAcc.setPersonLiableId(params.get("person_liable_id").toString());
            safeSiWorkshopAcc.setPersonLiableNo(params.get("person_liable_no").toString());
            safeSiWorkshopAcc.setPersonLiableName(params.get("person_liable_name").toString());
            safeSiWorkshopAcc.setPersonLiableTel(params.get("person_liable_tel").toString());
            if (save(safeSiWorkshopAcc)) {
                Map<String, Object>  queueParam = new HashMap<>(1);
                queueParam.put("action_code", "N");
                queueParam.put("id_list", safeSiWorkshopAcc.getId());
                sendSafeWorkshopAccMessageQueue(queueParam);
                return ResponseUtil.toResult(params, "车间明细台账保存成功");
            } else {
                return ResponseUtil.toError(params, "车间明细台账保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "车间明细台账已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeWorkshopAcc(Map<String, Object> params) {
        SafeSiWorkshopAcc safeSiWorkshopAcc = getById(params.get("id").toString());
        if (null != safeSiWorkshopAcc) {
            QueryWrapper<SafeSiWorkshopAcc> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("workshop_name", params.get("workshop_name").toString())
                    .eq("position", params.get("position").toString())
                    .eq("material_name", params.get("material_name").toString())
                    .eq("specification", params.get("specification").toString())
                    .ne("id", params.get("id").toString());
            if (listMaps(queryWrapper).size() == 0) {
                safeSiWorkshopAcc.setWorkshopName(params.get("workshop_name").toString());
                safeSiWorkshopAcc.setMaterialName(params.get("material_name").toString());
                safeSiWorkshopAcc.setType(params.get("type").toString());
                safeSiWorkshopAcc.setSpecification(params.get("specification").toString());
                safeSiWorkshopAcc.setAmount(Float.valueOf(params.get("amount").toString()));
                safeSiWorkshopAcc.setPosition(params.get("position").toString());
                safeSiWorkshopAcc.setModifyUserId(params.get("modify_user_id").toString());
                safeSiWorkshopAcc.setModifyUserNo(params.get("modify_user_no").toString());
                safeSiWorkshopAcc.setModifyUser(params.get("modify_user").toString());
                safeSiWorkshopAcc.setModifyTime(DateUtil.parseLocalDateTime(DateUtil.now()));
                safeSiWorkshopAcc.setPersonLiableId(params.get("person_liable_id").toString());
                safeSiWorkshopAcc.setPersonLiableNo(params.get("person_liable_no").toString());
                safeSiWorkshopAcc.setPersonLiableName(params.get("person_liable_name").toString());
                safeSiWorkshopAcc.setPersonLiableTel(params.get("person_liable_tel").toString());
                if (updateById(safeSiWorkshopAcc)) {
                    Map<String, Object>  queueParam = new HashMap<>(1);
                    queueParam.put("action_code", "R");
                    queueParam.put("id_list", safeSiWorkshopAcc.getId());
                    sendSafeWorkshopAccMessageQueue(queueParam);
                    return ResponseUtil.toResult(params, "车间明细台账保存成功");
                } else {
                    return ResponseUtil.toError(params, "车间明细台账保存失败");
                }
            }
            else {
                return ResponseUtil.toError(params, "车间明细台账已存在");
            }
        }
        else {
            return ResponseUtil.toError(params, "车间明细台账记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeWorkshopAcc(Map<String, Object> params) {
        if (removeById(params.get(ID).toString())) {
            return ResponseUtil.toResult(params, "车间明细台账删除成功");
        }
        else {
            return ResponseUtil.toError(params, "车间明细台账删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeWorkshopAccs(Map<String, Object> params) {
        try {
            Map<String, Object>  deleteList = safeWorkshopAccMapper.getErpSafeworkshopAccDeleteList(params);
            Map<String, Object>  queueParam = new HashMap<>(1);
            queueParam.put("action_code", "D");
            queueParam.put("id_list", deleteList.get("id_list"));
            safeWorkshopAccMapper.deleteSafeWorkshopAcc(params);
            sendSafeWorkshopAccMessageQueue(queueParam);
            return ResponseUtil.toResult(params, "车间明细台账删除成功");
        } catch (Exception ex) {
            return ResponseUtil.toError(params, "车间明细台账删除失败");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Object getSafeworkshopAccSectionList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeWorkshopAccMapper.getSafeworkshopAccSectionList(params));
    }

    @Override
    @Transactional(readOnly = true)
    @Async
    public void sendSafeWorkshopAccMessageQueue(Map<String, Object> params) {
        Map<String, Object> outMap = new HashMap<>(1);
        outMap.put("class", "com.icsc.si.logic.sijcnb30Logic");
        outMap.put("message_id", "si30");
        outMap.put("action_code", params.get("action_code").toString());
        outMap.put("data_source", "cep");
        outMap.put("data", safeWorkshopAccMapper.getErpSafeworkshopAccListByIds(params));
        rabbitTemplate.convertAndSend(SAFETY_EXCHANGE, SAFETY_WORKSHOPACC_ROUTE_KEY, JSONObject.toJSONString(outMap));
    }

    @Override
    public Object getSafeworkshopAccInfo(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeWorkshopAccMapper.getSafeworkshopAccInfo(params));
    }
}
