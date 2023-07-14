package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionCoalTowerStock;
import com.jlkj.product.oi.dto.coalconfigmanual.*;

import java.util.List;
import java.util.Map;

/**
*@description: 针对表【product_oi_coal_tower_stock(生产实绩-储煤塔存量)】的数据库操作Service
*@Author: 265823
*@date: 2023/7/10 8:55
*/
public interface ProductionCoalTowerStockService extends IService<ProductionCoalTowerStock> {

    /**
     * 手动配煤-储煤塔列表查询
     * @return
     */
    List<Map<String, Object>> getTowerList();

    /**
     * 手动配煤-储煤塔配煤详细列表查询
     * @param dto
     * @return
     */
    IPage<Map<String, Object>> getTowerBlendDetailList(GetPageDTO dto);

    /**
     * 手动配煤-储煤塔配煤记录对应详细配煤仓配煤列表查询
     * @param dto
     * @return
     */
    List<Map<String, Object>> getBlendWareDetailList(GetWareHouseDTO dto);

    /**
     * 手动配煤-根据配煤开始时间，查询前置数据
     * @param dto
     * @return
     */
    Map<String, Object> getLastConfigPlan(GetDTO dto);

    /**
     * 手动配煤-新增
     * @param dto
     */
    void saveCustom(SaveOrUpdateManualDTO dto);

    /**
     * 手动配煤-删除
     * @param dto
     */
    void del(DelDTO dto);

    /**
     * 获取配煤计划状态2或3列表
     * @return
     */
    List<Map<String, Object>> getPlanConfigCoke();
}
