package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.constants.CommonConstant;
import com.jlkj.product.oi.domain.ProductionConveyingCoalActual;
import com.jlkj.product.oi.domain.ProductionConveyingCoalRecord;
import com.jlkj.product.oi.domain.ProductionPlanConfigCoke;
import com.jlkj.product.oi.dto.material.SendDTO;
import com.jlkj.product.oi.dto.productionconveyingcoalrecord.DeleteProductionConveyingCoalRecordDTO;
import com.jlkj.product.oi.dto.productionconveyingcoalrecord.InsertProductionConveyingCoalRecordDTO;
import com.jlkj.product.oi.dto.productionconveyingcoalrecord.PageProductionConveyingCoalRecordDTO;
import com.jlkj.product.oi.feignclients.MaterialsCoalStockFeignService;
import com.jlkj.product.oi.mapper.ProductionConveyingCoalRecordMapper;
import com.jlkj.product.oi.service.ProductionConveyingCoalActualService;
import com.jlkj.product.oi.service.ProductionConveyingCoalRecordService;
import com.jlkj.product.oi.service.ProductionPlanConfigCokeService;
import com.jlkj.product.oi.vo.productionconveyingcoalrecord.PageProductionConveyingCoalRecordVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 服务实现-生产实绩-上煤记录
 *
 * @author sudeyou
 * @since 2022-11-17 15:18:05
 */
@Service
public class ProductionConveyingCoalRecordServiceImpl extends ServiceImpl<ProductionConveyingCoalRecordMapper, ProductionConveyingCoalRecord>
    implements ProductionConveyingCoalRecordService {

    private static final String TEMP_TOKEN = "client-test";

    @Resource
    private ProductionConveyingCoalActualService productionConveyingCoalActualService;
    @Resource
    private MaterialsCoalStockFeignService materialsCoalStockFeignService;

    @Resource
    private ProductionPlanConfigCokeService productionPlanConfigCokeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertProductionConveyingCoalRecordData(InsertProductionConveyingCoalRecordDTO insertProductionConveyingCoalRecordDTO) {
        ProductionConveyingCoalRecord lastProductionConveyingCoalRecord = null;
        Map<String, Object> map = getMap(new QueryWrapper<ProductionConveyingCoalRecord>()
                .select("max(create_time) as create_time")
                .lambda()
                .eq(ProductionConveyingCoalRecord::getCokeWarehouseNumber, insertProductionConveyingCoalRecordDTO.getCokeWarehouseNumber())
        );
        if(null != map) {
            lastProductionConveyingCoalRecord = getOne(new QueryWrapper<ProductionConveyingCoalRecord>().lambda()
                    .eq(ProductionConveyingCoalRecord::getCokeWarehouseNumber, insertProductionConveyingCoalRecordDTO.getCokeWarehouseNumber())
                    .eq(ProductionConveyingCoalRecord::getCreateTime, map.get("create_time").toString())
                    .last(CommonConstant.LIMIT_ONE_ROW));
        }
        ProductionConveyingCoalRecord productionConveyingCoalRecord = new ProductionConveyingCoalRecord();
        productionConveyingCoalRecord.setId(IdUtil.randomUUID());
        productionConveyingCoalRecord.setShiftName(insertProductionConveyingCoalRecordDTO.getShiftName());
        productionConveyingCoalRecord.setClassName(insertProductionConveyingCoalRecordDTO.getClassName());
        productionConveyingCoalRecord.setConfigCokePlanId(insertProductionConveyingCoalRecordDTO.getConfigCokePlanId());
        productionConveyingCoalRecord.setCoalLevelMaterialsCode(insertProductionConveyingCoalRecordDTO.getCoalLevelMaterialsCode());
        productionConveyingCoalRecord.setCoalLevelMaterialsName(insertProductionConveyingCoalRecordDTO.getCoalLevelMaterialsName());
        productionConveyingCoalRecord.setCokeWarehouseNumber(insertProductionConveyingCoalRecordDTO.getCokeWarehouseNumber());
        productionConveyingCoalRecord.setCokeMaterialsCode(insertProductionConveyingCoalRecordDTO.getCokeMaterialsCode());
        productionConveyingCoalRecord.setCokeMaterialsName(insertProductionConveyingCoalRecordDTO.getCokeMaterialsName());
        productionConveyingCoalRecord.setStartTime(DateUtil.parseDateTime(insertProductionConveyingCoalRecordDTO.getStartTime()));
        productionConveyingCoalRecord.setEndTime(DateUtil.parseDateTime(insertProductionConveyingCoalRecordDTO.getEndTime()));
        productionConveyingCoalRecord.setCokeWeight(insertProductionConveyingCoalRecordDTO.getCokeWeight());
        productionConveyingCoalRecord.setCokeWeightBefor(null == lastProductionConveyingCoalRecord ? 0 : lastProductionConveyingCoalRecord.getCokeWeightAfter());
        productionConveyingCoalRecord.setCokeWeightAfter(insertProductionConveyingCoalRecordDTO.getCokeWeight() + productionConveyingCoalRecord.getCokeWeightBefor());
        productionConveyingCoalRecord.setCokeMaterialLevelBefor(insertProductionConveyingCoalRecordDTO.getCokeMaterialLevelBefor());
        productionConveyingCoalRecord.setCokeMaterialLevelAfter(insertProductionConveyingCoalRecordDTO.getCokeMaterialLevelAfter());
        productionConveyingCoalRecord.setDataSources(0);
        productionConveyingCoalRecord.setCreateUserId(insertProductionConveyingCoalRecordDTO.getCreateUserId());
        productionConveyingCoalRecord.setCreateUserName(insertProductionConveyingCoalRecordDTO.getCreateUserName());
        productionConveyingCoalRecord.setCreateTime(new Date());
        productionConveyingCoalRecord.setCokeMaterialsSmallCode(insertProductionConveyingCoalRecordDTO.getCokeMaterialsSmallCode());
        productionConveyingCoalRecord.setCokeMaterialsSmallName(insertProductionConveyingCoalRecordDTO.getCokeMaterialsSmallName());
        save(productionConveyingCoalRecord);
        updateCoalActual(productionConveyingCoalRecord);
        changePlanStatus(insertProductionConveyingCoalRecordDTO);
        SendDTO send = new SendDTO();
        send.setOrganizationCode(insertProductionConveyingCoalRecordDTO.getDepartmentId());
        send.setMatrlnoA(productionConveyingCoalRecord.getCokeMaterialsCode());
        send.setStgNoA("WK0100");
        send.setQty(insertProductionConveyingCoalRecordDTO.getCokeWeight().toString());
        List<Map<String,Object>> list =getBaseMapper().getQualityTsRecordByTeam(insertProductionConveyingCoalRecordDTO);
        send.setWaterRate(list.size() > 0 ? list.get(0).get("mt").toString() : "-1");
        materialsCoalStockFeignService.materialsCoalStockSend(send,TEMP_TOKEN);
        return AjaxResult.success("上煤记录增加成功");
    }
    private void updateCoalActual(ProductionConveyingCoalRecord productionConveyingCoalRecord) {
        ProductionConveyingCoalActual productionConveyingCoalActual = productionConveyingCoalActualService.getOne(new QueryWrapper<ProductionConveyingCoalActual>().lambda()
                .eq(ProductionConveyingCoalActual::getConfigCokePlanId, productionConveyingCoalRecord.getConfigCokePlanId())
                .eq(ProductionConveyingCoalActual::getShiftName, productionConveyingCoalRecord.getShiftName())
                .eq(ProductionConveyingCoalActual::getClassName, productionConveyingCoalRecord.getClassName())
                .eq(ProductionConveyingCoalActual::getCokeWarehouseNumber, productionConveyingCoalRecord.getCokeWarehouseNumber())
                .eq(ProductionConveyingCoalActual::getCoalLevelMaterialsCode, productionConveyingCoalRecord.getCoalLevelMaterialsCode())
                .eq(ProductionConveyingCoalActual::getCokeMaterialsCode, productionConveyingCoalRecord.getCokeMaterialsCode())
                .eq(ProductionConveyingCoalActual::getCokeMaterialsSmallCode, productionConveyingCoalRecord.getCokeMaterialsSmallCode())
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if(null == productionConveyingCoalActual) {
            productionConveyingCoalActual = new ProductionConveyingCoalActual();
            productionConveyingCoalActual.setId(IdUtil.randomUUID());
            productionConveyingCoalActual.setShiftName(productionConveyingCoalRecord.getShiftName());
            productionConveyingCoalActual.setClassName(productionConveyingCoalRecord.getClassName());
            productionConveyingCoalActual.setConfigCokePlanId(productionConveyingCoalRecord.getConfigCokePlanId());
            productionConveyingCoalActual.setCoalLevelMaterialsCode(productionConveyingCoalRecord.getCoalLevelMaterialsCode());
            productionConveyingCoalActual.setCoalLevelMaterialsName(productionConveyingCoalRecord.getCoalLevelMaterialsName());
            productionConveyingCoalActual.setCokeWarehouseNumber(productionConveyingCoalRecord.getCokeWarehouseNumber());
            productionConveyingCoalActual.setCokeMaterialsCode(productionConveyingCoalRecord.getCokeMaterialsCode());
            productionConveyingCoalActual.setCokeMaterialsName(productionConveyingCoalRecord.getCokeMaterialsName());
            productionConveyingCoalActual.setCokeWeight(productionConveyingCoalRecord.getCokeWeight());
            productionConveyingCoalActual.setCreateUserId(productionConveyingCoalRecord.getCreateUserId());
            productionConveyingCoalActual.setCreateUserName(productionConveyingCoalRecord.getCreateUserName());
            productionConveyingCoalActual.setCreateTime(new Date());
            productionConveyingCoalActual.setModifyUserId(productionConveyingCoalRecord.getCreateUserId());
            productionConveyingCoalActual.setModifyUserName(productionConveyingCoalRecord.getCreateUserName());
            productionConveyingCoalActual.setModifyTime(new Date());
            productionConveyingCoalActual.setCokeMaterialsSmallCode(productionConveyingCoalRecord.getCokeMaterialsSmallCode());
            productionConveyingCoalActual.setCokeMaterialsSmallName(productionConveyingCoalRecord.getCokeMaterialsSmallName());
            productionConveyingCoalActualService.save(productionConveyingCoalActual);
        }
        else {
            productionConveyingCoalActual.setCokeWeight(productionConveyingCoalActual.getCokeWeight() + productionConveyingCoalRecord.getCokeWeight());
            productionConveyingCoalActual.setModifyUserId(productionConveyingCoalRecord.getCreateUserId());
            productionConveyingCoalActual.setModifyUserName(productionConveyingCoalRecord.getCreateUserName());
            productionConveyingCoalActual.setModifyTime(new Date());
            productionConveyingCoalActualService.updateById(productionConveyingCoalActual);
        }
    }
    private void changePlanStatus(InsertProductionConveyingCoalRecordDTO insertProductionConveyingCoalRecordDTO) {
        ProductionPlanConfigCoke productionPlanConfigCoke = productionPlanConfigCokeService.getById(insertProductionConveyingCoalRecordDTO.getConfigCokePlanId());
        short confirmState = 2;
        Integer workState = 3;
        short finishState = 4;
        if(productionPlanConfigCoke.getPlanState() == confirmState) {
            productionPlanConfigCokeService.update(new UpdateWrapper<ProductionPlanConfigCoke>()
                    .lambda()
                    .set(ProductionPlanConfigCoke::getPlanState, finishState)
                    .set(ProductionPlanConfigCoke::getPlanEndTime, DateUtil.parseDateTime(insertProductionConveyingCoalRecordDTO.getStartTime()))
                    .eq(ProductionPlanConfigCoke::getPlanState, workState)
            );
            productionPlanConfigCoke.setPlanState(workState);
            productionPlanConfigCoke.setPlanStartTime(DateUtil.parseDateTime(insertProductionConveyingCoalRecordDTO.getStartTime()));
            productionPlanConfigCokeService.updateById(productionPlanConfigCoke);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteProductionConveyingCoalRecordData(DeleteProductionConveyingCoalRecordDTO deleteProductionConveyingCoalRecordDTO) {
        ProductionConveyingCoalRecord productionConveyingCoalRecord = getById(deleteProductionConveyingCoalRecordDTO.getId());
        if (null != productionConveyingCoalRecord) {
            if(productionConveyingCoalRecord.getDataSources() == 1) {
                return AjaxResult.error("上煤记录为自动采集，不能删除");
            }
            if(!productionConveyingCoalRecord.getCreateUserId().equals(deleteProductionConveyingCoalRecordDTO.getDeleteUserId())) {
                return AjaxResult.error("非本人创建的记录，不能删除");
            }

            ProductionConveyingCoalRecord lastProductionConveyingCoalRecord = null;
            Map<String, Object> map = getMap(new QueryWrapper<ProductionConveyingCoalRecord>()
                    .select("max(create_time) as create_time")
            );
            if(null != map) {
                lastProductionConveyingCoalRecord = getOne(new QueryWrapper<ProductionConveyingCoalRecord>().lambda()
                        .eq(ProductionConveyingCoalRecord::getCreateTime, map.get("create_time").toString())
                        .last(CommonConstant.LIMIT_ONE_ROW));
            }
            if(null != lastProductionConveyingCoalRecord && !lastProductionConveyingCoalRecord.getId().equals(deleteProductionConveyingCoalRecordDTO.getId())) {
                return AjaxResult.error("只能删除最后一条上煤记录");
            }
            List<ProductionConveyingCoalActual> list = productionConveyingCoalActualService.list(new QueryWrapper<ProductionConveyingCoalActual>().lambda()
                    .eq(ProductionConveyingCoalActual::getConfigCokePlanId, productionConveyingCoalRecord.getConfigCokePlanId())
                    .eq(ProductionConveyingCoalActual::getShiftName, productionConveyingCoalRecord.getShiftName())
                    .eq(ProductionConveyingCoalActual::getClassName, productionConveyingCoalRecord.getClassName())
                    .eq(ProductionConveyingCoalActual::getCokeWarehouseNumber, productionConveyingCoalRecord.getCokeWarehouseNumber())
                    .eq(ProductionConveyingCoalActual::getCoalLevelMaterialsCode, productionConveyingCoalRecord.getCoalLevelMaterialsCode())
                    .eq(ProductionConveyingCoalActual::getCokeMaterialsCode, productionConveyingCoalRecord.getCokeMaterialsCode())
                    .eq(ProductionConveyingCoalActual::getCokeWeight, productionConveyingCoalRecord.getCokeWeight())
            );
            if(list.size() > 0) {
                productionConveyingCoalActualService.remove(new QueryWrapper<ProductionConveyingCoalActual>().lambda()
                        .eq(ProductionConveyingCoalActual::getConfigCokePlanId, productionConveyingCoalRecord.getConfigCokePlanId())
                        .eq(ProductionConveyingCoalActual::getShiftName, productionConveyingCoalRecord.getShiftName())
                        .eq(ProductionConveyingCoalActual::getClassName, productionConveyingCoalRecord.getClassName())
                        .eq(ProductionConveyingCoalActual::getCokeWarehouseNumber, productionConveyingCoalRecord.getCokeWarehouseNumber())
                        .eq(ProductionConveyingCoalActual::getCoalLevelMaterialsCode, productionConveyingCoalRecord.getCoalLevelMaterialsCode())
                        .eq(ProductionConveyingCoalActual::getCokeMaterialsCode, productionConveyingCoalRecord.getCokeMaterialsCode())
                        .eq(ProductionConveyingCoalActual::getCokeWeight, productionConveyingCoalRecord.getCokeWeight())
                );
            }
            else {
                productionConveyingCoalActualService.update(new UpdateWrapper<ProductionConveyingCoalActual>().lambda()
                        .setSql("coke_weight = coke_weight - " + productionConveyingCoalRecord.getCokeWeight().toString())
                        .eq(ProductionConveyingCoalActual::getConfigCokePlanId, productionConveyingCoalRecord.getConfigCokePlanId())
                        .eq(ProductionConveyingCoalActual::getShiftName, productionConveyingCoalRecord.getShiftName())
                        .eq(ProductionConveyingCoalActual::getClassName, productionConveyingCoalRecord.getClassName())
                        .eq(ProductionConveyingCoalActual::getCokeWarehouseNumber, productionConveyingCoalRecord.getCokeWarehouseNumber())
                        .eq(ProductionConveyingCoalActual::getCoalLevelMaterialsCode, productionConveyingCoalRecord.getCoalLevelMaterialsCode())
                        .eq(ProductionConveyingCoalActual::getCokeMaterialsCode, productionConveyingCoalRecord.getCokeMaterialsCode())
                );
            }
            removeById(productionConveyingCoalRecord);
            return AjaxResult.success("上煤记录删除成功");
        }
        else {
            return AjaxResult.error("上煤记录不存在或已被删除");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public IPage<PageProductionConveyingCoalRecordVO> getProductionConveyingCoalRecordPageData(PageProductionConveyingCoalRecordDTO pageProductionConveyingCoalRecordDTO) {
        Page<Map<String, Object>> page = new Page<>(pageProductionConveyingCoalRecordDTO.getCurrent(), pageProductionConveyingCoalRecordDTO.getSize());
        return getBaseMapper().getProductionConveyingCoalRecordPageData(page, pageProductionConveyingCoalRecordDTO);
    }
}
