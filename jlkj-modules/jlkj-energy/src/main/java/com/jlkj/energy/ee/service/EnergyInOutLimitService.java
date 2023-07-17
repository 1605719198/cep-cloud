package com.jlkj.energy.ee.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.energy.ee.domain.EnergyInOutLimit;
import com.jlkj.energy.ee.dto.energyinoutlimit.DeleteEnergyInOutLimitDTO;
import com.jlkj.energy.ee.dto.energyinoutlimit.InsertEnergyInOutLimitDTO;
import com.jlkj.energy.ee.dto.energyinoutlimit.PageEnergyInOutLimitDTO;
import com.jlkj.energy.ee.dto.energyinoutlimit.UpdateEnergyInOutLimitDTO;

/**
* @author Liukuiyan
* 针对表【t_energy_in_out_limit(能源产耗标准配置表)】的数据库操作Service
* @createDate 2023-02-13 16:39:04
*/
public interface EnergyInOutLimitService extends IService<EnergyInOutLimit> {

    /**
     * 能源信息配置-查询-分页
     * @param pageEnergyInformationConfigurationDTO 查询条件dto
     * @return 分页数据
     */
    IPage<EnergyInOutLimit> getEnergyInformationConfigurationPageData(PageEnergyInOutLimitDTO pageEnergyInformationConfigurationDTO);

    /**
     * 能源信息配置-新增
     * @param insertEnergyInformationConfigurationDTO 新增dto
     * @return 执行结果
     */
    Object insertEnergyInformationConfigurationData(InsertEnergyInOutLimitDTO insertEnergyInformationConfigurationDTO);

    /**
     * 能源信息配置-修改
     * @param updateEnergyInformationConfigurationDTO 修改dto
     * @return 执行结果
     */
    Object updateEnergyInformationConfigurationData(UpdateEnergyInOutLimitDTO updateEnergyInformationConfigurationDTO);

    /**
     * 能源信息配置-删除
     * @param deleteEnergyInformationConfigurationDTO 删除dto
     * @return 执行结果
     */
    Object deleteEnergyInformationConfigurationData(DeleteEnergyInOutLimitDTO deleteEnergyInformationConfigurationDTO);
}
