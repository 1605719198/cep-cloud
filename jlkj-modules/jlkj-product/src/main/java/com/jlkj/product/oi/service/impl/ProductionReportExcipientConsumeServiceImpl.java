package com.jlkj.product.oi.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.product.oi.domain.ProductionReportExcipientConsume;
import com.jlkj.product.oi.dto.productionreportexcipientconsume.AddProductionReportExcipientConsumeDTO;
import com.jlkj.product.oi.dto.productionreportexcipientconsume.DeleteProductionReportExcipientConsumeDTO;
import com.jlkj.product.oi.dto.productionreportexcipientconsume.PageProductionReportExcipientConsumeDTO;
import com.jlkj.product.oi.dto.productionreportexcipientconsume.UpdateProductionReportExcipientConsumeDTO;
import com.jlkj.product.oi.mapper.ProductionReportExcipientConsumeMapper;
import com.jlkj.product.oi.service.ProductionReportExcipientConsumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
*@description: 生产管理-报表管理-辅料消耗
*@Author: 265823
*@date: 2023/7/12 9:03
*/
@Service
@Slf4j
public class ProductionReportExcipientConsumeServiceImpl extends ServiceImpl<ProductionReportExcipientConsumeMapper, ProductionReportExcipientConsume>
        implements ProductionReportExcipientConsumeService {
    /**
     * 查询生产管理-报表管理-辅料消耗
     * @param pageProductionReportExcipientConsumeDTO 查询条件dto
     * @return
     */
    @Override
    public IPage<Map<String, String>> getListPage(PageProductionReportExcipientConsumeDTO pageProductionReportExcipientConsumeDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionReportExcipientConsumeDTO.getCurrent(), pageProductionReportExcipientConsumeDTO.getSize());
        return getBaseMapper().getListPage(page, pageProductionReportExcipientConsumeDTO);
    }

    /**
     * 新增-辅料消耗
     * @param addProductionReportExcipientConsumeDTO
     */
    @Override
    public void saveCustom(AddProductionReportExcipientConsumeDTO addProductionReportExcipientConsumeDTO) {
        QueryWrapper<ProductionReportExcipientConsume> qw = new QueryWrapper<>();
        qw.lambda().eq(ProductionReportExcipientConsume::getConsumeDate, addProductionReportExcipientConsumeDTO.getConsumeDate())
                .eq(ProductionReportExcipientConsume::getExcipientName, addProductionReportExcipientConsumeDTO.getExcipientName());
        List<ProductionReportExcipientConsume> list = baseMapper.selectList(qw);
        if (list.size() > 0) {
            throw new ServiceException("此日期已存在此辅料名称的数据！");
        }
        ProductionReportExcipientConsume productionReportExcipientConsume = new ProductionReportExcipientConsume();
        productionReportExcipientConsume.setId(IdUtil.randomUUID());
        productionReportExcipientConsume.setConsumeDate(addProductionReportExcipientConsumeDTO.getConsumeDate());
        productionReportExcipientConsume.setExcipientName(addProductionReportExcipientConsumeDTO.getExcipientName());
        productionReportExcipientConsume.setDailyConsumption(addProductionReportExcipientConsumeDTO.getDailyConsumption());
        productionReportExcipientConsume.setStock(addProductionReportExcipientConsumeDTO.getStock());

        productionReportExcipientConsume.setCreateUserId(addProductionReportExcipientConsumeDTO.getCreateUserId());
        productionReportExcipientConsume.setCreateUserName(addProductionReportExcipientConsumeDTO.getCreateUserName());
        productionReportExcipientConsume.setCreateTime(new Date());
        productionReportExcipientConsume.setModifyUserId(addProductionReportExcipientConsumeDTO.getCreateUserId());
        productionReportExcipientConsume.setModifyUserName(addProductionReportExcipientConsumeDTO.getCreateUserName());
        productionReportExcipientConsume.setModifyTime(new Date());
        baseMapper.insert(productionReportExcipientConsume);
    }

    /**
     * 修改-辅料消耗
     * @param updateProductionReportExcipientConsumeDTO
     */
    @Override
    public void updateCustom(UpdateProductionReportExcipientConsumeDTO updateProductionReportExcipientConsumeDTO) {
        ProductionReportExcipientConsume productionReportExcipientConsume = baseMapper.selectById(updateProductionReportExcipientConsumeDTO.getId());
        if (null != productionReportExcipientConsume) {

            QueryWrapper<ProductionReportExcipientConsume> qw = new QueryWrapper<>();
            qw.lambda().eq(ProductionReportExcipientConsume::getConsumeDate, updateProductionReportExcipientConsumeDTO.getConsumeDate())
                    .eq(ProductionReportExcipientConsume::getExcipientName, updateProductionReportExcipientConsumeDTO.getExcipientName())
                    .ne(ProductionReportExcipientConsume::getId, updateProductionReportExcipientConsumeDTO.getId());
            List<ProductionReportExcipientConsume> list = baseMapper.selectList(qw);
            if (list.size() > 0) {
                throw new ServiceException("此日期已存在此辅料名称的数据！");
            }

            productionReportExcipientConsume.setConsumeDate(updateProductionReportExcipientConsumeDTO.getConsumeDate());
            productionReportExcipientConsume.setExcipientName(updateProductionReportExcipientConsumeDTO.getExcipientName());
            productionReportExcipientConsume.setDailyConsumption(updateProductionReportExcipientConsumeDTO.getDailyConsumption());
            productionReportExcipientConsume.setStock(updateProductionReportExcipientConsumeDTO.getStock());

            productionReportExcipientConsume.setModifyUserId(updateProductionReportExcipientConsumeDTO.getModifyUserId());
            productionReportExcipientConsume.setModifyUserName(updateProductionReportExcipientConsumeDTO.getModifyUserName());
            productionReportExcipientConsume.setModifyTime(new Date());
            baseMapper.updateById(productionReportExcipientConsume);
        } else {
            throw new ServiceException("生产管理-报表管理-辅料消耗不存在");
        }
    }

    /**
     * 删除-辅料消耗
     * @param deleteProductionReportExcipientConsumeDTO
     */
    @Override
    public void delete(DeleteProductionReportExcipientConsumeDTO deleteProductionReportExcipientConsumeDTO) {
        ProductionReportExcipientConsume productionReportExcipientConsume = baseMapper.selectById(deleteProductionReportExcipientConsumeDTO.getId());
        if (null != productionReportExcipientConsume) {
            baseMapper.deleteById(productionReportExcipientConsume);
        } else {
           throw new ServiceException("生产管理-报表管理-辅料消耗不存在或已被删除");
        }
    }
}

