package com.jlkj.product.oi.service.impl;

import com.jlkj.product.oi.dto.dashboardindex.GetCokePlanPerformanceDTO;
import com.jlkj.product.oi.mapper.ProductionBoardEmsDataMapper;
import com.jlkj.product.oi.service.ProductionBoardEmsDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @description
 * @createDate 2022年7月20日 08:45:22
 */
@Service
public class ProductionBoardEmsDataServiceImpl implements ProductionBoardEmsDataService {

    @Resource
    ProductionBoardEmsDataMapper productionBoardEmsDataMapper;

    @Override
    public List<Map<String, Object>> getSteamByBoiler(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getSteamByBoiler(dto);
    }

    @Override
    public List<Map<String, Object>> getSteamByOutput(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getSteamByOutput(dto);
    }

    @Override
    public List<Map<String, Object>> getSteamByRetrieve(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getSteamByRetrieve(dto);
    }

    @Override
    public List<Map<String, Object>> getSteamByWater(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getSteamByWater(dto);
    }

    @Override
    public List<Map<String, Object>> getElectricityDayConsume(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getElectricityDayConsume(dto);
    }

    @Override
    public List<Map<String, Object>> getElectricityDayConsumeInfo(GetCokePlanPerformanceDTO dto) {
        return productionBoardEmsDataMapper.getElectricityDayConsumeInfo(dto);
    }


}
