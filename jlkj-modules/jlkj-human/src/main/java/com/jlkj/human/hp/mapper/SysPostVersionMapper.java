package com.jlkj.human.hp.mapper;

import com.jlkj.human.hp.domain.SysPostVersion;

import java.util.List;

/**
 * 岗位信息版本数据维护Mapper接口
 *
 * @author 266861
 * @date 2023-03-15
 */
public interface SysPostVersionMapper
{
    /**
     * 查询岗位信息版本数据维护
     *
     * @param id 岗位信息版本数据维护主键
     * @return 岗位信息版本数据维护
     */
    public SysPostVersion selectSysPostVersionById(Long id);

    /**
     * 查询岗位信息版本数据维护列表
     *
     * @param sysPostVersion 岗位信息版本数据维护
     * @return 岗位信息版本数据维护集合
     */
    public List<SysPostVersion> selectSysPostVersionList(SysPostVersion sysPostVersion);

    /**
     * 新增岗位信息版本数据维护
     *
     * @param sysPostVersion 岗位信息版本数据维护
     * @return 结果
     */
    public int insertSysPostVersion(SysPostVersion sysPostVersion);

    /**
     * 修改岗位信息版本数据维护
     *
     * @param sysPostVersion 岗位信息版本数据维护
     * @return 结果
     */
    public int updateSysPostVersion(SysPostVersion sysPostVersion);

    /**
     * 修改历史版本都为非最新
     *
     * @param postId 部门资料变更版本
     * @return 结果
     */
    public int updateisNew(Long postId);

    /**
     * 删除岗位信息版本数据维护
     *
     * @param id 岗位信息版本数据维护主键
     * @return 结果
     */
    public int deleteSysPostVersionById(Long id);

    /**
     * 批量删除岗位信息版本数据维护
     *
     * @param Ids 需要删除的数据主键集合
     * @return 结果
     */
}
