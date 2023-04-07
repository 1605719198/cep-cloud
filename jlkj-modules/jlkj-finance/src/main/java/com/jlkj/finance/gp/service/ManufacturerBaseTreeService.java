package com.jlkj.finance.gp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.finance.gp.domain.ManufacturerTree;
import com.jlkj.finance.gp.dto.ManufacturerBaseDTO;
import com.jlkj.finance.gp.domain.ManufacturerBase;
/**
 * @author wang'mai
 * @description 针对表【t_manufacturer_base(厂商基本资料)】的数据库操作Service

 */
public interface ManufacturerBaseTreeService extends IService<ManufacturerBase> {
    /**Mq新增厂商基本资料同步树表资料
     * @param manufacturerBaseDTO 厂商基本资料DTO
     * @return ManufacturerBaseDTO createTreeData(ManufacturerBaseDTO manufacturerBaseDTO);
     */
    ManufacturerBaseDTO createTreeData(ManufacturerBaseDTO manufacturerBaseDTO);

}

