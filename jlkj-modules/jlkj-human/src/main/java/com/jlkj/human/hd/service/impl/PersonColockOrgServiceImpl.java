package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.human.hd.domain.PersonColock;
import com.jlkj.human.hd.domain.PersonColockDetail;
import com.jlkj.human.hd.domain.PersonColockOrg;
import com.jlkj.human.hd.mapper.PersonColockOrgMapper;
import com.jlkj.human.hd.service.IPersonColockDetailService;
import com.jlkj.human.hd.service.IPersonColockOrgService;
import com.jlkj.human.hd.service.IPersonColockService;
import com.jlkj.human.hp.dto.FirstDeptDTO;
import com.jlkj.human.hp.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 人员卡钟组织机构Service业务层处理
 *
 * @author 266861
 * @date 2023-03-31
 */
@Service
public class PersonColockOrgServiceImpl implements IPersonColockOrgService
{
    @Autowired
    private PersonColockOrgMapper personColockOrgMapper;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private IPersonColockDetailService personColockDetailService;
    @Autowired
    private IPersonColockService personColockService;

    /**
     * 查询人员卡钟组织机构
     *
     * @param id 人员卡钟组织机构主键
     * @return 人员卡钟组织机构
     */
    @Override
    public PersonColockOrg selectPersonColockOrgById(String id)
    {
        PersonColockOrg personColockOrg = personColockOrgMapper.selectPersonColockOrgById(id);
        FirstDeptDTO firstDeptDTO = sysDeptService.getFirstDeptByDept(personColockOrg.getDeptId());
        personColockOrg.setFirstDeptId(firstDeptDTO.getFirstDeptId());
        personColockOrg.setFirstDeptName(firstDeptDTO.getFirstDeptName());

        return personColockOrg;
    }

    /**
     * 查询人员卡钟组织机构列表
     *
     * @param personColockOrg 人员卡钟组织机构
     * @return 人员卡钟组织机构
     */
    @Override
    public List<PersonColockOrg> selectPersonColockOrgList(PersonColockOrg personColockOrg)
    {

        List<PersonColockOrg> colockList = personColockOrgMapper.selectPersonColockOrgList(personColockOrg);
        colockList.forEach(item->{
            try{
                FirstDeptDTO firstDeptDTO = sysDeptService.getFirstDeptByDept(item.getDeptId());
                item.setFirstDeptId(firstDeptDTO.getFirstDeptId());
                item.setFirstDeptName(firstDeptDTO.getFirstDeptName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return colockList;
    }

    /**
     * 新增人员卡钟组织机构
     *
     * @param personColockOrg 人员卡钟组织机构
     * @return 结果
     */
    @Override
    public int insertPersonColockOrg(PersonColockOrg personColockOrg)throws Exception
    {
        PersonColockOrg oldPersonColockOrg =new PersonColockOrg();
        oldPersonColockOrg.setEffectDate(personColockOrg.getEffectDate());
        oldPersonColockOrg.setDeptId(personColockOrg.getDeptId());
        List<PersonColockOrg> oldList = personColockOrgMapper.selectPersonColockOrgList(personColockOrg);
        Boolean bool = oldList.size()==0||(oldList.size()==1 && oldList.get(0).getId().equals(personColockOrg.getId()));
        if(bool){
            PersonColockOrg lastEffectData = personColockOrgMapper.queryLastEffectData(personColockOrg);
            if(lastEffectData == null || personColockOrg.getEffectDate().getTime() > lastEffectData.getEffectDate().getTime()){
                personColockOrg.setId(UUID.randomUUID().toString().substring(0, 32));
                PersonColockDetail personColockDetail =new PersonColockDetail();
                ArrayList<String> arrayList  = personColockOrg.getColockList();
                for (String strTemp : arrayList){
                    personColockDetail.setId(UUID.randomUUID().toString().substring(0, 32));
                    personColockDetail.setPersonColockId(personColockOrg.getId());
                    personColockDetail.setCreator(personColockOrg.getCreator());
                    personColockDetail.setCreatorId(personColockOrg.getCreatorId());
                    personColockDetail.setCreateDate(personColockOrg.getCreateDate());
                    personColockDetail.setMacId(strTemp);
                    personColockDetailService.insertPersonColockDetail(personColockDetail);
                }
                int result = personColockService.insertPersonColockByDept(personColockOrg);
                if(result==0){
                    throw new Exception("该部门下无员工，请重新选择部门");
                }
                return personColockOrgMapper.insertPersonColockOrg(personColockOrg);
            }else{
                SimpleDateFormat ymddate = new SimpleDateFormat("yyyy-MM-dd");
                throw new Exception("该部门新的生效日期必须大于"+ymddate.format(lastEffectData.getEffectDate()));
            }
        }else{
            throw new Exception("该部门在所选生效日期下已有数据，请重新选择生效日期");
        }

    }

    /**
     * 修改人员卡钟组织机构
     *
     * @param personColockOrg 人员卡钟组织机构
     * @return 结果
     */
    @Override
    public int updatePersonColockOrg(PersonColockOrg personColockOrg)throws Exception
    {
        PersonColockOrg oldPersonColockOrg =new PersonColockOrg();
        oldPersonColockOrg.setEffectDate(personColockOrg.getEffectDate());
        oldPersonColockOrg.setDeptId(personColockOrg.getDeptId());
        List<PersonColockOrg> oldList = personColockOrgMapper.selectPersonColockOrgList(personColockOrg);
        if(oldList.size()==0){
            PersonColockOrg lastEffectData = personColockOrgMapper.queryLastEffectData(personColockOrg);
            if(lastEffectData == null || personColockOrg.getEffectDate().getTime() >= lastEffectData.getEffectDate().getTime()){
                personColockDetailService.updatePersonColockDetail(personColockOrg);
                PersonColock personColockparam = new PersonColock();
                personColockparam.setOrgColockId(personColockOrg.getId());
                List<PersonColock> personColockList = personColockService.selectPersonColockList(personColockparam);
                Iterator<PersonColock> iterator = personColockList.iterator();
                while (iterator.hasNext()){
                    PersonColock personColock = iterator.next();
                    personColock.setCreator(personColockOrg.getCreator());
                    personColock.setCreatorId(personColockOrg.getCreatorId());
                    personColock.setCreateDate(personColockOrg.getCreateDate());
                    personColock.setEffectDate(personColockOrg.getEffectDate());
                    personColock.setCheckcard(personColockOrg.getCheckcard());
                    personColock.setType("2");
                    personColock.setColockList(personColockOrg.getColockList());
                    try {
                        personColockService.updatePersonColock(personColock);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return personColockOrgMapper.updatePersonColockOrg(personColockOrg);
            }else{
                SimpleDateFormat ymddate = new SimpleDateFormat("yyyy-MM-dd");
                throw new Exception("该部门新的生效日期必须大于等于"+ymddate.format(lastEffectData.getEffectDate()));
            }

        }else {
            throw new Exception("该部门在所选生效日期下已有数据，请重新选择生效日期");
        }

    }

    /**
     * 批量删除人员卡钟组织机构
     *
     * @param ids 需要删除的人员卡钟组织机构主键
     * @return 结果
     */
    @Override
    public int deletePersonColockOrgByIds(String[] ids)
    {
        personColockDetailService.deletePersonColockDetailByPersonColockId(ids[0]);
        return personColockOrgMapper.deletePersonColockOrgByIds(ids);
    }

    /**
     * 删除人员卡钟组织机构信息
     *
     * @param id 人员卡钟组织机构主键
     * @return 结果
     */
    @Override
    public int deletePersonColockOrgById(String id)
    {
        personColockDetailService.deletePersonColockDetailByPersonColockId(id);
        return personColockOrgMapper.deletePersonColockOrgById(id);
    }
}
