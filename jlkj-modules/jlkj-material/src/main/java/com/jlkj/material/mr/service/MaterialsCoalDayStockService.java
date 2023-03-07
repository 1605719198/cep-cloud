package com.jlkj.material.mr.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.material.mr.dto.materialscoaldaystock.ListHomeCoalStockDTO;
import com.jlkj.material.mr.domain.MaterialsCoalDayStock;
import com.jlkj.material.mr.vo.materialscoaldaystock.ListHomeCoalStockVO;

import java.util.List;

/**
 * 服务接口-物料煤场每日库存表
 * @author sudeyou
 * @since 2022-09-02 10:55:24
 */
public interface MaterialsCoalDayStockService extends IService<MaterialsCoalDayStock> {

    /**
     * 首页-原煤库存
     * @param listHomeCoalStockDTO 查询条件dto
     * @return 列表数据
     */
    List<ListHomeCoalStockVO> getHomeCoalStockListData(ListHomeCoalStockDTO listHomeCoalStockDTO);

    /**
     * 原煤库存-每日初始化
     * @return 执行结果
     */
    Object initMaterialsCoalDayStockData();
}

