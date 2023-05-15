package com.jlkj.human.hs.service;

import java.util.List;
import com.jlkj.human.hs.domain.AverageDays;

/**
 * 各公司平均天数设定Service接口
 * 
 * @author 266861
 * @date 2023-05-12
 */
public interface IAverageDaysService 
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
     * 批量删除各公司平均天数设定
     * 
     * @param uuids 需要删除的各公司平均天数设定主键集合
     * @return 结果
     */
    public int deleteAverageDaysByUuids(String[] uuids);

    /**
     * 删除各公司平均天数设定信息
     * 
     * @param uuid 各公司平均天数设定主键
     * @return 结果
     */
    public int deleteAverageDaysByUuid(String uuid);
}
