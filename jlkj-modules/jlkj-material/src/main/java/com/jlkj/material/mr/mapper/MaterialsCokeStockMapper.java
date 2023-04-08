package com.jlkj.material.mr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.material.mr.dto.materialscokestock.GetCokeStockDTO;
import com.jlkj.material.mr.dto.materialscokestock.PageMaterialsCokeStockDTO;
import com.jlkj.material.mr.domain.MaterialsCokeStock;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @Description 物料焦场库存
 * @create 2022年6月10日 10:12:26
 */
public interface MaterialsCokeStockMapper extends BaseMapper<MaterialsCokeStock> {

    /**
     * 查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getListPage(Page<Map<String, String>> page, @Param("condition") PageMaterialsCokeStockDTO condition);
    /**
     * 分类汇总
     * @param condition
     * @return
     */
    @MapKey("id")
    List<Map<String, String>> getCokeStock(GetCokeStockDTO condition);



    /**
     * 焦炭入库 查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getCokePutStorageList(Page<Map<String, String>> page, @Param("condition") PageMaterialsCokeStockDTO condition);
    /**
     * 焦炭出库 查询
     * @param page
     * @param condition
     * @return
     */
    @MapKey("id")
    IPage<Map<String, String>> getCokeOutStorageList(Page<Map<String, String>> page, @Param("condition") PageMaterialsCokeStockDTO condition);
}


