package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.human.hs.domain.AverageDays;
import com.jlkj.human.hs.mapper.AverageDaysMapper;
import com.jlkj.human.hs.service.IAverageDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 各公司平均天数设定Service业务层处理
 *
 * @author 266861
 * @date 2023-05-12
 */
@Service
public class AverageDaysServiceImpl implements IAverageDaysService
{
    @Autowired
    private AverageDaysMapper averageDaysMapper;

    /**
     * 查询各公司平均天数设定
     *
     * @param uuid 各公司平均天数设定主键
     * @return 各公司平均天数设定
     */
    @Override
    public AverageDays selectAverageDaysByUuid(String uuid)
    {
        return averageDaysMapper.selectAverageDaysByUuid(uuid);
    }

    /**
     * 查询各公司平均天数设定列表
     *
     * @param averageDays 各公司平均天数设定
     * @return 各公司平均天数设定
     */
    @Override
    public List<AverageDays> selectAverageDaysList(AverageDays averageDays)
    {
        return averageDaysMapper.selectAverageDaysList(averageDays);
    }

    /**
     * 新增各公司平均天数设定
     *
     * @param averageDays 各公司平均天数设定
     * @return 结果
     */
    @Override
    public int insertAverageDays(AverageDays averageDays)
    {
        averageDays.setUuid(UUID.randomUUID().toString().substring(0, 32));
        try {
            averageDays.setVersionNo(averageDaysMapper.queryAverageDaysVersion(averageDays)+1);
        } catch (Exception e){
            averageDays.setVersionNo(1L);
        }
        return averageDaysMapper.insertAverageDays(averageDays);
    }

    /**
     * 修改各公司平均天数设定
     *
     * @param averageDays 各公司平均天数设定
     * @return 结果
     */
    @Override
    public int updateAverageDays(AverageDays averageDays)
    {
        return averageDaysMapper.updateAverageDays(averageDays);
    }

    /**
     * 批量删除各公司平均天数设定
     *
     * @param uuids 需要删除的各公司平均天数设定主键
     * @return 结果
     */
    @Override
    public int deleteAverageDaysByUuids(String[] uuids)
    {
        return averageDaysMapper.deleteAverageDaysByUuids(uuids);
    }

    /**
     * 删除各公司平均天数设定信息
     *
     * @param uuid 各公司平均天数设定主键
     * @return 结果
     */
    @Override
    public int deleteAverageDaysByUuid(String uuid)
    {
        return averageDaysMapper.deleteAverageDaysByUuid(uuid);
    }
}
