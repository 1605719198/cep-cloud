package com.jlkj.product.oi.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.constants.CommonConstant;
import com.jlkj.product.oi.domain.ProductionInformationConfiguration;
import com.jlkj.product.oi.dto.productioninformationconfiguration.DeleteProductionInformationConfigurationDTO;
import com.jlkj.product.oi.dto.productioninformationconfiguration.InsertProductionInformationConfigurationDTO;
import com.jlkj.product.oi.dto.productioninformationconfiguration.PageProductionInformationConfigurationDTO;
import com.jlkj.product.oi.dto.productioninformationconfiguration.UpdateProductionInformationConfigurationDTO;
import com.jlkj.product.oi.mapper.ProductionInformationConfigurationMapper;
import com.jlkj.product.oi.service.ProductionInformationConfigurationService;
import com.jlkj.product.oi.vo.productioninformationconfiguration.PageProductionInformationConfigurationVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 服务实现-生产信息配置
 *
 * @author sudeyou
 * @since 2022-10-18 14:47:44
 */
@Service
public class ProductionInformationConfigurationServiceImpl extends ServiceImpl<ProductionInformationConfigurationMapper, ProductionInformationConfiguration>
    implements ProductionInformationConfigurationService {

    @Override
    @Transactional(readOnly = true)
    public IPage<PageProductionInformationConfigurationVO> getProductionInformationConfigurationPageData(PageProductionInformationConfigurationDTO pageProductionInformationConfigurationDTO) {
        Page<Map<String, Object>> page = new Page<>(pageProductionInformationConfigurationDTO.getCurrent(), pageProductionInformationConfigurationDTO.getSize());
        return getBaseMapper().getProductionInformationConfigurationPageData(page, pageProductionInformationConfigurationDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertProductionInformationConfigurationData(InsertProductionInformationConfigurationDTO insertProductionInformationConfigurationDTO) {
        List<ProductionInformationConfiguration> list = list(new QueryWrapper<ProductionInformationConfiguration>().lambda()
                .eq(ProductionInformationConfiguration::getMaterialsCode, insertProductionInformationConfigurationDTO.getMaterialsCode())
                .eq(ProductionInformationConfiguration::getQuenchingMethod, insertProductionInformationConfigurationDTO.getQuenchingMethod())
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if (list.size() > 0) {
            return AjaxResult.error("生产信息配置已存在");
        }
        ProductionInformationConfiguration productionInformationConfiguration = new ProductionInformationConfiguration();
        productionInformationConfiguration.setId(IdUtil.randomUUID());
        productionInformationConfiguration.setMaterialsCode(insertProductionInformationConfigurationDTO.getMaterialsCode());
        productionInformationConfiguration.setMaterialsName(insertProductionInformationConfigurationDTO.getMaterialsName());
        productionInformationConfiguration.setQuenchingMethod(insertProductionInformationConfigurationDTO.getQuenchingMethod());
        productionInformationConfiguration.setSingleHoleWeight(insertProductionInformationConfigurationDTO.getSingleHoleWeight());
        productionInformationConfiguration.setSingleConsumption(insertProductionInformationConfigurationDTO.getSingleConsumption());
        productionInformationConfiguration.setCreateUserId(insertProductionInformationConfigurationDTO.getCreateUserId());
        productionInformationConfiguration.setCreateUserName(insertProductionInformationConfigurationDTO.getCreateUserName());
        productionInformationConfiguration.setCreateTime(new Date());
        productionInformationConfiguration.setModifyUserId(insertProductionInformationConfigurationDTO.getCreateUserId());
        productionInformationConfiguration.setModifyUserName(insertProductionInformationConfigurationDTO.getCreateUserName());
        productionInformationConfiguration.setModifyTime(new Date());
        save(productionInformationConfiguration);
        return AjaxResult.success("生产信息配置增加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateProductionInformationConfigurationData(UpdateProductionInformationConfigurationDTO updateProductionInformationConfigurationDTO) {
        ProductionInformationConfiguration productionInformationConfiguration = getById(updateProductionInformationConfigurationDTO.getId());
        if (null != productionInformationConfiguration) {
            List<ProductionInformationConfiguration> list = list(new QueryWrapper<ProductionInformationConfiguration>().lambda()
                    .eq(ProductionInformationConfiguration::getMaterialsCode, updateProductionInformationConfigurationDTO.getMaterialsCode())
                    .eq(ProductionInformationConfiguration::getQuenchingMethod, updateProductionInformationConfigurationDTO.getQuenchingMethod())
                    .ne(ProductionInformationConfiguration::getId, updateProductionInformationConfigurationDTO.getId())
                    .last(CommonConstant.LIMIT_ONE_ROW)
            );
            if (list.size() > 0) {
                return AjaxResult.error("生产信息配置已存在");
            }
            productionInformationConfiguration.setMaterialsCode(updateProductionInformationConfigurationDTO.getMaterialsCode());
            productionInformationConfiguration.setMaterialsName(updateProductionInformationConfigurationDTO.getMaterialsName());
            productionInformationConfiguration.setQuenchingMethod(updateProductionInformationConfigurationDTO.getQuenchingMethod());
            productionInformationConfiguration.setSingleHoleWeight(updateProductionInformationConfigurationDTO.getSingleHoleWeight());
            productionInformationConfiguration.setSingleConsumption(updateProductionInformationConfigurationDTO.getSingleConsumption());
            productionInformationConfiguration.setModifyUserId(updateProductionInformationConfigurationDTO.getModifyUserId());
            productionInformationConfiguration.setModifyUserName(updateProductionInformationConfigurationDTO.getModifyUserName());
            productionInformationConfiguration.setModifyTime(new Date());
            updateById(productionInformationConfiguration);
            return AjaxResult.success("生产信息配置修改成功");
        }
        else {
            return AjaxResult.error("生产信息配置不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteProductionInformationConfigurationData(DeleteProductionInformationConfigurationDTO deleteProductionInformationConfigurationDTO) {
        ProductionInformationConfiguration productionInformationConfiguration = getById(deleteProductionInformationConfigurationDTO.getId());
        if (null != productionInformationConfiguration) {
            removeById(productionInformationConfiguration);
            return AjaxResult.success("生产信息配置删除成功");
        }
        else {
            return AjaxResult.error("生产信息配置不存在或已被删除");
        }
    }
}

