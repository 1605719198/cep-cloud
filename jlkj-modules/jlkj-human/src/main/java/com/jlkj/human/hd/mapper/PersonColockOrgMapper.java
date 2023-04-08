package com.jlkj.human.hd.mapper;

import java.util.List;

import com.jlkj.human.hd.domain.PersonColock;
import com.jlkj.human.hd.domain.PersonColockOrg;

/**
 * 人员卡钟组织机构Mapper接口
 * 
 * @author 266861
 * @date 2023-03-31
 */
public interface PersonColockOrgMapper 
{
    /**
     * 查询人员卡钟组织机构
     * 
     * @param id 人员卡钟组织机构主键
     * @return 人员卡钟组织机构
     */
    public PersonColockOrg selectPersonColockOrgById(String id);

    /**
     * 查询最新生效卡钟
     *
     * @param personColockOrg 部门卡钟
     * @return 人员卡钟
     */
    public PersonColockOrg queryLastEffectData(PersonColockOrg personColockOrg);

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
     */
    public int insertPersonColockOrg(PersonColockOrg personColockOrg);

    /**
     * 修改人员卡钟组织机构
     * 
     * @param personColockOrg 人员卡钟组织机构
     * @return 结果
     */
    public int updatePersonColockOrg(PersonColockOrg personColockOrg);

    /**
     * 删除人员卡钟组织机构
     * 
     * @param id 人员卡钟组织机构主键
     * @return 结果
     */
    public int deletePersonColockOrgById(String id);

    /**
     * 批量删除人员卡钟组织机构
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePersonColockOrgByIds(String[] ids);
}
