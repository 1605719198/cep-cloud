package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.PersonalSalary;
import com.jlkj.human.hs.domain.PersonalSalaryDetail;
import com.jlkj.human.hs.mapper.PersonalSalaryDetailMapper;
import com.jlkj.human.hs.service.IPersonalSalaryDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 个人薪资核定明细Service业务层处理
 *
 * @author 266861
 * @date 2023-06-01
 */
@Service
public class PersonalSalaryDetailServiceImpl implements IPersonalSalaryDetailService
{
    @Autowired
    private PersonalSalaryDetailMapper personalSalaryDetailMapper;

    /**
     * 查询个人薪资核定明细
     *
     * @param id 个人薪资核定明细主键
     * @return 个人薪资核定明细
     */
    @Override
    public PersonalSalaryDetail selectPersonalSalaryDetailById(String id)
    {
        return personalSalaryDetailMapper.selectPersonalSalaryDetailById(id);
    }

    /**
     * 查询个人薪资核定明细列表
     *
     * @param personalSalaryDetail 个人薪资核定明细
     * @return 个人薪资核定明细
     */
    @Override
    public List<PersonalSalaryDetail> selectPersonalSalaryDetailList(PersonalSalaryDetail personalSalaryDetail)
    {
        return personalSalaryDetailMapper.selectPersonalSalaryDetailList(personalSalaryDetail);
    }

    /**
     * 新增个人薪资核定明细
     *
     * @param personalSalaryDetail 个人薪资核定明细
     * @return 结果
     */
    @Override
    public int insertPersonalSalaryDetail(PersonalSalaryDetail personalSalaryDetail)
    {
        personalSalaryDetail.setId(IdUtils.simpleUUID());
        personalSalaryDetail.setCreatorId(SecurityUtils.getUserId().toString());
        personalSalaryDetail.setCreator(SecurityUtils.getNickName());
        personalSalaryDetail.setCreatorNo(SecurityUtils.getUsername());
        personalSalaryDetail.setCreateDate(new Date());
        return personalSalaryDetailMapper.insertPersonalSalaryDetail(personalSalaryDetail);
    }

    /**
     * 通过主档新增个人薪资核定明细
     *
     * @param personalSalary 个人薪资核定主档
     * @return 结果
     */
    @Override
    public int insertPersonalSalaryDetailByMain(PersonalSalary personalSalary){
        personalSalaryDetailMapper.deletePersonalSalaryDetailByMain(personalSalary.getId());
        int number = 0;
        List<PersonalSalaryDetail> detailList = personalSalary.getDetailList();
        for(PersonalSalaryDetail detail:detailList){
            detail.setIstax(personalSalary.getIstax());
            detail.setSalaryId(personalSalary.getId());
            detail.setIsCheck(personalSalary.getIsCheck());
            detail.setCompId(personalSalary.getCompId());
            detail.setEmpId(personalSalary.getEmpId());
            detail.setEmpNo(personalSalary.getEmpNo());
            detail.setVersionNo(personalSalary.getVersionNo().toString());
            insertPersonalSalaryDetail(detail);
            number++;
        }
        return number;
    }

    /**
     * 修改个人薪资核定明细
     *
     * @param personalSalaryDetail 个人薪资核定明细
     * @return 结果
     */
    @Override
    public int updatePersonalSalaryDetail(PersonalSalaryDetail personalSalaryDetail)
    {
        return personalSalaryDetailMapper.updatePersonalSalaryDetail(personalSalaryDetail);
    }

    /**
     * 批量删除个人薪资核定明细
     *
     * @param ids 需要删除的个人薪资核定明细主键
     * @return 结果
     */
    @Override
    public int deletePersonalSalaryDetailByIds(String[] ids)
    {
        return personalSalaryDetailMapper.deletePersonalSalaryDetailByIds(ids);
    }

    /**
     * 删除个人薪资核定明细信息
     *
     * @param id 个人薪资核定明细主键
     * @return 结果
     */
    @Override
    public int deletePersonalSalaryDetailById(String id)
    {
        return personalSalaryDetailMapper.deletePersonalSalaryDetailById(id);
    }
}
