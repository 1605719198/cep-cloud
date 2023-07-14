package com.jlkj.product.oi.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.product.oi.domain.ProductionReportOilConsume;
import com.jlkj.product.oi.dto.productionreportoilconsume.AddProductionReportOilConsumeDTO;
import com.jlkj.product.oi.dto.productionreportoilconsume.DeleteProductionReportOilConsumeDTO;
import com.jlkj.product.oi.dto.productionreportoilconsume.PageProductionReportOilConsumeDTO;
import com.jlkj.product.oi.dto.productionreportoilconsume.UpdateProductionReportOilConsumeDTO;
import com.jlkj.product.oi.mapper.ProductionReportOilConsumeMapper;
import com.jlkj.product.oi.service.ProductionReportOilConsumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
*@description: 生产管理-报表管理-油品消耗
*@Author: 265823
*@date: 2023/7/12 8:42
*/
@Service
@Slf4j
public class ProductionReportOilConsumeServiceImpl extends ServiceImpl<ProductionReportOilConsumeMapper, ProductionReportOilConsume>
        implements ProductionReportOilConsumeService {
    /**
     * 查询-分页-生产管理-统计分析-指标跟踪(月)
     * @param pageProductionReportOilConsumeDTO 查询条件dto
     * @return
     */
    @Override
    public IPage<Map<String, String>> getListPage(PageProductionReportOilConsumeDTO pageProductionReportOilConsumeDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionReportOilConsumeDTO.getCurrent(), pageProductionReportOilConsumeDTO.getSize());
        return getBaseMapper().getListPage(page, pageProductionReportOilConsumeDTO);
    }

    /**
     * 新增
     * @param addProductionReportOilConsumeDTO
     */
    @Override
    public void saveCustom(AddProductionReportOilConsumeDTO addProductionReportOilConsumeDTO) {
        QueryWrapper<ProductionReportOilConsume> qw = new QueryWrapper<>();
        qw.lambda().eq(ProductionReportOilConsume::getConsumeDate, addProductionReportOilConsumeDTO.getConsumeDate())
                .eq(ProductionReportOilConsume::getDeviceName, addProductionReportOilConsumeDTO.getDeviceName())
                .eq(ProductionReportOilConsume::getDeviceName, addProductionReportOilConsumeDTO.getDeviceName());
        List<ProductionReportOilConsume> list = baseMapper.selectList(qw);
        if (list.size() > 0) {
            throw new ServiceException("此日期已存在此设备名称下此油品类型的数据！");
        }
        ProductionReportOilConsume productionReportOilConsume = new ProductionReportOilConsume();
        productionReportOilConsume.setId(IdUtil.randomUUID());
        productionReportOilConsume.setConsumeDate(addProductionReportOilConsumeDTO.getConsumeDate());
        productionReportOilConsume.setDeviceName(addProductionReportOilConsumeDTO.getDeviceName());
        productionReportOilConsume.setOilType(addProductionReportOilConsumeDTO.getOilType());
        productionReportOilConsume.setDailyConsumption(addProductionReportOilConsumeDTO.getDailyConsumption());

        productionReportOilConsume.setCreateUserId(addProductionReportOilConsumeDTO.getCreateUserId());
        productionReportOilConsume.setCreateUserName(addProductionReportOilConsumeDTO.getCreateUserName());
        productionReportOilConsume.setCreateTime(new Date());
        productionReportOilConsume.setModifyUserId(addProductionReportOilConsumeDTO.getCreateUserId());
        productionReportOilConsume.setModifyUserName(addProductionReportOilConsumeDTO.getCreateUserName());
        productionReportOilConsume.setModifyTime(new Date());
        baseMapper.insert(productionReportOilConsume);
    }

    /**
     * 修改
     * @param updateProductionReportOilConsumeDTO
     */
    @Override
    public void updateCustom(UpdateProductionReportOilConsumeDTO updateProductionReportOilConsumeDTO) {
        ProductionReportOilConsume productionReportOilConsume = baseMapper.selectById(updateProductionReportOilConsumeDTO.getId());
        if (null != productionReportOilConsume) {
            QueryWrapper<ProductionReportOilConsume> qw = new QueryWrapper<>();
            qw.lambda().eq(ProductionReportOilConsume::getConsumeDate, updateProductionReportOilConsumeDTO.getConsumeDate())
                    .eq(ProductionReportOilConsume::getDeviceName, updateProductionReportOilConsumeDTO.getDeviceName())
                    .eq(ProductionReportOilConsume::getDeviceName, updateProductionReportOilConsumeDTO.getDeviceName())
                    .ne(ProductionReportOilConsume::getId, updateProductionReportOilConsumeDTO.getId());
            List<ProductionReportOilConsume> list = baseMapper.selectList(qw);
            if (list.size() > 0) {
                throw new ServiceException("此日期已存在此设备名称下此油品类型的数据！");
            }
            productionReportOilConsume.setConsumeDate(updateProductionReportOilConsumeDTO.getConsumeDate());
            productionReportOilConsume.setDeviceName(updateProductionReportOilConsumeDTO.getDeviceName());
            productionReportOilConsume.setOilType(updateProductionReportOilConsumeDTO.getOilType());
            productionReportOilConsume.setDailyConsumption(updateProductionReportOilConsumeDTO.getDailyConsumption());

            productionReportOilConsume.setModifyUserId(updateProductionReportOilConsumeDTO.getModifyUserId());
            productionReportOilConsume.setModifyUserName(updateProductionReportOilConsumeDTO.getModifyUserName());
            productionReportOilConsume.setModifyTime(new Date());
            baseMapper.updateById(productionReportOilConsume);
        }
        else {
            throw new ServiceException("生产管理-报表管理-油品消耗不存在");
        }
    }

    /**
     * 删除
     * @param deleteProductionReportOilConsumeDTO
     */
    @Override
    public void delete(DeleteProductionReportOilConsumeDTO deleteProductionReportOilConsumeDTO) {
        ProductionReportOilConsume productionReportOilConsume = baseMapper.selectById(deleteProductionReportOilConsumeDTO.getId());
        if (null != productionReportOilConsume) {
            baseMapper.deleteById(productionReportOilConsume);
        }
        else {
            throw new ServiceException("生产管理-报表管理-油品消耗不存在或已被删除");
        }
    }
}

