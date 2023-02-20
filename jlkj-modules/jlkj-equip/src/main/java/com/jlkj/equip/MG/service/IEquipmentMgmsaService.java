package com.jlkj.equip.MG.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.equip.MG.domain.EquipmentMgmsa;

import java.math.BigDecimal;

/**
 * <p>
 * 停机责任分摊纪录 服务类
 * </p>
 *
 * @author xin
 * @since 2022-05-23
 */
public interface IEquipmentMgmsaService extends IService<EquipmentMgmsa> {

    /**
     * 获取已分摊时间总和
     * @param mgms1No
     * @param id
     * @return
     */
    BigDecimal getShareTime(String mgms1No,String id);
}
