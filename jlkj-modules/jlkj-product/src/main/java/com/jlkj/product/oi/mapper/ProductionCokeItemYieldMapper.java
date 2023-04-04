package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlkj.product.oi.domain.ProductionCokeItemYieldEntity;
import com.jlkj.product.oi.domain.ProductionMaterialsCokeStock;
import com.jlkj.product.oi.dto.productioncokeitemyield.CokeItemYieldQueryDTO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author zzh
 * 焦炭分项产量mapper 接口类
 * @time 2022年9月9日13:35:10
 */
public interface ProductionCokeItemYieldMapper extends BaseMapper<ProductionCokeItemYieldEntity> {

    List<Object> getProductionCokeMaterialsNameByTime(@Param("datetime") String datetime);

    ProductionMaterialsCokeStock getMaterialsCokeStockOneByMaterialName(@Param("materialsName") String materialsName);

    boolean updateMaterialsCokeStockById(ProductionMaterialsCokeStock stock);

    List<ProductionMaterialsCokeStock> getMaterialsCokeStockMultipleByMaterialName(@Param("nameList") List<Object> nameList);


    @MapKey("id")
    IPage<Map<String, Object>> getCokeItemYieldList2(Page<?> page,@Param("condition") CokeItemYieldQueryDTO condition);

}
