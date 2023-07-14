package com.jlkj.product.oi.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.product.oi.domain.ProductionReportUnloading;
import com.jlkj.product.oi.dto.productionreportunloading.AddProductionReportUnloadingDTO;
import com.jlkj.product.oi.dto.productionreportunloading.DeleteProductionReportUnloadingDTO;
import com.jlkj.product.oi.dto.productionreportunloading.PageProductionReportUnloadingDTO;
import com.jlkj.product.oi.dto.productionreportunloading.UpdateProductionReportUnloadingDTO;
import com.jlkj.product.oi.mapper.ProductionReportUnloadingMapper;
import com.jlkj.product.oi.service.ProductionReportUnloadingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
*@description: 生产管理-报表管理-煤场卸车情况
*@Author: 265823
*@date: 2023/7/12 9:29
*/
@Service
@Slf4j
public class ProductionReportUnloadingServiceImpl extends ServiceImpl<ProductionReportUnloadingMapper, ProductionReportUnloading>
        implements ProductionReportUnloadingService {
    /**
     * 查询生产管理-报表管理-煤场卸车情况
     * @param pageProductionReportUnloadingDTO
     * @return
     */
    @Override
    public IPage<Map<String, String>> getListPage(PageProductionReportUnloadingDTO pageProductionReportUnloadingDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionReportUnloadingDTO.getCurrent(), pageProductionReportUnloadingDTO.getSize());
        return getBaseMapper().getListPage(page, pageProductionReportUnloadingDTO);
    }

    /**
     * 新增
     * @param dto
     */
    @Override
    public void saveCustom(AddProductionReportUnloadingDTO dto) {
        QueryWrapper<ProductionReportUnloading> qw = new QueryWrapper<>();
        qw.lambda().eq(ProductionReportUnloading::getStatisticsDate, dto.getStatisticsDate())
                .eq(ProductionReportUnloading::getCoalWay, dto.getCoalWay());
        List<ProductionReportUnloading> list = baseMapper.selectList(qw);
        if (list.size() > 0) {
            throw new ServiceException("此日期已存在此来煤方式的数据！");
        }

        ProductionReportUnloading productionReportUnloading = new ProductionReportUnloading();
        productionReportUnloading.setId(IdUtil.randomUUID());
        productionReportUnloading.setStatisticsDate(dto.getStatisticsDate());
        productionReportUnloading.setCoalWay(dto.getCoalWay());
        productionReportUnloading.setCoalNum(dto.getCoalNum());
        productionReportUnloading.setStock(dto.getStock());
        productionReportUnloading.setUnloadingCoalScrew(dto.getUnloadingCoalScrew());
        productionReportUnloading.setUnloadingCoalPart(dto.getUnloadingCoalPart());
        productionReportUnloading.setUnloadedScrew(dto.getUnloadedScrew());
        productionReportUnloading.setUnloadedPart(dto.getUnloadedPart());
        productionReportUnloading.setCreateUserId(dto.getCreateUserId());
        productionReportUnloading.setCreateUserName(dto.getCreateUserName());
        productionReportUnloading.setCreateTime(new Date());
        productionReportUnloading.setModifyUserId(dto.getCreateUserId());
        productionReportUnloading.setModifyUserName(dto.getCreateUserName());
        productionReportUnloading.setModifyTime(new Date());
        baseMapper.insert(productionReportUnloading);
    }

    /**
     * 修改-煤场卸车情况
     * @param dto
     */
    @Override
    public void updateCustom(UpdateProductionReportUnloadingDTO dto) {
        ProductionReportUnloading productionReportUnloading = baseMapper.selectById(dto.getId());
        if (null != productionReportUnloading) {

            QueryWrapper<ProductionReportUnloading> qw = new QueryWrapper<>();
            qw.lambda().eq(ProductionReportUnloading::getStatisticsDate, dto.getStatisticsDate())
                    .eq(ProductionReportUnloading::getCoalWay, dto.getCoalWay())
                    .ne(ProductionReportUnloading::getId, dto.getId());
            List<ProductionReportUnloading> list = baseMapper.selectList(qw);
            if (list.size() > 0) {
                throw new ServiceException("此日期已存在此来煤方式的数据！");
            }
            productionReportUnloading.setStatisticsDate(dto.getStatisticsDate());
            productionReportUnloading.setCoalWay(dto.getCoalWay());
            productionReportUnloading.setCoalNum(dto.getCoalNum());
            productionReportUnloading.setStock(dto.getStock());
            productionReportUnloading.setUnloadingCoalScrew(dto.getUnloadingCoalScrew());
            productionReportUnloading.setUnloadingCoalPart(dto.getUnloadingCoalPart());
            productionReportUnloading.setUnloadedScrew(dto.getUnloadedScrew());
            productionReportUnloading.setUnloadedPart(dto.getUnloadedPart());
            productionReportUnloading.setModifyUserId(dto.getModifyUserId());
            productionReportUnloading.setModifyUserName(dto.getModifyUserName());
            productionReportUnloading.setModifyTime(new Date());
            baseMapper.updateById(productionReportUnloading);
        } else {
            throw new ServiceException("生产管理-报表管理-煤场卸车情况不存在");
        }
    }

    /**
     * 删除生产管理-报表管理-煤场卸车情况
     * @param deleteProductionReportUnloadingDTO
     */
    @Override
    public void delete(DeleteProductionReportUnloadingDTO deleteProductionReportUnloadingDTO) {
        ProductionReportUnloading productionReportUnloading = baseMapper.selectById(deleteProductionReportUnloadingDTO.getId());
        if (null != productionReportUnloading) {
            baseMapper.deleteById(productionReportUnloading);
        } else {
            throw new ServiceException("生产管理-报表管理-煤场卸车情况不存在或已被删除");
        }
    }
}

