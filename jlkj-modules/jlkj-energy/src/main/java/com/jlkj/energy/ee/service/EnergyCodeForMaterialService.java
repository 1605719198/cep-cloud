package com.jlkj.energy.ee.service;

import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.common.dto.energy.ee.EnergyCodeForMaterialDTO;
import com.jlkj.energy.ee.domain.EnergyCodeForMaterial;

import java.util.List;

/**
* @author 265675
* @Description: 针对表【t_energy_code_fo_material(固、液体能源代码、料号对照)】的数据库操作Service
* @Date:  2022-04-28 11:25:31
*/

public interface EnergyCodeForMaterialService extends IService<EnergyCodeForMaterial> {
    /**
     * (固、液体能源代码、料号对照)查询方法
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:17:33
     * @param energyCodeForMaterialDTO 查询条件
     * @return java.util.List<com.jlkj.energy.ee.domain.EnergyCodeForMaterial> 分页结果
     */

    List<EnergyCodeForMaterial> queryEnergyCodeForMaterial(EnergyCodeForMaterialDTO energyCodeForMaterialDTO);
    /**
     * 根据Id查询能源代码
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:34:21
     * @param id 查询条件 - 能源代码id
     * @return java.util.List<com.jlkj.energy.ee.domain.EnergyCodeForMaterial> 单笔数据结果
     */
    EnergyCodeForMaterial queryEnergyCodeForMaterialById(String id);
    /**
     * 新增固液体能源代码对应料号维护资料
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:47:24
     * @param energyCodeForMaterial 固液体能源代码对应料号维护资料
     * @return int 返回操作成功/失败信息及数据结果
     */
    int addEnergyCodeForMaterial(EnergyCodeForMaterial energyCodeForMaterial);
    /**
     * 修改 固液体能源代码对应料号维护资料
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:48:28
     * @param energyCodeForMaterial 固液体能源代码对应料号维护资料
     * @return int 返回操作成功/失败信息及数据结果
     */
    int updateEnergyCodeForMaterial(EnergyCodeForMaterial energyCodeForMaterial);
    /**
     * 根据engyId 删除数据
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:41:03
     * @param engyId  能源代码id
     * @return int 操作笔数（删除数）
     */
    int deleteEnergyCode(String engyId);
    /**
     * 删除固液体能源代码对应料号维护资料
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:50:35
     * @param ids 能源代码ids 多笔
     * @return int 操作笔数（删除数）
     */
    int deleteEnergyCodeForMaterial(String[] ids);
    /**
     * 查询能源代码下拉选单(中文label)
     * @Author: 111191
     * @Date: 2023年7月7日, 0007 上午 09:23:00
     * @param
     * @return JSONArray 选单数据
     */
    JSONArray queryDropDownMenuZh();
}
