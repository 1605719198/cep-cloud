package com.jlkj.product.oi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.product.oi.domain.ProductionCokeRecords;
import com.jlkj.product.oi.dto.productioncokerecords.ListHomeCokeProportionDTO;
import com.jlkj.product.oi.vo.productioncokerecords.ListHomeCokeProportionVO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;

/**
* @author zyf
* @description 针对表【product_oi_coke_records(焦炭产量记录)】的数据库操作Mapper
* @createDate 2022-08-11 13:52:37
* @Entity com.jlkj.product.oi.entity.ProductionCokeRecords
*/
public interface ProductionCokeRecordsMapper extends BaseMapper<ProductionCokeRecords> {

    /**
     * 首页-焦碳比例
     * @param condition 查询条件dto
     * @return 列表数据
     */
    @MapKey("id")
    List<ListHomeCokeProportionVO> getHomeCokeProportionListData(ListHomeCokeProportionDTO condition);

}




