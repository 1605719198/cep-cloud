package com.jlkj.human.hd.service;

import java.util.List;
import com.jlkj.human.hd.domain.PersonClassMaster;

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
}
