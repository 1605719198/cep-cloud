package com.jlkj.human.pa.service.impl;

import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.pa.domain.PerformanceLevel;
import com.jlkj.human.pa.mapper.PerformanceLevelMapper;
import com.jlkj.human.pa.service.IPerformanceLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 绩效系数设定Service业务层处理
 *
 * @author 116519
 * @date 2023-05-15
 */
@Service
public class PerformanceLevelServiceImpl implements IPerformanceLevelService
{
    @Autowired
    private PerformanceLevelMapper performanceLevelMapper;

    /**
     * 查询绩效系数设定
     *
     * @param uuid 绩效系数设定主键
     * @return 绩效系数设定
     */
    @Override
    public PerformanceLevel selectPerformanceLevelByUuid(String uuid)
    {
        return performanceLevelMapper.selectPerformanceLevelByUuid(uuid);
    }

    /**
     * 查询绩效系数设定列表
     *
     * @param performanceLevel 绩效系数设定
     * @return 绩效系数设定
     */
    @Override
    public List<PerformanceLevel> selectPerformanceLevelList(PerformanceLevel performanceLevel)
    {
        return performanceLevelMapper.selectPerformanceLevelList(performanceLevel);
    }

    /**
     * 新增薪酬项目
     *
     * @param performanceLevelList 绩效系数设定列表
     * @return 结果
     */
    @Override
    public int insertPerformanceLevel(List<PerformanceLevel> performanceLevelList,PerformanceLevel performanceLevel)
    {
        for(PerformanceLevel performanceLevelnew :performanceLevelList){
            if(performanceLevelnew.getUuid()!=null){
                performanceLevelnew.setCreatorId(SecurityUtils.getUserId().toString());
                performanceLevelMapper.updatePerformanceLevel(performanceLevelnew);

            }else{
                performanceLevelnew.setUuid(IdUtils.simpleUUID());
                performanceLevelnew.setCompId(performanceLevel.getCompId());
                performanceLevelnew.setPerfTypeId(performanceLevel.getPerfTypeId());
                performanceLevelMapper.insertPerformanceLevel(performanceLevelnew);

            }

        }
        return 1;
    }


    /**
     * 批量删除绩效系数设定
     *
     * @param uuids 需要删除的绩效系数设定主键
     * @return 结果
     */
    @Override
    public int deletePerformanceLevelByUuids(String[] uuids)
    {
        return performanceLevelMapper.deletePerformanceLevelByUuids(uuids);
    }

    /**
     * 删除绩效系数设定信息
     *
     * @param uuid 绩效系数设定主键
     * @return 结果
     */
    @Override
    public int deletePerformanceLevelByUuid(String uuid)
    {
        return performanceLevelMapper.deletePerformanceLevelByUuid(uuid);
    }
}
