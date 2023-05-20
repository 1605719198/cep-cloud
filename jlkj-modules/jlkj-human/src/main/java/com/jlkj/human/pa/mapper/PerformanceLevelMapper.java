package com.jlkj.human.pa.mapper;

import java.util.List;
import com.jlkj.human.pa.domain.PerformanceLevel;

/**
 * 绩效系数设定Mapper接口
 * 
 * @author 116519
 * @date 2023-05-15
 */
public interface PerformanceLevelMapper 
{
    /**
     * 查询绩效系数设定
     * 
     * @param uuid 绩效系数设定主键
     * @return 绩效系数设定
     */
    public PerformanceLevel selectPerformanceLevelByUuid(String uuid);

    /**
     * 查询绩效系数设定列表
     * 
     * @param performanceLevel 绩效系数设定
     * @return 绩效系数设定集合
     */
    public List<PerformanceLevel> selectPerformanceLevelList(PerformanceLevel performanceLevel);

    /**
     * 新增绩效系数设定
     * 
     * @param performanceLevel 绩效系数设定
     * @return 结果
     */
    public int insertPerformanceLevel(PerformanceLevel performanceLevel);

    /**
     * 修改绩效系数设定
     * 
     * @param performanceLevel 绩效系数设定
     * @return 结果
     */
    public int updatePerformanceLevel(PerformanceLevel performanceLevel);

    /**
     * 删除绩效系数设定
     * 
     * @param uuid 绩效系数设定主键
     * @return 结果
     */
    public int deletePerformanceLevelByUuid(String uuid);

    /**
     * 批量删除绩效系数设定
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePerformanceLevelByUuids(String[] uuids);
}
