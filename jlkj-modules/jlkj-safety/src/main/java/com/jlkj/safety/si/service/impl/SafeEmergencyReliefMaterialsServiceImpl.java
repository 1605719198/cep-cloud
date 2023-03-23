package com.jlkj.safety.si.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.entity.SafeEmergencyReliefMaterials;
import com.jlkj.safety.si.mapper.SafeEmergencyReliefMaterialsMapper;
import com.jlkj.safety.si.service.SafeEmergencyReliefMaterialsService;
import com.jlkj.safety.si.utils.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.jlkj.safety.si.constants.Constant.ID;

/**
 * <p>
 * 气防站应急救援物资 服务实现类
 * </p>
 *
 * @author su
 * @since 2021-05-20
 */
@Service
public class SafeEmergencyReliefMaterialsServiceImpl extends ServiceImpl<SafeEmergencyReliefMaterialsMapper, SafeEmergencyReliefMaterials> implements SafeEmergencyReliefMaterialsService {
    @Resource
    private SafeEmergencyReliefMaterialsMapper safeEmergencyReliefMaterialsMapper;

    @Override
    @Transactional(readOnly = true)
    public Object getSafeEmergencyReliefMaterialsPageList(Map<String, Object> params) {
        return ResponseUtil.toResult(params, safeEmergencyReliefMaterialsMapper.getSafeEmergencyReliefMaterialsPageList(ResponseUtil.initPage(params), params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertSafeEmergencyReliefMaterials(Map<String, Object> params) {
        Map<String, Object> query = new HashMap<>(1);
        query.put("material_name", params.get("material_name").toString());
        query.put("specification", params.get("specification").toString());
        if (listByMap(query).size() == 0) {
            SafeEmergencyReliefMaterials safeEmergencyReliefMaterials = new SafeEmergencyReliefMaterials();
            safeEmergencyReliefMaterials.setId(IdUtil.randomUUID());
            safeEmergencyReliefMaterials.setMaterialName(params.get("material_name").toString());
            safeEmergencyReliefMaterials.setSpecification(params.get("specification").toString());
            safeEmergencyReliefMaterials.setAmount(Float.valueOf(params.get("amount").toString()));
            safeEmergencyReliefMaterials.setChargePersonId(params.get("charge_person_id").toString());
            safeEmergencyReliefMaterials.setChargePerson(params.get("charge_person").toString());
            safeEmergencyReliefMaterials.setRemark(params.get("remark").toString());
            safeEmergencyReliefMaterials.setCreateTime(DateUtil.parseLocalDateTime(DateUtil.now()));
            safeEmergencyReliefMaterials.setCreateUser(params.get("create_user").toString());
            if (save(safeEmergencyReliefMaterials)) {
                return ResponseUtil.toResult(params, "气防站应急救援物资保存成功");
            } else {
                return ResponseUtil.toError(params, "气防站应急救援物资保存失败");
            }
        }
        else {
            return ResponseUtil.toError(params, "物资已存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateSafeEmergencyReliefMaterials(Map<String, Object> params) {
        SafeEmergencyReliefMaterials safeEmergencyReliefMaterials = getById(params.get("id").toString());
        if (null != safeEmergencyReliefMaterials) {
            QueryWrapper<SafeEmergencyReliefMaterials> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("material_name", params.get("material_name").toString())
                    .eq("specification", params.get("specification").toString())
                    .ne("id", params.get("id").toString());
            if (listMaps(queryWrapper).size() == 0) {
                safeEmergencyReliefMaterials.setMaterialName(params.get("material_name").toString());
                safeEmergencyReliefMaterials.setSpecification(params.get("specification").toString());
                safeEmergencyReliefMaterials.setAmount(Float.valueOf(params.get("amount").toString()));
                safeEmergencyReliefMaterials.setChargePersonId(params.get("charge_person_id").toString());
                safeEmergencyReliefMaterials.setChargePerson(params.get("charge_person").toString());
                safeEmergencyReliefMaterials.setRemark(params.get("remark").toString());
                if (updateById(safeEmergencyReliefMaterials)) {
                    return ResponseUtil.toResult(params, "气防站应急救援物资保存成功");
                } else {
                    return ResponseUtil.toError(params, "气防站应急救援物资保存失败");
                }
            }
            else {
                return ResponseUtil.toError(params, "物资已存在");
            }
        }
        else {
            return ResponseUtil.toError(params, "气防站应急救援物资记录不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteSafeEmergencyReliefMaterials(Map<String, Object> params) {
        if (removeById(params.get(ID).toString())) {
            return ResponseUtil.toResult(params, "气防站应急救援物资删除成功");
        }
        else {
            return ResponseUtil.toError(params, "气防站应急救援物资删除失败");
        }
    }

}
