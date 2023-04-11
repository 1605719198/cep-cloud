package com.jlkj.human.hd.service.impl;

import com.jlkj.human.hd.domain.HomeLeaveHoliday;
import com.jlkj.human.hd.mapper.HomeLeaveHolidayMapper;
import com.jlkj.human.hd.service.IHomeLeaveHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 探亲假天数设定Service业务层处理
 *
 * @author jiangbingchen
 * @date 2023-04-08
 */
@Service
public class HomeLeaveHolidayServiceImpl implements IHomeLeaveHolidayService
{
    @Autowired
    private HomeLeaveHolidayMapper homeLeaveHolidayMapper;

    /**
     * 查询探亲假天数设定
     *
     * @param id 探亲假天数设定主键
     * @return 探亲假天数设定
     */
    @Override
    public HomeLeaveHoliday selectHomeLeaveHolidayById(String id)
    {
        return homeLeaveHolidayMapper.selectHomeLeaveHolidayById(id);
    }

    /**
     * 查询探亲假天数设定列表
     *
     * @param homeLeaveHoliday 探亲假天数设定
     * @return 探亲假天数设定
     */
    @Override
    public List<HomeLeaveHoliday> selectHomeLeaveHolidayList(HomeLeaveHoliday homeLeaveHoliday)
    {
        return homeLeaveHolidayMapper.selectHomeLeaveHolidayList(homeLeaveHoliday);
    }

    /**
     * 新增探亲假天数设定
     *
     * @param homeLeaveHoliday 探亲假天数设定
     * @return 结果
     */
    @Override
    public int insertHomeLeaveHoliday(HomeLeaveHoliday homeLeaveHoliday)
    {
        return homeLeaveHolidayMapper.insertHomeLeaveHoliday(homeLeaveHoliday);
    }

    /**
     * 修改探亲假天数设定
     *
     * @param homeLeaveHoliday 探亲假天数设定
     * @return 结果
     */
    @Override
    public int updateHomeLeaveHoliday(HomeLeaveHoliday homeLeaveHoliday)
    {
        return homeLeaveHolidayMapper.updateHomeLeaveHoliday(homeLeaveHoliday);
    }

    /**
     * 批量删除探亲假天数设定
     *
     * @param ids 需要删除的探亲假天数设定主键
     * @return 结果
     */
    @Override
    public int deleteHomeLeaveHolidayByIds(String[] ids)
    {
        return homeLeaveHolidayMapper.deleteHomeLeaveHolidayByIds(ids);
    }

    /**
     * 删除探亲假天数设定信息
     *
     * @param id 探亲假天数设定主键
     * @return 结果
     */
    @Override
    public int deleteHomeLeaveHolidayById(String id)
    {
        return homeLeaveHolidayMapper.deleteHomeLeaveHolidayById(id);
    }
}
