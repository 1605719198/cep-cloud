package com.jlkj.human.hd.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.human.hd.domain.PersonColock;
import com.jlkj.human.hd.domain.PersonColockOrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.hd.mapper.PersonColockDetailMapper;
import com.jlkj.human.hd.domain.PersonColockDetail;
import com.jlkj.human.hd.service.IPersonColockDetailService;

/**
 * 人员卡钟明细Service业务层处理
 *
 * @author 266861
 * @date 2023-03-29
 */
@Service
public class PersonColockDetailServiceImpl implements IPersonColockDetailService
{
    @Autowired
    private PersonColockDetailMapper personColockDetailMapper;

    /**
     * 查询人员卡钟明细
     *
     * @param id 人员卡钟明细主键
     * @return 人员卡钟明细
     */
    @Override
    public PersonColockDetail selectPersonColockDetailById(String id)
    {
        return personColockDetailMapper.selectPersonColockDetailById(id);
    }

    /**
     * 查询人员卡钟明细列表
     *
     * @param personColockDetail 人员卡钟明细
     * @return 人员卡钟明细
     */
    @Override
    public List<PersonColockDetail> selectPersonColockDetailList(PersonColockDetail personColockDetail)
    {
        return personColockDetailMapper.selectPersonColockDetailList(personColockDetail);
    }

    /**
     * 新增人员卡钟明细
     *
     * @param personColockDetail 人员卡钟明细
     * @return 结果
     */
    @Override
    public int insertPersonColockDetail(PersonColockDetail personColockDetail)
    {
        personColockDetail.setId(UUID.randomUUID().toString().substring(0, 32));
        return personColockDetailMapper.insertPersonColockDetail(personColockDetail);
    }

    /**
     * 修改人员卡钟明细
     *
     * @param personColockDetail 人员卡钟明细
     * @return 结果
     */
    @Override
    public int updatePersonColockDetail(PersonColockDetail personColockDetail)
    {
        return personColockDetailMapper.updatePersonColockDetail(personColockDetail);
    }

    /**
     * 批量删除人员卡钟明细
     *
     * @param ids 需要删除的人员卡钟明细主键
     * @return 结果
     */
    @Override
    public int deletePersonColockDetailByIds(String[] ids)
    {
        return personColockDetailMapper.deletePersonColockDetailByIds(ids);
    }

    /**
     * 删除人员卡钟明细信息
     *
     * @param id 人员卡钟明细主键
     * @return 结果
     */
    @Override
    public int deletePersonColockDetailById(String id)
    {
        return personColockDetailMapper.deletePersonColockDetailById(id);
    }

    /**
     * 删除人员卡钟明细信息
     *
     * @param personColockId 主表主键
     * @return 结果
     */
    @Override
    public int deletePersonColockDetailByPersonColockId(String personColockId)
    {
        return personColockDetailMapper.deletePersonColockDetailByPersonColockId(personColockId);
    }

    /**
     * 修改人员卡钟明细信息
     *
     * @param personColock 人员卡钟
     * @return 结果
     */
    @Override
    public int updatePersonColockDetail(PersonColock personColock){
        PersonColockDetail personColockDetail =new PersonColockDetail();
        personColockDetailMapper.deletePersonColockDetailByPersonColockId(personColock.getId());
        ArrayList<String> arrayList  = personColock.getColockList();
        for (String strTemp : arrayList){
            personColockDetail.setId(UUID.randomUUID().toString().substring(0, 32));
            personColockDetail.setPersonColockId(personColock.getId());
            personColockDetail.setCreator(personColock.getCreator());
            personColockDetail.setCreatorId(personColock.getCreatorId());
            personColockDetail.setCreateDate(personColock.getCreateDate());
            personColockDetail.setMacId(strTemp);
            personColockDetailMapper.insertPersonColockDetail(personColockDetail);
        }
        return 1;
    }

    /**
     * 修改人员卡钟明细信息
     *
     * @param personColockOrg 部门卡钟
     * @return 结果
     */
    @Override
    public int updatePersonColockDetail(PersonColockOrg personColockOrg){
        PersonColockDetail personColockDetail =new PersonColockDetail();
        personColockDetailMapper.deletePersonColockDetailByPersonColockId(personColockOrg.getId());
        ArrayList<String> arrayList  = personColockOrg.getColockList();
        for (String strTemp : arrayList){
            personColockDetail.setId(UUID.randomUUID().toString().substring(0, 32));
            personColockDetail.setPersonColockId(personColockOrg.getId());
            personColockDetail.setCreator(personColockOrg.getCreator());
            personColockDetail.setCreatorId(personColockOrg.getCreatorId());
            personColockDetail.setCreateDate(personColockOrg.getCreateDate());
            personColockDetail.setMacId(strTemp);
            personColockDetailMapper.insertPersonColockDetail(personColockDetail);
        }
        return 1;
    }
}
