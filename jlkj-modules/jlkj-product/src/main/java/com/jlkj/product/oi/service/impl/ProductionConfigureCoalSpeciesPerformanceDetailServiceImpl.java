package com.jlkj.product.oi.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.utils.bean.BeanUtils;
import com.jlkj.product.oi.domain.ProductionConfigureCoalSpeciesPerformance;
import com.jlkj.product.oi.domain.ProductionConfigureCoalSpeciesPerformanceDetail;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformancedetail.PageProductionConfigureCoalSpeciesPerformanceDetailDTO;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformancedetail.SaveOrUpdateDTO;
import com.jlkj.product.oi.mapper.ProductionConfigureCoalSpeciesPerformanceDetailMapper;
import com.jlkj.product.oi.mapper.ProductionConfigureCoalSpeciesPerformanceMapper;
import com.jlkj.product.oi.service.ProductionConfigureCoalSpeciesPerformanceDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 服务实现-配煤实绩明细
 * @author sudeyou
 */
@Service
public class ProductionConfigureCoalSpeciesPerformanceDetailServiceImpl extends ServiceImpl<ProductionConfigureCoalSpeciesPerformanceDetailMapper, ProductionConfigureCoalSpeciesPerformanceDetail>
    implements ProductionConfigureCoalSpeciesPerformanceDetailService {

    @Resource
    ProductionConfigureCoalSpeciesPerformanceMapper configureCoalSpeciesPerformanceMapper;

    /**
     * 查询-分页-配煤实绩明细
     * @param pageProductionConfigureCoalSpeciesPerformanceDetailDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public IPage<Map<String, String>> getPageData(PageProductionConfigureCoalSpeciesPerformanceDetailDTO pageProductionConfigureCoalSpeciesPerformanceDetailDTO) {
        Page<Map<String, String>> page = new Page<>(pageProductionConfigureCoalSpeciesPerformanceDetailDTO.getCurrent(), pageProductionConfigureCoalSpeciesPerformanceDetailDTO.getSize());
        return getBaseMapper().getPageData(page, pageProductionConfigureCoalSpeciesPerformanceDetailDTO);
    }

    /**
     * 配煤实绩明细-新增
     * @param dto
     */
    @Override
    public void saveCustom(SaveOrUpdateDTO dto) {
        ProductionConfigureCoalSpeciesPerformanceDetail entity = new ProductionConfigureCoalSpeciesPerformanceDetail();
        BeanUtils.copyProperties(dto,entity);
        entity.setId(IdUtil.randomUUID());
        entity.setCreateTime(new Date());
        entity.setDataSources(0);
        save(entity);
        updateMainRecord(dto.getMain_id());
    }

    /**
     * 配煤实绩明细-修改
     * @param dto
     */
    @Override
    public void updateCustom(SaveOrUpdateDTO dto) {
        ProductionConfigureCoalSpeciesPerformanceDetail entity = getById(dto.getId());
        entity.setStartTime(dto.getStart_time());
        entity.setEndTime(dto.getEnd_time());
        entity.setDuration(dto.getDuration());
        entity.setMaterialWeight(dto.getMaterial_weight());
        entity.setMaterialNumber(dto.getMaterial_number());
        entity.setMaterialCode(dto.getMaterial_code());
        entity.setMaterialName(dto.getMaterial_name());
        entity.setModifyTime(new Date());
        save(entity);
        updateMainRecord(dto.getMain_id());
    }

    /**
     * 配煤实绩明细-删除
     * @param dto
     * @return
     */
    @Override
    public void del(SaveOrUpdateDTO dto) {
        removeById(dto.getId());
        updateMainRecord(dto.getMain_id());
    }

    public void updateMainRecord(String mainId) {
        Map<String, Object> map = getMap(new QueryWrapper<ProductionConfigureCoalSpeciesPerformanceDetail>()
                .select("sum(material_weight) as material_weight, sum(duration) as duration")
                .lambda()
                .eq(ProductionConfigureCoalSpeciesPerformanceDetail::getMainId, mainId));
        BigDecimal coalWeight = new BigDecimal("0.00");
        BigDecimal duration = new BigDecimal("0.00");
        String materialWeight = "material_weight";
        if (map.containsKey(materialWeight) && ObjectUtil.isNotNull(map.get(materialWeight))) {
            coalWeight = new BigDecimal(map.get(materialWeight).toString());
        }
        String durationStr = "duration";
        if (map.containsKey(durationStr) && ObjectUtil.isNotNull(map.get(durationStr))) {
            duration = new BigDecimal(map.get(durationStr).toString());
        }
        ProductionConfigureCoalSpeciesPerformance performance = configureCoalSpeciesPerformanceMapper.selectById(mainId);
        performance.setCoalTowerWeight(coalWeight);
        performance.setDuration(duration);
        configureCoalSpeciesPerformanceMapper.updateById(performance);
    }
}

