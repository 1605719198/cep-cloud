package com.jlkj.material.mr.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.material.mr.dto.materialscoalstock.GetCoalStockDTO;
import com.jlkj.material.mr.dto.materialscoalstock.PageMaterialsCoalStockDTO;
import com.jlkj.material.mr.domain.MaterialsCoalStock;
import com.jlkj.material.mr.vo.materialscoalstock.ListHomeMaterialAnalysisListVO;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @Description 物料煤场库存
 * @create 2022年6月10日 10:12:26
 */
public interface MaterialsCoalStockService extends IService<MaterialsCoalStock> {
    /**
     * 查询
     * @param pageMaterialsCoalStockDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getListPage(PageMaterialsCoalStockDTO pageMaterialsCoalStockDTO);
    /**
     * 分类汇总
     * @param dto 查询条件dto
     * @return
     */
    List<Map<String, String>> getCoalStock(GetCoalStockDTO dto);

    /**
     * 首页-物料分析
     * @return 列表数据
     */
    List<ListHomeMaterialAnalysisListVO> getHomeMaterialAnalysisList();

    /**
     * 煤场库存 - 上煤记录 - 查询
     * @param pageMaterialsCoalStockDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getConveyingCoalList(PageMaterialsCoalStockDTO pageMaterialsCoalStockDTO);

    /**
     * 煤场库存 - 卸煤记录 - 查询
     * @param pageMaterialsCoalStockDTO 查询条件dto
     * @return 分页列表
     */
    IPage<Map<String, String>> getCoalUnloadingList(PageMaterialsCoalStockDTO pageMaterialsCoalStockDTO);
}
