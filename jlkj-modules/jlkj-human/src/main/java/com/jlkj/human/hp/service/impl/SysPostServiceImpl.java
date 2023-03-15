package com.jlkj.human.hp.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.human.hp.domain.SysPost;
import com.jlkj.human.hp.mapper.SysPostMapper;
import com.jlkj.human.hp.service.ISysPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 岗位信息数据维护Service业务层处理
 *
 * @author 266861
 * @date 2023-03-15
 */
@Service
public class SysPostServiceImpl implements ISysPostService
{
    @Autowired
    private SysPostMapper sysPostMapper;

    /**
     * 查询岗位信息数据维护
     *
     * @param postId 岗位信息数据维护主键
     * @return 岗位信息数据维护
     */
    @Override
    public SysPost selectSysPostByPostId(Long postId)
    {
        return sysPostMapper.selectSysPostByPostId(postId);
    }

    /**
     * 查询岗位信息数据维护列表
     *
     * @param sysPost 岗位信息数据维护
     * @return 岗位信息数据维护
     */
    @Override
    public List<SysPost> selectSysPostList(SysPost sysPost)
    {
        return sysPostMapper.selectSysPostList(sysPost);
    }

    /**
     * 新增岗位信息数据维护
     *
     * @param sysPost 岗位信息数据维护
     * @return 结果
     */
    @Override
    public int insertSysPost(SysPost sysPost)
    {
        sysPost.setCreateTime(DateUtils.getNowDate());
        return sysPostMapper.insertSysPost(sysPost);
    }

    /**
     * 修改岗位信息数据维护
     *
     * @param sysPost 岗位信息数据维护
     * @return 结果
     */
    @Override
    public int updateSysPost(SysPost sysPost)
    {
        sysPost.setUpdateTime(DateUtils.getNowDate());
        return sysPostMapper.updateSysPost(sysPost);
    }

    /**
     * 批量删除岗位信息数据维护
     *
     * @param postIds 需要删除的岗位信息数据维护主键
     * @return 结果
     */
    @Override
    public int deleteSysPostByPostIds(Long[] postIds)
    {
        return sysPostMapper.deleteSysPostByPostIds(postIds);
    }

    /**
     * 删除岗位信息数据维护信息
     *
     * @param postId 岗位信息数据维护主键
     * @return 结果
     */
    @Override
    public int deleteSysPostByPostId(Long postId)
    {
        return sysPostMapper.deleteSysPostByPostId(postId);
    }
}
