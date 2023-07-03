package com.jlkj.energy.ee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.dto.energy.ee.EnergyCodeDTO;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.energy.ee.domain.EnergyCode;
import com.jlkj.energy.ee.mapper.EnergyCodeMapper;
import com.jlkj.energy.ee.service.EnergyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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
        QueryWrapper queryWrapper = new QueryWrapper();
        String energyId = queryWrapper.eq("engyId", energyCode.getEngyId()).toString();
        if(StringUtils.isNotBlank(energyId)){
            new ServiceException("能源代码为：" + energyId + "的资料已存在，请勿重复添加");
        }
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
    /**
     * @description 批量多笔删除 能源代码（逻辑删除）
     * @author: 111191
     * @date: 2023年7月3日, 0003 上午 09:33:08
     * @param: ids 用户选择要删除的数据Id
     * @return: int 操作成功的笔数
     * @throws:
    */
    @Override
    public int deleteEnergyCode(String[] ids){
        int result = 0;
        if(StringUtils.checkValNotNull(ids)){
            List list = Arrays.asList(ids);
            result = energyCodeMapper.deleteBatchIds(list);
        }else {
            new ServiceException("请选择要删除的数据项后，再进行操作");
        }
        return result;
    }
    /**
     * @description TODO
     * @author: 111191
     * @date: 2023年7月3日, 0003 上午 09:48:49
     * @param: null
     * @return:
     * @throws:
    */
    @Override
    public List<EnergyCode> queryEnergyCode(EnergyCodeDTO energyCodeDTO){
        String[] solidLiquid = {"G000", "Y000"};
        LambdaQueryWrapper<EnergyCode> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.notIn(EnergyCode::getEngyType, solidLiquid)
                .orderByAsc(EnergyCode::getEngyId);
        if (StringUtils.isNotBlank(energyCodeDTO.getEngyIdStart()) && StringUtils.isNotBlank(energyCodeDTO.getEngyIdEnd())) {
            queryWrapper.between(EnergyCode::getEngyId, energyCodeDTO.getEngyIdStart(), energyCodeDTO.getEngyIdEnd());
        } else if (StringUtils.isNotBlank(energyCodeDTO.getEngyIdStart())) {
            queryWrapper.eq(EnergyCode::getEngyId, energyCodeDTO.getEngyIdStart());
        } else if (StringUtils.isNotBlank(energyCodeDTO.getEngyIdEnd())) {
            queryWrapper.eq(EnergyCode::getEngyId, energyCodeDTO.getEngyIdEnd());
        }

        return energyCodeMapper.selectList(queryWrapper);
    }
}




