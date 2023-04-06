package com.jlkj.material.mr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.material.mr.dto.materialscode.ListMaterialsCodeByTypeDTO;
import com.jlkj.material.mr.dto.materialscode.ListMaterialsCodeDTO;
import com.jlkj.material.mr.dto.materialscode.PageMaterialsCodeDTO;
import com.jlkj.material.mr.domain.MaterialsCode;
import com.jlkj.material.mr.vo.materialscode.ListMaterialsCodeByTypeVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口-物料代码维护
 * @author sudeyou
 */
public interface MaterialsCodeMapper extends BaseMapper<MaterialsCode> {

    /**
     * 查询-分页-物料代码维护
     * @param page 分页
     * @param condition 查询条件dto
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getPageData(Page<?> page, @Param("condition") PageMaterialsCodeDTO condition);

    /**
     * 查询-列表-物料代码-下拉
     * @return
     */
    @MapKey("id")
    List<Map<String, String>> getListBoxData();

    /**
     * 查询-列表-物料代码-下拉-通过物料类别id
     * @param listMaterialsCodeDTO 查询dto
     * @return
     */
    @MapKey("id")
    List<Map<String, String>> getListBoxByCataIdData(ListMaterialsCodeDTO listMaterialsCodeDTO);

    /**
     * 物料代码下拉列表-类型(煤/焦)
     * @param condition 查询条件dto
     * @return 列表数据
     */
    @MapKey("id")
    List<ListMaterialsCodeByTypeVO> getMaterialsCodeListByTypeData(ListMaterialsCodeByTypeDTO condition);
}

