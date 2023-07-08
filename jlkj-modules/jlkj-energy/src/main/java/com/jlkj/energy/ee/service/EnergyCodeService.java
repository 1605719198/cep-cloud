package com.jlkj.energy.ee.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.common.dto.energy.ee.EnergyCodeDTO;
import com.jlkj.energy.ee.domain.EnergyCode;

import java.util.List;

/**
 * @author 265675
 * 针对表【t_energy_code(能源项目基本资料档)】的数据库操作Service
 * @Date:  2022-04-25 13:13:14
 */
public interface EnergyCodeService extends IService<EnergyCode> {
    /**
     * 新增能源项目基本资料档
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 11:33:27
     * @param energyCode 能源项目资料
     * @return int 操作数据笔数 大于0：成功；小于等于0：失败
     */
    int addEnergyCode(EnergyCode energyCode);

    /**
     * 修改能源项目基本资料档
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 11:34:01
     * @param energyCode 能源项目基本资料
     * @return int 操作数据笔数 大于0：成功；小于等于0：失败
     */
    int updateEnergyCode(EnergyCode energyCode);

    /**
     * 多笔删除（逻辑删除）
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 11:34:25
     * @param ids 资料id 多笔
     * @return int  操作数据笔数 大于0：成功；小于等于0：失败
     */
    int deleteEnergyCode(String[] ids);

    /**
     * 分页查询
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 11:34:48
     * @param energyCodeDTO 能源代码
     * @return java.util.List<com.jlkj.energy.ee.domain.EnergyCode> 
     */
    List<EnergyCode> queryEnergyCode(EnergyCodeDTO energyCodeDTO);

    /**
     * 删除能源代码资料(mq)
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 11:38:58
     * @param engyId 能源代码ID
     * @return int 删除笔数（物理删除）
     */
    int removeEnergyCode(String engyId);

    /**
     * 根据能源ID单笔查询
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 11:39:21
     * @param id 能源代码ID
     * @return com.jlkj.energy.ee.domain.EnergyCode  查询结果
     */
    EnergyCode queryEnergyCodeById(String id);

    /**
     * 查询能源代码下拉选单（el-Select)
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 11:35:44
     * @return com.alibaba.fastjson.JSONArray
     */
    JSONArray queryDropDownMenu();

    /**
     * 查询能源代码下拉选单(中文label)
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 11:37:24
     * @return com.alibaba.fastjson.JSONArray
     */
    JSONArray queryDropDownMenuZh();

    /**
     * 固液体能源代码资料查询与列表
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 11:36:02
     * @param energyCodeDTO 固液体能源代码资料
     * @return java.util.List<com.jlkj.energy.ee.domain.EnergyCode>
     */
    List<EnergyCode> querySolidLiquidEnergyCode(EnergyCodeDTO energyCodeDTO);

    /**
     * 新增固液体能源代码资料
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 11:36:09
     * @param energyCode 固液体能源代码资料
     * @return int 操作数据笔数 大于0：成功；小于等于0：失败
     */
    int addSolidLiquidEnergyCode(EnergyCode energyCode);
    /**
     * 修改固液体能源代码资料
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 11:36:19
     * @param energyCode 固液体能源代码资料
     * @return int 操作数据笔数 大于0：成功；小于等于0：失败
     */
    int updateSolidLiquidEnergyCode(EnergyCode energyCode);
    /**
     * delete固液体能源代码资料
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 11:36:28
     * @param ids 固液体能源代码ID
     * @return int 删除成功笔数
     */
    int deleteSolidLiquidEnergyCode(String[] ids);
    /**
     * 查询能源代码下拉选单（el-Select)
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 11:37:01
     * @return com.alibaba.fastjson.JSONArray
     */
    JSONArray querySolidLiquidDropDownMenu();
}
