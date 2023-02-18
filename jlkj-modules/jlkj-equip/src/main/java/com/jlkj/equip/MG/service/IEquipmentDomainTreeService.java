package com.jlkj.equip.MG.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.equip.MG.dto.EquipmentDomainTreeDTO;
import com.jlkj.equip.MG.entity.EquipmentDomainTree;

import java.util.List;

/**
 * <p>
 * 设备域维护 服务类
 * </p>
 *
 * @author xin
 * @since 2022-06-09
 */
public interface IEquipmentDomainTreeService extends IService<EquipmentDomainTree> {

    /**
     * 获取设备域树结构资料
     * @return List<EquipmentDomainTreeDTO>
     */
    List<EquipmentDomainTreeDTO> getEquipmentDomainTreeList();
}
