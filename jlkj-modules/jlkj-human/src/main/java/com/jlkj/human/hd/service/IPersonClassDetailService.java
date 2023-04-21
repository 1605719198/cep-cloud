package com.jlkj.human.hd.service;

import java.util.ArrayList;
import java.util.List;
import com.jlkj.human.hd.domain.PersonClassDetail;

/**
 * 人员排班明细Service接口
 * 
 * @author 266861
 * @date 2023-04-12
 */
public interface IPersonClassDetailService 
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
     * 调班
     *
     * @param list 人员排班明细列表
     * @return 结果
     */
    public int changeDetail(ArrayList<PersonClassDetail> list);

    /**
     * 排班操作
     *
     * @param personClassDetail 人员排班
     * @return 结果
     */
    public int setPersonClassDetail(PersonClassDetail personClassDetail);

    /**
     * 修改人员排班明细
     * 
     * @param personClassDetail 人员排班明细
     * @return 结果
     */
    public int updatePersonClassDetail(PersonClassDetail personClassDetail);

    /**
     * 批量删除人员排班明细
     * 
     * @param ids 需要删除的人员排班明细主键集合
     * @return 结果
     */
    public int deletePersonClassDetailByIds(String[] ids);

    /**
     * 删除人员排班明细信息
     * 
     * @param id 人员排班明细主键
     * @return 结果
     */
    public int deletePersonClassDetailById(String id);
}
