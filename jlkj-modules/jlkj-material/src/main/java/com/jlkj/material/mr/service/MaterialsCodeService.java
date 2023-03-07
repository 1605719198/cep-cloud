package com.jlkj.material.mr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.material.mr.dto.materialscode.*;
import com.jlkj.material.mr.domain.MaterialsCode;
import com.jlkj.material.mr.vo.materialscode.ListMaterialsCodeByTypeVO;

import java.util.List;
import java.util.Map;

/**
 * 服务接口-物料代码维护
 * @author sudeyou
 */
public interface MaterialsCodeService extends IService<MaterialsCode> {

    /**
     * 查询-分页-物料代码维护
     * @param pageMaterialsCodeDTO 查询条件dto
     * @return 分页数据
     */
    IPage<Map<String, String>> getPageData(PageMaterialsCodeDTO pageMaterialsCodeDTO);

    /**
     * 查询-列表-物料代码-下拉
     * @return 列表数据
     */
    List<Map<String, String>> getListBoxData();

    /**
     * 查询-列表-物料代码-下拉-通过物料类别id
     * @param listMaterialsCodeDTO 查询条件dto
     * @return 列表数据
     */
    List<Map<String, String>> getListBoxByCataIdData(ListMaterialsCodeDTO listMaterialsCodeDTO);

    /**
     * 查询-列表-物料代码-下拉-煤
     * @return 列表数据
     */
    List<Map<String, Object>> getListBoxDataM();

    /**
     * 查询-列表-物料代码-下拉-焦
     * @return 列表数据
     */
    List<Map<String, Object>> getListBoxDataJ();

    /**
     * 新增-物料代码维护
     * @param addMaterialsCodeDTO 新增dto
     * @return 执行结果
     */
    Object addData(AddMaterialsCodeDTO addMaterialsCodeDTO);

    /**
     * 修改-物料代码维护
     * @param updateMaterialsCodeDTO 操作dto
     * @return 执行结果
     */
    Object updateData(UpdateMaterialsCodeDTO updateMaterialsCodeDTO);

    /**
     * 删除-物料代码维护表
     * @param deleteMaterialsCodeDTO 操作dto
     * @return 执行结果
     */
    Object deleteData(DeleteMaterialsCodeDTO deleteMaterialsCodeDTO);

    /**
     * 物料代码下拉列表-类型(煤/焦)
     * @param listMaterialsCodeByTypeDTO 查询条件dto
     * @return 列表数据
     */
    List<ListMaterialsCodeByTypeVO> getMaterialsCodeListByTypeData(ListMaterialsCodeByTypeDTO listMaterialsCodeByTypeDTO);

}

