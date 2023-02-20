package com.jlkj.equip.MG.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.equip.MG.dto.EquipmentDownTypeTreeDTO;
import com.jlkj.equip.MG.domain.EquipmentDownTypeTree;

import java.util.List;

/**
 * <p>
 * 设备停机类型维护  服务类
 * </p>
 *
 * @author xin
 * @since 2022-05-09
 */
public interface IEquipmentDownTypeTreeService extends IService<EquipmentDownTypeTree> {

    /**
     * 获取停机类型树结构
     * @return List<EquipmentDownTypeTreeDTO>
     */
    List<EquipmentDownTypeTreeDTO> getEquipmentTypeTreeList();
}
