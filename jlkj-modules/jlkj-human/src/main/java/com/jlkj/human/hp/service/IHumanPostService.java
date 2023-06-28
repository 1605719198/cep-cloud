package com.jlkj.human.hp.service;

import com.jlkj.human.hp.domain.HumanPost;

import java.util.List;

/**
 * 岗位信息数据维护Service接口
 * 
 * @author 266861
 * @date 2023-03-15
 */
public interface IHumanPostService
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
     * @throws Exception 抛出异常停止执行
     */
    public int insertSysPost(HumanPost humanPost) throws Exception;

    /**
     * 修改岗位信息数据维护
     * 
     * @param humanPost 岗位信息数据维护
     * @return 结果
     */
    public int updateSysPost(HumanPost humanPost);

    /**
     * 批量删除岗位信息数据维护
     * 
     * @param postIds 需要删除的岗位信息数据维护主键集合
     * @return 结果
     */
    public int deleteSysPostByPostIds(Long[] postIds);

    /**
     * 删除岗位信息数据维护信息
     * 
     * @param postId 岗位信息数据维护主键
     * @return 结果
     */
    public int deleteSysPostByPostId(Long postId);

    /**
     * 岗位现员增加
     *
     * @param postId 岗位信息数据维护主键
     * @return 结果
     */
    public int updateSysPostNowCapacity(Long postId);
}
