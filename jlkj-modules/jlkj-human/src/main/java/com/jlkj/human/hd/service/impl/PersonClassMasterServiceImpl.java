package com.jlkj.human.hd.service.impl;

import com.jlkj.common.core.exception.ServiceException;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanValidators;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hd.domain.PersonClassDetail;
import com.jlkj.human.hd.domain.PersonClassMaster;
import com.jlkj.human.hd.mapper.PersonClassMasterMapper;
import com.jlkj.human.hd.service.IPersonClassDetailService;
import com.jlkj.human.hd.service.IPersonClassMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Validator;
import java.util.Date;
import java.util.List;

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
    @Autowired
    protected Validator validator;

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
     * 查询某员工出勤身份
     *
     * @param personClassMaster 员工信息
     * @return 出勤身份
     */
    @Override
    public PersonClassMaster selectClassMasterByPerson(PersonClassMaster personClassMaster){
        return personClassMasterMapper.selectClassMasterByPerson(personClassMaster);
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
        personClassMaster.setId(IdUtils.simpleUUID());
        PersonClassDetail personClassDetail = new PersonClassDetail();
        BeanUtils.copyProperties(personClassMaster,personClassDetail);
        personClassDetail.setShiftModeId(personClassMaster.getShiftmodeId());
        personClassDetail.setClassType("0");
        personClassDetail.setPersonClassMasterId(personClassMaster.getId());
        int detailResult = personClassDetailService.setPersonClassDetail(personClassDetail);
        if(detailResult>0){
            return personClassMasterMapper.insertPersonClassMaster(personClassMaster);
        }else{
            return detailResult;
        }
    }

    /**
     * 批量新增出勤身份
     *
     * @param personClassMasterList 出勤身份
     * @return 结果
     */
    @Override
    public int insertBatchPersonClassMaster(List<PersonClassMaster> personClassMasterList){
        int number = 0;
        for(PersonClassMaster personClassMaster :personClassMasterList){
            personClassMaster.setCreator(SecurityUtils.getNickName());
            personClassMaster.setCreatorId(SecurityUtils.getUsername());
            personClassMaster.setCreateDate(new Date());
            insertPersonClassMaster(personClassMaster);
            number++;
        }
        return number;
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
    /**
     * 导入批量排班资料数据
     *
     * @param personClassMasterList 批量排班资料列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<PersonClassMaster> personClassMasterList, Boolean isUpdateSupport, String operName){
        if (StringUtils.isNull(personClassMasterList) || personClassMasterList.size() == 0) {
            throw new ServiceException("批量排班数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (PersonClassMaster personClassMaster : personClassMasterList) {
            try {
                BeanValidators.validateWithException(validator, personClassMaster);
                personClassMaster.setStatus(String.valueOf(1));
                personClassMaster.setRemark("2");
                personClassMaster.setCreator(SecurityUtils.getNickName());
                personClassMaster.setCreatorId(SecurityUtils.getUsername());
                personClassMaster.setCreateDate(new Date());
                insertPersonClassMaster(personClassMaster);
                successNum++;
                successMsg.append("<br/>" + successNum + "、员工工号 " + personClassMaster.getEmpId() + " 导入成功");
                if (isUpdateSupport) {
                    
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、员工工号 " + personClassMaster.getEmpId() + " 更新成功");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、员工工号 " + personClassMaster.getEmpId() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }
}
