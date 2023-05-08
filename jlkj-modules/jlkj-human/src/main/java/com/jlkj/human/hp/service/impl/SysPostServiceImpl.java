package com.jlkj.human.hp.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.human.hp.domain.SysPost;
import com.jlkj.human.hp.domain.SysPostVersion;
import com.jlkj.human.hp.mapper.SysPostMapper;
import com.jlkj.human.hp.mapper.SysPostVersionMapper;
import com.jlkj.human.hp.service.ISysDeptService;
import com.jlkj.human.hp.service.ISysPostService;
import org.springframework.beans.BeanUtils;
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
    @Autowired
    private SysPostVersionMapper sysPostVersionMapper;
    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 查询岗位信息数据维护
     *
     * @param postId 岗位信息数据维护主键
     * @return 岗位信息数据维护
     */
    @Override
    public SysPost selectSysPostByPostId(Long postId)
    {
        int childCapacity ;
        try {
            childCapacity =sysPostMapper.queryChildCapacity(postId);
        } catch (Exception e) {
            childCapacity=0;
        }
        SysPost sysPost = sysPostMapper.selectSysPostByPostId(postId);
        sysPost.setChildCapacity((long) childCapacity);
        return sysPost;
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
    public int insertSysPost(SysPost sysPost) throws Exception
    {
        SysPost oldsysPost = sysPostMapper.selectSysPostByPostCode(sysPost);
        if(oldsysPost!=null){
            throw new Exception("岗位编码或名称已存在，请重复输入");
        }
        String serialNumber="";
        int addZero = 10;
        try {
            int number = Integer.parseInt(sysPostMapper.querySerialNumber(sysPost).getSerialNumber())+1;
            if(number<addZero){
                serialNumber  ="0"+number;
            }else{
                serialNumber = String.valueOf(number);
            }
        } catch (Exception e) {
            serialNumber= "01";
        }
        sysPost.setSerialNumber(serialNumber);
        String deptCode= (sysDeptService.selectSysDeptByDeptId(sysPost.getOrgId())).getDeptCode();
        String postCode = sysPost.getCompId()+"-"+deptCode+"-"+sysPost.getJobTitleId()+"-"+sysPost.getSpecialityId()+"-"+serialNumber;
        sysPost.setPostCode(postCode);

        SysPostVersion sysPostVersion = new SysPostVersion();
        sysPost.setCreateTime(DateUtils.getNowDate());
        sysPost.setUpdateTime(DateUtils.getNowDate());
        int insertOk=sysPostMapper.insertSysPost(sysPost);
        if(insertOk>=1){
            BeanUtils.copyProperties(sysPost,sysPostVersion);
            sysPostVersionMapper.insertSysPostVersion(sysPostVersion);
        }
        return insertOk;
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
        int addZero = 10;
        SysPostVersion sysPostVersion = new SysPostVersion();
        sysPost.setUpdateTime(DateUtils.getNowDate());
        String serialNumber="";
        try {
            int number = Integer.parseInt(sysPostMapper.querySerialNumber(sysPost).getSerialNumber())+1;
            if(number<addZero){
                serialNumber  ="0"+number;
            }else{
                serialNumber = String.valueOf(number);
            }
        } catch (Exception e) {
            serialNumber= "01";
        }
        sysPost.setSerialNumber(serialNumber);
        String deptCode= (sysDeptService.selectSysDeptByDeptId(sysPost.getOrgId())).getDeptCode();
        String postCode = sysPost.getCompId()+"-"+deptCode+"-"+sysPost.getJobTitleId()+"-"+sysPost.getSpecialityId()+"-"+serialNumber;
        sysPost.setPostCode(postCode);

        int updateOk=sysPostMapper.updateSysPost(sysPost);
        if(updateOk==1){
            BeanUtils.copyProperties(sysPost,sysPostVersion);
            sysPostVersionMapper.updateisNew(sysPostVersion.getPostId());
            sysPostVersionMapper.insertSysPostVersion(sysPostVersion);
        }
        return updateOk;
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
