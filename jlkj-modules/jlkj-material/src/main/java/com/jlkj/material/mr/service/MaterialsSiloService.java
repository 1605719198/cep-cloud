package com.jlkj.material.mr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.material.mr.dto.materialssilo.AddMaterialsSiloDTO;
import com.jlkj.material.mr.dto.materialssilo.DeleteMaterialsSiloDTO;
import com.jlkj.material.mr.dto.materialssilo.PageMaterialsSiloDTO;
import com.jlkj.material.mr.dto.materialssilo.UpdateMaterialsSiloDTO;
import com.jlkj.material.mr.domain.MaterialsSilo;

import java.util.List;
import java.util.Map;

/**
 * 服务接口-料仓维护
 * @author sudeyou
 */
public interface MaterialsSiloService extends IService<MaterialsSilo> {

    /**
     * 查询-分页-料仓维护
     * @param pageMaterialsSiloDTO 查询条件dto
     * @return 分页数据
     */
    IPage<Map<String, String>> getPageData(PageMaterialsSiloDTO pageMaterialsSiloDTO);

    /**
     * 查询-列表-料仓-下拉
     * @return 列表数据
     */
    List<Map<String, String>> getListBoxData();

    /**
     * 新增-料仓维护
     * @param addMaterialsSiloDTO 新增dto
     * @return 执行结果
     */
    Object addData(AddMaterialsSiloDTO addMaterialsSiloDTO);

    /**
     * 修改-料仓维护
     * @param updateMaterialsSiloDTO 修改dto
     * @return 执行结果
     */
    Object updateData(UpdateMaterialsSiloDTO updateMaterialsSiloDTO);

    /**
     * 删除-料仓维护
     * @param deleteMaterialsSiloDTO 删除dto
     * @return 执行结果
     */
    Object deleteData(DeleteMaterialsSiloDTO deleteMaterialsSiloDTO);
}

