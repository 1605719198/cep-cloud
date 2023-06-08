package com.jlkj.human.hs.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.UUID;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hm.domain.Personnel;
import com.jlkj.human.hm.service.IPersonnelService;
import com.jlkj.human.hs.domain.PersonalSalary;
import com.jlkj.human.hs.mapper.PersonalSalaryMapper;
import com.jlkj.human.hs.service.IPersonalSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 薪资核定Service业务层处理
 *
 * @author 266861
 * @date 2023-06-01
 */
@Service
public class PersonalSalaryServiceImpl implements IPersonalSalaryService
{
    @Autowired
    private PersonalSalaryMapper personalSalaryMapper;
    @Autowired
    private IPersonnelService iPersonnelService;

    /**
     * 查询薪资核定
     *
     * @param id 薪资核定主键
     * @return 薪资核定
     */
    @Override
    public PersonalSalary selectPersonalSalaryById(String id)
    {
        return personalSalaryMapper.selectPersonalSalaryById(id);
    }

    /**
     * 查询薪资核定列表
     *
     * @param personalSalary 薪资核定
     * @return 薪资核定
     */
    @Override
    public List<PersonalSalary> selectPersonalSalaryList(PersonalSalary personalSalary)
    {
        return personalSalaryMapper.selectPersonalSalaryList(personalSalary);
    }

    /**
     * 新增薪资核定
     *
     * @param personalSalary 薪资核定
     * @return 结果
     */
    @Override
    public int insertPersonalSalary(PersonalSalary personalSalary)
    {
        List<PersonalSalary> list = personalSalaryMapper.selectPersonalSalaryByEmp(personalSalary);
        PersonalSalary lastData = new PersonalSalary();
        //最大生效日期与版本
        Date maxDate;
        long versionNo;
        if(list.size()==0){
            maxDate = DateUtils.parseDate(DateUtils.getDate());
            versionNo = 1L;
        } else {
            lastData = list.get(0);
            maxDate = lastData.getEffectDate();
            versionNo = lastData.getVersionNo()+1;
        }
        //如果生效日期大于最大生效日期则新增
        if(personalSalary.getEffectDate().getTime()>maxDate.getTime()){
            if(list.size()!=0){
                PersonalSalary newItem = new PersonalSalary();
                newItem.setId(lastData.getId());
                newItem.setIsNew("0");
                personalSalaryMapper.updatePersonalSalary(newItem);
            }
            personalSalary.setVersionNo(versionNo);
            PersonalSalary salary = setData(personalSalary);
            int number = personalSalaryMapper.insertPersonalSalary(salary);

            return number;
        }else{
            return -1;
        }
    }

    /**
     * 薪资核定主档数据处理
     *
     * @param personalSalary 薪资核定
     * @return 结果
     */
    @SuppressWarnings("unchecked")
    public PersonalSalary setData(PersonalSalary personalSalary){
        Object obj = iPersonnelService.selectPersonnelInfo(personalSalary.getEmpNo());
        AjaxResult result = (AjaxResult) obj;
        Object result2 = result.get("data");
        Map<String, Object> resultMap = (Map<String, Object>) result2;
        System.out.println(resultMap);
        List<Personnel> personnelList = (List<Personnel>) resultMap.get("personnelList");
        //定义转化为字符串的日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
        if (personnelList.size() != 0) {
            Personnel personnel = personnelList.get(0);
            personalSalary.setEmpId(personnel.getId());
            personalSalary.setMainPostName(personnel.getPostName());
            personalSalary.setPostLevel(personnel.getPostLevelName());
            personalSalary.setIsNew("1");
            personalSalary.setId(UUID.randomUUID().toString().substring(0, 32));
            personalSalary.setCreatorId(SecurityUtils.getUserId().toString());
            personalSalary.setCreator(SecurityUtils.getNickName());
            personalSalary.setCreatorNo(SecurityUtils.getUsername());
            personalSalary.setCreateDate(new Date());
        }
        return personalSalary;
    }

    /**
     * 修改薪资核定
     *
     * @param personalSalary 薪资核定
     * @return 结果
     */
    @Override
    public int updatePersonalSalary(PersonalSalary personalSalary)
    {
        List<PersonalSalary> list = personalSalaryMapper.selectPersonalSalaryByEmp(personalSalary);
        PersonalSalary lastData,lastData2;
        if(personalSalary.getId()!=null){
            lastData = list.get(0);
            if((personalSalary.getEffectDate().getTime()>=lastData.getEffectDate().getTime())){
                PersonalSalary preData = personalSalaryMapper.selectPersonalSalaryById(personalSalary.getId());
                //如果生效日期不变则修改，反之新增一版本
                if(personalSalary.getEffectDate().getTime() ==preData.getEffectDate().getTime()){
                    personalSalary.setCreatorId(SecurityUtils.getUserId().toString());
                    personalSalary.setCreator(SecurityUtils.getNickName());
                    personalSalary.setCreatorNo(SecurityUtils.getUsername());
                    personalSalary.setCreateDate(new Date());
                    personalSalaryMapper.updatePersonalSalary(personalSalary);
                    return 1;
                }else{
                    return insertPersonalSalary(personalSalary);
                }
            }else{
                return -1;
            }
        }else{
            return -1;
        }
    }

    /**
     * 批量删除薪资核定
     *
     * @param ids 需要删除的薪资核定主键
     * @return 结果
     */
    @Override
    public int deletePersonalSalaryByIds(String[] ids)
    {
        return personalSalaryMapper.deletePersonalSalaryByIds(ids);
    }

    /**
     * 删除薪资核定信息
     *
     * @param id 薪资核定主键
     * @return 结果
     */
    @Override
    public int deletePersonalSalaryById(String id)
    {
        return personalSalaryMapper.deletePersonalSalaryById(id);
    }
}
