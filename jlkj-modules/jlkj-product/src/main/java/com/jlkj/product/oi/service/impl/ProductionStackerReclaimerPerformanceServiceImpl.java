package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.product.oi.domain.MaterialsStorageSpaces;
import com.jlkj.product.oi.domain.ProductionStackerReclaimerPerformance;
import com.jlkj.product.oi.dto.productionconveyingcoalperformance.GetProductionStackerReclaimerPerformanceDTO;
import com.jlkj.product.oi.dto.productionstackerreclaimerperformance.DeleteProductionStackerReclaimerPerformanceDTO;
import com.jlkj.product.oi.dto.productionstackerreclaimerperformance.InfoSchedulingDTO;
import com.jlkj.product.oi.dto.productionstackerreclaimerperformance.InsertProductionStackerReclaimerPerformanceDTO;
import com.jlkj.product.oi.dto.productionstackerreclaimerperformance.UpdateProductionStackerReclaimerPerformanceDTO;
import com.jlkj.product.oi.mapper.ProductionStackerReclaimerPerformanceMapper;
import com.jlkj.product.oi.service.MaterialsStorageSpacesService;
import com.jlkj.product.oi.service.ProductionStackerReclaimerPerformanceService;
import com.jlkj.product.oi.vo.productionstackerreclaimerperformance.InfoSchedulingVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
*@description: 针对表【product_oi_stacker_reclaimer_performance(堆取料机实绩)】的数据库操作Service实现
*@Author: 265823
*@date: 2023/7/11 13:08
*/
@Service
public class ProductionStackerReclaimerPerformanceServiceImpl extends ServiceImpl<ProductionStackerReclaimerPerformanceMapper, ProductionStackerReclaimerPerformance>
    implements ProductionStackerReclaimerPerformanceService {

    @Resource
    private MaterialsStorageSpacesService materialsStorageSpacesService;

    /**
     * 堆取料机实绩查询
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public IPage<Map<String, Object>> getStackerReclaimerPerformance(GetProductionStackerReclaimerPerformanceDTO dto) {
        Date start = DateUtil.parse(StrUtil.isEmpty(dto.getStartTime()) ? "1790-01-01" : dto.getStartTime() + " 00:00:00");
        Date end = DateUtil.parse(StrUtil.isEmpty(dto.getEndTime()) ? "1790-01-01" : dto.getEndTime() + " 23:59:59");
        QueryWrapper<ProductionStackerReclaimerPerformance> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, "asc".equals(dto.getOrderby()), dto.getOrder())
                .lambda()
                .ge(!StrUtil.isEmpty(dto.getStartTime()), ProductionStackerReclaimerPerformance::getStartTime, start)
                .le(!StrUtil.isEmpty(dto.getEndTime()), ProductionStackerReclaimerPerformance::getEndTime, end)
                .eq(!StrUtil.isEmpty(dto.getShiftName()), ProductionStackerReclaimerPerformance::getShiftName, dto.getShiftName())
                .eq(!StrUtil.isEmpty(dto.getClassName()), ProductionStackerReclaimerPerformance::getClassName, dto.getClassName());
        Page<Map<String, Object>> page = new Page<>(dto.getCurrent(), dto.getSize());
        IPage<Map<String, Object>> list = pageMaps(page, queryWrapper);
        return list;
    }

    /**
     * 堆取料机实绩-新增
     * @param insertProductionStackerReclaimerPerformanceDTO 新增dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertProductionStackerReclaimerPerformanceData(InsertProductionStackerReclaimerPerformanceDTO insertProductionStackerReclaimerPerformanceDTO) {
        MaterialsStorageSpaces materialsStorageSpaces = materialsStorageSpacesService.getById(insertProductionStackerReclaimerPerformanceDTO.getSlotNumber());
        if (null == materialsStorageSpaces) {
            throw new ServiceException("储位数据不存在");
        }
        ProductionStackerReclaimerPerformance productionStackerReclaimerPerformance = new ProductionStackerReclaimerPerformance();
        productionStackerReclaimerPerformance.setId(IdUtil.randomUUID());
        productionStackerReclaimerPerformance.setShiftName(insertProductionStackerReclaimerPerformanceDTO.getShiftName());
        productionStackerReclaimerPerformance.setClassName(insertProductionStackerReclaimerPerformanceDTO.getClassName());
        productionStackerReclaimerPerformance.setStackerReclaimerNumber(insertProductionStackerReclaimerPerformanceDTO.getStackerReclaimerNumber());
        productionStackerReclaimerPerformance.setSlotNumber(insertProductionStackerReclaimerPerformanceDTO.getSlotNumber());
        productionStackerReclaimerPerformance.setSlotName(insertProductionStackerReclaimerPerformanceDTO.getSlotName());
        productionStackerReclaimerPerformance.setStartTime(DateUtil.parseDateTime(insertProductionStackerReclaimerPerformanceDTO.getStartTime()));
        productionStackerReclaimerPerformance.setEndTime(DateUtil.parseDateTime(insertProductionStackerReclaimerPerformanceDTO.getEndTime()));
        productionStackerReclaimerPerformance.setDuration(insertProductionStackerReclaimerPerformanceDTO.getDuration());
        productionStackerReclaimerPerformance.setMaterialNumber(materialsStorageSpaces.getMaterialCategoryId());
        productionStackerReclaimerPerformance.setMaterialName(materialsStorageSpaces.getStorageSpacesName());
        productionStackerReclaimerPerformance.setCreateTime(new Date());
        save(productionStackerReclaimerPerformance);
    }

    /**
     * 堆取料机实绩-修改
     * @param updateProductionStackerReclaimerPerformanceDTO 修改dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProductionStackerReclaimerPerformanceData(UpdateProductionStackerReclaimerPerformanceDTO updateProductionStackerReclaimerPerformanceDTO) {
        ProductionStackerReclaimerPerformance productionStackerReclaimerPerformance = getById(updateProductionStackerReclaimerPerformanceDTO.getId());
        if (null != productionStackerReclaimerPerformance) {
            MaterialsStorageSpaces materialsStorageSpaces = materialsStorageSpacesService.getById(updateProductionStackerReclaimerPerformanceDTO.getSlotNumber());
            if (null == materialsStorageSpaces) {
                throw new ServiceException("储位数据不存在");
            }
            productionStackerReclaimerPerformance.setShiftName(updateProductionStackerReclaimerPerformanceDTO.getShiftName());
            productionStackerReclaimerPerformance.setClassName(updateProductionStackerReclaimerPerformanceDTO.getClassName());
            productionStackerReclaimerPerformance.setStackerReclaimerNumber(updateProductionStackerReclaimerPerformanceDTO.getStackerReclaimerNumber());
            productionStackerReclaimerPerformance.setSlotNumber(updateProductionStackerReclaimerPerformanceDTO.getSlotNumber());
            productionStackerReclaimerPerformance.setSlotName(updateProductionStackerReclaimerPerformanceDTO.getSlotName());
            productionStackerReclaimerPerformance.setStartTime(DateUtil.parseDateTime(updateProductionStackerReclaimerPerformanceDTO.getStartTime()));
            productionStackerReclaimerPerformance.setEndTime(DateUtil.parseDateTime(updateProductionStackerReclaimerPerformanceDTO.getEndTime()));
            productionStackerReclaimerPerformance.setDuration(updateProductionStackerReclaimerPerformanceDTO.getDuration());
            productionStackerReclaimerPerformance.setMaterialNumber(materialsStorageSpaces.getMaterialCategoryId());
            productionStackerReclaimerPerformance.setMaterialName(materialsStorageSpaces.getStorageSpacesName());
            updateById(productionStackerReclaimerPerformance);
        }
        else {
            throw new ServiceException("堆取料机实绩不存在");
        }
    }

    /**
     * 堆取料机实绩-删除
     * @param deleteProductionStackerReclaimerPerformanceDTO 删除dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProductionStackerReclaimerPerformanceData(DeleteProductionStackerReclaimerPerformanceDTO deleteProductionStackerReclaimerPerformanceDTO) {
        ProductionStackerReclaimerPerformance productionStackerReclaimerPerformance = getById(deleteProductionStackerReclaimerPerformanceDTO.getId());
        if (null != productionStackerReclaimerPerformance) {
            removeById(productionStackerReclaimerPerformance);
        }
        else {
            throw new ServiceException("堆取料机实绩不存在或已被删除");
        }
    }

    /**
     * 获取排班信息
     * @param infoSchedulingDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public InfoSchedulingVO getUserSchedulingInfoData(InfoSchedulingDTO infoSchedulingDTO) {
        return getBaseMapper().getUserSchedulingInfoData(infoSchedulingDTO);
    }
}




