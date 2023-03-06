package com.jlkj.material.mr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.material.mr.dto.materialsstoragespaces.*;
import com.jlkj.material.mr.domain.MaterialsStorageSpaces;

import java.util.List;
import java.util.Map;

/**
 * 服务接口-储位维护表
 * @author sudeyou
 */
public interface MaterialsStorageSpacesService extends IService<MaterialsStorageSpaces> {

    /**
     * 查询-分页-储位维护
     * @param pageMaterialsStorageSpacesDTO 查询条件dto
     * @return 分页数据
     */
    IPage<Map<String, String>> getPageData(PageMaterialsStorageSpacesDTO pageMaterialsStorageSpacesDTO);

    /**
     * 查询-列表-储位
     * @param listMaterialsStorageSpacesDTO 查询条件dto
     * @return 列表数据
     */
    List<Map<String, String>> getListData(ListMaterialsStorageSpacesDTO listMaterialsStorageSpacesDTO);

    /**
     * 新增-储位维护
     * @param addMaterialsStorageSpacesDTO 新增dto
     * @return 执行结果
     */
    Object addData(AddMaterialsStorageSpacesDTO addMaterialsStorageSpacesDTO);

    /**
     * 修改-储位维护
     * @param updateMaterialsStorageSpacesDTO 修改dto
     * @return 执行结果
     */
    Object updateData(UpdateMaterialsStorageSpacesDTO updateMaterialsStorageSpacesDTO);

    /**
     * 删除-储位维护
     * @param deleteMaterialsStorageSpacesDTO 删除dto
     * @return 执行结果
     */
    Object deleteData(DeleteMaterialsStorageSpacesDTO deleteMaterialsStorageSpacesDTO);

    /**
     * 查询-列表-储位-下拉
     * @return 列表数据
     */
    List<Map<String, String>> getListDataBox();
}

