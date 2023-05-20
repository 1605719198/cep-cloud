package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hd.domain.PersonHoliday;
import com.jlkj.human.hd.domain.TravelApplication;
import com.jlkj.human.hd.mapper.PersonHolidayMapper;
import com.jlkj.human.hd.mapper.TravelApplicationMapper;
import com.jlkj.human.hd.service.ITravelApplicationService;
import com.jlkj.system.api.domain.SysUser;
import com.jlkj.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 出差申请Service业务层处理
 *
 * @author jlkj
 * @date 2023-04-25
 */
@Service
public class TravelApplicationServiceImpl implements ITravelApplicationService
{

    @Autowired
    private TravelApplicationMapper travelApplicationMapper;
    @Autowired
    private PersonHolidayMapper personHolidayMapper;


    /**
     * 查询出差申请
     *
     * @param id 出差申请主键
     * @return 出差申请
     */
    @Override
    public TravelApplication selectTravelApplicationById(String id)
    {
        return travelApplicationMapper.selectTravelApplicationById(id);
    }

    /**
     * 查询出差申请列表
     *
     * @param travelApplication 出差申请
     * @return 出差申请
     */
    @Override
    public List<TravelApplication> selectTravelApplicationList(TravelApplication travelApplication)
    {
        return travelApplicationMapper.selectTravelApplicationList(travelApplication);
    }

    /**
     * 新增出差申请
     *
     * @param travelApplication 出差申请
     * @return 结果
     */
    @Override
    public int insertTravelApplication(TravelApplication travelApplication)
    {
        //根据 出差开始结束日期 查询 员工 时间段内是否存在请假或者补休资料  或已经存在出差资料
        travelApplication.setId(UUID.randomUUID().toString().substring(0,32));
        travelApplication.setTravelNo(String.valueOf(System.currentTimeMillis()));
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getSysUser();
        travelApplication.setCreator(user.getUserName());
        travelApplication.setCreateDate(DateUtils.getNowDate());
        travelApplication.setRealStartDate(travelApplication.getStartDate());
        travelApplication.setRealEndDate(travelApplication.getEndDate());
        //判断时间段内是否存在出差单
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String  startdate=sdf.format(travelApplication.getStartDate());
        String  enddate=sdf.format(travelApplication.getEndDate());
        //判断时间段内是否存在出差单
        List<TravelApplication> application = travelApplicationMapper.getTravelApplication(travelApplication.getId(),travelApplication.getEmpNo(),
                startdate,enddate);
        if (application.size()>0){
            throw new ServiceException("当前员工出差时间段已存在出差申请单，请确认后再申请！");
        }
        //判断时间段内是否有请假假
        PersonHoliday  personHoliday = new   PersonHoliday();
        personHoliday.setEmpNo(travelApplication.getEmpNo());
        personHoliday.setStartDate(travelApplication.getStartDate());
        personHoliday.setEndDate(travelApplication.getEndDate());
        List<PersonHoliday> personHolidayList = personHolidayMapper.selectPersonHolidayList(personHoliday);
        if (personHolidayList.size()>0){
            throw new ServiceException("当前员工出差时间段已存在请假信息，请确认后再申请！");
        }
        return travelApplicationMapper.insertTravelApplication(travelApplication);
    }

    /**
     * 修改出差申请
     *
     * @param travelApplication 出差申请
     * @return 结果
     */
    @Override
    public int updateTravelApplication(TravelApplication travelApplication)
    {
        //判断时间段内是否存在出差单
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String  startdate=sdf.format(travelApplication.getStartDate());
        String  enddate=sdf.format(travelApplication.getEndDate());

        List<TravelApplication> application = travelApplicationMapper.getTravelApplication(travelApplication.getId(),travelApplication.getEmpNo(),
                startdate,enddate);
        if (application.size()>0){
            throw new ServiceException("出差时间段已存在出差申请单，请确认后再申请！");
        }
        PersonHoliday  personHoliday = new   PersonHoliday();
        personHoliday.setEmpNo(travelApplication.getEmpNo());
        personHoliday.setStartDate(travelApplication.getStartDate());
        personHoliday.setEndDate(travelApplication.getEndDate());
        List<PersonHoliday> personHolidayList = personHolidayMapper.selectPersonHolidayList(personHoliday);
        if (personHolidayList.size()>0){
            throw new ServiceException("当前员工出差时间段已存在请假信息，请确认后再申请！");
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getSysUser();
        travelApplication.setCreator(user.getUserName());
        travelApplication.setCreateDate(DateUtils.getNowDate());
        return travelApplicationMapper.updateTravelApplication(travelApplication);
    }

    /**
     * 批量删除出差申请
     *
     * @param ids 需要删除的出差申请主键
     * @return 结果
     */
    @Override
    public int deleteTravelApplicationByIds(String[] ids)
    {
        return travelApplicationMapper.deleteTravelApplicationByIds(ids);
    }

    /**
     * 删除出差申请信息
     *
     * @param id 出差申请主键
     * @return 结果
     */
    @Override
    public int deleteTravelApplicationById(String id)
    {
        return travelApplicationMapper.deleteTravelApplicationById(id);
    }
}
