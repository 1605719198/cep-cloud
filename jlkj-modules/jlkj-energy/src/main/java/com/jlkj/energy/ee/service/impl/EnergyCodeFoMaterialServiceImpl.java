package com.jlkj.energy.ee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.energy.ee.EnergyCodeFoMaterialDTO;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.energy.ee.domain.EnergyCode;
import com.jlkj.energy.ee.domain.EnergyCodeFoMaterial;
import com.jlkj.energy.ee.mapper.EnergyCodeFoMaterialMapper;
import com.jlkj.energy.ee.service.EnergyCodeFoMaterialService;
import com.jlkj.energy.ee.service.EnergyCodeService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 265800
* @description 针对表【t_energy_code_fo_material(固、液体能源代码、料号对照)】的数据库操作Service实现
* @createDate 2022-04-28 11:25:31
*/
@Service
public class EnergyCodeFoMaterialServiceImpl extends ServiceImpl<EnergyCodeFoMaterialMapper, EnergyCodeFoMaterial>
    implements EnergyCodeFoMaterialService{
    @Autowired
    private EnergyCodeFoMaterialMapper energyCodeFoMaterialMapper;
    @Autowired
    EnergyCodeService energyCodeService;

    /**
     * @description 新增固、液体能源代码、料号对照
     * @author: 265800
     * @date: 2023/7/6 13:30
     * @param: [energyCodeFoMaterial]
     * @return: int
     * @throws:
     */
    @Override
    public int addEnergyCodeFoMaterial(EnergyCodeFoMaterial energyCodeFoMaterial) {
        if (isReady(energyCodeFoMaterial)) {
            new ServiceException("您输入的'能源代码、成本中心代号、能源量类型、来源系统'系统中已存在，请重新输入！");
        }
        energyCodeFoMaterial.setCompId(SecurityUtils.getCompId());
        int result = energyCodeFoMaterialMapper.insert(energyCodeFoMaterial);
        return result;
    }

    /**
     * @description 修改固、液体能源代码、料号对照
     * @author: 265800
     * @date: 2023/7/6 13:31
     * @param: [energyCodeFoMaterial]
     * @return: com.jlkj.common.core.web.domain.AjaxResult
     * @throws:
     */
    @Override
    public AjaxResult updateEnergyCodeFoMaterial(EnergyCodeFoMaterial energyCodeFoMaterial) {
        try {
            String engyId = "engy_id";
            String engyId1 = energyCodeFoMaterial.getEngyId();
            LambdaQueryWrapper<EnergyCodeFoMaterial> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(EnergyCodeFoMaterial::getEngyId, energyCodeFoMaterial.getEngyId())
                    .eq(StringUtils.isNotNull(energyCodeFoMaterial.getCostCenter()),EnergyCodeFoMaterial::getCostCenter, energyCodeFoMaterial.getCostCenter())
                    .eq(EnergyCodeFoMaterial::getEngyClass, energyCodeFoMaterial.getEngyClass())
                    .eq(EnergyCodeFoMaterial::getEngySource, energyCodeFoMaterial.getEngySource());
            LambdaUpdateWrapper<EnergyCodeFoMaterial> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(StringUtils.isNotBlank(engyId1), EnergyCodeFoMaterial::getEngyId, engyId1);
            List<Map<String, Object>> map1 = getBaseMapper().selectMaps(queryWrapper);
            if (map1.size() <= 0) {
                LambdaQueryWrapper<EnergyCode> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(StringUtils.isNotBlank(engyId1), EnergyCode::getEngyId, engyId1);
                Map<String, Object> map = energyCodeService.getMap(queryWrapper1);
                energyCodeFoMaterial.setEngyName(String.valueOf(map.get("engy_name")));
                getBaseMapper().insert(energyCodeFoMaterial);
                return AjaxResult.success("能源代码为空,已为您新增了一笔数据");
            } else {
                if(map1.get(0).get(engyId).equals(energyCodeFoMaterial.getEngyId())) {
                    int update = getBaseMapper().update(energyCodeFoMaterial, updateWrapper);
                    if (update>0){
                        Map<String, String> resultMap = new HashMap<>(16);
                        resultMap.put("compId", energyCodeFoMaterial.getCompId());
                        resultMap.put("engyId", energyCodeFoMaterial.getEngyId());
                        resultMap.put("engyName", energyCodeFoMaterial.getEngyName());
                        resultMap.put("costCenter", energyCodeFoMaterial.getCostCenter());
                        resultMap.put("engyClass", energyCodeFoMaterial.getEngyClass());
                        resultMap.put("engySource", energyCodeFoMaterial.getEngySource());
                        resultMap.put("engyCmp1No", energyCodeFoMaterial.getEngyCmp1No());
                        resultMap.put("engyCmp2No", energyCodeFoMaterial.getEngyCmp2No());
                        resultMap.put("engyCmp3No", energyCodeFoMaterial.getEngyCmp3No());
                        resultMap.put("engyCmp4No", energyCodeFoMaterial.getEngyCmp4No());
                        List<Map<String, String>> resultList = new ArrayList<>();
                        resultList.add(resultMap);
                        return AjaxResult.success("修改成功", resultList);
                    } else {
                        return AjaxResult.error("修改失败，请重新提交");
                    }
                } else {
                    return AjaxResult.error("'能源代码、成本中心代号、能源量类型、来源系统'，不允许修改");
                }
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * @description 删除固、液体能源代码、料号对照
     * @author: 265800
     * @date: 2023/7/6 13:32
     * @param: [id]
     * @return: com.jlkj.common.core.web.domain.AjaxResult
     * @throws:
     */
    @Override
    public AjaxResult deleteEnergyCodeFoMaterial(List<String> id) {
        try {
            int result = getBaseMapper().deleteBatchIds(id);
            if (result>0) {
                return AjaxResult.success("删除成功");
            } else {
                return AjaxResult.error("删除失败，请重新提交");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * @description 删除固液体能源代码对应料号维护资料(mq)
     * @author: 265800
     * @date: 2023/7/6 13:40
     * @param: [engyId]
     * @return: com.jlkj.common.core.web.domain.AjaxResult
     * @throws:
     */
    @Override
    public AjaxResult deleteEnergyCode(String engyId) {
        try {
            LambdaQueryWrapper<EnergyCodeFoMaterial> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(EnergyCodeFoMaterial::getEngyId, engyId);
            int result = getBaseMapper().delete(queryWrapper);
            if (result>0) {
                return AjaxResult.success("删除成功");
            } else {
                return AjaxResult.error("删除失败，请重新提交");
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * @description 固液体能源代码对应料号资料查询与列表
     * @author: 265800
     * @date: 2023/7/6 14:11
     * @param: [energyCodeFoMaterialDTO]
     * @return: com.jlkj.common.core.web.domain.AjaxResult
     * @throws:
     */
    @Override
    public AjaxResult queryEnergyCodeFoMaterial(EnergyCodeFoMaterialDTO energyCodeFoMaterialDTO) {
        try {
            String engyIdStart = energyCodeFoMaterialDTO.getEngyIdStart();
            String engyIdEnd = energyCodeFoMaterialDTO.getEngyIdEnd();
            Long pageNum = energyCodeFoMaterialDTO.getPageNum();
            Long pageSize = energyCodeFoMaterialDTO.getPageSize();
            LambdaQueryWrapper<EnergyCodeFoMaterial> queryWrapper = new LambdaQueryWrapper<>();
            if (StringUtils.isNotBlank(engyIdStart) && StringUtils.isNotBlank(engyIdEnd)) {
                queryWrapper.between(EnergyCodeFoMaterial::getEngyId, engyIdStart, engyIdEnd);
            } else if (StringUtils.isNotBlank(engyIdStart)) {
                queryWrapper.eq(EnergyCodeFoMaterial::getEngyId, engyIdStart);
            } else if (StringUtils.isNotBlank(engyIdEnd)) {
                queryWrapper.eq(EnergyCodeFoMaterial::getEngyId, engyIdEnd);
            }
            Page<EnergyCodeFoMaterial> page = getBaseMapper().selectPage(new Page<>(pageNum, pageSize), queryWrapper);
            //总记录数
            long total = page.getTotal();
            //数据list集合
            List<EnergyCodeFoMaterial> records = page.getRecords();
            Map<String,Object> dataMap = new HashMap<>(16);
            dataMap.put("total",total);
            dataMap.put("list",records);
            if (records.isEmpty()){
                return AjaxResult.success("查无资料", dataMap);
            } else {
                return AjaxResult.success("查询成功！", dataMap);
            }
        } catch (Exception e) {
            return AjaxResult.error();
        }
    }

    /**
     * @description 查询固液体能源代码对应料号下拉选单
     * @author: 265800
     * @date: 2023/7/6 14:10
     * @param: []
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @throws:
     */
    @Override
    public List<Map<String, String>> queryDropDownMenuZh() {
        List<EnergyCodeFoMaterial> list = getBaseMapper().selectList(null);
        List<Map<String, String>> resultList = new ArrayList<>();
        for (EnergyCodeFoMaterial energyCodeFoMaterial : list) {
            Map<String, String> resultMap = new HashMap<>(16);
            resultMap.put("value", energyCodeFoMaterial.getEngyId());
            resultMap.put("label", energyCodeFoMaterial.getEngyName());
            resultList.add(resultMap);
        }
        return resultList;
    }


    /**
     * @description 判断能源代码、成本中心代号、能源量类型、来源系统是否已存在（在数据库表中）
     * @author: 265800
     * @date: 2023/7/6 9:12
     * @param: [energyCodeFoMaterial]
     * @return: boolean false:不存在; true:存在
     * @throws:
     */
    private boolean isReady(EnergyCodeFoMaterial energyCodeFoMaterial) {
        boolean isReady = false;
        LambdaQueryWrapper<EnergyCodeFoMaterial> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(EnergyCodeFoMaterial::getEngyId, energyCodeFoMaterial.getEngyId())
                .eq(EnergyCodeFoMaterial::getCostCenter, energyCodeFoMaterial.getCostCenter())
                .eq(EnergyCodeFoMaterial::getEngyClass, energyCodeFoMaterial.getEngyClass())
                .eq(EnergyCodeFoMaterial::getEngySource, energyCodeFoMaterial.getEngySource());
        List<EnergyCodeFoMaterial> list = getBaseMapper().selectList(queryWrapper);
        if (!list.isEmpty()) {
            isReady = true;
        }
        return isReady;
    }
}




