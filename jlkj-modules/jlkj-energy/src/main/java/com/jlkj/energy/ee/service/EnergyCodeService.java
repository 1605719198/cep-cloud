package com.jlkj.energy.ee.service;

import com.alibaba.fastjson.JSONArray;
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
     * @param: energyCode 能源代码
     * @return: java.util.List<com.jlkj.energy.ee.domain.EnergyCode>
     */
    List<EnergyCode> queryEnergyCode(EnergyCodeDTO energyCodeDTO);

    /**
     * @description 删除能源代码资料(mq)
     * @author: 111191
     * @date: 2023年7月3日, 0003 下午 12:25:50
     * @param: engyId 能源代码ID
     * @return: int 删除笔数（物理删除）
     */
    int removeEnergyCode(String engyId);

    /**
     * @description 根据能源ID单笔删除
     * @author: 111191
     * @date: 2023年7月3日, 0003 下午 12:32:31
     * @param: id 能源代码ID
     * @return: int 删除笔数
     */
    EnergyCode queryEnergyCodeById(String id);

    /**
     * @description 查询能源代码下拉选单（el-Select)
     * @author: 111191
     * @date: 2023年7月3日, 0003 下午 12:37:03
     * @param: 无
     * @return: JSONArray 选单所需的绑定数据
     */
    JSONArray queryDropDownMenu();

    /**
     * @description 查询能源代码下拉选单(中文label)
     * @author: 111191
     * @date: 2023年7月3日, 0003 下午 12:45:51
     * @param:
     * @return: com.alibaba.fastjson.JSONArray
     * @throws:
     */
    JSONArray queryDropDownMenuZh();

    /**
     * @description 固液体能源代码资料查询与列表
     * @author: 111191
     * @date: 2023年7月4日, 0004 下午 01:57:09
     * @param: energyCodeDTO 固液体能源代码资料
     * @return: java.util.List<com.jlkj.energy.ee.domain.EnergySolidLiquidData>
     * @throws:
     */
    List<EnergyCode> querySolidLiquidEnergyCode(EnergyCodeDTO energyCodeDTO);

    /**
     * @description 新增固液体能源代码资料
     * @author: 111191
     * @date: 2023年7月4日, 0004 下午 03:25:23
     * @param: energyCode 固液体能源代码资料
     * @return: int 新增成功笔数
     * @throws:
     */
    int addSolidLiquidEnergyCode(EnergyCode energyCode);
    /**
     * @description 修改固液体能源代码资料
     * @author: 111191
     * @date: 2023年7月4日, 0004 下午 03:41:24
     * @param: energyCode 固液体能源代码资料
     * @return: int 修改成功笔数
     * @throws:
     */
    int updateSolidLiquidEnergyCode(EnergyCode energyCode);
    /**
     * @description 删除固液体能源代码资料
     * @author: 111191
     * @date: 2023年7月4日, 0004 下午 03:42:15
     * @param: ids 固液体能源代码ID
     * @return: int 删除成功笔数
     * @throws:
     */
    int deleteSolidLiquidEnergyCode(String[] ids);
    /**
     * @description 查询能源代码下拉选单（el-Select)
     * @author: 111191
     * @date: 2023年7月4日, 0004 下午 04:34:27
     * @param:
     * @return: com.alibaba.fastjson.JSONArray
     * @throws:
     */
    JSONArray querySolidLiquidDropDownMenu();
}
