package com.jlkj.material.mr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.material.mr.dto.materialssiloareahistory.AddMaterialsSiloAreaHistoryDTO;
import com.jlkj.material.mr.dto.materialssiloareahistory.PageMaterialsSiloAreaHistoryDTO;
import com.jlkj.material.mr.domain.MaterialsSiloAreaHistory;

import java.util.Map;

/**
 * 服务接口-料仓区域历史表
 * @author sudeyou
 */
public interface MaterialsSiloAreaHistoryService extends IService<MaterialsSiloAreaHistory> {

    /**
     * 查询-分页-料仓区域历史
     * @param pageMaterialsSiloAreaHistoryDTO 查询条件dto
     * @return 分页数据
     */
    IPage<Map<String, String>> getPageData(PageMaterialsSiloAreaHistoryDTO pageMaterialsSiloAreaHistoryDTO);

    /**
     * 新增-料仓区域历史表
     * @param addMaterialsSiloAreaHistoryDTO 新增dto
     * @return 执行结果
     */
    Boolean addData(AddMaterialsSiloAreaHistoryDTO addMaterialsSiloAreaHistoryDTO);
}

