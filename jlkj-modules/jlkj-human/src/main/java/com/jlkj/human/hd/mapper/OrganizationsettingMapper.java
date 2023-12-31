package com.jlkj.human.hd.mapper;

import com.jlkj.human.hd.domain.Organizationsetting;

import java.util.List;

/**
 * 机构参数Mapper接口
 * 
 * @author 266861
 * @date 2023-03-28
 */
public interface OrganizationsettingMapper 
{
    /**
     * 查询机构参数
     * 
     * @param id 机构参数主键
     * @return 机构参数
     */
    public Organizationsetting selectOrganizationsettingById(String id);

    /**
     * 查询机构参数列表
     * 
     * @param organizationsetting 机构参数
     * @return 机构参数集合
     */
    public List<Organizationsetting> selectOrganizationsettingList(Organizationsetting organizationsetting);

    /**
     * 新增机构参数
     * 
     * @param organizationsetting 机构参数
     * @return 结果
     */
    public int insertOrganizationsetting(Organizationsetting organizationsetting);

    /**
     * 修改机构参数
     * 
     * @param organizationsetting 机构参数
     * @return 结果
     */
    public int updateOrganizationsetting(Organizationsetting organizationsetting);

    /**
     * 删除机构参数
     * 
     * @param id 机构参数主键
     * @return 结果
     */
    public int deleteOrganizationsettingById(String id);

    /**
     * 批量删除机构参数
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrganizationsettingByIds(String[] ids);
}
