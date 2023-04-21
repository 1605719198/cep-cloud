package com.jlkj.human.hd.service.impl;

import java.util.List;

import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.human.hd.domain.PersonClassDetail;
import com.jlkj.human.hd.service.IPersonClassDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jlkj.human.hd.mapper.PersonClassMasterMapper;
import com.jlkj.human.hd.domain.PersonClassMaster;
import com.jlkj.human.hd.service.IPersonClassMasterService;

/**
 * 出勤身份Service业务层处理
 *
 * @author 266861
 * @date 2023-04-12
 */
@Service
public class PersonClassMasterServiceImpl implements IPersonClassMasterService
{
    @Autowired
    private PersonClassMasterMapper personClassMasterMapper;
    @Autowired
    private IPersonClassDetailService personClassDetailService;


    /**
     * 查询出勤身份
     *
     * @param id 出勤身份主键
     * @return 出勤身份
     */
    @Override
    public PersonClassMaster selectPersonClassMasterById(String id)
    {
        return personClassMasterMapper.selectPersonClassMasterById(id);
    }

    /**
     * 查询出勤身份列表
     *
     * @param personClassMaster 出勤身份
     * @return 出勤身份
     */
    @Override
    public List<PersonClassMaster> selectPersonClassMasterList(PersonClassMaster personClassMaster)
    {
        return personClassMasterMapper.selectPersonClassMasterList(personClassMaster);
    }

    /**
     * 新增出勤身份
     *
     * @param personClassMaster 出勤身份
     * @return 结果
     */
    @Override
    public int insertPersonClassMaster(PersonClassMaster personClassMaster)
    {
        personClassMaster.setId(UUID.randomUUID().toString().substring(0, 32));
        PersonClassDetail personClassDetail = new PersonClassDetail();
        BeanUtils.copyProperties(personClassMaster,personClassDetail);
        personClassDetail.setShiftModeId(personClassMaster.getShiftmodeId());
        personClassDetail.setClassType("0");
        personClassDetail.setPersonClassMasterId(personClassMaster.getId());
        personClassDetailService.setPersonClassDetail(personClassDetail);
        return personClassMasterMapper.insertPersonClassMaster(personClassMaster);
    }

    /**
     * 修改出勤身份
     *
     * @param personClassMaster 出勤身份
     * @return 结果
     */
    @Override
    public int updatePersonClassMaster(PersonClassMaster personClassMaster)
    {
        PersonClassDetail personClassDetail = new PersonClassDetail();
        personClassDetailService.deletePersonClassDetailById(personClassMaster.getId());
        BeanUtils.copyProperties(personClassMaster,personClassDetail);
        personClassDetail.setShiftModeId(personClassMaster.getShiftmodeId());
        personClassDetail.setClassType("0");
        personClassDetail.setPersonClassMasterId(personClassMaster.getId());
        personClassDetailService.setPersonClassDetail(personClassDetail);
        return personClassMasterMapper.updatePersonClassMaster(personClassMaster);
    }

    /**
     * 批量删除出勤身份
     *
     * @param ids 需要删除的出勤身份主键
     * @return 结果
     */
    @Override
    public int deletePersonClassMasterByIds(String[] ids)
    {
        return personClassMasterMapper.deletePersonClassMasterByIds(ids);
    }

    /**
     * 删除出勤身份信息
     *
     * @param id 出勤身份主键
     * @return 结果
     */
    @Override
    public int deletePersonClassMasterById(String id)
    {
        return personClassMasterMapper.deletePersonClassMasterById(id);
    }
}
