package com.jlkj.human.hd.mapper;

import com.jlkj.human.hd.domain.PersonClassDetail;

import java.util.List;

/**
 * 人员排班明细Mapper接口
 * 
 * @author 266861
 * @date 2023-04-12
 */
public interface PersonClassDetailMapper 
{
    /**
     * 查询人员排班明细
     * 
     * @param id 人员排班明细主键
     * @return 人员排班明细
     */
    public PersonClassDetail selectPersonClassDetailById(String id);

    /**
     * 查询人员排班明细列表
     * 
     * @param personClassDetail 人员排班明细
     * @return 人员排班明细集合
     */
    public List<PersonClassDetail> selectPersonClassDetailList(PersonClassDetail personClassDetail);

    /**
     * 新增人员排班明细
     * 
     * @param personClassDetail 人员排班明细
     * @return 结果
     */
    public int insertPersonClassDetail(PersonClassDetail personClassDetail);

    /**
     * 修改人员排班明细
     * 
     * @param personClassDetail 人员排班明细
     * @return 结果
     */
    public int updatePersonClassDetail(PersonClassDetail personClassDetail);

    /**
     * 删除人员排班明细
     * 
     * @param id 人员排班明细主键
     * @return 结果
     */
    public int deletePersonClassDetailById(String id);

    /**
     * 批量删除人员排班明细
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePersonClassDetailByIds(String[] ids);

}
