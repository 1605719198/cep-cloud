package com.jlkj.material.mr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.material.mr.dto.materialssiloarea.DeleteMaterialsSiloAreaDTO;
import com.jlkj.material.mr.dto.materialssiloarea.ListMaterialsSiloAreaDTO;
import com.jlkj.material.mr.dto.materialssiloarea.SaveMaterialsSiloAreaDTO;
import com.jlkj.material.mr.domain.MaterialsSiloArea;

import java.util.List;
import java.util.Map;

/**
 * 服务接口-料仓区域维护
 * @author sudeyou
 */
public interface MaterialsSiloAreaService extends IService<MaterialsSiloArea> {

    /**
     * 查询-列表-料仓区域维护表
     * @param listMaterialsSiloAreaDTO 查询条件dto
     * @return 列表数据
     */
    List<Map<String, String>> getListData(ListMaterialsSiloAreaDTO listMaterialsSiloAreaDTO);

    /**
     * 修改-料仓区域维护
     * @param saveMaterialsSiloAreaDTO 修改dto
     * @return 执行结果
     */
    Object updateData(SaveMaterialsSiloAreaDTO saveMaterialsSiloAreaDTO);

    /**
     * 料仓区域维护-删除
     * @param deleteMaterialsSiloAreaDTO 删除dto
     * @return 执行结果
     */
    Object deleteMaterialsSiloAreaData(DeleteMaterialsSiloAreaDTO deleteMaterialsSiloAreaDTO);
}

