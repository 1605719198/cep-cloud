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
*@description: 服务实现-产量分析日表
*@Author: 265823
*@date: 2023/7/11 13:58
*/
@Service
public class ProductionYieldAnalysisDateServiceImpl extends ServiceImpl<ProductionYieldAnalysisDateMapper, ProductionYieldAnalysisDate>
    implements ProductionYieldAnalysisDateService {

    @Resource
    private ProductionYieldAnalysisDateMapper productionYieldAnalysisDateMapper;

    /**
     * 生产管理-产量跟踪-查询(日)
     * @param listProductionYieldAnalysisDateDTO 查询条件dto
     * @return
     */
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

    /**
     * 生产管理-产量跟踪-图表-物料名称(日)
     * @param listProductionYieldAnalysisDateMaterialDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ListProductionYieldAnalysisDateMaterialVO> getProductionYieldAnalysisChartDate(ListProductionYieldAnalysisDateMaterialDTO listProductionYieldAnalysisDateMaterialDTO) {
        return getBaseMapper().getProductionYieldAnalysisChartDate(listProductionYieldAnalysisDateMaterialDTO);
    }

    /**
     * 生产管理-首页-焦碳计划实绩图表
     * @param homeDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ListYieldChartVO> getProductionHomeYieldChartData(HomeDTO homeDTO) {
        return getBaseMapper().getProductionHomeYieldChartData(homeDTO);
    }

    /**
     * 生产管理-首页-焦碳计划实绩统计
     * @param homeDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public InfoYieldStatisticsVO getProductionHomeYieldStatisticsData(HomeDTO homeDTO) {
        return getBaseMapper().getProductionHomeYieldStatisticsData(homeDTO);
    }

    /**
     * 生产管理-首页-发电计划实绩统计
     * @param homeDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public InfoPowerStatisticsVO getProductionHomePowerStatisticsData(HomeDTO homeDTO) {
        return getBaseMapper().getProductionHomePowerStatisticsData(homeDTO);
    }

    /**
     * 生产管理-首页-发电仪表图表
     * @param homeDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ListPowerMeterChartVO> getProductionHomePowerMeterChartData(HomeDTO homeDTO) {
        return getBaseMapper().getProductionHomePowerMeterChartData(homeDTO);
    }

    /**
     * 生产管理-首页-统计
     * @param homeDTO 查询条件dto
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public InfoStatisticsVO getProductionHomeStatisticsData(HomeDTO homeDTO) {
        return getBaseMapper().getProductionHomeStatisticsData(homeDTO);
    }

    /**
     * 生产管理-产量跟踪-查询-获取动态列表
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getProductionYieldAnalysisColList() {
        return getBaseMapper().getProductionYieldAnalysisColList();
    }
}

