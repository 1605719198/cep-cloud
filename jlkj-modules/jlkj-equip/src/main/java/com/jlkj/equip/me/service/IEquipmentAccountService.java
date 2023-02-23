package com.jlkj.equip.me.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.equip.me.domain.EquipmentAccount;
import com.jlkj.equip.me.domain.EquipmentBasicProperties;
import com.jlkj.equip.mg.dto.EquipmentDomainTreeDTO;

import java.util.List;

/**
 * <p>
 * 设备台账 服务类
 * </p>
 *
 * @author xin
 * @since 2022-06-13
 */
public interface IEquipmentAccountService extends IService<EquipmentAccount> {

    /**
     * 获取设备台账树结构
     * @return
     */
    List<EquipmentBasicProperties> getEquipmentAccountTree();

    /**
     * 获取设备树、设备位置树
     * @return
     */
    List<EquipmentDomainTreeDTO> getEquipmentCombineTree();
}
