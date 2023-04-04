package com.jlkj.product.oi.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.product.oi.domain.ProductionInformationConfiguration;
import com.jlkj.product.oi.dto.productioninformationconfiguration.DeleteProductionInformationConfigurationDTO;
import com.jlkj.product.oi.dto.productioninformationconfiguration.InsertProductionInformationConfigurationDTO;
import com.jlkj.product.oi.dto.productioninformationconfiguration.PageProductionInformationConfigurationDTO;
import com.jlkj.product.oi.dto.productioninformationconfiguration.UpdateProductionInformationConfigurationDTO;
import com.jlkj.product.oi.vo.productioninformationconfiguration.PageProductionInformationConfigurationVO;

/**
 * 服务接口-生产信息配置
 *
 * @author sudeyou
 * @since 2022-10-18 14:47:44
 */
public interface ProductionInformationConfigurationService extends IService<ProductionInformationConfiguration> {

    /**
     * 生产信息配置-查询-分页
     * @param pageProductionInformationConfigurationDTO 查询条件dto
     * @return 分页数据
     */
    IPage<PageProductionInformationConfigurationVO> getProductionInformationConfigurationPageData(PageProductionInformationConfigurationDTO pageProductionInformationConfigurationDTO);

    /**
     * 生产信息配置-新增
     * @param insertProductionInformationConfigurationDTO 新增dto
     * @return 执行结果
     */
    Object insertProductionInformationConfigurationData(InsertProductionInformationConfigurationDTO insertProductionInformationConfigurationDTO);

    /**
     * 生产信息配置-修改
     * @param updateProductionInformationConfigurationDTO 修改dto
     * @return 执行结果
     */
    Object updateProductionInformationConfigurationData(UpdateProductionInformationConfigurationDTO updateProductionInformationConfigurationDTO);

    /**
     * 生产信息配置-删除
     * @param deleteProductionInformationConfigurationDTO 删除dto
     * @return 执行结果
     */
    Object deleteProductionInformationConfigurationData(DeleteProductionInformationConfigurationDTO deleteProductionInformationConfigurationDTO);
}

