package com.jlkj.human.hp.mapper;

import com.jlkj.human.hp.domain.HumanDeptVersion;

import java.util.List;

/**
 * 部门资料变更版本Mapper接口
 * 
 * @author 266861
 * @date 2023-03-09
 */
public interface HumanDeptVersionMapper
{
    /**
     * 查询部门资料变更版本
     * 
     * @param id 部门资料变更版本主键
     * @return 部门资料变更版本
     */
    public HumanDeptVersion selectSysDeptVersionById(Long id);

    /**
     * 查询部门资料变更版本列表
     * 
     * @param humanDeptVersion 部门资料变更版本
     * @return 部门资料变更版本集合
     */
    public List<HumanDeptVersion> selectSysDeptVersionList(HumanDeptVersion humanDeptVersion);

    /**
     * 新增部门资料变更版本
     * 
     * @param humanDeptVersion 部门资料变更版本
     * @return 结果
     */
    public int insertSysDeptVersion(HumanDeptVersion humanDeptVersion);

    /**
     * 修改部门资料变更版本
     * 
     * @param humanDeptVersion 部门资料变更版本
     * @return 结果
     */
    public int updateSysDeptVersion(HumanDeptVersion humanDeptVersion);

    /**
     * 修改历史版本都为非最新
     *
     * @param deptId 部门资料变更版本
     * @return 结果
     */
    public int updateisNew(Long deptId);

    /**
     * 删除部门资料变更版本
     * 
     * @param id 部门资料变更版本主键
     * @return 结果
     */
    public int deleteSysDeptVersionById(Long id);

    /**
     * 批量删除部门资料变更版本
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysDeptVersionByIds(Long[] ids);
}
