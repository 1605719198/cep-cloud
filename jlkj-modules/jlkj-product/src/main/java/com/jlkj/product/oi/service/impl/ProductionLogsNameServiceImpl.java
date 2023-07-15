package com.jlkj.product.oi.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.ProductionLogsName;
import com.jlkj.product.oi.dto.productionlogsname.AddProductionLogsNameDTO;
import com.jlkj.product.oi.dto.productionlogsname.DeleteProductionLogsNameDTO;
import com.jlkj.product.oi.dto.productionlogsname.PageProductionLogsNameDTO;
import com.jlkj.product.oi.dto.productionlogsname.UpdateProductionLogsNameDTO;
import com.jlkj.product.oi.mapper.ProductionLogsNameMapper;
import com.jlkj.product.oi.service.ProductionLogsNameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
*@description: 日志项名称
*@Author: 265823
*@date: 2023/7/10 16:05
*/
@Service
@Slf4j
public class ProductionLogsNameServiceImpl extends ServiceImpl<ProductionLogsNameMapper, ProductionLogsName>
        implements ProductionLogsNameService {
    /**
     * 查询日志项名称
     * @param pageProductionLogsNameDTO 查询条件dto
     * @return
     */
    @Override
    public IPage<Map<String, String>> getListPage(PageProductionLogsNameDTO pageProductionLogsNameDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionLogsNameDTO.getCurrent(), pageProductionLogsNameDTO.getSize());
        return getBaseMapper().getListPage(page, pageProductionLogsNameDTO);
    }

    /**
     * 新增
     * @param addProductionLogsNameDTO
     */
    @Override
    public void saveCustom(AddProductionLogsNameDTO addProductionLogsNameDTO) {
        ProductionLogsName productionLogsName = new ProductionLogsName();
        productionLogsName.setId(IdUtil.randomUUID());
        productionLogsName.setLogsName(addProductionLogsNameDTO.getLogsName());
        productionLogsName.setCreateUserId(addProductionLogsNameDTO.getCreateUserId());
        productionLogsName.setCreateUserName(addProductionLogsNameDTO.getCreateUserName());
        productionLogsName.setCreateTime(new Date());
        productionLogsName.setModifyUserId(addProductionLogsNameDTO.getCreateUserId());
        productionLogsName.setModifyUserName(addProductionLogsNameDTO.getCreateUserName());
        productionLogsName.setModifyTime(new Date());
        baseMapper.insert(productionLogsName);
    }

    /**
     * 修改
     * @param updateProductionLogsNameDTO
     */
    @Override
    public void updateCustom(UpdateProductionLogsNameDTO updateProductionLogsNameDTO) {
        ProductionLogsName productionLogsName = baseMapper.selectById(updateProductionLogsNameDTO.getId());
        if (null != productionLogsName) {
            productionLogsName.setLogsName(updateProductionLogsNameDTO.getLogsName());
            productionLogsName.setModifyUserId(updateProductionLogsNameDTO.getModifyUserId());
            productionLogsName.setModifyUserName(updateProductionLogsNameDTO.getModifyUserName());
            productionLogsName.setModifyTime(new Date());
            baseMapper.updateById(productionLogsName);
        } else {
            throw new ServiceException("日志项名称不存在");
        }
    }

    /**
     * 删除日志项名称
     * @param deleteProductionLogsNameDTO
     */
    @Override
    public void delete(DeleteProductionLogsNameDTO deleteProductionLogsNameDTO) {
        ProductionLogsName productionLogsName = baseMapper.selectById(deleteProductionLogsNameDTO.getId());
        if (null != productionLogsName) {
            baseMapper.deleteById(productionLogsName);
        } else {
            throw new ServiceException("日志项名称不存在或已被删除");
        }
    }
}

