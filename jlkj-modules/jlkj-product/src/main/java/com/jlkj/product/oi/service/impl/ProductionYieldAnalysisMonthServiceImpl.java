package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionYieldAnalysisMonth;
import com.jlkj.product.oi.dto.productionyieldanalysismonth.ListProductionYieldAnalysisMonthDTO;
import com.jlkj.product.oi.dto.productionyieldanalysismonth.ListProductionYieldAnalysisMonthMaterialDTO;
import com.jlkj.product.oi.mapper.ProductionYieldAnalysisDateMapper;
import com.jlkj.product.oi.mapper.ProductionYieldAnalysisMonthMapper;
import com.jlkj.product.oi.service.ProductionYieldAnalysisMonthService;
import com.jlkj.product.oi.vo.productionyieldanalysismonth.ListProductionYieldAnalysisMonthMaterialVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 服务实现-产量分析月表
 * @author sudeyou
 * @since 2022-07-15 15:04:00
 */
@Service
public class ProductionYieldAnalysisMonthServiceImpl extends ServiceImpl<ProductionYieldAnalysisMonthMapper, ProductionYieldAnalysisMonth>
    implements ProductionYieldAnalysisMonthService {

    @Resource
    private ProductionYieldAnalysisDateMapper productionYieldAnalysisDateMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getProductionYieldAnalysisMonthList(ListProductionYieldAnalysisMonthDTO listProductionYieldAnalysisMonthDTO) {
        String qjItemId = "";
        String yjjItemId = "";
        StringBuilder sqlCol = new StringBuilder();
        StringBuilder sqlMax = new StringBuilder();
        List<Map<String, Object>> listCol = productionYieldAnalysisDateMapper.getProductionYieldAnalysisColList();
        for (Map<String, Object> stringObjectMap : listCol) {
            if("全焦".equals(stringObjectMap.get("materials_name").toString())) {
                qjItemId = stringObjectMap.get("id").toString();
            }
            if("冶金焦".equals(stringObjectMap.get("materials_name").toString())) {
                yjjItemId = stringObjectMap.get("id").toString();
            }
            sqlCol.append(", case when materials_name = '").append(stringObjectMap.get("materials_name").toString()).append("' then convert(production_plan, char) else '' end as `plan_").append(stringObjectMap.get("id").toString()).append("`").append(", case when materials_name = '").append(stringObjectMap.get("materials_name").toString()).append("' then convert(production_performance, char) else '' end as `performance_").append(stringObjectMap.get("id").toString()).append("`");

            sqlMax.append(", max(`plan_").append(stringObjectMap.get("id").toString()).append("`) as `plan_").append(stringObjectMap.get("id").toString()).append("`").append(", max(`performance_").append(stringObjectMap.get("id").toString()).append("`) as `performance_").append(stringObjectMap.get("id").toString()).append("`");
        }
        listProductionYieldAnalysisMonthDTO.setCols(sqlCol.toString());
        listProductionYieldAnalysisMonthDTO.setColsMax(sqlMax.toString());
        listProductionYieldAnalysisMonthDTO.setQjItemId(qjItemId);
        listProductionYieldAnalysisMonthDTO.setYjjItemId(yjjItemId);
        return getBaseMapper().getProductionYieldAnalysisMonthList(listProductionYieldAnalysisMonthDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListProductionYieldAnalysisMonthMaterialVO> getProductionYieldAnalysisChartMonth(ListProductionYieldAnalysisMonthMaterialDTO listProductionYieldAnalysisMonthMaterialDTO) {
        return getBaseMapper().getProductionYieldAnalysisChartMonth(listProductionYieldAnalysisMonthMaterialDTO);
    }
}

