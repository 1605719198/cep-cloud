package com.jlkj.human.hp.service;

import com.jlkj.human.hp.domain.HumanPostVersion;

import java.util.List;

/**
 * 岗位信息版本数据维护Service接口
 *
 * @author 266861
 * @date 2023-03-15
 */
public interface IHumanPostVersionService
{
    /**
     * 查询岗位信息版本数据维护
     *
     * @param id 岗位信息版本数据维护主键
     * @return 岗位信息版本数据维护
     */
    public HumanPostVersion selectSysPostVersionById(Long id);

    /**
     * 查询岗位信息版本数据维护列表
     *
     * @param humanPostVersion 岗位信息版本数据维护
     * @return 岗位信息版本数据维护集合
     */
    public List<HumanPostVersion> selectSysPostVersionList(HumanPostVersion humanPostVersion);

    /**
     * 新增岗位信息版本数据维护
     *
     * @param humanPostVersion 岗位信息版本数据维护
     * @return 结果
     */
    public int insertSysPostVersion(HumanPostVersion humanPostVersion);

    /**
     * 修改岗位信息版本数据维护
     *
     * @param humanPostVersion 岗位信息版本数据维护
     * @return 结果
     */
    public int updateSysPostVersion(HumanPostVersion humanPostVersion);


    /**
     * 删除岗位信息版本数据维护信息
     *
     * @param id 岗位信息版本数据维护主键
     * @return 结果
     */
    public int deleteSysPostVersionById(Long id);
}
