package com.jlkj.human.hd.service;

import com.jlkj.human.hd.domain.PersonColock;
import com.jlkj.human.hd.domain.PersonColockOrg;

import java.util.List;

/**
 * 人员卡钟Service接口
 *
 * @author 266861
 * @date 2023-03-29
 */
public interface IPersonColockService
{
    /**
     * 查询人员卡钟
     *
     * @param id 人员卡钟主键
     * @return 人员卡钟
     */
    public PersonColock selectPersonColockById(String id);

    /**
     * 查询人员卡钟列表
     *
     * @param personColock 人员卡钟
     * @return 人员卡钟集合
     */
    public List<PersonColock> selectPersonColockList(PersonColock personColock);

    /**
     * 新增人员卡钟
     *
     * @param personColock 人员卡钟
     * @return 结果
     * @throws Exception 抛出异常停止执行
     */
    public int insertPersonColock(PersonColock personColock) throws Exception;

    /**
     * 机构新增人员卡钟
     *
     * @param personColockOrg 部门卡钟
     * @return 结果
     */
    public int insertPersonColockByDept(PersonColockOrg personColockOrg);

    /**
     * 修改人员卡钟
     *
     * @param personColock 人员卡钟
     * @return 结果
     * @throws Exception 抛出异常停止执行
     */
    public int updatePersonColock(PersonColock personColock) throws Exception ;



    /**
     * 批量删除人员卡钟
     *
     * @param ids 需要删除的人员卡钟主键集合
     * @return 结果
     */
    public int deletePersonColockByIds(String[] ids);

    /**
     * 删除人员卡钟信息
     *
     * @param id 人员卡钟主键
     * @return 结果
     */
    public int deletePersonColockById(String id);
}
