package com.jlkj.product.oi.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.product.oi.dto.productionreportliftingcosts.AddProductionReportLiftingCostsDTO;
import com.jlkj.product.oi.dto.productionreportliftingcosts.DeleteProductionReportLiftingCostsDTO;
import com.jlkj.product.oi.dto.productionreportliftingcosts.PageProductionReportLiftingCostsDTO;
import com.jlkj.product.oi.domain.ProductionReportLiftingCosts;
import com.jlkj.product.oi.dto.productionreportliftingcosts.UpdateProductionReportLiftingCostsDTO;
import com.jlkj.product.oi.mapper.ProductionReportLiftingCostsMapper;
import com.jlkj.product.oi.service.ProductionReportLiftingCostsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
*@description: 生产管理-报表管理-吊装费用
*@Author: 265823
*@date: 2023/7/12 10:01
*/
@Service
@Slf4j
public class ProductionReportLiftingCostsServiceImpl extends ServiceImpl<ProductionReportLiftingCostsMapper, ProductionReportLiftingCosts>
        implements ProductionReportLiftingCostsService {
    @Override
    public IPage<Map<String, String>> getListPage(PageProductionReportLiftingCostsDTO pageProductionReportLiftingCostsDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionReportLiftingCostsDTO.getCurrent(), pageProductionReportLiftingCostsDTO.getSize());
        return getBaseMapper().getListPage(page, pageProductionReportLiftingCostsDTO);
    }

    /**
     * 新增生产管理-报表管理-吊装费用
     * @param dto
     */
    @Override
    public void saveCustom(AddProductionReportLiftingCostsDTO dto) {
        ProductionReportLiftingCosts productionReportLiftingCosts = new ProductionReportLiftingCosts();
        productionReportLiftingCosts.setId(IdUtil.randomUUID());
        productionReportLiftingCosts.setStatisticsDate(dto.getStatisticsDate());
        productionReportLiftingCosts.setAreaName(dto.getAreaName());
        productionReportLiftingCosts.setCarType(dto.getCarType());
        productionReportLiftingCosts.setTonnes(dto.getTonnes());
        productionReportLiftingCosts.setDayCost(dto.getDayCost());
        productionReportLiftingCosts.setCarTime(dto.getCarTime());

        productionReportLiftingCosts.setCreateUserId(dto.getCreateUserId());
        productionReportLiftingCosts.setCreateUserName(dto.getCreateUserName());
        productionReportLiftingCosts.setCreateTime(new Date());
        productionReportLiftingCosts.setModifyUserId(dto.getCreateUserId());
        productionReportLiftingCosts.setModifyUserName(dto.getCreateUserName());
        productionReportLiftingCosts.setModifyTime(new Date());
        baseMapper.insert(productionReportLiftingCosts);
    }

    /**
     * 修改生产管理-报表管理-吊装费用
     * @param dto
     */
    @Override
    public void updateCustom(UpdateProductionReportLiftingCostsDTO dto) {
        ProductionReportLiftingCosts productionReportLiftingCosts = baseMapper.selectById(dto.getId());
        if (null != productionReportLiftingCosts) {
            productionReportLiftingCosts.setStatisticsDate(dto.getStatisticsDate());
            productionReportLiftingCosts.setAreaName(dto.getAreaName());
            productionReportLiftingCosts.setCarType(dto.getCarType());
            productionReportLiftingCosts.setTonnes(dto.getTonnes());
            productionReportLiftingCosts.setDayCost(dto.getDayCost());
            productionReportLiftingCosts.setCarTime(dto.getCarTime());

            productionReportLiftingCosts.setModifyUserId(dto.getModifyUserId());
            productionReportLiftingCosts.setModifyUserName(dto.getModifyUserName());
            productionReportLiftingCosts.setModifyTime(new Date());
            baseMapper.updateById(productionReportLiftingCosts);
        } else {
            throw new ServiceException("生产管理-报表管理-吊装费用不存在");
        }
    }

    /**
     * 删除生产管理-报表管理-吊装费用
     * @param deleteProductionReportLiftingCostsDTO
     */
    @Override
    public void delete(DeleteProductionReportLiftingCostsDTO deleteProductionReportLiftingCostsDTO) {
        ProductionReportLiftingCosts productionReportLiftingCosts = baseMapper.selectById(deleteProductionReportLiftingCostsDTO.getId());
        if (null != productionReportLiftingCosts) {
            baseMapper.deleteById(productionReportLiftingCosts);
        } else {
            throw new ServiceException("生产管理-报表管理-吊装费用不存在或已被删除");
        }
    }
}

