package com.jlkj.product.oi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.product.oi.domain.ProductionYieldAnalysisDate;
import com.jlkj.product.oi.dto.home.HomeDTO;
import com.jlkj.product.oi.dto.productionyieldanalysisdate.ListProductionYieldAnalysisDateDTO;
import com.jlkj.product.oi.dto.productionyieldanalysisdate.ListProductionYieldAnalysisDateMaterialDTO;
import com.jlkj.product.oi.mapper.ProductionYieldAnalysisDateMapper;
import com.jlkj.product.oi.service.ProductionYieldAnalysisDateService;
import com.jlkj.product.oi.vo.home.*;
import com.jlkj.product.oi.vo.productionyieldanalysisdate.ListProductionYieldAnalysisDateMaterialVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 服务实现-产量分析日表
 * @author sudeyou
 * @since 2022-07-15 14:27:41
 */
@Service
public class ProductionYieldAnalysisDateServiceImpl extends ServiceImpl<ProductionYieldAnalysisDateMapper, ProductionYieldAnalysisDate>
    implements ProductionYieldAnalysisDateService {

    @Resource
    private ProductionYieldAnalysisDateMapper productionYieldAnalysisDateMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getProductionYieldAnalysisDateList(ListProductionYieldAnalysisDateDTO listProductionYieldAnalysisDateDTO) {
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
        listProductionYieldAnalysisDateDTO.setCols(sqlCol.toString());
        listProductionYieldAnalysisDateDTO.setColsMax(sqlMax.toString());
        listProductionYieldAnalysisDateDTO.setQjItemId(qjItemId);
        listProductionYieldAnalysisDateDTO.setYjjItemId(yjjItemId);
        return getBaseMapper().getProductionYieldAnalysisDateList(listProductionYieldAnalysisDateDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListProductionYieldAnalysisDateMaterialVO> getProductionYieldAnalysisChartDate(ListProductionYieldAnalysisDateMaterialDTO listProductionYieldAnalysisDateMaterialDTO) {
        return getBaseMapper().getProductionYieldAnalysisChartDate(listProductionYieldAnalysisDateMaterialDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListYieldChartVO> getProductionHomeYieldChartData(HomeDTO homeDTO) {
        return getBaseMapper().getProductionHomeYieldChartData(homeDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public InfoYieldStatisticsVO getProductionHomeYieldStatisticsData(HomeDTO homeDTO) {
        return getBaseMapper().getProductionHomeYieldStatisticsData(homeDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public InfoPowerStatisticsVO getProductionHomePowerStatisticsData(HomeDTO homeDTO) {
        return getBaseMapper().getProductionHomePowerStatisticsData(homeDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ListPowerMeterChartVO> getProductionHomePowerMeterChartData(HomeDTO homeDTO) {
        return getBaseMapper().getProductionHomePowerMeterChartData(homeDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public InfoStatisticsVO getProductionHomeStatisticsData(HomeDTO homeDTO) {
        return getBaseMapper().getProductionHomeStatisticsData(homeDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getProductionYieldAnalysisColList() {
        return getBaseMapper().getProductionYieldAnalysisColList();
    }
}

