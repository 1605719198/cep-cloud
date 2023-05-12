package com.jlkj.human.hd.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.human.hd.domain.PersonColock;
import com.jlkj.human.hd.domain.PersonColockDetail;
import com.jlkj.human.hd.domain.PersonColockOrg;
import com.jlkj.human.hd.mapper.PersonColockMapper;
import com.jlkj.human.hd.service.IPersonColockDetailService;
import com.jlkj.human.hd.service.IPersonColockService;
import com.jlkj.human.hp.dto.FirstDeptDTO;
import com.jlkj.human.hp.service.ISysDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 人员卡钟Service业务层处理
 *
 * @author 266861
 * @date 2023-03-29
 */
@Service
public class PersonColockServiceImpl implements IPersonColockService
{
    @Autowired
    private PersonColockMapper personColockMapper;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private IPersonColockDetailService personColockDetailService;

    /**
     * 查询人员卡钟
     *
     * @param id 人员卡钟主键
     * @return 人员卡钟
     */
    @Override
    public PersonColock selectPersonColockById(String id)
    {
        PersonColock personColock = personColockMapper.selectPersonColockById(id);
        FirstDeptDTO firstDeptDTO = sysDeptService.getFirstDeptByPerson(personColock.getEmpId());
        personColock.setFirstDeptId(firstDeptDTO.getFirstDeptId());
        personColock.setFirstDeptName(firstDeptDTO.getFirstDeptName());
        return personColock;
    }

    /**
     * 查询人员卡钟列表
     *
     * @param personColock 人员卡钟
     * @return 人员卡钟
     */
    @Override
    public List<PersonColock> selectPersonColockList(PersonColock personColock)
    {

        List<PersonColock> colockList = personColockMapper.selectPersonColockList(personColock);
        colockList.forEach(item->{
            try{
                FirstDeptDTO firstDeptDTO = sysDeptService.getFirstDeptByPerson(item.getEmpId());
                item.setFirstDeptId(firstDeptDTO.getFirstDeptId());
                item.setFirstDeptName(firstDeptDTO.getFirstDeptName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return colockList;
    }

    /**
     * 新增人员卡钟
     *
     * @param personColock 人员卡钟
     * @return 结果
     */
    @Override
    public int insertPersonColock(PersonColock personColock) throws Exception
    {
        //员工新增：1，部门新增2
        String type;
        PersonColock oldPersonColock =new PersonColock();
        oldPersonColock.setEffectDate(personColock.getEffectDate());
        oldPersonColock.setEmpId(personColock.getEmpId());
        List<PersonColock> oldList = personColockMapper.selectPersonColockList(oldPersonColock);
        if(oldList.size()==0){
            PersonColock lastEffectData = personColockMapper.queryLastEffectData(personColock);
            if(lastEffectData == null || personColock.getEffectDate().getTime() > lastEffectData.getEffectDate().getTime()){
                personColock.setId(UUID.randomUUID().toString().substring(0, 32));
                if(personColock.getOrgColockId()!=null){
                    type = "2";
                }else{
                    type = "1";
                }
                personColock.setType(type);
                PersonColockDetail personColockDetail =new PersonColockDetail();
                ArrayList<String> arrayList  = personColock.getColockList();
                for (String strTemp : arrayList){
                    personColockDetail.setId(UUID.randomUUID().toString().substring(0, 32));
                    personColockDetail.setPersonColockId(personColock.getId());
                    personColockDetail.setCreator(personColock.getCreator());
                    personColockDetail.setCreatorId(personColock.getCreatorId());
                    personColockDetail.setCreateDate(personColock.getCreateDate());
                    personColockDetail.setMacId(strTemp);
                    personColockDetailService.insertPersonColockDetail(personColockDetail);
                }
                return personColockMapper.insertPersonColock(personColock);
            }else{
                SimpleDateFormat ymddate = new SimpleDateFormat("yyyy-MM-dd");
                throw new Exception("该人员新的生效日期必须大于"+ymddate.format(lastEffectData.getEffectDate()));
            }
        }else{
            if(personColock.getOrgColockId()!=null){
                personColock.setId(oldList.get(0).getId());
                return updatePersonColock(personColock);
            }else{
                throw new Exception("该人员在所选生效日期下已有数据，请重新选择生效日期");
            }
        }
    }

    /**
     * 机构新增人员卡钟
     *
     * @param personColockOrg 部门卡钟
     * @return 结果
     */
    @Override
    public int insertPersonColockByDept(PersonColockOrg personColockOrg){
        int result = 1;
        String deptId = personColockOrg.getDeptId();
        PersonColock personColock = new PersonColock();
        ArrayList<FirstDeptDTO> personList= sysDeptService.getPersonByDept(deptId);
        if(CollectionUtils.isEmpty(personList)||personList.get(0)!=null){
            for (FirstDeptDTO strTemp : personList){
                BeanUtils.copyProperties(personColockOrg,personColock);
                personColock.setEmpId(strTemp.getChildPersonId());
                personColock.setOrgColockId(personColockOrg.getId());
                try {
                    insertPersonColock(personColock);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }else{
            result = 0;
        }
        return result;
    }

    /**
     * 修改人员卡钟
     *
     * @param personColock 人员卡钟
     * @return 结果
     */
    @Override
    public int updatePersonColock(PersonColock personColock) throws Exception
    {
        PersonColock oldPersonColock =new PersonColock();
        oldPersonColock.setEffectDate(personColock.getEffectDate());
        oldPersonColock.setEmpId(personColock.getEmpId());
        oldPersonColock.setId(personColock.getId());
        List<PersonColock> oldList = personColockMapper.selectPersonColockList(oldPersonColock);
        Boolean bool = oldList.size()==0;
        if(bool){
            PersonColock lastEffectData = personColockMapper.queryLastEffectData(personColock);
            if(lastEffectData == null || personColock.getEffectDate().getTime() >= lastEffectData.getEffectDate().getTime()){
                personColockDetailService.updatePersonColockDetail(personColock);
                return personColockMapper.updatePersonColock(personColock);
            }else{
                SimpleDateFormat ymddate = new SimpleDateFormat("yyyy-MM-dd");
                throw new Exception("该人员新的生效日期必须大于等于"+ymddate.format(lastEffectData.getEffectDate()));
            }
        }else{
            if(personColock.getOrgColockId()!=null && oldList.size()==1 && !oldList.get(0).getId().equals(personColock.getId())){
                deletePersonColockById(oldList.get(0).getId());
                return updatePersonColock(personColock);
            }else{
                throw new Exception("该人员在所选生效日期下已有数据，请重新选择生效日期");
            }
        }
    }



    /**
     * 批量删除人员卡钟
     *
     * @param ids 需要删除的人员卡钟主键
     * @return 结果
     */
    @Override
    public int deletePersonColockByIds(String[] ids)
    {
        personColockDetailService.deletePersonColockDetailByPersonColockId(ids[0]);
        return personColockMapper.deletePersonColockByIds(ids);
    }

    /**
     * 删除人员卡钟信息
     *
     * @param id 人员卡钟主键
     * @return 结果
     */
    @Override
    public int deletePersonColockById(String id)
    {
        return personColockMapper.deletePersonColockById(id);
    }
}
