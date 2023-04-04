package com.jlkj.product.oi.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.constants.CommonConstant;
import com.jlkj.product.oi.domain.*;
import com.jlkj.product.oi.dto.productioncokeshedoutput.*;
import com.jlkj.product.oi.feignclients.LogisticsFeignService;
import com.jlkj.product.oi.mapper.ProductionCokeShedOutputMapper;
import com.jlkj.product.oi.service.*;
import com.jlkj.product.oi.vo.productioncokeshedoutput.PageProductionCokeShedOutputVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.jlkj.product.oi.constants.RabbitConstant.MATERIAL_CONSUMPTION_ROUTE_KEY;
import static com.jlkj.product.oi.constants.RabbitConstant.MATERIAL_EXCHANGE;

/**
 * 服务实现-焦棚产量维护
 *
 * @author sudeyou
 * @since 2022-10-18 14:57:19
 */
@Service
@Slf4j
public class ProductionCokeShedOutputServiceImpl extends ServiceImpl<ProductionCokeShedOutputMapper, ProductionCokeShedOutput>
    implements ProductionCokeShedOutputService {

    @Resource
    private LogisticsFeignService logisticsFeignService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private MaterialsCokeStockService materialsCokeStockService;

    @Resource
    private MaterialsCokeDayStockService materialsCokeDayStockService;

    @Resource
    private MaterialsCodeService materialsCodeService;

    @Resource
    private MaterialsCategoryService materialsCategoryService;

    @Override
    @Transactional(readOnly = true)
    public IPage<PageProductionCokeShedOutputVO> getProductionCokeShedOutputPageData(PageProductionCokeShedOutputDTO pageProductionCokeShedOutputDTO) {
        Page<Map<String, Object>> page = new Page<>(pageProductionCokeShedOutputDTO.getCurrent(), pageProductionCokeShedOutputDTO.getSize());
        return getBaseMapper().getProductionCokeShedOutputPageData(page, pageProductionCokeShedOutputDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertProductionCokeShedOutputData(InsertProductionCokeShedOutputDTO insertProductionCokeShedOutputDTO) {
        List<ProductionCokeShedOutput> list = list(new QueryWrapper<ProductionCokeShedOutput>().lambda()
                .eq(ProductionCokeShedOutput::getAccountDate, insertProductionCokeShedOutputDTO.getAccountDate())
                .eq(ProductionCokeShedOutput::getMaterialsCode, insertProductionCokeShedOutputDTO.getMaterialsCode())
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if (list.size() > 0) {
            return AjaxResult.error("焦棚产量已存在");
        }
        ProductionCokeShedOutput productionCokeShedOutput = new ProductionCokeShedOutput();
        productionCokeShedOutput.setId(IdUtil.randomUUID());
        productionCokeShedOutput.setAccountDate(DateUtil.parseDate(insertProductionCokeShedOutputDTO.getAccountDate()));
        productionCokeShedOutput.setMaterialsCode(insertProductionCokeShedOutputDTO.getMaterialsCode());
        productionCokeShedOutput.setMaterialsName(insertProductionCokeShedOutputDTO.getMaterialsName());
        productionCokeShedOutput.setYield(insertProductionCokeShedOutputDTO.getYield());
        productionCokeShedOutput.setThrowState("0");
        productionCokeShedOutput.setCreateUserId(insertProductionCokeShedOutputDTO.getCreateUserId());
        productionCokeShedOutput.setCreateUserName(insertProductionCokeShedOutputDTO.getCreateUserName());
        productionCokeShedOutput.setCreateTime(new Date());
        productionCokeShedOutput.setModifyUserId(insertProductionCokeShedOutputDTO.getCreateUserId());
        productionCokeShedOutput.setModifyUserName(insertProductionCokeShedOutputDTO.getCreateUserName());
        productionCokeShedOutput.setModifyTime(new Date());
        save(productionCokeShedOutput);

        List<MaterialsCokeStock> materialsCokeStockList = materialsCokeStockService.list(new QueryWrapper<MaterialsCokeStock>().lambda()
                .eq(MaterialsCokeStock::getMaterialsId, insertProductionCokeShedOutputDTO.getMaterialsCode())
        );
        MaterialsCokeStock materialsCokeStock;
        if(materialsCokeStockList.size() > 0) {
            materialsCokeStock = materialsCokeStockList.get(0);
            materialsCokeStock.setInventory(materialsCokeStock.getInventory().add(new BigDecimal(insertProductionCokeShedOutputDTO.getYield())));
            materialsCokeStockService.updateById(materialsCokeStock);
        }
        else {
            List<MaterialsCode> materialsCodeList = materialsCodeService.list(new QueryWrapper<MaterialsCode>().lambda()
                    .eq(MaterialsCode::getMaterialsCode, insertProductionCokeShedOutputDTO.getMaterialsCode())
            );
            MaterialsCode materialsCode = materialsCodeList.get(0);
            MaterialsCategory materialsCategory = materialsCategoryService.getById(materialsCode.getCategoryId());
            materialsCokeStock = new MaterialsCokeStock();
            materialsCokeStock.setId(IdUtil.randomUUID());
            materialsCokeStock.setMaterialsId(insertProductionCokeShedOutputDTO.getMaterialsCode());
            materialsCokeStock.setMaterialsName(insertProductionCokeShedOutputDTO.getMaterialsName());
            materialsCokeStock.setCategoryId(materialsCode.getCategoryId());
            materialsCokeStock.setCategoryName(materialsCategory.getCategoryName());
            materialsCokeStock.setInventory(new BigDecimal(insertProductionCokeShedOutputDTO.getYield()));
            materialsCokeStock.setCreateTime(new Date());
            materialsCokeStockService.save(materialsCokeStock);
        }
        List<MaterialsCokeDayStock> materialsCokeDayStockList = materialsCokeDayStockService.list(new QueryWrapper<MaterialsCokeDayStock>().lambda()
                .eq(MaterialsCokeDayStock::getStatDate, DateUtil.today())
                .eq(MaterialsCokeDayStock::getMaterialsId, insertProductionCokeShedOutputDTO.getMaterialsCode())
        );
        MaterialsCokeDayStock materialsCokeDayStock;
        if(materialsCokeDayStockList.size() > 0) {
            materialsCokeDayStock = materialsCokeDayStockList.get(0);
            materialsCokeDayStock.setInventory(materialsCokeDayStock.getInventory().add(new BigDecimal(insertProductionCokeShedOutputDTO.getYield())));
            materialsCokeDayStockService.updateById(materialsCokeDayStock);
        }
        else {
            materialsCokeDayStock = new MaterialsCokeDayStock();
            materialsCokeDayStock.setId(IdUtil.randomUUID());
            materialsCokeDayStock.setMaterialsId(materialsCokeStock.getMaterialsId());
            materialsCokeDayStock.setMaterialsName(materialsCokeStock.getMaterialsName());
            materialsCokeDayStock.setCategoryId(materialsCokeStock.getCategoryId());
            materialsCokeDayStock.setCategoryName(materialsCokeStock.getCategoryName());
            materialsCokeDayStock.setInventory(materialsCokeStock.getInventory());
            materialsCokeDayStock.setStatDate(new Date());
            materialsCokeDayStock.setCreateTime(new Date());
            materialsCokeDayStockService.save(materialsCokeDayStock);
        }
        return AjaxResult.success("焦棚产量增加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateProductionCokeShedOutputData(UpdateProductionCokeShedOutputDTO updateProductionCokeShedOutputDTO) {
        ProductionCokeShedOutput productionCokeShedOutput = getById(updateProductionCokeShedOutputDTO.getId());
        if (null != productionCokeShedOutput) {
            List<ProductionCokeShedOutput> list = list(new QueryWrapper<ProductionCokeShedOutput>().lambda()
                    .eq(ProductionCokeShedOutput::getAccountDate, updateProductionCokeShedOutputDTO.getAccountDate())
                    .eq(ProductionCokeShedOutput::getMaterialsCode, updateProductionCokeShedOutputDTO.getMaterialsCode())
                    .ne(ProductionCokeShedOutput::getId, updateProductionCokeShedOutputDTO.getId())
                    .last(CommonConstant.LIMIT_ONE_ROW)
            );
            if (list.size() > 0) {
                return AjaxResult.error("焦棚产量已存在");
            }
            String status = "1";
            if(status.equals(productionCokeShedOutput.getThrowState())) {
                return AjaxResult.error("不能修改已抛送的焦棚产量记录");
            }
            List<MaterialsCokeStock> materialsCokeStockList = materialsCokeStockService.list(new QueryWrapper<MaterialsCokeStock>().lambda()
                    .eq(MaterialsCokeStock::getMaterialsId, productionCokeShedOutput.getMaterialsCode())
            );
            MaterialsCokeStock materialsCokeStock;
            if(materialsCokeStockList.size() > 0) {
                materialsCokeStock = materialsCokeStockList.get(0);
                materialsCokeStock.setInventory(materialsCokeStock.getInventory().subtract(new BigDecimal(productionCokeShedOutput.getYield())).add(new BigDecimal(updateProductionCokeShedOutputDTO.getYield())));
                materialsCokeStockService.updateById(materialsCokeStock);
            }
            else {
                List<MaterialsCode> materialsCodeList = materialsCodeService.list(new QueryWrapper<MaterialsCode>().lambda()
                        .eq(MaterialsCode::getMaterialsCode, productionCokeShedOutput.getMaterialsCode())
                );
                MaterialsCode materialsCode = materialsCodeList.get(0);
                MaterialsCategory materialsCategory = materialsCategoryService.getById(materialsCode.getCategoryId());
                materialsCokeStock = new MaterialsCokeStock();
                materialsCokeStock.setId(IdUtil.randomUUID());
                materialsCokeStock.setMaterialsId(productionCokeShedOutput.getMaterialsCode());
                materialsCokeStock.setMaterialsName(productionCokeShedOutput.getMaterialsName());
                materialsCokeStock.setCategoryId(materialsCode.getCategoryId());
                materialsCokeStock.setCategoryName(materialsCategory.getCategoryName());
                materialsCokeStock.setInventory(new BigDecimal(updateProductionCokeShedOutputDTO.getYield()).multiply(new BigDecimal("-1")));
                materialsCokeStock.setCreateTime(new Date());
                materialsCokeStockService.save(materialsCokeStock);
            }
            List<MaterialsCokeDayStock> materialsCokeDayStockList = materialsCokeDayStockService.list(new QueryWrapper<MaterialsCokeDayStock>().lambda()
                    .eq(MaterialsCokeDayStock::getStatDate, DateUtil.today())
                    .eq(MaterialsCokeDayStock::getMaterialsId, productionCokeShedOutput.getMaterialsCode())
            );
            MaterialsCokeDayStock materialsCokeDayStock;
            if(materialsCokeDayStockList.size() > 0) {
                materialsCokeDayStock = materialsCokeDayStockList.get(0);
                materialsCokeDayStock.setInventory(materialsCokeDayStock.getInventory().subtract(new BigDecimal(productionCokeShedOutput.getYield())).add(new BigDecimal(updateProductionCokeShedOutputDTO.getYield())));
                materialsCokeDayStockService.updateById(materialsCokeDayStock);
            }
            else {
                materialsCokeDayStock = new MaterialsCokeDayStock();
                materialsCokeDayStock.setId(IdUtil.randomUUID());
                materialsCokeDayStock.setMaterialsId(materialsCokeStock.getMaterialsId());
                materialsCokeDayStock.setMaterialsName(materialsCokeStock.getMaterialsName());
                materialsCokeDayStock.setCategoryId(materialsCokeStock.getCategoryId());
                materialsCokeDayStock.setCategoryName(materialsCokeStock.getCategoryName());
                materialsCokeDayStock.setInventory(materialsCokeStock.getInventory());
                materialsCokeDayStock.setStatDate(new Date());
                materialsCokeDayStock.setCreateTime(new Date());
                materialsCokeDayStockService.save(materialsCokeDayStock);
            }
            productionCokeShedOutput.setAccountDate(DateUtil.parseDate(updateProductionCokeShedOutputDTO.getAccountDate()));
            productionCokeShedOutput.setMaterialsCode(updateProductionCokeShedOutputDTO.getMaterialsCode());
            productionCokeShedOutput.setMaterialsName(updateProductionCokeShedOutputDTO.getMaterialsName());
            productionCokeShedOutput.setYield(updateProductionCokeShedOutputDTO.getYield());
            productionCokeShedOutput.setModifyUserId(updateProductionCokeShedOutputDTO.getModifyUserId());
            productionCokeShedOutput.setModifyUserName(updateProductionCokeShedOutputDTO.getModifyUserName());
            productionCokeShedOutput.setModifyTime(new Date());
            updateById(productionCokeShedOutput);
            return AjaxResult.success("焦棚产量修改成功");
        }
        else {
            return AjaxResult.error("焦棚产量不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmProductionCokeShedOutputData(ConfirmProductionCokeShedOutputDTO confirmProductionCokeShedOutputDTO) {
        List<ProductionCokeShedOutput> list = list(new QueryWrapper<ProductionCokeShedOutput>().lambda()
                .eq(ProductionCokeShedOutput::getAccountDate, confirmProductionCokeShedOutputDTO.getAccountDate())
        );
        if (list.size() > 0) {
            sendQueueMessage(list);
        }
        return AjaxResult.success("焦棚产量报文抛送成功");
    }

    private String getSeqNo(Integer no) {
        // 0 代表前面补充0
        // 5 代表长度为5
        // d 代表参数为正数型
        return String.format("%05d", no);
    }
    private void sendQueueMessage(List<ProductionCokeShedOutput> list) {
        List<ProductionCokeShedOutput> listSend = list.stream().filter(productionCokeShedOutput -> "1".equals(productionCokeShedOutput.getThrowState())).collect(Collectors.toList());
        List<ProductionCokeShedOutput> listReady = list.stream().filter(productionCokeShedOutput -> "0".equals(productionCokeShedOutput.getThrowState())).collect(Collectors.toList());
        Integer no = listSend.size() + 1;
        for (ProductionCokeShedOutput productionCokeShedOutput: listReady) {
            Map<String, Object> outMap = new HashMap<>(1);
            outMap.put("class", "com.icsc.mr.api.mrjc150Api");
            outMap.put("messageId", "MR58");
            outMap.put("actionCode", "N");
            outMap.put("dataSource", "cep");
            outMap.put("data", logisticsFeignService.getLogisticsMrPlan(
                    DateUtil.format(productionCokeShedOutput.getAccountDate(), "yyyyMMdd"),
                    new BigDecimal(productionCokeShedOutput.getYield()),
                    "11",
                    DateUtil.format(DateUtil.date(), "yyyyMMddHHmm") + getSeqNo(no),
                    new BigDecimal(-1),
                    productionCokeShedOutput.getMaterialsCode(),
                    "carrierType_C",
                    "clent-test"
            ));
            log.info("send mq message:{}:{} => {}", MATERIAL_EXCHANGE, MATERIAL_CONSUMPTION_ROUTE_KEY, JSONObject.toJSONString(outMap));
            rabbitTemplate.convertAndSend(MATERIAL_EXCHANGE, MATERIAL_CONSUMPTION_ROUTE_KEY, JSONObject.toJSONString(outMap));
            productionCokeShedOutput.setThrowState("1");
            updateById(productionCokeShedOutput);
            no++;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteProductionCokeShedOutputData(DeleteProductionCokeShedOutputDTO deleteProductionCokeShedOutputDTO) {
        ProductionCokeShedOutput productionCokeShedOutput = getById(deleteProductionCokeShedOutputDTO.getId());
        if (null != productionCokeShedOutput) {
            String status = "1";
            if(status.equals(productionCokeShedOutput.getThrowState())) {
                return AjaxResult.error("不能删除已抛送的焦棚产量记录");
            }
            List<MaterialsCokeStock> materialsCokeStockList = materialsCokeStockService.list(new QueryWrapper<MaterialsCokeStock>().lambda()
                    .eq(MaterialsCokeStock::getMaterialsId, productionCokeShedOutput.getMaterialsCode())
            );
            MaterialsCokeStock materialsCokeStock;
            if(materialsCokeStockList.size() > 0) {
                materialsCokeStock = materialsCokeStockList.get(0);
                materialsCokeStock.setInventory(materialsCokeStock.getInventory().subtract(new BigDecimal(productionCokeShedOutput.getYield())));
                materialsCokeStockService.updateById(materialsCokeStock);
            }
            else {
                List<MaterialsCode> materialsCodeList = materialsCodeService.list(new QueryWrapper<MaterialsCode>().lambda()
                        .eq(MaterialsCode::getMaterialsCode, productionCokeShedOutput.getMaterialsCode())
                );
                MaterialsCode materialsCode = materialsCodeList.get(0);
                MaterialsCategory materialsCategory = materialsCategoryService.getById(materialsCode.getCategoryId());
                materialsCokeStock = new MaterialsCokeStock();
                materialsCokeStock.setId(IdUtil.randomUUID());
                materialsCokeStock.setMaterialsId(productionCokeShedOutput.getMaterialsCode());
                materialsCokeStock.setMaterialsName(productionCokeShedOutput.getMaterialsName());
                materialsCokeStock.setCategoryId(materialsCode.getCategoryId());
                materialsCokeStock.setCategoryName(materialsCategory.getCategoryName());
                materialsCokeStock.setInventory(new BigDecimal(productionCokeShedOutput.getYield()).multiply(new BigDecimal("-1")));
                materialsCokeStock.setCreateTime(new Date());
                materialsCokeStockService.save(materialsCokeStock);
            }
            List<MaterialsCokeDayStock> materialsCokeDayStockList = materialsCokeDayStockService.list(new QueryWrapper<MaterialsCokeDayStock>().lambda()
                    .eq(MaterialsCokeDayStock::getStatDate, DateUtil.today())
                    .eq(MaterialsCokeDayStock::getMaterialsId, productionCokeShedOutput.getMaterialsCode())
            );
            MaterialsCokeDayStock materialsCokeDayStock;
            if(materialsCokeDayStockList.size() > 0) {
                materialsCokeDayStock = materialsCokeDayStockList.get(0);
                materialsCokeDayStock.setInventory(materialsCokeDayStock.getInventory().subtract(new BigDecimal(productionCokeShedOutput.getYield())));
                materialsCokeDayStockService.updateById(materialsCokeDayStock);
            }
            else {
                materialsCokeDayStock = new MaterialsCokeDayStock();
                materialsCokeDayStock.setId(IdUtil.randomUUID());
                materialsCokeDayStock.setMaterialsId(materialsCokeStock.getMaterialsId());
                materialsCokeDayStock.setMaterialsName(materialsCokeStock.getMaterialsName());
                materialsCokeDayStock.setCategoryId(materialsCokeStock.getCategoryId());
                materialsCokeDayStock.setCategoryName(materialsCokeStock.getCategoryName());
                materialsCokeDayStock.setInventory(materialsCokeStock.getInventory().multiply(new BigDecimal("-1")));
                materialsCokeDayStock.setStatDate(new Date());
                materialsCokeDayStock.setCreateTime(new Date());
                materialsCokeDayStockService.save(materialsCokeDayStock);
            }
            removeById(productionCokeShedOutput);
            return AjaxResult.success("焦棚产量删除成功");
        }
        else {
            return AjaxResult.error("焦棚产量不存在或已被删除");
        }
    }
}

