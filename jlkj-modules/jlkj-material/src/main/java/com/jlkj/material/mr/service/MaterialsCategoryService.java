package com.jlkj.material.mr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.material.mr.dto.materialscategory.AddMaterialsCategoryDTO;
import com.jlkj.material.mr.dto.materialscategory.DeleteMaterialsCategoryDTO;
import com.jlkj.material.mr.dto.materialscategory.PageMaterialsCategoryDTO;
import com.jlkj.material.mr.dto.materialscategory.UpdateMaterialsCategoryDTO;
import com.jlkj.material.mr.domain.MaterialsCategory;

import java.util.List;
import java.util.Map;

/**
 * 服务接口-物料类别维护
 * @author sudeyou
 */
public interface MaterialsCategoryService extends IService<MaterialsCategory> {

    /**
     * 查询-分页-物料类别维护
     * @param pageMaterialsCategoryDTO 查询条件dto
     * @return 分页数据
     */
    IPage<Map<String, String>> getPageData(PageMaterialsCategoryDTO pageMaterialsCategoryDTO);

    /**
     * 查询-列表-物料类别-下拉
     * @return 列表数据
     */
    List<Map<String, String>> getListBoxData();

    /**
     * 新增-物料类别维护
     * @param addMaterialsCategoryDTO 新增dto
     * @return 执行结果
     */
    Object addData(AddMaterialsCategoryDTO addMaterialsCategoryDTO);

    /**
     * 修改-物料类别维护
     * @param updateMaterialsCategoryDTO 操作dto
     * @return 执行结果
     */
    Object updateData(UpdateMaterialsCategoryDTO updateMaterialsCategoryDTO);

    /**
     * 删除-物料类别维护
     * @param deleteMaterialsCategoryDTO 操作dto
     * @return 执行结果
     */
    Object deleteData(DeleteMaterialsCategoryDTO deleteMaterialsCategoryDTO);
}

