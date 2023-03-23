package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.safety.si.mapper.SafeWorkshopFireExtinguisherAccMapper;
import com.jlkj.safety.si.service.SafeWorkshopFireExtinguisherAccService;
import com.jlkj.safety.si.entity.SafeWorkshopFireExtinguisherAcc;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.jlkj.safety.si.constants.Constant.ID;

/**
 * <p>
 * 灭火器台账 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-05-20
 */
@Service
@DS("slave")
public class SafeWorkshopFireExtinguisherAccServiceImpl extends ServiceImpl<SafeWorkshopFireExtinguisherAccMapper, SafeWorkshopFireExtinguisherAcc> implements SafeWorkshopFireExtinguisherAccService {

    @Resource
    private SafeWorkshopFireExtinguisherAccMapper safeWorkshopFireExtinguisherAccMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeWorkshopFireExtinguisherAccPageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeWorkshopFireExtinguisherAccMapper.getSafeWorkshopFireExtinguisherAccPageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeWorkshopFireExtinguisherAcc(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(0);
        query.put("workshop_name", params.get("workshop_name").toString());
        query.put("position", params.get("position").toString());
        query.put("material_name", params.get("material_name").toString());
        query.put("specification", params.get("specification").toString());
        if (listByMap(query).size() == 0) {
            SafeWorkshopFireExtinguisherAcc safeWorkshopFireExtinguisherAcc = new SafeWorkshopFireExtinguisherAcc();
            safeWorkshopFireExtinguisherAcc.setId(IdUtil.randomUUID());
            safeWorkshopFireExtinguisherAcc.setWorkshopName(params.get("workshop_name").toString());
            safeWorkshopFireExtinguisherAcc.setPosition(params.get("position").toString());
            safeWorkshopFireExtinguisherAcc.setMaterialName(params.get("material_name").toString());
            safeWorkshopFireExtinguisherAcc.setSpecification(params.get("specification").toString());
            safeWorkshopFireExtinguisherAcc.setAmount(Float.valueOf(params.get("amount").toString()));
            safeWorkshopFireExtinguisherAcc.setOutDate("".equals(params.get("out_date").toString()) ? null : DateUtil.parseLocalDateTime(params.get("out_date").toString()));
            safeWorkshopFireExtinguisherAcc.setFirstDate("".equals(params.get("first_date").toString()) ? null : DateUtil.parseLocalDateTime(params.get("first_date").toString()));
            safeWorkshopFireExtinguisherAcc.setNextDate("".equals(params.get("next_date").toString()) ? null : DateUtil.parseLocalDateTime(params.get("next_date").toString()));
            safeWorkshopFireExtinguisherAcc.setBadDate("".equals(params.get("bad_date").toString()) ? null : DateUtil.parseLocalDateTime(params.get("bad_date").toString()));
            safeWorkshopFireExtinguisherAcc.setChargePerson(params.get("charge_person").toString());
            safeWorkshopFireExtinguisherAcc.setRemark(params.get("remark").toString());
            safeWorkshopFireExtinguisherAcc.setCreateTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            safeWorkshopFireExtinguisherAcc.setCreateUser(params.get("create_user").toString());
            if (save(safeWorkshopFireExtinguisherAcc)) {
                return ResponseUtil.toResult(params, "灭火器台账保存成功");
            } else {
                return ResponseUtil.toError(params, "灭火器台账保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "灭火器台账已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeWorkshopFireExtinguisherAcc(Map<String, Object> params) {
        SafeWorkshopFireExtinguisherAcc safeWorkshopFireExtinguisherAcc = getById(params.get("id").toString());
        if (null != safeWorkshopFireExtinguisherAcc) {
            QueryWrapper<SafeWorkshopFireExtinguisherAcc> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("workshop_name", params.get("workshop_name").toString())
                    .eq("position", params.get("position").toString())
                    .eq("material_name", params.get("material_name").toString())
                    .eq("specification", params.get("specification").toString())
                    .ne("id", params.get("id").toString());
            if (listMaps(queryWrapper).size() == 0) {
                safeWorkshopFireExtinguisherAcc.setWorkshopName(params.get("workshop_name").toString());
                safeWorkshopFireExtinguisherAcc.setPosition(params.get("position").toString());
                safeWorkshopFireExtinguisherAcc.setMaterialName(params.get("material_name").toString());
                safeWorkshopFireExtinguisherAcc.setSpecification(params.get("specification").toString());
                safeWorkshopFireExtinguisherAcc.setAmount(Float.valueOf(params.get("amount").toString()));
                safeWorkshopFireExtinguisherAcc.setOutDate("".equals(params.get("out_date").toString()) ? null : DateUtil.parseLocalDateTime(params.get("out_date").toString()));
                safeWorkshopFireExtinguisherAcc.setFirstDate("".equals(params.get("first_date").toString()) ? null : DateUtil.parseLocalDateTime(params.get("first_date").toString()));
                safeWorkshopFireExtinguisherAcc.setNextDate("".equals(params.get("next_date").toString()) ? null : DateUtil.parseLocalDateTime(params.get("next_date").toString()));
                safeWorkshopFireExtinguisherAcc.setBadDate("".equals(params.get("bad_date").toString()) ? null : DateUtil.parseLocalDateTime(params.get("bad_date").toString()));
                safeWorkshopFireExtinguisherAcc.setChargePerson(params.get("charge_person").toString());
                safeWorkshopFireExtinguisherAcc.setRemark(params.get("remark").toString());
                if (updateById(safeWorkshopFireExtinguisherAcc)) {
                    return ResponseUtil.toResult(params, "灭火器台账保存成功");
                } else {
                    return ResponseUtil.toError(params, "灭火器台账保存失败");
                }
            }
            else {
                return ResponseUtil.toError(params, "灭火器台账已存在");
            }
        }
        else {
            return ResponseUtil.toError(params, "灭火器台账记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeWorkshopFireExtinguisherAcc(Map<String, Object> params) {
        if (removeById(params.get(ID).toString())) {
            return ResponseUtil.toResult(params, "灭火器台账删除成功");
        }
        else {
            return ResponseUtil.toError(params, "灭火器台账删除失败");
        }
    }
}
