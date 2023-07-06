package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hd.domain.OverTime;
import com.jlkj.human.hd.mapper.OverTimeMapper;
import com.jlkj.human.hd.service.IOverTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 加班汇总（薪酬计算用）Service业务层处理
 *
 * @author 266861
 * @date 2023-06-28
 */
@Service
public class OverTimeServiceImpl implements IOverTimeService
{
    @Autowired
    private OverTimeMapper overTimeMapper;

    /**
     * 查询加班汇总（薪酬计算用）
     *
     * @author 266861
     * @date 2023-06-28
     * @param id 加班汇总（薪酬计算用）主键
     * @return 加班汇总（薪酬计算用）
     */
    @Override
    public OverTime selectOverTimeById(String id)
    {
        return overTimeMapper.selectOverTimeById(id);
    }

    /**
     * 查询加班汇总（薪酬计算用）列表
     *
     * @author 266861
     * @date 2023-06-28
     * @param overTime 加班汇总（薪酬计算用）
     * @return 加班汇总（薪酬计算用）
     */
    @Override
    public List<OverTime> selectOverTimeList(OverTime overTime)
    {
        return overTimeMapper.selectOverTimeList(overTime);
    }

    /**
     * 新增加班汇总（薪酬计算用）
     *
     * @author 266861
     * @date 2023-06-28
     * @param overTimeList 加班汇总（薪酬计算用）列表
     * @return 结果
     */
    @Override
    public int insertOverTime(List<OverTime> overTimeList)
    {
        int result =0;
        for(OverTime overTime :overTimeList){
            List<OverTime> dataList = overTimeMapper.selectOverTimeList(overTime);
            if(dataList.isEmpty()){
                overTime.setId(IdUtils.simpleUUID());
                overTime.setCreatorId(SecurityUtils.getUsername());
                overTime.setCreator(SecurityUtils.getNickName());
                overTime.setCreateDate(new Date());
                overTimeMapper.insertOverTime(overTime);
                result++;
            }else{
                OverTime oldData = dataList.get(0);
                oldData.setDutyNum(oldData.getDutyNum().add(overTime.getDutyNum()));
                oldData.setHourNum(oldData.getHourNum().add(overTime.getHourNum()));
                overTimeMapper.updateOverTime(oldData);
                result++;
            }
        }
        return result;
    }

    /**
     * 修改加班汇总（薪酬计算用）
     *
     * @author 266861
     * @date 2023-06-28
     * @param overTime 加班汇总（薪酬计算用）
     * @return 结果
     */
    @Override
    public int updateOverTime(OverTime overTime)
    {
        return overTimeMapper.updateOverTime(overTime);
    }

    /**
     * 批量删除加班汇总（薪酬计算用）
     *
     * @author 266861
     * @date 2023-06-28
     * @param ids 需要删除的加班汇总（薪酬计算用）主键
     * @return 结果
     */
    @Override
    public int deleteOverTimeByIds(String[] ids)
    {
        return overTimeMapper.deleteOverTimeByIds(ids);
    }

    /**
     * 删除加班汇总（薪酬计算用）信息
     *
     * @author 266861
     * @date 2023-06-28
     * @param id 加班汇总（薪酬计算用）主键
     * @return 结果
     */
    @Override
    public int deleteOverTimeById(String id)
    {
        return overTimeMapper.deleteOverTimeById(id);
    }
}
