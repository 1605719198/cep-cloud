package com.jlkj.equip.ME.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.equip.ME.domain.EquipmentBasicProperties;
import com.jlkj.equip.ME.dto.EquipmentBasicAccountDTO;
import com.jlkj.equip.ME.dto.EquipmentQueryAccountDTO;

import java.util.List;

/**
 * <p>
 * 设备台账 服务类
 * </p>
 *
 * @author xin
 * @since 2022-06-13
 */
public interface IEquipmentBasicPropertiesService extends IService<EquipmentBasicProperties> {

    /**
     * 分页查询设备台账
     * @param current
     * @param limit
     * @return
     */
    IPage<EquipmentBasicAccountDTO> queryEquipmentAccount(Integer current, Integer limit);

    /**
     * 根据参数查询设备台账
     * @param queryAccountDTO
     * @return
     */
    IPage<EquipmentBasicAccountDTO> getEquipmentAccountByParams(EquipmentQueryAccountDTO queryAccountDTO);

    /**
     * 根据设备位置ID查询设备
     * @param locationId
     * @return
     */
    List<EquipmentBasicProperties> getEquipmentByLocationId(String locationId);
}
