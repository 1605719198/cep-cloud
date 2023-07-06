package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hd.domain.PersonHoliday;
import com.jlkj.human.hd.domain.PersonHolidayCancel;
import com.jlkj.human.hd.mapper.PersonHolidayCancelMapper;
import com.jlkj.human.hd.service.IPersonHolidayCancelService;
import com.jlkj.human.hd.service.IPersonHolidayService;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.dto.HumanresourcePersonnelInfoDTO;
import com.jlkj.human.hm.service.IPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 员工销假Service业务层处理
 *
 * @author 266861
 * @date 2023-04-21
 */
@Service
public class PersonHolidayCancelServiceImpl implements IPersonHolidayCancelService
{
    @Autowired
    private PersonHolidayCancelMapper personHolidayCancelMapper;
    @Autowired
    private IPersonnelService iPersonnelService;
    @Autowired
    private IPersonHolidayService iPersonHolidayService;
    /**
     * 查询员工销假
     *
     * @param id 员工销假主键
     * @return 员工销假
     */
    @Override
    public PersonHolidayCancel selectPersonHolidayCancelById(String id)
    {
        return personHolidayCancelMapper.selectPersonHolidayCancelById(id);
    }

    /**
     * 查询员工销假列表
     *
     * @param personHolidayCancel 员工销假
     * @return 员工销假
     */
    @Override
    public List<PersonHolidayCancel> selectPersonHolidayCancelList(PersonHolidayCancel personHolidayCancel)
    {
        return personHolidayCancelMapper.selectPersonHolidayCancelList(personHolidayCancel);
    }

    /**
     * 新增员工销假
     *
     * @param personHolidayCancel 员工销假
     * @return 结果
     */
    @Override
    public int insertPersonHolidayCancel(PersonHolidayCancel personHolidayCancel)
    {
        //请销假初始状态
        String oriStatus = "0";
        personHolidayCancel.setId(IdUtils.simpleUUID());
        PersonHoliday personHoliday = iPersonHolidayService.selectPersonHolidayById(personHolidayCancel.getPersonHolidayId());
        personHoliday.setStatusCancel(oriStatus);
        iPersonHolidayService.updatePersonHoliday(personHoliday);
        return personHolidayCancelMapper.insertPersonHolidayCancel(setData(personHolidayCancel));
    }

    /**
     * @Description 员工销假数据处理
     * @param personHolidayCancel 员工销假数据
     * @return 数据处理后的员工销假数据
     * @author 266861
     * @date 2023/6/30 11:11
     **/
    public PersonHolidayCancel setData(PersonHolidayCancel personHolidayCancel){

        HumanresourcePersonnelInfoDTO personnelInfoDTO = iPersonnelService.selectPersonnelInfo(personHolidayCancel.getEmpNo());
        Personnel personnel = personnelInfoDTO.getPersonnelList().get(0);
        personHolidayCancel.setEmpId(personnel.getId());
        personHolidayCancel.setEmpName(personnel.getFullName());
        personHolidayCancel.setPostName(personnel.getPostName());
        personHolidayCancel.setPostId(personnel.getPostId().toString());
        personHolidayCancel.setOrgId(personnel.getDepartmentId());
        personHolidayCancel.setCreator(SecurityUtils.getNickName());
        personHolidayCancel.setCreatorId(SecurityUtils.getUsername());
        personHolidayCancel.setCreateDate(new Date());
        return personHolidayCancel;
    }

    /**
     * 修改员工销假
     *
     * @param personHolidayCancel 员工销假
     * @return 结果
     */
    @Override
    public int updatePersonHolidayCancel(PersonHolidayCancel personHolidayCancel)
    {
        return personHolidayCancelMapper.updatePersonHolidayCancel(setData(personHolidayCancel));
    }

    /**
     * 批量删除员工销假
     *
     * @param ids 需要删除的员工销假主键
     * @return 结果
     */
    @Override
    public int deletePersonHolidayCancelByIds(String[] ids)
    {
        return personHolidayCancelMapper.deletePersonHolidayCancelByIds(ids);
    }

    /**
     * 删除员工销假信息
     *
     * @param id 员工销假主键
     * @return 结果
     */
    @Override
    public int deletePersonHolidayCancelById(String id)
    {
        return personHolidayCancelMapper.deletePersonHolidayCancelById(id);
    }

}
