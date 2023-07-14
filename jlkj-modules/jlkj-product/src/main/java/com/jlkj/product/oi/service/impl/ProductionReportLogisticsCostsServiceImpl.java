package com.jlkj.product.oi.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.product.oi.dto.productionreportlogisticscosts.AddProductionReportLogisticsCostsDTO;
import com.jlkj.product.oi.dto.productionreportlogisticscosts.DeleteProductionReportLogisticsCostsDTO;
import com.jlkj.product.oi.dto.productionreportlogisticscosts.PageProductionReportLogisticsCostsDTO;
import com.jlkj.product.oi.domain.ProductionReportLogisticsCosts;
import com.jlkj.product.oi.dto.productionreportlogisticscosts.UpdateProductionReportLogisticsCostsDTO;
import com.jlkj.product.oi.mapper.ProductionReportLogisticsCostsMapper;
import com.jlkj.product.oi.service.ProductionReportLogisticsCostsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
*@description: 生产管理-报表管理-物流费用
*@Author: 265823
*@date: 2023/7/12 10:27
*/
@Service
@Slf4j
public class ProductionReportLogisticsCostsServiceImpl extends ServiceImpl<ProductionReportLogisticsCostsMapper, ProductionReportLogisticsCosts>
        implements ProductionReportLogisticsCostsService {
    /**
     * 查询生产管理-报表管理-物流费用
     * @param pageProductionReportLogisticsCostsDTO 查询条件dto
     * @return
     */
    @Override
    public IPage<Map<String, String>> getListPage(PageProductionReportLogisticsCostsDTO pageProductionReportLogisticsCostsDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionReportLogisticsCostsDTO.getCurrent(), pageProductionReportLogisticsCostsDTO.getSize());
        return getBaseMapper().getListPage(page, pageProductionReportLogisticsCostsDTO);
    }

    /**
     * 新增生产管理-报表管理-物流费用
     * @param dto
     */
    @Override
    public void saveCustom(AddProductionReportLogisticsCostsDTO dto) {
        QueryWrapper<ProductionReportLogisticsCosts> qw = new QueryWrapper<>();
        qw.lambda().eq(ProductionReportLogisticsCosts::getStatisticsDate, dto.getStatisticsDate());
        List<ProductionReportLogisticsCosts> list = baseMapper.selectList(qw);
        if (list.size() > 0) {
            throw new ServiceException("此日期已存在物流费用的数据！");
        }
        ProductionReportLogisticsCosts productionReportLogisticsCosts = new ProductionReportLogisticsCosts();
        productionReportLogisticsCosts.setId(IdUtil.randomUUID());
        productionReportLogisticsCosts.setStatisticsDate(dto.getStatisticsDate());
        productionReportLogisticsCosts.setUnloadingFee(dto.getUnloadingFee());
        productionReportLogisticsCosts.setReverseFee(dto.getReverseFee());
        productionReportLogisticsCosts.setTemporaryFee(dto.getTemporaryFee());

        productionReportLogisticsCosts.setCreateUserId(dto.getCreateUserId());
        productionReportLogisticsCosts.setCreateUserName(dto.getCreateUserName());
        productionReportLogisticsCosts.setCreateTime(new Date());
        productionReportLogisticsCosts.setModifyUserId(dto.getCreateUserId());
        productionReportLogisticsCosts.setModifyUserName(dto.getCreateUserName());
        productionReportLogisticsCosts.setModifyTime(new Date());
        baseMapper.insert(productionReportLogisticsCosts);
    }

    /**
     * 修改生产管理-报表管理-物流费用
     * @param dto
     */
    @Override
    public void updateCustom(UpdateProductionReportLogisticsCostsDTO dto) {
        ProductionReportLogisticsCosts productionReportLogisticsCosts = baseMapper.selectById(dto.getId());
        if (null != productionReportLogisticsCosts) {
            QueryWrapper<ProductionReportLogisticsCosts> qw = new QueryWrapper<>();
            qw.lambda().eq(ProductionReportLogisticsCosts::getStatisticsDate, dto.getStatisticsDate())
                    .ne(ProductionReportLogisticsCosts::getId, dto.getId());
            List<ProductionReportLogisticsCosts> list = baseMapper.selectList(qw);
            if (list.size() > 0) {
                throw new ServiceException("此日期已存在物流费用的数据！");
            }
            productionReportLogisticsCosts.setStatisticsDate(dto.getStatisticsDate());
            productionReportLogisticsCosts.setUnloadingFee(dto.getUnloadingFee());
            productionReportLogisticsCosts.setReverseFee(dto.getReverseFee());
            productionReportLogisticsCosts.setTemporaryFee(dto.getTemporaryFee());

            productionReportLogisticsCosts.setModifyUserId(dto.getModifyUserId());
            productionReportLogisticsCosts.setModifyUserName(dto.getModifyUserName());
            productionReportLogisticsCosts.setModifyTime(new Date());
            baseMapper.updateById(productionReportLogisticsCosts);
        } else {
            throw new ServiceException("生产管理-报表管理-物流费用不存在");
        }
    }

    /**
     * 删除生产管理-报表管理-物流费用
     * @param deleteProductionReportLogisticsCostsDTO
     */
    @Override
    public void delete(DeleteProductionReportLogisticsCostsDTO deleteProductionReportLogisticsCostsDTO) {
        ProductionReportLogisticsCosts productionReportLogisticsCosts = baseMapper.selectById(deleteProductionReportLogisticsCostsDTO.getId());
        if (null != productionReportLogisticsCosts) {
            baseMapper.deleteById(productionReportLogisticsCosts);
        } else {
            throw new ServiceException("生产管理-报表管理-物流费用不存在或已被删除");
        }
    }
}

