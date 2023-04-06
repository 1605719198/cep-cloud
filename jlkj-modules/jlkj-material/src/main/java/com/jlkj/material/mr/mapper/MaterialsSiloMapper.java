package com.jlkj.material.mr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.material.mr.dto.materialssilo.PageMaterialsSiloDTO;
import com.jlkj.material.mr.domain.MaterialsSilo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口-料仓维护
 * @author sudeyou
 */
public interface MaterialsSiloMapper extends BaseMapper<MaterialsSilo> {

    /**
     * 查询-分页-料仓维护
     * @param page 分页
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getPageData(Page<?> page,@Param("condition")  PageMaterialsSiloDTO condition);

    /**
     * 查询-列表-料仓-下拉
     * @return
     */
    @MapKey("id")
    List<Map<String, String>> getListBoxData();
}

