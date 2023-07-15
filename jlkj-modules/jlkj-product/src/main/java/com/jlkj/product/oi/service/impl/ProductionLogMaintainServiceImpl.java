package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionLogMaintain;
import com.jlkj.product.oi.dto.logmaintain.GetDto;
import com.jlkj.product.oi.dto.logmaintain.SaveOrUpdateDTO;
import com.jlkj.product.oi.mapper.ProductionLogMaintainMapper;
import com.jlkj.product.oi.service.ProductionLogMaintainService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
*@description: 针对表【product_oi_log_maintain(生产管理-日志维护)】的数据库操作Service实现
*@Author: 265823
*@date: 2023/7/10 15:09
*/
@Service
public class ProductionLogMaintainServiceImpl extends ServiceImpl<ProductionLogMaintainMapper, ProductionLogMaintain>
        implements ProductionLogMaintainService {

    /**
     * 日志查询
     * @param dto
     * @return
     */
    @Override
    public Page<Map<String, Object>> get(GetDto dto) {
        Page<Map<String, Object>> page = new Page<>(dto.getCurrent(), dto.getSize());
        Date start = DateUtil.parse(StrUtil.isEmpty(dto.getStartTime()) ? "1790-01-01" : dto.getStartTime() + " 00:00:00");
        Date end = DateUtil.parse(StrUtil.isEmpty(dto.getEndTime()) ? "1790-01-01" : dto.getEndTime() + " 23:59:59");
        Page<Map<String, Object>> mapPage = pageMaps(page, new QueryWrapper<ProductionLogMaintain>()
                .orderBy(true, "asc".equals(dto.getOrderby()), dto.getOrder())
                .lambda()
                .ge(ObjectUtil.isNotEmpty(dto.getStartTime()), ProductionLogMaintain::getCreateTime, start)
                .le(ObjectUtil.isNotEmpty(dto.getEndTime()), ProductionLogMaintain::getCreateTime, end)
                .eq(ObjectUtil.isNotEmpty(dto.getItemId()), ProductionLogMaintain::getItemId, dto.getItemId()));
        return mapPage;
    }

    /**
     * 日志新增
     * @param dto
     */
    @Override
    public void saveCustom(SaveOrUpdateDTO dto) {
        ProductionLogMaintain entity = new ProductionLogMaintain();
        entity.setId(IdUtil.randomUUID());
        entity.setItemId(dto.getItemId());
        entity.setItemName(dto.getItemName());
        entity.setContent(dto.getContent());
        entity.setCreateTime(DateUtil.parse(dto.getTime()));
        entity.setCreateUserId(dto.getUserId());
        entity.setCreateUserName(dto.getUserName());
        entity.setModifyTime(new Date());
        entity.setModifyUserId(dto.getUserId());
        entity.setModifyUserName(dto.getUserName());
        save(entity);
    }

    /**
     * 日志修改
     * @param dto
     */
    @Override
    public void updateCustom(SaveOrUpdateDTO dto) {
        ProductionLogMaintain entity = getOne(new LambdaQueryWrapper<ProductionLogMaintain>()
                .eq(ProductionLogMaintain::getId, dto.getId()));
        entity.setItemId(dto.getItemId());
        entity.setItemName(dto.getItemName());
        entity.setContent(dto.getContent());
        entity.setCreateTime(DateUtil.parse(dto.getTime()));
        entity.setModifyTime(new Date());
        entity.setModifyUserId(dto.getUserId());
        entity.setModifyUserName(dto.getUserName());
        updateById(entity);
    }

    /**
     * 日志删除
     * @param dto
     */
    @Override
    public void del(SaveOrUpdateDTO dto) {
        removeById(dto.getId());
    }
}




