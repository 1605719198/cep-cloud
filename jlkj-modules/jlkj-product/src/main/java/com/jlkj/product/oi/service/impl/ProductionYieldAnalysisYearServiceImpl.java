package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionYieldAnalysisYear;
import com.jlkj.product.oi.dto.productionyieldanalysisyear.ListProductionYieldAnalysisYearDTO;
import com.jlkj.product.oi.mapper.ProductionYieldAnalysisDateMapper;
import com.jlkj.product.oi.mapper.ProductionYieldAnalysisYearMapper;
import com.jlkj.product.oi.service.ProductionYieldAnalysisYearService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 服务实现-产量分析年表
 * @author sudeyou
 * @since 2022-07-18 15:47:11
 */
@Service
public class ProductionYieldAnalysisYearServiceImpl extends ServiceImpl<ProductionYieldAnalysisYearMapper, ProductionYieldAnalysisYear>
    implements ProductionYieldAnalysisYearService {

    @Resource
    private ProductionYieldAnalysisDateMapper productionYieldAnalysisDateMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getProductionYieldAnalysisYearList(ListProductionYieldAnalysisYearDTO listProductionYieldAnalysisYearDTO) {
        StringBuilder sqlCol = new StringBuilder();
        StringBuilder sqlMax = new StringBuilder();
        List<Map<String, Object>> listCol = productionYieldAnalysisDateMapper.getProductionYieldAnalysisColList();
        for (Map<String, Object> stringObjectMap : listCol) {
            sqlCol.append(", case when materials_name = '").append(stringObjectMap.get("materials_name").toString()).append("' then convert(production_plan, char) else '' end as `plan_").append(stringObjectMap.get("id").toString()).append("`").append(", case when materials_name = '").append(stringObjectMap.get("materials_name").toString()).append("' then convert(production_performance, char) else '' end as `performance_").append(stringObjectMap.get("id").toString()).append("`");

            sqlMax.append(", max(`plan_").append(stringObjectMap.get("id").toString()).append("`) as `plan_").append(stringObjectMap.get("id").toString()).append("`").append(", max(`performance_").append(stringObjectMap.get("id").toString()).append("`) as `performance_").append(stringObjectMap.get("id").toString()).append("`");
        }
        listProductionYieldAnalysisYearDTO.setCols(sqlCol.toString());
        listProductionYieldAnalysisYearDTO.setColsMax(sqlMax.toString());
        return getBaseMapper().getProductionYieldAnalysisYearList(listProductionYieldAnalysisYearDTO);
    }
}

