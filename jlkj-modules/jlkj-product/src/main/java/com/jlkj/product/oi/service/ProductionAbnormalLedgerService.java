package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionAbnormalLedger;
import com.jlkj.product.oi.dto.productionabnormalledger.AddProductionAbnormalLedgerDTO;
import com.jlkj.product.oi.dto.productionabnormalledger.DelProductionAbnormalLedgerDTO;
import com.jlkj.product.oi.dto.productionabnormalledger.GetProductionAbnormalLedgerDTO;
import com.jlkj.product.oi.dto.productionabnormalledger.UpdateProductionAbnormalLedgerDTO;

import java.util.Map;

/**针对表【product_oi_abnormal_ledger(生产异常台账)】的数据库操作Service
*@description:
*@Author: 265823
*@date: 2023/7/10 10:52
*/
public interface ProductionAbnormalLedgerService extends IService<ProductionAbnormalLedger> {

    /**
     * 生产异常查询
     * @param dto
     * @return
     */
    IPage<Map<String, Object>> get(GetProductionAbnormalLedgerDTO dto);

    /**
     * 生产异常台账新增
     * @param dto
     */
    void saveCustom(AddProductionAbnormalLedgerDTO dto);

    /**
     * 生产异常台账修改
     * @param dto
     */
    void updateCustom(UpdateProductionAbnormalLedgerDTO dto);

    /**
     * 生产异常台账删除
     * @param dto
     */
    void delete(DelProductionAbnormalLedgerDTO dto);
}
