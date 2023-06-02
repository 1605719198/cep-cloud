package com.jlkj.human.hd.service;

import com.jlkj.human.hd.domain.PersonClassMaster;

import java.util.List;

/**
 * 出勤身份Service接口
 * 
 * @author 266861
 * @date 2023-04-12
 */
public interface IPersonClassMasterService 
{
    /**
     * 查询出勤身份
     * 
     * @param id 出勤身份主键
     * @return 出勤身份
     */
    public PersonClassMaster selectPersonClassMasterById(String id);

    /**
     * 查询某员工出勤身份
     *
     * @param personClassMaster 员工信息
     * @return 出勤身份
     */
    public PersonClassMaster selectClassMasterByPerson(PersonClassMaster personClassMaster);

    /**
     * 查询出勤身份列表
     * 
     * @param personClassMaster 出勤身份
     * @return 出勤身份集合
     */
    public List<PersonClassMaster> selectPersonClassMasterList(PersonClassMaster personClassMaster);

    /**
     * 新增出勤身份
     * 
     * @param personClassMaster 出勤身份
     * @return 结果
     */
    public int insertPersonClassMaster(PersonClassMaster personClassMaster);

    /**
     * 批量新增出勤身份
     *
     * @param personClassMasterList 出勤身份
     * @return 结果
     */
    public int insertBatchPersonClassMaster(List<PersonClassMaster> personClassMasterList);

    /**
     * 修改出勤身份
     * 
     * @param personClassMaster 出勤身份
     * @return 结果
     */
    public int updatePersonClassMaster(PersonClassMaster personClassMaster);

    /**
     * 批量删除出勤身份
     * 
     * @param ids 需要删除的出勤身份主键集合
     * @return 结果
     */
    public int deletePersonClassMasterByIds(String[] ids);

    /**
     * 删除出勤身份信息
     * 
     * @param id 出勤身份主键
     * @return 结果
     */
    public int deletePersonClassMasterById(String id);

    /**
     * 导入批量排班资料数据
     *
     * @param personClassMasterList 批量排班资料列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<PersonClassMaster> personClassMasterList, Boolean isUpdateSupport, String operName);
}
