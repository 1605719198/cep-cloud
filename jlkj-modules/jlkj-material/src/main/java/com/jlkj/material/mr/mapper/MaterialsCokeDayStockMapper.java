package com.jlkj.material.mr.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlkj.material.mr.dto.materialscokedaystock.ListHomeCokeStockDTO;
import com.jlkj.material.mr.domain.MaterialsCokeDayStock;
import com.jlkj.material.mr.vo.materialscokedaystock.ListHomeCokeStockVO;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;

/**
 * Mapper接口-物料焦场每日库存表
 * @author sudeyou
 * @since 2022-09-02 10:29:24
 */
public interface MaterialsCokeDayStockMapper extends BaseMapper<MaterialsCokeDayStock> {

    /**
     * 首页-焦碳库存
     * @param condition 查询条件dto
     * @return 列表数据
     */
    @MapKey("id")
    List<ListHomeCokeStockVO> getHomeCokeStockListData(ListHomeCokeStockDTO condition);
}

