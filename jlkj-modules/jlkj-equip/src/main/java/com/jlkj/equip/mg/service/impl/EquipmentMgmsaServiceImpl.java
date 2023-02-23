package com.jlkj.equip.mg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.equip.mg.domain.EquipmentMgmsa;
import com.jlkj.equip.mg.mapper.EquipmentMgmsaMapper;
import com.jlkj.equip.mg.service.IEquipmentMgmsaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 停机责任分摊纪录 服务实现类
 * </p>
 *
 * @author xin
 * @since 2022-05-23
 */
@Service
public class EquipmentMgmsaServiceImpl extends ServiceImpl<EquipmentMgmsaMapper, EquipmentMgmsa> implements IEquipmentMgmsaService {

    @Autowired
    private EquipmentMgmsaMapper equipmentMgmsaMapper;
    /**
     * 获取分摊时间总和
     * @param mgms1No
     * @param id
     * @return
     */
    @Override
    public BigDecimal getShareTime(String mgms1No,String id) {
        return equipmentMgmsaMapper.getShareTime(mgms1No,id);
    }
}
