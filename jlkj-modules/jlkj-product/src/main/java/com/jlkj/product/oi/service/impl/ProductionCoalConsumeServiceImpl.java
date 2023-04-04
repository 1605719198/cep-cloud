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
import com.jlkj.product.oi.domain.ProductionCoalConsume;
import com.jlkj.product.oi.dto.productioncoalconsume.*;
import com.jlkj.product.oi.dto.sysdictdata.ListSysDictDataDTO;
import com.jlkj.product.oi.mapper.ProductionCoalConsumeMapper;
import com.jlkj.product.oi.service.ProductionCoalConsumeService;
import com.jlkj.product.oi.service.SysDictDataService;
import com.jlkj.product.oi.vo.productioncoalconsume.PageProductionCoalConsumeVO;
import com.jlkj.product.oi.vo.sysdictdata.ListSysDictDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.jlkj.product.oi.constants.RabbitConstant.MATERIAL_CONSUMPTION_ROUTE_KEY;
import static com.jlkj.product.oi.constants.RabbitConstant.MATERIAL_EXCHANGE;

/**
 * 服务实现-炼焦煤消耗维护
 *
 * @author sudeyou
 * @since 2022-11-03 17:10:54
 */
@Service
@Slf4j
public class ProductionCoalConsumeServiceImpl extends ServiceImpl<ProductionCoalConsumeMapper, ProductionCoalConsume>
    implements ProductionCoalConsumeService {

    @Resource
    private SysDictDataService sysDictDataService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    @Transactional(readOnly = true)
    public IPage<PageProductionCoalConsumeVO> getProductionCoalConsumePageData(PageProductionCoalConsumeDTO pageProductionCoalConsumeDTO) {
        Page<Map<String, Object>> page = new Page<>(pageProductionCoalConsumeDTO.getCurrent(), pageProductionCoalConsumeDTO.getSize());
        return getBaseMapper().getProductionCoalConsumePageData(page, pageProductionCoalConsumeDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object insertProductionCoalConsumeData(InsertProductionCoalConsumeDTO insertProductionCoalConsumeDTO) {
        List<ProductionCoalConsume> list = list(new QueryWrapper<ProductionCoalConsume>().lambda()
                .eq(ProductionCoalConsume::getAccountDate, insertProductionCoalConsumeDTO.getAccountDate())
                .eq(ProductionCoalConsume::getMaterialsCode, insertProductionCoalConsumeDTO.getMaterialsCode())
                .last(CommonConstant.LIMIT_ONE_ROW)
        );
        if (list.size() > 0) {
            return AjaxResult.error("炼焦煤消耗已存在");
        }
        ProductionCoalConsume productionCoalConsume = new ProductionCoalConsume();
        productionCoalConsume.setId(IdUtil.randomUUID());
        productionCoalConsume.setAccountDate(DateUtil.parseDate(insertProductionCoalConsumeDTO.getAccountDate()));
        productionCoalConsume.setMaterialsCode(insertProductionCoalConsumeDTO.getMaterialsCode());
        productionCoalConsume.setMaterialsName(insertProductionCoalConsumeDTO.getMaterialsName());
        productionCoalConsume.setConsumption(insertProductionCoalConsumeDTO.getConsumption());
        productionCoalConsume.setThrowState("0");
        productionCoalConsume.setCreateUserId(insertProductionCoalConsumeDTO.getCreateUserId());
        productionCoalConsume.setCreateUserName(insertProductionCoalConsumeDTO.getCreateUserName());
        productionCoalConsume.setCreateTime(new Date());
        productionCoalConsume.setModifyUserId(insertProductionCoalConsumeDTO.getCreateUserId());
        productionCoalConsume.setModifyUserName(insertProductionCoalConsumeDTO.getCreateUserName());
        productionCoalConsume.setModifyTime(new Date());
        save(productionCoalConsume);
        return AjaxResult.success("炼焦煤消耗增加成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object updateProductionCoalConsumeData(UpdateProductionCoalConsumeDTO updateProductionCoalConsumeDTO) {
        ProductionCoalConsume productionCoalConsume = getById(updateProductionCoalConsumeDTO.getId());
        if (null != productionCoalConsume) {
            List<ProductionCoalConsume> list = list(new QueryWrapper<ProductionCoalConsume>().lambda()
                    .eq(ProductionCoalConsume::getAccountDate, updateProductionCoalConsumeDTO.getAccountDate())
                    .eq(ProductionCoalConsume::getMaterialsCode, updateProductionCoalConsumeDTO.getMaterialsCode())
                    .ne(ProductionCoalConsume::getId, updateProductionCoalConsumeDTO.getId())
                    .last(CommonConstant.LIMIT_ONE_ROW)
            );
            if (list.size() > 0) {
                return AjaxResult.error("炼焦煤消耗已存在");
            }
            productionCoalConsume.setAccountDate(DateUtil.parseDate(updateProductionCoalConsumeDTO.getAccountDate()));
            productionCoalConsume.setMaterialsCode(updateProductionCoalConsumeDTO.getMaterialsCode());
            productionCoalConsume.setMaterialsName(updateProductionCoalConsumeDTO.getMaterialsName());
            productionCoalConsume.setConsumption(updateProductionCoalConsumeDTO.getConsumption());
            productionCoalConsume.setModifyUserId(updateProductionCoalConsumeDTO.getModifyUserId());
            productionCoalConsume.setModifyUserName(updateProductionCoalConsumeDTO.getModifyUserName());
            productionCoalConsume.setModifyTime(new Date());
            updateById(productionCoalConsume);
            return AjaxResult.success("炼焦煤消耗修改成功");
        }
        else {
            return AjaxResult.error( "炼焦煤消耗不存在");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object confirmProductionCoalConsumeData(ConfirmProductionCoalConsumeDTO confirmProductionCoalConsumeDTO) {
        List<ProductionCoalConsume> list = list(new QueryWrapper<ProductionCoalConsume>().lambda()
                .eq(ProductionCoalConsume::getAccountDate, confirmProductionCoalConsumeDTO.getAccountDate())
        );
        if (list.size() > 0) {
            sendQueueMessage(list);
        }
        return AjaxResult.success("炼焦煤消耗报文抛送成功");
    }

    private String getSeqNo(Integer no) {
        // 0 代表前面补充0
        // 5 代表长度为5
        // d 代表参数为正数型
        return String.format("%05d", no);
    }

    private void sendQueueMessage(List<ProductionCoalConsume> list) {
        String planListNo = "";
        String planListNoKey = "planListNo";
        String deptNo = "";
        String deptNoKey = "deptNo_j";
        String costCenter = "";
        String costCenterKey = "costCenter";
        String stgNoA = "";
        ListSysDictDataDTO listSysDictDataDTO = new ListSysDictDataDTO();
        listSysDictDataDTO.setDictType("MR58");
        List<ListSysDictDataVO> listDic = sysDictDataService.getSysDictDataListData(listSysDictDataDTO);
        for (ListSysDictDataVO listSysDictDataVO: listDic) {
            if (planListNoKey.equals(listSysDictDataVO.getDictLabel())) {
                planListNo = listSysDictDataVO.getDictValue();
            }
            if (deptNoKey.equals(listSysDictDataVO.getDictLabel())) {
                deptNo = listSysDictDataVO.getDictValue();
            }
            if (costCenterKey.equals(listSysDictDataVO.getDictLabel())) {
                costCenter = listSysDictDataVO.getDictValue();
            }
        }
        List<ProductionCoalConsume> listSend = list.stream().filter(productionCoalConsume -> "1".equals(productionCoalConsume.getThrowState())).collect(Collectors.toList());
        List<ProductionCoalConsume> listReady = list.stream().filter(productionCoalConsume -> "0".equals(productionCoalConsume.getThrowState())).collect(Collectors.toList());
        Integer no = listSend.size() + 1;
        for (ProductionCoalConsume productionCoalConsume: listReady) {
            for (ListSysDictDataVO listSysDictDataVO: listDic) {
                if (productionCoalConsume.getMaterialsCode().equals(listSysDictDataVO.getDictLabel())) {
                    stgNoA = listSysDictDataVO.getDictValue();
                    break;
                }
            }
            Map<String, Object> outMap = new HashMap<>(1);
            outMap.put("class", "com.icsc.mr.api.mrjc150Api");
            outMap.put("messageId", "MR58");
            outMap.put("actionCode", "N");
            outMap.put("dataSource", "cep");
            Map<String, Object> dataMap = new HashMap<>(1);
            dataMap.put("planListNo", planListNo);
            dataMap.put("conveyance", "C");
            dataMap.put("purposeId", "41");
            dataMap.put("reptDate", DateUtil.format(productionCoalConsume.getAccountDate(), "yyyyMMdd"));
            dataMap.put("deptNo", deptNo);
            dataMap.put("seqNo", DateUtil.format(DateUtil.date(), "yyyyMMddHHmm") + getSeqNo(no));
            dataMap.put("matrlnoA", productionCoalConsume.getMaterialsCode());
            dataMap.put("matrlnoB", "");
            dataMap.put("stgNoA", stgNoA);
            dataMap.put("stgNoB", "");
            dataMap.put("costCenter", costCenter);
            dataMap.put("qty", String.valueOf(productionCoalConsume.getConsumption()));
            dataMap.put("waterRate", "-1");
            outMap.put("data", dataMap);
            log.info("send mq message:{}:{} => {}", MATERIAL_EXCHANGE, MATERIAL_CONSUMPTION_ROUTE_KEY, JSONObject.toJSONString(outMap));
            rabbitTemplate.convertAndSend(MATERIAL_EXCHANGE, MATERIAL_CONSUMPTION_ROUTE_KEY, JSONObject.toJSONString(outMap));
            productionCoalConsume.setThrowState("1");
            updateById(productionCoalConsume);
            no++;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Object deleteProductionCoalConsumeData(DeleteProductionCoalConsumeDTO deleteProductionCoalConsumeDTO) {
        ProductionCoalConsume productionCoalConsume = getById(deleteProductionCoalConsumeDTO.getId());
        if (null != productionCoalConsume) {
            removeById(productionCoalConsume);
            return AjaxResult.success("炼焦煤消耗删除成功");
        }
        else {
            return AjaxResult.error("炼焦煤消耗不存在或已被删除");
        }
    }
}

