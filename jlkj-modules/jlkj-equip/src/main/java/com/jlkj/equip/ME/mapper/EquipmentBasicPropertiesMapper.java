package com.jlkj.equip.ME.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.equip.ME.domain.EquipmentBasicProperties;
import com.jlkj.equip.ME.dto.EquipmentBasicAccountDTO;
import com.jlkj.equip.ME.dto.EquipmentQueryAccountDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 设备台账 Mapper 接口
 * </p>
 *
 * @author xin
 * @since 2022-06-13
 */
public interface EquipmentBasicPropertiesMapper extends BaseMapper<EquipmentBasicProperties> {

    /**
     * 分页查询设备台账
     * @param page
     * @return
     */
    IPage<EquipmentBasicAccountDTO> queryEquipmentAccount(Page<EquipmentBasicAccountDTO> page);

    /**
     * 根据参数查询设备台账
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<EquipmentBasicAccountDTO> getEquipmentAccountByParams(Page<?> page,@Param("condition") EquipmentQueryAccountDTO condition);

}
