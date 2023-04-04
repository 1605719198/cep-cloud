package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.domain.ProductionHandoverStockVerify;
import com.jlkj.product.oi.dto.productionHandoverStockVerify.PageProductionHandoverStockVerifyDTO;
import com.jlkj.product.oi.dto.productionHandoverStockVerify.UpdateProductionHandoverStockVerifyDTO;
import com.jlkj.product.oi.mapper.ProductionHandoverStockVerifyMapper;
import com.jlkj.product.oi.service.ProductionHandoverStockVerifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * @author yzl
 * @Description 交班仓存核验
 * @create 2022年7月26日 08:48:35
 */
@Service
@Slf4j
public class ProductionHandoverStockVerifyServiceImpl extends ServiceImpl<ProductionHandoverStockVerifyMapper, ProductionHandoverStockVerify>
        implements ProductionHandoverStockVerifyService {
    @Override
    public IPage<Map<String, String>> getListPage(PageProductionHandoverStockVerifyDTO pageProductionHandoverStockVerifyDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionHandoverStockVerifyDTO.getCurrent(), pageProductionHandoverStockVerifyDTO.getSize());
        return getBaseMapper().getListPage(page, pageProductionHandoverStockVerifyDTO);
    }

    public Object update(UpdateProductionHandoverStockVerifyDTO dto) {
        ProductionHandoverStockVerify productionHandoverStockVerify = getById(dto.getId());
        if (null != productionHandoverStockVerify) {
            productionHandoverStockVerify.setCoalBlendingLevel1(dto.getCoalBlendingLevel1());
            productionHandoverStockVerify.setCoalBlendingStock1(dto.getCoalBlendingStock1());
            productionHandoverStockVerify.setCoalBlendingLevel2(dto.getCoalBlendingLevel2());
            productionHandoverStockVerify.setCoalBlendingStock2(dto.getCoalBlendingStock2());
            productionHandoverStockVerify.setCoalBlendingLevel3(dto.getCoalBlendingLevel3());
            productionHandoverStockVerify.setCoalBlendingStock3(dto.getCoalBlendingStock3());
            productionHandoverStockVerify.setCoalBlendingLevel4(dto.getCoalBlendingLevel4());
            productionHandoverStockVerify.setCoalBlendingStock4(dto.getCoalBlendingStock4());
            productionHandoverStockVerify.setCoalBlendingLevel5(dto.getCoalBlendingLevel5());
            productionHandoverStockVerify.setCoalBlendingStock5(dto.getCoalBlendingStock5());
            productionHandoverStockVerify.setCoalBlendingLevel6(dto.getCoalBlendingLevel6());
            productionHandoverStockVerify.setCoalBlendingStock6(dto.getCoalBlendingStock6());
            productionHandoverStockVerify.setCoalBlendingLevel7(dto.getCoalBlendingLevel7());
            productionHandoverStockVerify.setCoalBlendingStock7(dto.getCoalBlendingStock7());
            productionHandoverStockVerify.setCoalBlendingLevel8(dto.getCoalBlendingLevel8());
            productionHandoverStockVerify.setCoalBlendingStock8(dto.getCoalBlendingStock8());
            productionHandoverStockVerify.setCoalBlendingLevel9(dto.getCoalBlendingLevel9());
            productionHandoverStockVerify.setCoalBlendingStock9(dto.getCoalBlendingStock9());
            productionHandoverStockVerify.setCoalBlendingLevel10(dto.getCoalBlendingLevel10());
            productionHandoverStockVerify.setCoalBlendingStock10(dto.getCoalBlendingStock10());
            productionHandoverStockVerify.setCoalStorageTowerLevel1(dto.getCoalStorageTowerLevel1());
            productionHandoverStockVerify.setCoalStorageTowerStock1(dto.getCoalStorageTowerStock1());
            productionHandoverStockVerify.setCoalStorageTowerLevel2(dto.getCoalStorageTowerLevel2());
            productionHandoverStockVerify.setCoalStorageTowerStock2(dto.getCoalStorageTowerStock2());
            productionHandoverStockVerify.setCoalStorageTowerLevel3(dto.getCoalStorageTowerLevel3());
            productionHandoverStockVerify.setCoalStorageTowerStock3(dto.getCoalStorageTowerStock3());
            productionHandoverStockVerify.setCoalStorageTowerLevel4(dto.getCoalStorageTowerLevel4());
            productionHandoverStockVerify.setCoalStorageTowerStock4(dto.getCoalStorageTowerStock4());
            productionHandoverStockVerify.setCoalStorageTowerLevel5(dto.getCoalStorageTowerLevel5());
            productionHandoverStockVerify.setCoalStorageTowerStock5(dto.getCoalStorageTowerStock5());
            productionHandoverStockVerify.setCoalStorageTowerLevel6(dto.getCoalStorageTowerLevel6());
            productionHandoverStockVerify.setCoalStorageTowerStock6(dto.getCoalStorageTowerStock6());

            BigDecimal coalBlendingStockTotal = dto.getCoalBlendingStock1().add(dto.getCoalBlendingStock2().add(dto.getCoalBlendingStock3().add(dto.getCoalBlendingStock4().add(dto.getCoalBlendingStock5().add(dto.getCoalBlendingStock6().add(dto.getCoalBlendingStock7().add(dto.getCoalBlendingStock8().add(dto.getCoalBlendingStock9().add(dto.getCoalBlendingStock10())))))))));
            BigDecimal coalStorageTowerStockTotal123 = dto.getCoalStorageTowerStock1().add(dto.getCoalStorageTowerStock2().add(dto.getCoalStorageTowerStock3()));
            BigDecimal coalStorageTowerStockTotal456 = dto.getCoalStorageTowerStock4().add(dto.getCoalStorageTowerStock5().add(dto.getCoalStorageTowerStock6()));

            productionHandoverStockVerify.setCoalBlendingStockTotal(coalBlendingStockTotal);
            productionHandoverStockVerify.setCoalStorageTowerStockTotal123(coalStorageTowerStockTotal123);
            productionHandoverStockVerify.setCoalStorageTowerStockTotal456(coalStorageTowerStockTotal456);

            productionHandoverStockVerify.setModifyUserId(dto.getModifyUserId());
            productionHandoverStockVerify.setModifyUserName(dto.getModifyUserName());
            productionHandoverStockVerify.setModifyTime(new Date());
            updateById(productionHandoverStockVerify);
            return AjaxResult.success("交班仓存核验修改成功");
        } else {
            return AjaxResult.error("交班仓存核验不存在");
        }
    }
}

