package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionOutputPerformanceManualBake;
import com.jlkj.product.oi.dto.productionoutputperformancemanualbake.DeleteProductionOutputPerformanceManualBakeDTO;
import com.jlkj.product.oi.dto.productionoutputperformancemanualbake.InsertProductionOutputPerformanceManualBakeDTO;
import com.jlkj.product.oi.dto.productionoutputperformancemanualbake.PageProductionOutputPerformanceManualBakeDTO;
import com.jlkj.product.oi.vo.productionoutputperformancemanualbake.PageProductionOutputPerformanceManualBakeVO;

/**
*@description: 服务接口-生产管理-炼焦实绩-出炉实绩-手动出炉
*@Author: 265823
*@date: 2023/7/10 17:02
*/
public interface ProductionOutputPerformanceManualBakeService extends IService<ProductionOutputPerformanceManualBake> {

    /**
     * 生产管理-炼焦实绩-出炉实绩-手动出炉-查询-分页
     * @param pageProductionOutputPerformanceManualBakeDTO 查询条件dto
     * @return 分页数据
     */
    IPage<PageProductionOutputPerformanceManualBakeVO> getProductionOutputPerformanceManualBakePageData(PageProductionOutputPerformanceManualBakeDTO pageProductionOutputPerformanceManualBakeDTO);

    /**
     * 生产管理-炼焦实绩-出炉实绩-手动出炉-新增
     * @param insertProductionOutputPerformanceManualBakeDTO 新增dto
     */
    void insertProductionOutputPerformanceManualBakeData(InsertProductionOutputPerformanceManualBakeDTO insertProductionOutputPerformanceManualBakeDTO);

    /**
     * 生产管理-炼焦实绩-出炉实绩-手动出炉-删除
     * @param deleteProductionOutputPerformanceManualBakeDTO 删除dto
     * @return 执行结果
     */
    void deleteProductionOutputPerformanceManualBakeData(DeleteProductionOutputPerformanceManualBakeDTO deleteProductionOutputPerformanceManualBakeDTO);

}
