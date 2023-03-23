package com.jlkj.safety.si.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.safety.si.dto.environmentalcollectionpoints.PageEnvironmentalCollectionPointsDTO;
import com.jlkj.safety.si.entity.EnvironmentalCollectionPoints;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @create 2022-12-14 09:04:30.932275
 */
public interface EnvironmentalCollectionPointsService extends IService<EnvironmentalCollectionPoints> {

    /**
     * 查询环保采集点位
     * @param sort
     * @return
     */
    List<Map<String, String>> getEnvironmentalCollectionPoints(String sort);

    /**
     * 环保采集点位 列表
     * @param dto
     * @return
     */
    IPage<Map<String, String>> getPageData(PageEnvironmentalCollectionPointsDTO dto);

}
