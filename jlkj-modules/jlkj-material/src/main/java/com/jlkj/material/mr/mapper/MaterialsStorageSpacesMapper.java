package com.jlkj.material.mr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.material.mr.dto.materialsstoragespaces.ListMaterialsStorageSpacesDTO;
import com.jlkj.material.mr.dto.materialsstoragespaces.PageMaterialsStorageSpacesDTO;
import com.jlkj.material.mr.domain.MaterialsStorageSpaces;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口-储位维护表
 * @author sudeyou
 */
public interface MaterialsStorageSpacesMapper extends BaseMapper<MaterialsStorageSpaces> {

    /**
     * 查询-分页-储位维护
     * @param page 分页
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getPageData(Page<?> page, @Param("condition") PageMaterialsStorageSpacesDTO condition);

    /**
     * 查询-列表-储位
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    List<Map<String, String>> getListData(ListMaterialsStorageSpacesDTO condition);

    /**
     * 查询-列表-储位-下拉
     * @return
     */
    @MapKey("id")
    List<Map<String, String>> getListDataBox();
}
