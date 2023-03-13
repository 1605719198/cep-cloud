package com.jlkj.human.hp.mapper;

import com.jlkj.human.hp.domain.SysDeptVersion;

import java.util.List;

/**
 * 部门资料变更版本Mapper接口
 * 
 * @author 266861
 * @date 2023-03-09
 */
public interface SysDeptVersionMapper 
{
    /**
     * 查询部门资料变更版本
     * 
     * @param id 部门资料变更版本主键
     * @return 部门资料变更版本
     */
    public SysDeptVersion selectSysDeptVersionById(Long id);

    /**
     * 查询部门资料变更版本列表
     * 
     * @param sysDeptVersion 部门资料变更版本
     * @return 部门资料变更版本集合
     */
    public List<SysDeptVersion> selectSysDeptVersionList(SysDeptVersion sysDeptVersion);

    /**
     * 新增部门资料变更版本
     * 
     * @param sysDeptVersion 部门资料变更版本
     * @return 结果
     */
    public int insertSysDeptVersion(SysDeptVersion sysDeptVersion);

    /**
     * 修改部门资料变更版本
     * 
     * @param sysDeptVersion 部门资料变更版本
     * @return 结果
     */
    public int updateSysDeptVersion(SysDeptVersion sysDeptVersion);

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
