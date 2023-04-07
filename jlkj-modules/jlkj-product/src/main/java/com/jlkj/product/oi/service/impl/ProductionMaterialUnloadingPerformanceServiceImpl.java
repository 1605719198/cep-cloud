package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.MaterialsCoalDayStock;
import com.jlkj.product.oi.domain.MaterialsCoalStock;
import com.jlkj.product.oi.domain.MaterialsCode;
import com.jlkj.product.oi.domain.ProductionMaterialUnloadingPerformance;
import com.jlkj.product.oi.dto.changelog.InsertChangeLogDTO;
import com.jlkj.product.oi.dto.productionmaterialunloadingperformance.AddProductionMaterialUnloadingPerformanceDTO;
import com.jlkj.product.oi.dto.productionmaterialunloadingperformance.GetProductionMaterialUnloadingPerformanceDTO;
import com.jlkj.product.oi.dto.productionmaterialunloadingperformance.UpdateProductionMaterialUnloadingPerformanceDTO;
import com.jlkj.product.oi.dto.productionmaterialunloadingperformance.UpdateProductionMaterialUnloadingPerformanceWeightDTO;
import com.jlkj.product.oi.mapper.MaterialsCoalStockMapper;
import com.jlkj.product.oi.mapper.ProductionMaterialUnloadingPerformanceMapper;
import com.jlkj.product.oi.service.ChangeLogService;
import com.jlkj.product.oi.service.MaterialsCoalDayStockService;
import com.jlkj.product.oi.service.ProductionMaterialUnloadingPerformanceService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static com.jlkj.product.oi.constants.RedissonUtil.getLock;

/**
* @author zyf
* @description 针对表【product_oi_material_unloading_performance(物料卸车实绩)】的数据库操作Service实现
* @createDate 2022-05-10 08:43:09
*/
@Service
public class ProductionMaterialUnloadingPerformanceServiceImpl extends ServiceImpl<ProductionMaterialUnloadingPerformanceMapper, ProductionMaterialUnloadingPerformance>
    implements ProductionMaterialUnloadingPerformanceService {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    MaterialsCodeServiceImpl materialsCodeService;

    @Resource
    MaterialsCoalStockMapper coalStockMapper;

    @Resource
    private ChangeLogService changeLogService;

    @Resource
    private MaterialsCoalDayStockService materialsCoalDayStockService;


    public Object get(GetProductionMaterialUnloadingPerformanceDTO dto) {
        Date start = DateUtil.parse(StrUtil.isEmpty(dto.getUnloadingStart()) ? "1790-01-01" : dto.getUnloadingStart() + " 00:00:00");
        Date end = DateUtil.parse(StrUtil.isEmpty(dto.getUnloadingEnd()) ? "1790-01-01" : dto.getUnloadingEnd() + " 23:59:59");
        QueryWrapper<ProductionMaterialUnloadingPerformance> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderBy(true, "asc".equals(dto.getOrderby()), dto.getOrder())
                .lambda()
                .ge(!StrUtil.isEmpty(dto.getUnloadingStart()), ProductionMaterialUnloadingPerformance::getUnloadingTime, start)
                .le(!StrUtil.isEmpty(dto.getUnloadingEnd()), ProductionMaterialUnloadingPerformance::getUnloadingTime, end)
                .eq(!StrUtil.isEmpty(dto.getShiftName()), ProductionMaterialUnloadingPerformance::getShiftName, dto.getShiftName())
                .eq(!StrUtil.isEmpty(dto.getClassName()), ProductionMaterialUnloadingPerformance::getClassName, dto.getClassName())
                .eq(!StrUtil.isEmpty(dto.getShippingMethod()), ProductionMaterialUnloadingPerformance::getShippingMethod, dto.getShippingMethod())
                .eq(!StrUtil.isEmpty(dto.getMaterialCode()), ProductionMaterialUnloadingPerformance::getMaterialCode, dto.getMaterialCode())
                .isNull(dto.getIsDone() == 1, ProductionMaterialUnloadingPerformance::getUnloadingTime)
                .isNotNull(dto.getIsDone() == 2, ProductionMaterialUnloadingPerformance::getUnloadingTime);
        Page<Map<String, Object>> page = new Page<>(dto.getCurrent(), dto.getSize());
        IPage<Map<String, Object>> list = pageMaps(page, queryWrapper);
        return AjaxResult.success(list);
    }

    public Object save(AddProductionMaterialUnloadingPerformanceDTO dto) {
        RLock rLock = redissonClient.getLock(getLock("saveMaterialUnloadingPerformance", dto.getPlanId()));
        rLock.lock();
        try {
            Date now = DateUtil.date();
            MaterialsCode materialsCode = materialsCodeService.getOne(new LambdaQueryWrapper<MaterialsCode>()
                    .eq(MaterialsCode::getMaterialsCode, dto.getMaterialCode())
                    .eq(MaterialsCode::getDeleteFlag, 0));
            ProductionMaterialUnloadingPerformance unloadingPerformance = new ProductionMaterialUnloadingPerformance();
            unloadingPerformance.setId(IdUtil.randomUUID());
            unloadingPerformance.setPlanId(dto.getPlanId());
            unloadingPerformance.setPlanDate(DateUtil.parse(dto.getPlanDate()));
            unloadingPerformance.setSupplierName(dto.getSupplierName());
            unloadingPerformance.setShippingMethod(dto.getShippingMethod());
            unloadingPerformance.setVehicleNumber(dto.getVehicleNumber());
            if (materialsCode != null) {
                unloadingPerformance.setMaterialNumber(materialsCode.getId());
            }
            unloadingPerformance.setMaterialName(dto.getMaterialName());
            unloadingPerformance.setMaterialCode(dto.getMaterialCode());
            unloadingPerformance.setMaterialWeight(dto.getMaterialWeight());
            unloadingPerformance.setCreateTime(now);
            save(unloadingPerformance);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error(e.getMessage());
        } finally {
            rLock.unlock();
        }
        return AjaxResult.success();
    }

    public Object update(UpdateProductionMaterialUnloadingPerformanceDTO dto) {
        RLock rLock = redissonClient.getLock(getLock("updateMaterialUnloadingPerformance", dto.getId()));
        rLock.lock();
        try {
            Date now = DateUtil.date();
            ProductionMaterialUnloadingPerformance unloadingPerformance = getById(dto.getId());
            boolean noTime = false;
            if (unloadingPerformance == null) {
                return AjaxResult.error("当前卸车记录不存在或已删除");
            }
            if (Objects.isNull(unloadingPerformance.getUnloadingTime())) {
                noTime = true;
            }
            StringBuilder content = new StringBuilder();
            if (!(null != unloadingPerformance.getUnloadingTime() ?  DateUtil.formatDateTime(unloadingPerformance.getUnloadingTime()) : "").equals(dto.getUnloadingTime())) {
                content.append("[卸车时间：").append(null != unloadingPerformance.getUnloadingTime() ?  DateUtil.formatDateTime(unloadingPerformance.getUnloadingTime()) : "").append("->").append(dto.getUnloadingTime()).append("]");
            }
            if (!(null != unloadingPerformance.getStorageMaintainSpaceName() ? unloadingPerformance.getStorageMaintainSpaceName() : "").equals(dto.getStorageSpacesName())) {
                content.append("[储位：").append(null != unloadingPerformance.getStorageMaintainSpaceName() ? unloadingPerformance.getStorageMaintainSpaceName() : "").append("->").append(dto.getStorageSpacesName()).append("]");
            }
            if (content.length() > 0) {
                InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
                insertChangeLogDTO.setModuleName("物料管理");
                insertChangeLogDTO.setFunctionName("物料卸煤实绩");
                insertChangeLogDTO.setContent("修改：" + content);
                insertChangeLogDTO.setCreateUserId(dto.getUserId());
                insertChangeLogDTO.setCreateUserName(dto.getUserName());
                changeLogService.insertChangeLogData(insertChangeLogDTO);
            }
            Map<String, Object> param = new HashMap<>(1);
            param.put("now", DateUtil.parse(dto.getUnloadingTime()));
            Map<String, Object> vHumanresourceSchedulingWork = baseMapper.getShift(param);
            unloadingPerformance.setUnloadingTime(DateUtil.parse(dto.getUnloadingTime()));
            unloadingPerformance.setStorageMaintainId(dto.getStorageSpacesId());
            unloadingPerformance.setStorageMaintainSpaceName(dto.getStorageSpacesName());
            unloadingPerformance.setModifyUserId(dto.getUserId());
            unloadingPerformance.setModifyUserName(dto.getUserName());
            unloadingPerformance.setModifyTime(now);
            if (ObjectUtil.isNotNull(vHumanresourceSchedulingWork)) {
                unloadingPerformance.setShiftName(vHumanresourceSchedulingWork.get("shift").toString());
                unloadingPerformance.setClassName(vHumanresourceSchedulingWork.get("class_type").toString());
            }
            updateById(unloadingPerformance);
            if (noTime) {
                MaterialsCoalStock materialsCoalStock = coalStockMapper.selectOne(new LambdaQueryWrapper<MaterialsCoalStock>()
                        .eq(MaterialsCoalStock::getMaterialsId, unloadingPerformance.getMaterialCode()));
                BigDecimal stock = materialsCoalStock.getInventory().add(unloadingPerformance.getMaterialWeight());
                materialsCoalStock.setInventory(stock);
                coalStockMapper.updateById(materialsCoalStock);
                List<MaterialsCoalDayStock> materialsCoalDayStockList = materialsCoalDayStockService.list(new QueryWrapper<MaterialsCoalDayStock>().lambda()
                        .eq(MaterialsCoalDayStock::getStatDate, DateUtil.today())
                        .eq(MaterialsCoalDayStock::getMaterialsId, unloadingPerformance.getMaterialCode())
                );
                MaterialsCoalDayStock materialsCoalDayStock;
                if(materialsCoalDayStockList.size() > 0) {
                    materialsCoalDayStock = materialsCoalDayStockList.get(0);
                    materialsCoalDayStock.setInventory(materialsCoalDayStock.getInventory().add(unloadingPerformance.getMaterialWeight()));
                    materialsCoalDayStockService.updateById(materialsCoalDayStock);
                }
                else {
                    materialsCoalDayStock = new MaterialsCoalDayStock();
                    materialsCoalDayStock.setId(IdUtil.randomUUID());
                    materialsCoalDayStock.setMaterialsId(materialsCoalStock.getMaterialsId());
                    materialsCoalDayStock.setMaterialsName(materialsCoalStock.getMaterialsName());
                    materialsCoalDayStock.setCategoryId(materialsCoalStock.getCategoryId());
                    materialsCoalDayStock.setCategoryName(materialsCoalStock.getCategoryName());
                    materialsCoalDayStock.setStorageSpacesId(materialsCoalStock.getStorageSpacesId());
                    materialsCoalDayStock.setStorageSpacesName(materialsCoalStock.getStorageSpacesName());
                    materialsCoalDayStock.setTodayUnloadedWeight(new BigDecimal("0"));
                    materialsCoalDayStock.setTodayLoadingWeight(new BigDecimal("0"));
                    materialsCoalDayStock.setInventory(materialsCoalStock.getInventory());
                    materialsCoalDayStock.setStatDate(new Date());
                    materialsCoalDayStock.setCreateTime(new Date());
                    materialsCoalDayStockService.save(materialsCoalDayStock);
                }
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error(e.getMessage());
        } finally {
            rLock.unlock();
        }
        return AjaxResult.success();
    }

    public Object updateMaterialUnloadingPerformanceWeight(UpdateProductionMaterialUnloadingPerformanceWeightDTO dto) {
        RLock rLock = redissonClient.getLock(getLock("updateMaterialUnloadingPerformanceWeight", dto.getPlanListNo()));
        rLock.lock();
        try {
            ProductionMaterialUnloadingPerformance unloadingPerformance = lambdaQuery()
                    .eq(ProductionMaterialUnloadingPerformance::getPlanId, dto.getPlanListNo()).one();
            if (unloadingPerformance == null) {
                return AjaxResult.error("当前卸车记录不存在或已删除");
            }
            unloadingPerformance.setStorageSpacesId(dto.getStgNo());
            unloadingPerformance.setStorageSpacesName(dto.getStgName());
            unloadingPerformance.setChkNo(dto.getChkNo());
            unloadingPerformance.setRecvDate(DateUtil.parse(dto.getRecvDate()));
            updateById(unloadingPerformance);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AjaxResult.error(e.getMessage());
        } finally {
            rLock.unlock();
        }
        return AjaxResult.success();
    }

    public Object del(AddProductionMaterialUnloadingPerformanceDTO dto) {
        ProductionMaterialUnloadingPerformance unloadingPerformance = getOne(new LambdaQueryWrapper<ProductionMaterialUnloadingPerformance>()
                .eq(ProductionMaterialUnloadingPerformance::getPlanId, dto.getPlanId()));
        if (ObjectUtil.isNotNull(unloadingPerformance)) {
            removeById(unloadingPerformance);
        }
        return AjaxResult.success();
    }
}




