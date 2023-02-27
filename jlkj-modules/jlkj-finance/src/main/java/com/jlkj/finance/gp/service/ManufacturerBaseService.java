package com.jlkj.finance.gp.service;
import com.jlkj.common.dto.finance.aa.ManufacturerBaseDTO;
import com.jlkj.finance.gp.domain.ManufacturerBase;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * @author wang'mai
 * @description 针对表【t_manufacturer_base(厂商基本资料)】的数据库操作Service

 */
public interface ManufacturerBaseService extends IService<ManufacturerBase> {
    /**Mq新增厂商基本资料同步树表资料
     * @param manufacturerBaseDTO 厂商基本资料DTO
     * @return ManufacturerBaseDTO createTreeData(ManufacturerBaseDTO manufacturerBaseDTO);
     */
    ManufacturerBaseDTO createTreeData(ManufacturerBaseDTO manufacturerBaseDTO);


}
