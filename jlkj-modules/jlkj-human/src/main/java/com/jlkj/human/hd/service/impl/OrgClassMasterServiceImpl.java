package com.jlkj.human.hd.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.human.hd.domain.PersonClassDetail;
import com.jlkj.human.hd.domain.PersonClassMaster;
import com.jlkj.human.hd.domain.PersonColock;
import com.jlkj.human.hd.service.IPersonClassDetailService;
import com.jlkj.human.hd.service.IPersonClassMasterService;
import com.jlkj.human.hp.dto.FirstDeptDTO;
import com.jlkj.human.hp.service.ISysDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.hd.mapper.OrgClassMasterMapper;
import com.jlkj.human.hd.domain.OrgClassMaster;
import com.jlkj.human.hd.service.IOrgClassMasterService;

/**
 * 出勤身份Service业务层处理
 *
 * @author 266861
 * @date 2023-04-12
 */
@Service
public class OrgClassMasterServiceImpl implements IOrgClassMasterService
{
    @Autowired
    private OrgClassMasterMapper orgClassMasterMapper;
    @Autowired
    private IPersonClassDetailService personClassDetailService;
    @Autowired
    private IPersonClassMasterService personClassMasterService;
    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 查询出勤身份
     *
     * @param id 出勤身份主键
     * @return 出勤身份
     */
    @Override
    public OrgClassMaster selectOrgClassMasterById(String id)
    {
        return orgClassMasterMapper.selectOrgClassMasterById(id);
    }

    /**
     * 查询出勤身份列表
     *
     * @param orgClassMaster 出勤身份
     * @return 出勤身份
     */
    @Override
    public List<OrgClassMaster> selectOrgClassMasterList(OrgClassMaster orgClassMaster)
    {
        return orgClassMasterMapper.selectOrgClassMasterList(orgClassMaster);
    }

    /**
     * 新增出勤身份
     *
     * @param orgClassMaster 出勤身份
     * @return 结果
     */
    @Override
    public int insertOrgClassMaster(OrgClassMaster orgClassMaster)
    {
        orgClassMaster.setId(UUID.randomUUID().toString().substring(0, 32));
        PersonClassDetail personClassDetail = new PersonClassDetail();
        BeanUtils.copyProperties(orgClassMaster,personClassDetail);
        personClassDetail.setShiftModeId(orgClassMaster.getShiftmodeId());
        personClassDetail.setClassType("1");
        personClassDetail.setPersonClassMasterId(orgClassMaster.getId());
        personClassDetailService.setPersonClassDetail(personClassDetail);
        ArrayList<FirstDeptDTO> personList= sysDeptService.getPersonByDept(personClassDetail.getDeptId());
        if(!personList.contains(null)){
            for (FirstDeptDTO strTemp : personList){
                PersonClassMaster personClassMaster = new PersonClassMaster();
                BeanUtils.copyProperties(orgClassMaster,personClassMaster);
                personClassMaster.setEmpId(strTemp.getChildPersonId());
                personClassMaster.setOrgId(orgClassMaster.getId());
                personClassMasterService.insertPersonClassMaster(personClassMaster);
            }
        }
        return orgClassMasterMapper.insertOrgClassMaster(orgClassMaster);
    }

    /**
     * 修改出勤身份
     *
     * @param orgClassMaster 出勤身份
     * @return 结果
     */
    @Override
    public int updateOrgClassMaster(OrgClassMaster orgClassMaster)
    {
        PersonClassDetail personClassDetail = new PersonClassDetail();
        personClassDetailService.deletePersonClassDetailById(orgClassMaster.getId());
        BeanUtils.copyProperties(orgClassMaster,personClassDetail);
        personClassDetail.setShiftModeId(orgClassMaster.getShiftmodeId());
        personClassDetail.setClassType("1");
        personClassDetail.setPersonClassMasterId(orgClassMaster.getId());
        personClassDetailService.setPersonClassDetail(personClassDetail);

        PersonClassMaster personClassMaster = new PersonClassMaster();
        personClassMaster.setOrgId(orgClassMaster.getId());
        List<PersonClassMaster> personClassList = personClassMasterService.selectPersonClassMasterList(personClassMaster);
        Iterator<PersonClassMaster> iterator = personClassList.iterator();
        while (iterator.hasNext()){
            PersonClassMaster classMaster = iterator.next();
            String id = classMaster.getId();
            BeanUtils.copyProperties(orgClassMaster,classMaster);
            classMaster.setId(id);
            personClassMasterService.updatePersonClassMaster(classMaster);
        }
        return orgClassMasterMapper.updateOrgClassMaster(orgClassMaster);
    }

    /**
     * 批量删除出勤身份
     *
     * @param ids 需要删除的出勤身份主键
     * @return 结果
     */
    @Override
    public int deleteOrgClassMasterByIds(String[] ids)
    {
        return orgClassMasterMapper.deleteOrgClassMasterByIds(ids);
    }

    /**
     * 删除出勤身份信息
     *
     * @param id 出勤身份主键
     * @return 结果
     */
    @Override
    public int deleteOrgClassMasterById(String id)
    {
        return orgClassMasterMapper.deleteOrgClassMasterById(id);
    }
}
