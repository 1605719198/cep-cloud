package com.jlkj.energy.ee.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.energy.ee.domain.EnergyCode;
import com.jlkj.energy.ee.mapper.EnergyCodeMapper;
import com.jlkj.energy.ee.service.EnergyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 265675
 * @description 针对表【t_energy_code(能源项目基本资料档)】的数据库操作Service实现
 * @createDate 2022-04-25 13:13:14
 */
@Service
public class EnergyCodeServiceImpl extends ServiceImpl<EnergyCodeMapper, EnergyCode> implements EnergyCodeService {
    @Autowired
    private EnergyCodeMapper energyCodeMapper;

    /**
     * @description 新增能源代码
     * @author: 111191
     * @date: 2023年6月30日, 0030 上午 11:28:02
     * @param: energyCode
     * @return: int
     * @throws:
     */
    @Override
    public int addEnergyCode(EnergyCode energyCode) {
        energyCode.setCompId(SecurityUtils.getCompId());
        int result = energyCodeMapper.insert(energyCode);
        return result;
    }

    /**
     * @description 修改能源代码
     * @author: 111191
     * @date: 2023年6月30日, 0030 下午 02:25:02
     * @param: energyCode
     * @return: int
     * @throws:
     */
    @Override
    public int updateEnergyCode(EnergyCode energyCode){
        if(StringUtils.isBlank(energyCode.getCompId())){
            energyCode.setCompId(SecurityUtils.getCompId());
        }

        LambdaUpdateWrapper<EnergyCode> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotBlank(energyCode.getEngyId()), EnergyCode::getEngyId, energyCode.getEngyId());

        return energyCodeMapper.update(energyCode,updateWrapper);
    }
}




