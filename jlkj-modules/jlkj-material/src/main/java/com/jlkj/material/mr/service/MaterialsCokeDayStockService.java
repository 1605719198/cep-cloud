package com.jlkj.material.mr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.material.mr.dto.materialscokedaystock.ListHomeCokeStockDTO;
import com.jlkj.material.mr.domain.MaterialsCokeDayStock;
import com.jlkj.material.mr.vo.materialscokedaystock.ListHomeCokeStockVO;

import java.util.List;

/**
 * 服务接口-物料焦场每日库存表
 * @author sudeyou
 * @since 2022-09-02 10:29:24
 */
public interface MaterialsCokeDayStockService extends IService<MaterialsCokeDayStock> {

    /**
     * 首页-焦碳库存
     * @param listHomeCokeStockDTO 查询条件dto
     * @return 列表数据
     */
    List<ListHomeCokeStockVO> getHomeCokeStockListData(ListHomeCokeStockDTO listHomeCokeStockDTO);

    /**
     * 焦碳库存-每日初始化
     * @return 执行结果
     */
    Object initMaterialsCokeDayStockData();
}

