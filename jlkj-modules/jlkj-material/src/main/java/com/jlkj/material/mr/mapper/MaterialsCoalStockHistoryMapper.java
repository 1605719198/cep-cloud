package com.jlkj.material.mr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.material.mr.domain.MaterialsCoalStockHistory;
import com.jlkj.material.mr.vo.water.CoalWaterRateVO;
import org.apache.ibatis.annotations.MapKey;

import java.util.Map;

/**
 * @author yzl
 * @Description 物料煤场库存调整记录
 * @create 2022年6月10日 10:12:26
 */
public interface MaterialsCoalStockHistoryMapper extends BaseMapper<MaterialsCoalStockHistory> {

    /**
     * 获取水份
     * @return 单行数据
     */
    @MapKey("id")
    CoalWaterRateVO getCoalWaterRateData();

}


