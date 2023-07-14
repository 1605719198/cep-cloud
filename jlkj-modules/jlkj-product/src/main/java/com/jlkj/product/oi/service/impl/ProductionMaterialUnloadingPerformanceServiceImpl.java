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
import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.product.oi.domain.*;
import com.jlkj.product.oi.dto.changelog.InsertChangeLogDTO;
import com.jlkj.product.oi.dto.productionmaterialunloadingperformance.AddProductionMaterialUnloadingPerformanceDTO;
import com.jlkj.product.oi.dto.productionmaterialunloadingperformance.GetProductionMaterialUnloadingPerformanceDTO;
import com.jlkj.product.oi.dto.productionmaterialunloadingperformance.UpdateProductionMaterialUnloadingPerformanceDTO;
import com.jlkj.product.oi.dto.productionmaterialunloadingperformance.UpdateProductionMaterialUnloadingPerformanceWeightDTO;
import com.jlkj.product.oi.mapper.MaterialsCoalStockMapper;
import com.jlkj.product.oi.mapper.ProductionMaterialUnloadingPerformanceMapper;
import com.jlkj.product.oi.service.ChangeLogService;
import com.jlkj.product.oi.service.MaterialsCoalDayStockService;
import com.jlkj.product.oi.service.MaterialsCodeSmallService;
import com.jlkj.product.oi.service.ProductionMaterialUnloadingPerformanceService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

import static com.jlkj.product.oi.constants.RedissonUtil.getLock;

/**
*@description: 针对表【product_oi_material_unloading_performance(物料卸车实绩)】的数据库操作Service实现
*@Author: 265823
*@date: 2023/7/10 16:51
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
    @Resource
    private MaterialsCodeSmallService materialsCodeSmallService;
    /**
     * 物料卸货实绩查询
     * @param dto
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public IPage<Map<String, Object>> get(GetProductionMaterialUnloadingPerformanceDTO dto) {
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
                .like(!StrUtil.isEmpty(dto.getSupplierName()), ProductionMaterialUnloadingPerformance::getSupplierName, dto.getSupplierName())
                .isNull(dto.getIsDone() == 1, ProductionMaterialUnloadingPerformance::getUnloadingTime)
                .isNotNull(dto.getIsDone() == 2, ProductionMaterialUnloadingPerformance::getUnloadingTime);
        Page<Map<String, Object>> page = new Page<>(dto.getCurrent(), dto.getSize());
        IPage<Map<String, Object>> list = pageMaps(page, queryWrapper);
        return list;
    }

    /**
     * 物料卸货实绩新增
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveCustom(AddProductionMaterialUnloadingPerformanceDTO dto) {
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
            //0609新增 start
            unloadingPerformance.setSupplierNumber(dto.getSupplierNumber());
            unloadingPerformance.setSupplierName(dto.getSupplierName());
            unloadingPerformance.setFromCode(dto.getFromCode());
            unloadingPerformance.setFromDecs(dto.getFromDecs());
            ////0609新增 end            unloadingPerformance.setShippingMethod(dto.getShippingMethod());
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
            throw new ServiceException(e.getMessage());
        } finally {
            rLock.unlock();
        }
    }

    /**
     * 物料卸货实绩修改
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCustom(UpdateProductionMaterialUnloadingPerformanceDTO dto) {
        RLock rLock = redissonClient.getLock(getLock("updateMaterialUnloadingPerformance", dto.getId()));
        rLock.lock();
        try {
            Date now = DateUtil.date();
            ProductionMaterialUnloadingPerformance unloadingPerformance = getById(dto.getId());
            Date unloadingTime = unloadingPerformance.getUnloadingTime();
            String storageSpacesId = unloadingPerformance.getStorageMaintainId();

            boolean noTime = false;
            if (unloadingPerformance == null) {
                throw new ServiceException("当前卸车记录不存在或已删除");
            }
            if (Objects.isNull(unloadingPerformance.getUnloadingTime())) {
                noTime = true;
            }
            List<MaterialsCodeSmall> codes =materialsCodeSmallService.list(new LambdaQueryWrapper<MaterialsCodeSmall>()
                    .eq(MaterialsCodeSmall::getVendorNo, unloadingPerformance.getSupplierNumber())
                    .eq(MaterialsCodeSmall::getMaterialsCode, unloadingPerformance.getMaterialCode())
                    .eq(MaterialsCodeSmall::getSendVendorNo, unloadingPerformance.getFromCode())
                    .eq(MaterialsCodeSmall::getDeleteFlag, 0)
            );
            if (codes.size() == 0) {
                throw new ServiceException("当前卸车记录信息未能匹配到对应的小煤种信息");
            }
            String materialsSmallCode = codes.get(0).getMaterialsSmallCode();
            String materialsSmallName = codes.get(0).getMaterialsSmallName();

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
                        .eq(MaterialsCoalStock::getMaterialsSmallCode, materialsSmallCode));
                BigDecimal stock = materialsCoalStock.getInventory().add(unloadingPerformance.getMaterialWeight());
                materialsCoalStock.setInventory(stock);
                coalStockMapper.updateById(materialsCoalStock);
                List<MaterialsCoalDayStock> materialsCoalDayStockList = materialsCoalDayStockService.list(new QueryWrapper<MaterialsCoalDayStock>().lambda()
                        .eq(MaterialsCoalDayStock::getStatDate, dto.getUnloadingTime().substring(0, 10))
                        .eq(MaterialsCoalDayStock::getMaterialsSmallCode, materialsSmallCode)
                );
                MaterialsCoalDayStock materialsCoalDayStock;
                if (materialsCoalDayStockList.size() > 0) {
                    materialsCoalDayStock = materialsCoalDayStockList.get(0);
                    materialsCoalDayStock.setInventory(materialsCoalDayStock.getInventory().add(unloadingPerformance.getMaterialWeight()));
                    materialsCoalDayStockService.updateById(materialsCoalDayStock);
                }
            }else {
                if (!DateUtil.formatDate(unloadingTime).equals(dto.getUnloadingTime().substring(0,10))) {

                    List<MaterialsCoalDayStock> materialsCoalDayStockList = materialsCoalDayStockService.list(new QueryWrapper<MaterialsCoalDayStock>().lambda()
                            .eq(MaterialsCoalDayStock::getStatDate, dto.getUnloadingTime().substring(0,10))
                            .eq(MaterialsCoalDayStock::getMaterialsSmallCode, materialsSmallCode)
                    );
                    MaterialsCoalDayStock materialsCoalDayStock;
                    if (materialsCoalDayStockList.size() > 0) {
                        materialsCoalDayStock = materialsCoalDayStockList.get(0);
                        materialsCoalDayStock.setInventory(materialsCoalDayStock.getInventory().add(unloadingPerformance.getMaterialWeight()));
                        materialsCoalDayStockService.updateById(materialsCoalDayStock);
                    }

                    List<MaterialsCoalDayStock> materialsCoalDayStockOldList = materialsCoalDayStockService.list(new QueryWrapper<MaterialsCoalDayStock>().lambda()
                            .eq(MaterialsCoalDayStock::getStatDate, DateUtil.formatDate(unloadingTime))
                            .eq(MaterialsCoalDayStock::getMaterialsSmallCode, materialsSmallCode)
                    );
                    MaterialsCoalDayStock materialsCoalDayOldStock;
                    materialsCoalDayOldStock = materialsCoalDayStockOldList.get(0);
                    materialsCoalDayOldStock.setInventory(materialsCoalDayOldStock.getInventory().subtract(unloadingPerformance.getMaterialWeight()));
                    materialsCoalDayStockService.updateById(materialsCoalDayOldStock);
                }
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException(e.getMessage());
        } finally {
            rLock.unlock();
        }
    }

    /**
     * 物料卸货实绩修改重量
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateMaterialUnloadingPerformanceWeight(UpdateProductionMaterialUnloadingPerformanceWeightDTO dto) {
        RLock rLock = redissonClient.getLock(getLock("updateMaterialUnloadingPerformanceWeight", dto.getPlanListNo()));
        rLock.lock();
        try {
            ProductionMaterialUnloadingPerformance unloadingPerformance = lambdaQuery()
                    .eq(ProductionMaterialUnloadingPerformance::getPlanId, dto.getPlanListNo()).one();
            if (unloadingPerformance == null) {
                throw new ServiceException("当前卸车记录不存在或已删除");
            }
            unloadingPerformance.setStorageSpacesId(dto.getStgNo());
            unloadingPerformance.setStorageSpacesName(dto.getStgName());
            unloadingPerformance.setChkNo(dto.getChkNo());
            unloadingPerformance.setRecvDate(DateUtil.parse(dto.getRecvDate()));
            updateById(unloadingPerformance);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new ServiceException(e.getMessage());
        } finally {
            rLock.unlock();
        }
    }

    /**
     * 物料卸货实绩删除重量
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void del(AddProductionMaterialUnloadingPerformanceDTO dto) {
        ProductionMaterialUnloadingPerformance unloadingPerformance = getOne(new LambdaQueryWrapper<ProductionMaterialUnloadingPerformance>()
                .eq(ProductionMaterialUnloadingPerformance::getPlanId, dto.getPlanId()));
        if (ObjectUtil.isNotNull(unloadingPerformance)) {
            removeById(unloadingPerformance);
        }
    }
}




