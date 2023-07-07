package com.jlkj.energy.ee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.dto.energy.ee.EnergyCodeFoMaterialDTO;
import com.jlkj.energy.ee.domain.EnergyCodeFoMaterial;

import java.util.List;
import java.util.Map;

/**
* @author 265675
* @description 针对表【t_energy_code_fo_material(固、液体能源代码、料号对照)】的数据库操作Service
* @createDate 2022-04-28 11:25:31
*/
public interface EnergyCodeFoMaterialService extends IService<EnergyCodeFoMaterial> {
    /**
     * @description 新增固、液体能源代码、料号对照
     * @author: 265800
     * @date: 2023/7/6 8:45
     * @return: int
     * @throws:
     * @param energyCodeFoMaterial
     */
    int addEnergyCodeFoMaterial(EnergyCodeFoMaterial energyCodeFoMaterial);

    /**
     * @description 修改固、液体能源代码、料号对照
     * @author: 265800
     * @date: 2023/7/6 11:34
     * @param: [energyCodeFoMaterial]
     * @return: int
     * @throws:
     */;
    AjaxResult updateEnergyCodeFoMaterial(EnergyCodeFoMaterial energyCodeFoMaterial);

    /**
     * @description 删除固、液体能源代码、料号对照
     * @author: 265800
     * @date: 2023/7/6 13:28
     * @param: [id]
     * @return: com.jlkj.common.core.web.domain.AjaxResult
     * @throws:
     */;
    AjaxResult deleteEnergyCodeFoMaterial(List<String> id);

    /**
     * @description 删除固液体能源代码对应料号维护资料(mq)
     * @author: 265800
     * @date: 2023/7/6 13:38
     * @param: [engyId]
     * @return: com.jlkj.common.core.web.domain.AjaxResult
     * @throws:
     */;
    AjaxResult deleteEnergyCode(String engyId);

    /**
     * @description 固液体能源代码对应料号资料查询与列表
     * @author: 265800
     * @date: 2023/7/6 13:50
     * @param: [energyCodeFoMaterialDTO]
     * @return: com.jlkj.common.core.web.domain.AjaxResult
     * @throws:
     */
    AjaxResult queryEnergyCodeFoMaterial(EnergyCodeFoMaterialDTO energyCodeFoMaterialDTO);

    /**
     * @description 查询固液体能源代码对应料号下拉选单
     * @author: 265800
     * @date: 2023/7/6 14:01
     * @param: []
     * @return: java.util.List<java.util.Map<java.lang.String,java.lang.String>>
     * @throws:
     */
    List<Map<String, String>> queryDropDownMenuZh();
}
