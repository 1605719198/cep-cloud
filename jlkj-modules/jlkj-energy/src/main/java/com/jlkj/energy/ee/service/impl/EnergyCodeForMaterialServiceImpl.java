package com.jlkj.energy.ee.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.dto.energy.ee.EnergyCodeForMaterialDTO;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.energy.ee.domain.EnergyCode;
import com.jlkj.energy.ee.domain.EnergyCodeForMaterial;
import com.jlkj.energy.ee.mapper.EnergyCodeFoMaterialMapper;
import com.jlkj.energy.ee.mapper.EnergyCodeMapper;
import com.jlkj.energy.ee.service.EnergyCodeForMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author 265675
 * 针对表【t_energy_code_fo_material(固、液体能源代码、料号对照)】的数据库操作Service实现
 * @Date: 2022-04-28 11:25:31
 */
@Service
public class EnergyCodeForMaterialServiceImpl extends ServiceImpl<EnergyCodeFoMaterialMapper, EnergyCodeForMaterial>
        implements EnergyCodeForMaterialService {

    @Autowired
    private EnergyCodeFoMaterialMapper energyCodeFoMaterialMapper;
    @Autowired
    private EnergyCodeMapper energyCodeMapper;

    private final String[] solidLiquid = {"G000", "Y000"};

    /**
     * @Description: 查询方法
     * @Author: 111191
     * @Date: 2023年7月6日, 0006 下午 02:03:30
     * @Param: energyCodeFoMaterialDTO
     * @Return: java.util.List<com.jlkj.energy.ee.domain.EnergyCodeFoMaterial>
     * @Throws:
     */
    @Override
    public List<EnergyCodeForMaterial> queryEnergyCodeFoMaterial(EnergyCodeForMaterialDTO energyCodeForMaterial) {
        LambdaQueryWrapper<EnergyCodeForMaterial> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(energyCodeForMaterial.getEngyIdStart()) && StringUtils.isNotBlank(energyCodeForMaterial.getEngyIdEnd())) {
            queryWrapper.between(EnergyCodeForMaterial::getEngyId, energyCodeForMaterial.getEngyIdStart(),
                    energyCodeForMaterial.getEngyIdEnd());
        } else if (StringUtils.isNotBlank(energyCodeForMaterial.getEngyIdStart())) {
            queryWrapper.eq(EnergyCodeForMaterial::getEngyId, energyCodeForMaterial.getEngyIdStart());
        } else if (StringUtils.isNotBlank(energyCodeForMaterial.getEngyIdEnd())) {
            queryWrapper.eq(EnergyCodeForMaterial::getEngyId, energyCodeForMaterial.getEngyIdEnd());
        }

        return energyCodeFoMaterialMapper.selectList(queryWrapper);
    }
    /**
     * 根据Id查询能源代码
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:34:21
     * @param id 查询条件 - 能源代码id
     * @return java.util.List<com.jlkj.energy.ee.domain.EnergyCodeFoMaterial> 单笔数据结果
     */
    @Override
    public EnergyCodeForMaterial queryEnergyCodeFoMaterialById(String id){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        queryWrapper.last("limit 1");
        return energyCodeFoMaterialMapper.selectOne(queryWrapper);
    }
    /**
     * 新增固液体能源代码对应料号维护资料
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:47:24
     * @param energyCodeForMaterial 固液体能源代码对应料号维护资料
     * @return int 返回操作成功/失败信息及数据结果
     */
    @Override
    public int addEnergyCodeFoMaterial(EnergyCodeForMaterial energyCodeForMaterial) {
        if (isReady(energyCodeForMaterial)) {
            new ServiceException("能源代码为：" + energyCodeForMaterial.getEngyId() + "的资料已存在，请勿重复添加");
        }
        energyCodeForMaterial.setCompId(SecurityUtils.getCompId());

        return energyCodeFoMaterialMapper.insert(energyCodeForMaterial);
    }
    /**
     * 修改 固液体能源代码对应料号维护资料
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:48:28
     * @param energyCodeForMaterial 固液体能源代码对应料号维护资料
     * @return int 返回操作成功/失败信息及数据结果
     */
    @Override
    public int updateEnergyCodeFoMaterial(EnergyCodeForMaterial energyCodeForMaterial){
        if (StringUtils.isBlank(energyCodeForMaterial.getCompId())) {
            energyCodeForMaterial.setCompId(SecurityUtils.getCompId());
        }
        LambdaUpdateWrapper<EnergyCodeForMaterial> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(energyCodeForMaterial.getEngyId()), EnergyCodeForMaterial::getEngyId, energyCodeForMaterial.getEngyId());

        return energyCodeFoMaterialMapper.update(energyCodeForMaterial, updateWrapper);
    }
    /**
     * 根据engyId 删除数据
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:41:03
     * @param engyId  能源代码id
     * @return int 操作笔数（删除数）
     */
    @Override
    public int deleteEnergyCode(String engyId){
        LambdaQueryWrapper<EnergyCodeForMaterial> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EnergyCodeForMaterial::getEngyId, engyId);

        return energyCodeFoMaterialMapper.delete(queryWrapper);
    }
    /**
     * 删除固液体能源代码对应料号维护资料
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:50:35
     * @param ids 能源代码ids 多笔
     * @return int 操作笔数（删除数）
     */
    @Override
    public int deleteEnergyCodeFoMaterial(String[] ids){
        int result = 0;
        if (StringUtils.checkValNotNull(ids)) {
            List list = Arrays.asList(ids);
            result = energyCodeFoMaterialMapper.deleteBatchIds(list);
        } else {
            new ServiceException("请选择要删除的数据项后，再进行操作");
        }
        return result;
    }
    /**
     * 查询能源代码下拉选单(中文label)
     *
     * @param
     * @return JSONArray 选单数据
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:23:00
     */
    @Override
    public JSONArray queryDropDownMenuZh() {
        LambdaQueryWrapper<EnergyCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(EnergyCode::getEngyType, solidLiquid)
                .orderByAsc(EnergyCode::getEngyId);
        List<EnergyCode> lists = energyCodeMapper.selectList(queryWrapper);
        JSONArray array = new JSONArray();
        if (ObjectUtils.isNotEmpty(lists)) {
            for (EnergyCode energyCode : lists) {
                JSONObject json = new JSONObject();
                json.put("key", energyCode.getEngyId());
                json.put("value", energyCode.getEngyId());
                json.put("label", energyCode.getEngyName());
                array.add(json);
            }
        }
        return array;
    }
    /**
     * 判断能源代码是否已存在（在数据库表中）
     * @Author: 111191
     * @Date: 2023年7月4日, 0004 下午 03:28:52
     * @Param: energyCode 能源代码
     * @Return: boolean false:不存在; true:存在
     */
    private boolean isReady(EnergyCodeForMaterial energyCodeForMaterial) {
        boolean isReady = false;
        QueryWrapper queryWrapper = new QueryWrapper();
        String energyId = queryWrapper.eq("engyId", energyCodeForMaterial.getEngyId()).toString();
        if (StringUtils.isNotBlank(energyId)) {
            isReady = true;
        }
        return isReady;
    }
}




