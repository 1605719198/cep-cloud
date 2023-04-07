package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
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
* @author zyf
* @description 针对表【product_oi_stacker_reclaimer_performance(堆取料机实绩)】的数据库操作Service实现
* @createDate 2022-05-11 09:37:46
*/
@Service
public class ProductionStackerReclaimerPerformanceServiceImpl extends ServiceImpl<ProductionStackerReclaimerPerformanceMapper, ProductionStackerReclaimerPerformance>
    implements ProductionStackerReclaimerPerformanceService {

    @Resource
    private MaterialsStorageSpacesService materialsStorageSpacesService;

    public Object getStackerReclaimerPerformance(GetProductionStackerReclaimerPerformanceDTO dto) {
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
        return AjaxResult.success(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertProductionStackerReclaimerPerformanceData(InsertProductionStackerReclaimerPerformanceDTO insertProductionStackerReclaimerPerformanceDTO) {
        MaterialsStorageSpaces materialsStorageSpaces = materialsStorageSpacesService.getById(insertProductionStackerReclaimerPerformanceDTO.getSlotNumber());
        if (null == materialsStorageSpaces) {
            return AjaxResult.error("储位数据不存在");
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
        return AjaxResult.success("堆取料机实绩增加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateProductionStackerReclaimerPerformanceData(UpdateProductionStackerReclaimerPerformanceDTO updateProductionStackerReclaimerPerformanceDTO) {
        ProductionStackerReclaimerPerformance productionStackerReclaimerPerformance = getById(updateProductionStackerReclaimerPerformanceDTO.getId());
        if (null != productionStackerReclaimerPerformance) {
            MaterialsStorageSpaces materialsStorageSpaces = materialsStorageSpacesService.getById(updateProductionStackerReclaimerPerformanceDTO.getSlotNumber());
            if (null == materialsStorageSpaces) {
                return AjaxResult.error("储位数据不存在");
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
            return AjaxResult.success("堆取料机实绩修改成功");
        }
        else {
            return AjaxResult.error("堆取料机实绩不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteProductionStackerReclaimerPerformanceData(DeleteProductionStackerReclaimerPerformanceDTO deleteProductionStackerReclaimerPerformanceDTO) {
        ProductionStackerReclaimerPerformance productionStackerReclaimerPerformance = getById(deleteProductionStackerReclaimerPerformanceDTO.getId());
        if (null != productionStackerReclaimerPerformance) {
            removeById(productionStackerReclaimerPerformance);
            return AjaxResult.success("堆取料机实绩删除成功");
        }
        else {
            return AjaxResult.error("堆取料机实绩不存在或已被删除");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public InfoSchedulingVO getUserSchedulingInfoData(InfoSchedulingDTO infoSchedulingDTO) {
        return getBaseMapper().getUserSchedulingInfoData(infoSchedulingDTO);
    }
}




