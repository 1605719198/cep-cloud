package com.jlkj.material.mr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.material.mr.dto.materialscoaldaystock.ListHomeCoalStockDTO;
import com.jlkj.material.mr.domain.MaterialsCoalDayStock;
import com.jlkj.material.mr.vo.materialscoaldaystock.ListHomeCoalStockVO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;

/**
 * Mapper接口-物料煤场每日库存表
 * @author sudeyou
 * @since 2022-09-02 10:55:24
 */
public interface MaterialsCoalDayStockMapper extends BaseMapper<MaterialsCoalDayStock> {

    /**
     * 首页-原煤库存
     * @param condition 查询条件dto
     * @return 列表数据
     */
    @MapKey("id")
    List<ListHomeCoalStockVO> getHomeCoalStockListData(ListHomeCoalStockDTO condition);
}

