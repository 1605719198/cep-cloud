package com.jlkj.material.mr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.material.mr.dto.materialscokestock.GetCokeStockDTO;
import com.jlkj.material.mr.dto.materialscokestock.PageMaterialsCokeStockDTO;
import com.jlkj.material.mr.domain.MaterialsCokeStock;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @Description 物料焦场库存
 * @create 2022年6月10日 10:12:26
 */
public interface MaterialsCokeStockService extends IService<MaterialsCokeStock> {
    /**
     * 查询
     * @param pageMaterialsCokeStockDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageMaterialsCokeStockDTO pageMaterialsCokeStockDTO);
    /**
     * 分类汇总
     * @return
     */
    List<Map<String, String>> getCokeStock(GetCokeStockDTO dto);

    /**
     * 焦炭入库 查询
     * @param dto 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getCokePutStorageList(PageMaterialsCokeStockDTO dto);

    /**
     * 焦炭出库 查询
     * @param dto 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getCokeOutStorageList(PageMaterialsCokeStockDTO dto);

}
