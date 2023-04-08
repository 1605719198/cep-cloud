package com.jlkj.human.hd.service;

import java.util.List;
import com.jlkj.human.hd.domain.PersonColockOrg;

/**
 * 人员卡钟组织机构Service接口
 * 
 * @author 266861
 * @date 2023-03-31
 */
public interface IPersonColockOrgService 
{
    /**
     * 查询人员卡钟组织机构
     * 
     * @param id 人员卡钟组织机构主键
     * @return 人员卡钟组织机构
     */
    public PersonColockOrg selectPersonColockOrgById(String id);

    /**
     * 查询人员卡钟组织机构列表
     * 
     * @param personColockOrg 人员卡钟组织机构
     * @return 人员卡钟组织机构集合
     */
    public List<PersonColockOrg> selectPersonColockOrgList(PersonColockOrg personColockOrg);

    /**
     * 新增人员卡钟组织机构
     * 
     * @param personColockOrg 人员卡钟组织机构
     * @return 结果
     * @throws Exception 抛出异常停止执行
     */
    public int insertPersonColockOrg(PersonColockOrg personColockOrg)throws Exception;

    /**
     * 修改人员卡钟组织机构
     * 
     * @param personColockOrg 人员卡钟组织机构
     * @return 结果
     * @throws Exception 抛出异常停止执行
     */
    public int updatePersonColockOrg(PersonColockOrg personColockOrg)throws Exception;

    /**
     * 批量删除人员卡钟组织机构
     * 
     * @param ids 需要删除的人员卡钟组织机构主键集合
     * @return 结果
     */
    public int deletePersonColockOrgByIds(String[] ids);

    /**
     * 删除人员卡钟组织机构信息
     * 
     * @param id 人员卡钟组织机构主键
     * @return 结果
     */
    public int deletePersonColockOrgById(String id);
}
