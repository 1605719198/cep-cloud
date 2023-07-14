package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionCokeRecords;
import com.jlkj.product.oi.dto.productioncoefficientrecord.GetProductionCoefficientRecordDTO;
import com.jlkj.product.oi.dto.productioncokerecords.ListHomeCokeProportionDTO;
import com.jlkj.product.oi.vo.productioncokerecords.ListHomeCokeProportionVO;

import java.util.List;
import java.util.Map;

/**
* @author zyf
* @description 针对表【product_oi_coke_records(焦炭产量记录)】的数据库操作Service
* @createDate 2022-08-11 13:52:37
*/
public interface ProductionCokeRecordsService extends IService<ProductionCokeRecords> {

    /**
     * 首页-焦碳比例
     * @param listHomeCokeProportionDTO 查询条件dto
     * @return 列表数据
     */
    List<ListHomeCokeProportionVO> getHomeCokeProportionListData(ListHomeCokeProportionDTO listHomeCokeProportionDTO);

    /**
     * 焦炭产量记录查询
     * @param dto
     * @return
     */
    IPage<Map<String, Object>> get(GetProductionCoefficientRecordDTO dto);
}
