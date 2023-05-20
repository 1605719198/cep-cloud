package com.jlkj.human.hs.mapper;

import com.jlkj.human.hs.domain.AverageDays;

import java.util.List;

/**
 * 各公司平均天数设定Mapper接口
 * 
 * @author 266861
 * @date 2023-05-12
 */
public interface AverageDaysMapper 
{
    /**
     * 查询各公司平均天数设定
     * 
     * @param uuid 各公司平均天数设定主键
     * @return 各公司平均天数设定
     */
    public AverageDays selectAverageDaysByUuid(String uuid);

    /**
     * 查询各公司平均天数设定列表
     * 
     * @param averageDays 各公司平均天数设定
     * @return 各公司平均天数设定集合
     */
    public List<AverageDays> selectAverageDaysList(AverageDays averageDays);

    /**
     * 新增各公司平均天数设定
     * 
     * @param averageDays 各公司平均天数设定
     * @return 结果
     */
    public int insertAverageDays(AverageDays averageDays);

    /**
     * 修改各公司平均天数设定
     * 
     * @param averageDays 各公司平均天数设定
     * @return 结果
     */
    public int updateAverageDays(AverageDays averageDays);

    /**
     * 查询公司平均天数设定最大版本
     *
     * @param averageDays 公司平均天数设定资料
     * @return 结果
     */
    public Long queryAverageDaysVersion(AverageDays averageDays);

    /**
     * 删除各公司平均天数设定
     * 
     * @param uuid 各公司平均天数设定主键
     * @return 结果
     */
    public int deleteAverageDaysByUuid(String uuid);

    /**
     * 批量删除各公司平均天数设定
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAverageDaysByUuids(String[] uuids);
}
