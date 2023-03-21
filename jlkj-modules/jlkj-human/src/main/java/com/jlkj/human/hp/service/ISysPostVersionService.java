package com.jlkj.human.hp.service;

import com.jlkj.human.hp.domain.SysPostVersion;

import java.util.List;

/**
 * 岗位信息版本数据维护Service接口
 *
 * @author 266861
 * @date 2023-03-15
 */
public interface ISysPostVersionService
{
    /**
     * 查询岗位信息版本数据维护
     *
     * @param Id 岗位信息版本数据维护主键
     * @return 岗位信息版本数据维护
     */
    public SysPostVersion selectSysPostVersionById(Long Id);

    /**
     * 查询岗位信息版本数据维护列表
     *
     * @param SysPostVersion 岗位信息版本数据维护
     * @return 岗位信息版本数据维护集合
     */
    public List<SysPostVersion> selectSysPostVersionList(SysPostVersion SysPostVersion);

    /**
     * 新增岗位信息版本数据维护
     *
     * @param SysPostVersion 岗位信息版本数据维护
     * @return 结果
     */
    public int insertSysPostVersion(SysPostVersion SysPostVersion);

    /**
     * 修改岗位信息版本数据维护
     *
     * @param SysPostVersion 岗位信息版本数据维护
     * @return 结果
     */
    public int updateSysPostVersion(SysPostVersion SysPostVersion);

    /**
     * 批量删除岗位信息版本数据维护
     *
     * @param Ids 需要删除的岗位信息版本数据维护主键集合
     * @return 结果
     */
    public int deleteSysPostVersionByIds(Long[] Ids);

    /**
     * 删除岗位信息版本数据维护信息
     *
     * @param Id 岗位信息版本数据维护主键
     * @return 结果
     */
    public int deleteSysPostVersionById(Long Id);
}
