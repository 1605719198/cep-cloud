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
import com.jlkj.product.oi.domain.ProductionCokeOvens;
import com.jlkj.product.oi.domain.ProductionInformationConfiguration;
import com.jlkj.product.oi.domain.QrtzJobMessageHistory;
import com.jlkj.product.oi.dto.productioncokeovens.DeleteProductionCokeOvensDTO;
import com.jlkj.product.oi.dto.productioncokeovens.InsertProductionCokeOvensDTO;
import com.jlkj.product.oi.dto.productioncokeovens.PageProductionCokeOvensDTO;
import com.jlkj.product.oi.dto.productioncokeovens.UpdateProductionCokeOvensDTO;
import com.jlkj.product.oi.feignclients.LogisticsFeignService;
import com.jlkj.product.oi.mapper.ProductionCokeOvensMapper;
import com.jlkj.product.oi.service.ProductionCokeOvensService;
import com.jlkj.product.oi.service.ProductionInformationConfigurationService;
import com.jlkj.product.oi.service.QrtzJobMessageHistoryService;
import com.jlkj.product.oi.vo.productioncokeovens.ListProductionCoalConsumeVO;
import com.jlkj.product.oi.vo.productioncokeovens.ListProductionCokeOvensAccountVO;
import com.jlkj.product.oi.vo.productioncokeovens.PageProductionCokeOvensVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jlkj.product.oi.constants.RabbitConstant.*;

/**
 * 服务实现-每日推焦炉数维护
 *
 * @author sudeyou
 * @since 2022-10-18 14:50:42
 */
@Service
@Slf4j
public class ProductionCokeOvensServiceImpl extends ServiceImpl<ProductionCokeOvensMapper, ProductionCokeOvens>
    implements ProductionCokeOvensService {

    @Resource
    private ProductionInformationConfigurationService productionInformationConfigurationService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private QrtzJobMessageHistoryService qrtzJobMessageHistoryService;

    @Resource
    private LogisticsFeignService logisticsFeignService;

    @Override
    @Transactional(readOnly = true)
    public IPage<PageProductionCokeOvensVO> getProductionCokeOvensPageData(PageProductionCokeOvensDTO pageProductionCokeOvensDTO) {
        Page<Map<String, Object>> page = new Page<>(pageProductionCokeOvensDTO.getCurrent(), pageProductionCokeOvensDTO.getSize());
        return getBaseMapper().getProductionCokeOvensPageData(page, pageProductionCokeOvensDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertProductionCokeOvensData(InsertProductionCokeOvensDTO insertProductionCokeOvensDTO) {
        List<ProductionCokeOvens> list = list(new QueryWrapper<ProductionCokeOvens>().lambda()
                .eq(ProductionCokeOvens::getAccountDate, insertProductionCokeOvensDTO.getAccountDate())
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if (list.size() > 0) {
            return AjaxResult.error("每日推焦炉数已存在");
        }
        BigDecimal drySingleHoleWeight = new BigDecimal(0);
        BigDecimal wetSingleHoleWeight = new BigDecimal(0);
        List<ProductionInformationConfiguration> listConfiguration = productionInformationConfigurationService.list(new QueryWrapper<ProductionInformationConfiguration>().lambda()
                        .eq(ProductionInformationConfiguration::getMaterialsName, insertProductionCokeOvensDTO.getMaterialsName())
        );
        for (ProductionInformationConfiguration productionInformationConfiguration: listConfiguration) {
            if ("1".equals(productionInformationConfiguration.getQuenchingMethod())) {
                drySingleHoleWeight = productionInformationConfiguration.getSingleHoleWeight();
                wetSingleHoleWeight = productionInformationConfiguration.getSingleHoleWeight();
            }
        }
        ProductionCokeOvens productionCokeOvens = new ProductionCokeOvens();
        productionCokeOvens.setId(IdUtil.randomUUID());
        productionCokeOvens.setAccountDate(DateUtil.parseDate(insertProductionCokeOvensDTO.getAccountDate()));
        productionCokeOvens.setMaterialsCode(insertProductionCokeOvensDTO.getMaterialsCode());
        productionCokeOvens.setMaterialsName(insertProductionCokeOvensDTO.getMaterialsName());
        productionCokeOvens.setOutNum(insertProductionCokeOvensDTO.getOutNum());
        productionCokeOvens.setDryOutNum(insertProductionCokeOvensDTO.getDryOutNum());
        productionCokeOvens.setWetOutNum(insertProductionCokeOvensDTO.getWetOutNum());
        productionCokeOvens.setDryTheoreticalYield(BigDecimal.valueOf(insertProductionCokeOvensDTO.getDryOutNum()).multiply(drySingleHoleWeight).setScale(2, RoundingMode.CEILING));
        productionCokeOvens.setWetTheoreticalYield(BigDecimal.valueOf(insertProductionCokeOvensDTO.getWetOutNum()).multiply(wetSingleHoleWeight).setScale(2, RoundingMode.CEILING));
        productionCokeOvens.setTheoreticalYield(productionCokeOvens.getDryTheoreticalYield().add(productionCokeOvens.getWetTheoreticalYield()));
        productionCokeOvens.setThrowState("0");
        productionCokeOvens.setCreateUserId(insertProductionCokeOvensDTO.getCreateUserId());
        productionCokeOvens.setCreateUserName(insertProductionCokeOvensDTO.getCreateUserName());
        productionCokeOvens.setCreateTime(new Date());
        productionCokeOvens.setModifyUserId(insertProductionCokeOvensDTO.getCreateUserId());
        productionCokeOvens.setModifyUserName(insertProductionCokeOvensDTO.getCreateUserName());
        productionCokeOvens.setModifyTime(new Date());
        save(productionCokeOvens);
        return AjaxResult.success("每日推焦炉数增加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateProductionCokeOvensData(UpdateProductionCokeOvensDTO updateProductionCokeOvensDTO) {
        ProductionCokeOvens productionCokeOvens = getById(updateProductionCokeOvensDTO.getId());
        if (null != productionCokeOvens) {
            List<ProductionCokeOvens> list = list(new QueryWrapper<ProductionCokeOvens>().lambda()
                    .eq(ProductionCokeOvens::getAccountDate, updateProductionCokeOvensDTO.getAccountDate())
                    .ne(ProductionCokeOvens::getId, updateProductionCokeOvensDTO.getId())
                    .last(CommonConstant.LIMIT_ONE_ROW)
            );
            if (list.size() > 0) {
                return AjaxResult.error("每日推焦炉数已存在");
            }
            BigDecimal drySingleHoleWeight = new BigDecimal(0);
            BigDecimal wetSingleHoleWeight = new BigDecimal(0);
            List<ProductionInformationConfiguration> listConfiguration = productionInformationConfigurationService.list(new QueryWrapper<ProductionInformationConfiguration>().lambda()
                    .eq(ProductionInformationConfiguration::getMaterialsName, updateProductionCokeOvensDTO.getMaterialsName())
            );
            for (ProductionInformationConfiguration productionInformationConfiguration: listConfiguration) {
                if ("1".equals(productionInformationConfiguration.getQuenchingMethod())) {
                    drySingleHoleWeight = productionInformationConfiguration.getSingleHoleWeight();
                    wetSingleHoleWeight = productionInformationConfiguration.getSingleHoleWeight();
                }
            }
            productionCokeOvens.setAccountDate(DateUtil.parseDate(updateProductionCokeOvensDTO.getAccountDate()));
            productionCokeOvens.setMaterialsCode(updateProductionCokeOvensDTO.getMaterialsCode());
            productionCokeOvens.setMaterialsName(updateProductionCokeOvensDTO.getMaterialsName());
            productionCokeOvens.setOutNum(updateProductionCokeOvensDTO.getOutNum());
            productionCokeOvens.setDryOutNum(updateProductionCokeOvensDTO.getDryOutNum());
            productionCokeOvens.setWetOutNum(updateProductionCokeOvensDTO.getWetOutNum());
            productionCokeOvens.setDryTheoreticalYield(BigDecimal.valueOf(updateProductionCokeOvensDTO.getDryOutNum()).multiply(drySingleHoleWeight).setScale(2, RoundingMode.CEILING));
            productionCokeOvens.setWetTheoreticalYield(BigDecimal.valueOf(updateProductionCokeOvensDTO.getWetOutNum()).multiply(wetSingleHoleWeight).setScale(2, RoundingMode.CEILING));
            productionCokeOvens.setTheoreticalYield(productionCokeOvens.getDryTheoreticalYield().add(productionCokeOvens.getWetTheoreticalYield()));
            productionCokeOvens.setModifyUserId(updateProductionCokeOvensDTO.getModifyUserId());
            productionCokeOvens.setModifyUserName(updateProductionCokeOvensDTO.getModifyUserName());
            productionCokeOvens.setModifyTime(new Date());
            updateById(productionCokeOvens);
            return AjaxResult.success("每日推焦炉数修改成功");
        }
        else {
            return AjaxResult.error("每日推焦炉数不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmProductionCokeOvensData() {
        List<ListProductionCokeOvensAccountVO> list = getBaseMapper().getProductionCokeOvensAccountListData();
        if (list.size() > 0) {
            sendQueueMessage(list);
        }
        return AjaxResult.success("报文抛送成功");
    }

    private String getSeqNo(Integer no) {
        // 0 代表前面补充0
        // 5 代表长度为5
        // d 代表参数为正数型
        return String.format("%05d", no);
    }

    private void sendQueueMessage(List<ListProductionCokeOvensAccountVO> list) {
        for (ListProductionCokeOvensAccountVO productionCokeOvensAccountVO: list) {
            Map<String, Object> outMap = new HashMap<>(1);
            outMap.put("class", "com.icsc.oi.mq.oijcC01Listener");
            outMap.put("messageId", "OIC01");
            outMap.put("actionCode", "N");
            outMap.put("dataSource", "cep");
            Map<String, Object> dataMap = new HashMap<>(1);
            dataMap.put("castDate", productionCokeOvensAccountVO.getCastDate());
            dataMap.put("totalCoOutputA", productionCokeOvensAccountVO.getTotalCoOutputA());
            dataMap.put("ferroCoOutputA", productionCokeOvensAccountVO.getFerroCoOutputA());
            dataMap.put("totalCoOutputB", productionCokeOvensAccountVO.getTotalCoOutputB());
            dataMap.put("ferroCoOutputB", productionCokeOvensAccountVO.getFerroCoOutputB());
            dataMap.put("totalCoOutputC", productionCokeOvensAccountVO.getTotalCoOutputC());
            dataMap.put("ferroCoOutputC", productionCokeOvensAccountVO.getFerroCoOutputC());
            dataMap.put("nutCoOutput", productionCokeOvensAccountVO.getNutCoOutput());
            dataMap.put("coFinesOutput", productionCokeOvensAccountVO.getCoFinesOutput());
            dataMap.put("waterCoFinesOutput", 0);
            dataMap.put("sulgaoOutput", 0);
            dataMap.put("totalCoOuterA", 0);
            dataMap.put("ferroCoOuterA", 0);
            dataMap.put("totalCoOuterB", 0);
            dataMap.put("ferroCoOuterB", 0);
            dataMap.put("totalCoOuterC", 0);
            dataMap.put("ferroCoOuterC", 0);
            dataMap.put("nutCoOuter", 0);
            dataMap.put("coFinesOuter", 0);
            dataMap.put("waterCoFinesOuter", 0);
            dataMap.put("sulgaoOuter", 0);
            dataMap.put("totalCoStoreA", productionCokeOvensAccountVO.getTotalCoStoreA());
            dataMap.put("ferroCoStoreA", productionCokeOvensAccountVO.getFerroCoStoreA());
            dataMap.put("totalCoStoreB", productionCokeOvensAccountVO.getTotalCoStoreB());
            dataMap.put("ferroCoStoreB", productionCokeOvensAccountVO.getFerroCoStoreB());
            dataMap.put("totalCoStoreC", productionCokeOvensAccountVO.getTotalCoStoreC());
            dataMap.put("ferroCoStoreC", productionCokeOvensAccountVO.getFerroCoStoreC());
            dataMap.put("nutCoStore", productionCokeOvensAccountVO.getNutCoStore());
            dataMap.put("coFinesStore", productionCokeOvensAccountVO.getCoFinesStore());
            dataMap.put("waterCoFinesStore", productionCokeOvensAccountVO.getWaterCoFinesStore());
            dataMap.put("sulgaoStore", productionCokeOvensAccountVO.getSulgaoStore());
            dataMap.put("planStoveNumber", productionCokeOvensAccountVO.getPlanStoveNumber());
            dataMap.put("realStoveNumber", productionCokeOvensAccountVO.getRealStoveNumber());
            dataMap.put("dryStoveNumberA", productionCokeOvensAccountVO.getDryStoveNumberA());
            dataMap.put("moistStoveNumberA", productionCokeOvensAccountVO.getMoistStoveNumberA());
            dataMap.put("dryStoveNumberB", productionCokeOvensAccountVO.getDryStoveNumberB());
            dataMap.put("moistStoveNumberB", productionCokeOvensAccountVO.getMoistStoveNumberB());
            dataMap.put("dryStoveNumberC", productionCokeOvensAccountVO.getDryStoveNumberC());
            dataMap.put("moistStoveNumberC", productionCokeOvensAccountVO.getMoistStoveNumberC());
            dataMap.put("steamOutput", 0);
            dataMap.put("steamOutputer", 0);
            dataMap.put("steamStore", 0);
            outMap.put("data", dataMap);
            log.info("send mq message:{}:{} => {}", PRODUCT_EXCHANGE, PRODUCT_COKING_OUTPUT_AND_OUTSOURCING_ROUTE_KEY, JSONObject.toJSONString(outMap));
            rabbitTemplate.convertAndSend(PRODUCT_EXCHANGE, PRODUCT_COKING_OUTPUT_AND_OUTSOURCING_ROUTE_KEY, JSONObject.toJSONString(outMap));
            QrtzJobMessageHistory qrtzJobMessageHistory = new QrtzJobMessageHistory();
            qrtzJobMessageHistory.setId(IdUtil.randomUUID());
            qrtzJobMessageHistory.setJobId("com.jljl.system.job.ProductionCokeOvensJob");
            qrtzJobMessageHistory.setQueueId("erp.oi.oic01");
            qrtzJobMessageHistory.setSendTime(new Date());
            qrtzJobMessageHistory.setMessageContent(JSONObject.toJSONString(outMap));
            qrtzJobMessageHistory.setDescriptionContent("");
            qrtzJobMessageHistory.setSendType((short)1);
            qrtzJobMessageHistoryService.save(qrtzJobMessageHistory);
            sendCoalData();
        }
    }

    private void sendCoalData() {
        String waterRate = "-1";
///        CoalWaterRateVO coalWaterRateVO = getBaseMapper().getCoalWaterRateData();
///        if(null != coalWaterRateVO) {
///            waterRate = coalWaterRateVO.getMt();
///        }
        int no = 1;
        List<ListProductionCoalConsumeVO> listProductionCoalConsume = getBaseMapper().getProductionCoalConsumeListData();
        for (ListProductionCoalConsumeVO productionCoalConsume: listProductionCoalConsume) {
            Map<String, Object> outCoalMap = new HashMap<>(1);
            outCoalMap.put("class", "com.icsc.me.link.mejc150Api");
            outCoalMap.put("messageId", "ME58");
            outCoalMap.put("actionCode", "N");
            outCoalMap.put("dataSource", "cep");
            outCoalMap.put("data", logisticsFeignService.getLogisticsMrPlan(
                    DateUtil.format(DateUtil.date(), "yyyyMMdd"),
                    productionCoalConsume.getQty(),
                    "41",
                    DateUtil.format(DateUtil.date(), "yyyyMMddHHmm") + getSeqNo(no),
                    new BigDecimal(waterRate),
                    productionCoalConsume.getCoalMaterialsCode(),
                    "carrierType_C_" + productionCoalConsume.getCokeMaterialsCode(),
                    "clent-test"
            ));
            log.info("send mq message:{}:{} => {}", MATERIAL_EXCHANGE, COAL_CONSUMPTION_ROUTE_KEY, JSONObject.toJSONString(outCoalMap));
            rabbitTemplate.convertAndSend(MATERIAL_EXCHANGE, COAL_CONSUMPTION_ROUTE_KEY, JSONObject.toJSONString(outCoalMap));
            QrtzJobMessageHistory qrtzJobMessageHistory = new QrtzJobMessageHistory();
            qrtzJobMessageHistory.setId(IdUtil.randomUUID());
            qrtzJobMessageHistory.setJobId("com.jljl.system.job.ProductionCokeOvensJob");
            qrtzJobMessageHistory.setQueueId("cep.erp.me.me58");
            qrtzJobMessageHistory.setSendTime(new Date());
            qrtzJobMessageHistory.setMessageContent(JSONObject.toJSONString(outCoalMap));
            qrtzJobMessageHistory.setDescriptionContent("");
            qrtzJobMessageHistory.setSendType((short)1);
            qrtzJobMessageHistoryService.save(qrtzJobMessageHistory);
            no++;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteProductionCokeOvensData(DeleteProductionCokeOvensDTO deleteProductionCokeOvensDTO) {
        ProductionCokeOvens productionCokeOvens = getById(deleteProductionCokeOvensDTO.getId());
        if (null != productionCokeOvens) {
            removeById(productionCokeOvens);
            return AjaxResult.success("每日推焦炉数删除成功");
        }
        else {
            return AjaxResult.error("每日推焦炉数不存在或已被删除");
        }
    }
}

