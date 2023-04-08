package com.jlkj.material.mr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.material.mr.dto.materialssiloareahistory.PageMaterialsSiloAreaHistoryDTO;
import com.jlkj.material.mr.domain.MaterialsSiloAreaHistory;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * Mapper接口-料仓区域历史表
 * @author sudeyou
 */
public interface MaterialsSiloAreaHistoryMapper extends BaseMapper<MaterialsSiloAreaHistory> {

    /**
     * 查询-分页-料仓区域历史
     * @param page 分页
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getPageData(Page<?> page,@Param("condition")  PageMaterialsSiloAreaHistoryDTO condition);
}

