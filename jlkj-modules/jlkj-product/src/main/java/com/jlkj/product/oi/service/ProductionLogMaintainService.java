package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionLogMaintain;
import com.jlkj.product.oi.dto.logmaintain.GetDto;
import com.jlkj.product.oi.dto.logmaintain.SaveOrUpdateDTO;

import java.util.Map;

/**
*@description: 针对表【product_oi_log_maintain(生产管理-日志维护)】的数据库操作Service
*@Author: 265823
*@date: 2023/7/10 15:09
*/
public interface ProductionLogMaintainService extends IService<ProductionLogMaintain> {

    /**
     * 日志查询
     * @param dto
     * @return
     */
    Page<Map<String, Object>> get(GetDto dto);

    /**
     * 日志新增
     * @param dto
     */
    void saveCustom(SaveOrUpdateDTO dto);

    /**
     * 日志修改
     * @param dto
     */
    void updateCustom(SaveOrUpdateDTO dto);

    /**
     * 日志删除
     * @param dto
     */
    void del(SaveOrUpdateDTO dto);
}
