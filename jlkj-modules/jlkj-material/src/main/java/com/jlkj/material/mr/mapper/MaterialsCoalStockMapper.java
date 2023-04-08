package com.jlkj.material.mr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.material.mr.dto.materialscoalstock.GetCoalStockDTO;
import com.jlkj.material.mr.dto.materialscoalstock.PageMaterialsCoalStockDTO;
import com.jlkj.material.mr.domain.MaterialsCoalStock;
import com.jlkj.material.mr.vo.materialscoalstock.ListHomeMaterialAnalysisListVO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @Description 物料煤场库存
 * @create 2022年6月10日 10:12:26
 */
public interface MaterialsCoalStockMapper extends BaseMapper<MaterialsCoalStock> {

    /**
     * 查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getListPage(Page<Map<String, String>> page, @Param("condition") PageMaterialsCoalStockDTO condition);
    /**
     * 分类汇总
     * @param condition
     * @return
     */
    @MapKey("id")
    List<Map<String, String>> getCoalStock(GetCoalStockDTO condition);

    /**
     * 首页-物料分析
     * @return
     */
    @MapKey("id")
    List<ListHomeMaterialAnalysisListVO> getHomeMaterialAnalysisList();

    /**
     * 煤场库存 - 上煤记录 - 查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getConveyingCoalList(Page<Map<String, String>> page, @Param("condition") PageMaterialsCoalStockDTO condition);
    /**
     * 煤场库存 - 卸煤记录 - 查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getCoalUnloadingList(Page<Map<String, String>> page, @Param("condition") PageMaterialsCoalStockDTO condition);


}


