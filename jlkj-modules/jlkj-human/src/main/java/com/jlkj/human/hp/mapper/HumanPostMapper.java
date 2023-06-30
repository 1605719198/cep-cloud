package com.jlkj.human.hp.mapper;

import com.jlkj.human.hp.domain.HumanPost;

import java.util.List;

/**
 * 岗位信息数据维护Mapper接口
 * 
 * @author 266861
 * @date 2023-03-15
 */
public interface HumanPostMapper
{
    /**
     * 查询岗位信息数据维护
     * 
     * @param postId 岗位信息数据维护主键
     * @return 岗位信息数据维护
     */
    public HumanPost selectSysPostByPostId(Long postId);

    /**
     * 查询岗位信息数据维护列表
     * 
     * @param humanPost 岗位信息数据维护
     * @return 岗位信息数据维护集合
     */
    public List<HumanPost> selectSysPostList(HumanPost humanPost);

    /**
     * 新增岗位信息数据维护
     * 
     * @param humanPost 岗位信息数据维护
     * @return 结果
     */
    public int insertSysPost(HumanPost humanPost);

    /**
     * 查询流水号
     *
     * @param humanPost 岗位信息数据维护
     * @return 结果
     */
    public HumanPost querySerialNumber(HumanPost humanPost);

    /**
     * 根据部门编码查询信息
     *
     * @param humanPost 部门
     * @return 部门信息
     */
    public HumanPost selectSysPostByPostCode(HumanPost humanPost);

    /**
     * 修改岗位信息数据维护
     * 
     * @param humanPost 岗位信息数据维护
     * @return 结果
     */
    public int updateSysPost(HumanPost humanPost);

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
