package com.jlkj.material.mr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.material.mr.dto.materialscategory.PageMaterialsCategoryDTO;
import com.jlkj.material.mr.domain.MaterialsCategory;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口-物料类别维护
 * @author sudeyou
 */
public interface MaterialsCategoryMapper extends BaseMapper<MaterialsCategory> {

    /**
     * 查询-分页-物料类别维护
     * @param page 分页
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getPageData(Page<?> page, PageMaterialsCategoryDTO condition);

    /**
     * 查询-列表-物料类别-下拉
     * @return
     */
    @MapKey("id")
    List<Map<String, String>> getListBoxData();
}

