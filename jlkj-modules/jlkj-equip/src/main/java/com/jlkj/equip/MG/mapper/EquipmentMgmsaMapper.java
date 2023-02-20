package com.jlkj.equip.MG.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.equip.MG.domain.EquipmentMgmsa;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 * 停机责任分摊纪录 Mapper 接口
 * </p>
 *
 * @author xin
 * @since 2022-05-23
 */
public interface EquipmentMgmsaMapper extends BaseMapper<EquipmentMgmsa> {

    /**
     * 获取已分摊总时间
     * @param mgms1No
     * @param id
     * @return
     */
    BigDecimal getShareTime(@Param("mgms1No") String mgms1No, @Param("id") String id);
}
