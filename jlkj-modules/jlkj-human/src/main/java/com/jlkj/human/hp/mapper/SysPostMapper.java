package com.jlkj.human.hp.mapper;

import com.jlkj.human.hp.domain.SysPost;

import java.util.List;

/**
 * 岗位信息数据维护Mapper接口
 * 
 * @author 266861
 * @date 2023-03-15
 */
public interface SysPostMapper 
{
    /**
     * 查询岗位信息数据维护
     * 
     * @param postId 岗位信息数据维护主键
     * @return 岗位信息数据维护
     */
    public SysPost selectSysPostByPostId(Long postId);

    /**
     * 查询岗位信息数据维护列表
     * 
     * @param sysPost 岗位信息数据维护
     * @return 岗位信息数据维护集合
     */
    public List<SysPost> selectSysPostList(SysPost sysPost);

    /**
     * 新增岗位信息数据维护
     * 
     * @param sysPost 岗位信息数据维护
     * @return 结果
     */
    public int insertSysPost(SysPost sysPost);

    /**
     * 查询流水号
     *
     * @param sysPost 岗位信息数据维护
     * @return 结果
     */
    public SysPost querySerialNumber(SysPost sysPost);

    /**
     * 根据部门编码查询信息
     *
     * @param sysPost 部门
     * @return 部门信息
     */
    public SysPost selectSysPostByPostCode(SysPost sysPost);

    /**
     * 修改岗位信息数据维护
     * 
     * @param sysPost 岗位信息数据维护
     * @return 结果
     */
    public int updateSysPost(SysPost sysPost);

    /**
     * 查询岗位下级定员
     *
     * @param postId 岗位id
     * @return 结果
     */
    public int queryChildCapacity(Long postId);

    /**
     * 删除岗位信息数据维护
     * 
     * @param postId 岗位信息数据维护主键
     * @return 结果
     */
    public int deleteSysPostByPostId(Long postId);

    /**
     * 批量删除岗位信息数据维护
     * 
     * @param postIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysPostByPostIds(Long[] postIds);
}
