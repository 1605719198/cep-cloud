package com.jlkj.energy.ee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.constant.CommonConstant;
import com.jlkj.energy.ee.dto.energyinoutlimit.DeleteEnergyInOutLimitDTO;
import com.jlkj.energy.ee.dto.energyinoutlimit.InsertEnergyInOutLimitDTO;
import com.jlkj.energy.ee.dto.energyinoutlimit.PageEnergyInOutLimitDTO;
import com.jlkj.energy.ee.dto.energyinoutlimit.UpdateEnergyInOutLimitDTO;
import com.jlkj.energy.ee.domain.EnergyInOutLimit;
import com.jlkj.energy.ee.mapper.EnergyInOutLimitMapper;
import com.jlkj.energy.ee.service.EnergyInOutLimitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
* @author Liukuiyan
* @description 针对表【t_energy_in_out_limit(能源产耗标准配置表)】的数据库操作Service实现
* @createDate 2023-02-13 16:39:04
*/
@Service
public class EnergyInOutLimitServiceImpl extends ServiceImpl<EnergyInOutLimitMapper, EnergyInOutLimit>
    implements EnergyInOutLimitService{

    @Override
    public IPage<EnergyInOutLimit> getEnergyInformationConfigurationPageData(PageEnergyInOutLimitDTO pageEnergyInOutLimitDTO) {
        Page<Map<String, Object>> page = new Page<>(pageEnergyInOutLimitDTO.getCurrent(), pageEnergyInOutLimitDTO.getSize());
        return getBaseMapper().getEnergyInOutLimitPageData(page, pageEnergyInOutLimitDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertEnergyInformationConfigurationData(InsertEnergyInOutLimitDTO insertEnergyInformationConfigurationDTO) {
        List<EnergyInOutLimit> list = list(new QueryWrapper<EnergyInOutLimit>().lambda()
                .eq(EnergyInOutLimit::getInOutTypeId, insertEnergyInformationConfigurationDTO.getInOutTypeId())
                .eq(EnergyInOutLimit::getEngyId, insertEnergyInformationConfigurationDTO.getEngyId())
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if (list.size() > 0) {
            return AjaxResult.error("能源信息配置已存在");
        }
        EnergyInOutLimit energyInOutLimit = new EnergyInOutLimit();
        energyInOutLimit.setId(UUID.randomUUID().toString());
        energyInOutLimit.setUpLimit(insertEnergyInformationConfigurationDTO.getUpLimit());
        energyInOutLimit.setDownLimit(insertEnergyInformationConfigurationDTO.getDownLimit());
        energyInOutLimit.setDownLimitPercent(insertEnergyInformationConfigurationDTO.getDownLimitPercent());
        energyInOutLimit.setUpLimitPercent(insertEnergyInformationConfigurationDTO.getUpLimitPercent());
        energyInOutLimit.setIsPercentTips(insertEnergyInformationConfigurationDTO.getIsPercentTips());
        energyInOutLimit.setTipsColor(insertEnergyInformationConfigurationDTO.getTipsColor());
        energyInOutLimit.setInOutTypeId(insertEnergyInformationConfigurationDTO.getInOutTypeId());
        energyInOutLimit.setEngyId(insertEnergyInformationConfigurationDTO.getEngyId());
        energyInOutLimit.setCostCenterNo(insertEnergyInformationConfigurationDTO.getCostCenterNo());
        energyInOutLimit.setCostCenterName(insertEnergyInformationConfigurationDTO.getCostCenterName());
        energyInOutLimit.setChargeCostCenterNo(insertEnergyInformationConfigurationDTO.getChargeCostCenterNo());
        energyInOutLimit.setChargeCostCenterName(insertEnergyInformationConfigurationDTO.getChargeCostCenterName());
        energyInOutLimit.setCreateUser(insertEnergyInformationConfigurationDTO.getCreateUser());
        energyInOutLimit.setCreateUserName(insertEnergyInformationConfigurationDTO.getCreateUserName());
        energyInOutLimit.setCreateTime(new Date());
        energyInOutLimit.setUpdateUser(insertEnergyInformationConfigurationDTO.getCreateUser());
        energyInOutLimit.setUpdateUserName(insertEnergyInformationConfigurationDTO.getCreateUserName());
        energyInOutLimit.setUpdateTime(new Date());
        save(energyInOutLimit);
        return AjaxResult.success("能源信息配置增加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateEnergyInformationConfigurationData(UpdateEnergyInOutLimitDTO updateEnergyInformationConfigurationDTO) {
        EnergyInOutLimit energyInOutLimit = getById(updateEnergyInformationConfigurationDTO.getId());
        if (null != energyInOutLimit) {
            List<EnergyInOutLimit> list = list(new QueryWrapper<EnergyInOutLimit>().lambda()
                    .eq(EnergyInOutLimit::getInOutTypeId, updateEnergyInformationConfigurationDTO.getInOutTypeId())
                    .eq(EnergyInOutLimit::getEngyId, updateEnergyInformationConfigurationDTO.getEngyId())
                    .ne(EnergyInOutLimit::getId, updateEnergyInformationConfigurationDTO.getId())
                    .last(CommonConstant.LIMIT_ONE_ROW)
            );
            if (list.size() > 0) {
                return AjaxResult.error( "能源信息配置已存在");
            }
            energyInOutLimit.setUpLimit(updateEnergyInformationConfigurationDTO.getUpLimit());
            energyInOutLimit.setDownLimit(updateEnergyInformationConfigurationDTO.getDownLimit());
            energyInOutLimit.setDownLimitPercent(updateEnergyInformationConfigurationDTO.getDownLimitPercent());
            energyInOutLimit.setUpLimitPercent(updateEnergyInformationConfigurationDTO.getUpLimitPercent());
            energyInOutLimit.setIsPercentTips(updateEnergyInformationConfigurationDTO.getIsPercentTips());
            energyInOutLimit.setTipsColor(updateEnergyInformationConfigurationDTO.getTipsColor());
            energyInOutLimit.setInOutTypeId(updateEnergyInformationConfigurationDTO.getInOutTypeId());
            energyInOutLimit.setEngyId(updateEnergyInformationConfigurationDTO.getEngyId());
            energyInOutLimit.setCostCenterNo(updateEnergyInformationConfigurationDTO.getCostCenterNo());
            energyInOutLimit.setCostCenterName(updateEnergyInformationConfigurationDTO.getCostCenterName());
            energyInOutLimit.setChargeCostCenterNo(updateEnergyInformationConfigurationDTO.getChargeCostCenterNo());
            energyInOutLimit.setChargeCostCenterName(updateEnergyInformationConfigurationDTO.getChargeCostCenterName());
            energyInOutLimit.setUpdateUser(updateEnergyInformationConfigurationDTO.getUpdateUser());
            energyInOutLimit.setUpdateUserName(updateEnergyInformationConfigurationDTO.getUpdateUserName());
            energyInOutLimit.setUpdateTime(new Date());
            updateById(energyInOutLimit);
            return AjaxResult.success("能源信息配置修改成功");
        }
        else {
            return AjaxResult.error( "能源信息配置不存在");
        }
    }

    @Override
    public Object deleteEnergyInformationConfigurationData(DeleteEnergyInOutLimitDTO deleteEnergyInformationConfigurationDTO) {
        EnergyInOutLimit productionInformationConfiguration = getById(deleteEnergyInformationConfigurationDTO.getId());
        if (null != productionInformationConfiguration) {
            removeById(productionInformationConfiguration);
            return AjaxResult.success("能源信息配置删除成功");
        }
        else {
            return AjaxResult.error( "能源信息配置不存在或已被删除");
        }
    }
}
