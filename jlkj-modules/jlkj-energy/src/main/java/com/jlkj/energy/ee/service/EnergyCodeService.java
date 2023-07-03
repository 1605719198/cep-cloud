package com.jlkj.energy.ee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.common.dto.energy.ee.EnergyCodeDTO;
import com.jlkj.energy.ee.domain.EnergyCode;

import java.util.List;

/**
 * @author 265675
 * @description 针对表【t_energy_code(能源项目基本资料档)】的数据库操作Service
 * @createDate 2022-04-25 13:13:14
 */
public interface EnergyCodeService extends IService<EnergyCode> {
    /**
     * @description 新增能源项目基本资料档
     * @author: 111191
     * @date: 2023年6月30日, 0030 上午 11:25:59
     * @param: energyCode
     * @return: int 操作数据笔数 大于0：成功；小于等于0：失败
     * @throws:
     */
    int addEnergyCode(EnergyCode energyCode);

    /**
     * @description 修改能源项目基本资料档
     * @author: 111191
     * @date: 2023年6月30日, 0030 下午 02:23:50
     * @param: energyCodeDTO
     * @return: int
     * @throws:
     */
    int updateEnergyCode(EnergyCode energyCode);

    /**
     * @description 多笔删除（逻辑删除）
     * @author: 111191
     * @date: 2023年7月3日, 0003 上午 09:27:45
     * @param: ids
     * @return: int
     * @throws:
     */
    int deleteEnergyCode(String[] ids);

    /**
     * @description 分页查询
     * @author: 111191
     * @date: 2023年7月3日, 0003 上午 09:48:18
     * @param: energyCode
     * @return: java.util.List<com.jlkj.energy.ee.domain.EnergyCode>
     * @throws:
     */
    List<EnergyCode> queryEnergyCode(EnergyCodeDTO energyCodeDTO);
}
