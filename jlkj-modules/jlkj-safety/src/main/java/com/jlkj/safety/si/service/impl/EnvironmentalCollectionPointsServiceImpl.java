package com.jlkj.safety.si.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.safety.si.dto.environmentalcollectionpoints.PageEnvironmentalCollectionPointsDTO;
import com.jlkj.safety.si.entity.EnvironmentalCollectionPoints;
import com.jlkj.safety.si.mapper.EnvironmentalCollectionPointsMapper;
import com.jlkj.safety.si.service.EnvironmentalCollectionPointsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author yzl
 * @create 2022-12-14 09:04:30.933272
 */
@Service
@Slf4j
public class EnvironmentalCollectionPointsServiceImpl extends ServiceImpl<EnvironmentalCollectionPointsMapper, EnvironmentalCollectionPoints>
        implements EnvironmentalCollectionPointsService {
    @Override
    public List<Map<String, String>> getEnvironmentalCollectionPoints(String sort) {
        return getBaseMapper().getEnvironmentalCollectionPoints(sort);
    }

    @Override
    @Transactional(readOnly = true)
    public IPage<Map<String, String>> getPageData(PageEnvironmentalCollectionPointsDTO dto) {
        Page<Map<String, String>> page = new Page<>(dto.getCurrent(), dto.getSize());
        return getBaseMapper().getPageData(page, dto);
    }
}

