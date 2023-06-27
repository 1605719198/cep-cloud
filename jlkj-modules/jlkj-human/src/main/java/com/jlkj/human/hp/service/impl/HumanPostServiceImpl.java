package com.jlkj.human.hp.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.human.hp.domain.HumanPost;
import com.jlkj.human.hp.domain.HumanPostVersion;
import com.jlkj.human.hp.mapper.HumanPostMapper;
import com.jlkj.human.hp.mapper.HumanPostVersionMapper;
import com.jlkj.human.hp.service.IHumanDeptService;
import com.jlkj.human.hp.service.IHumanPostService;
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
public class HumanPostServiceImpl implements IHumanPostService
{
    @Autowired
    private HumanPostMapper humanPostMapper;
    @Autowired
    private HumanPostVersionMapper humanPostVersionMapper;
    @Autowired
    private IHumanDeptService sysDeptService;

    /**
     * 查询岗位信息数据维护
     *
     * @param postId 岗位信息数据维护主键
     * @return 岗位信息数据维护
     */
    @Override
    public HumanPost selectSysPostByPostId(Long postId)
    {
        int childCapacity ;
        try {
            childCapacity = humanPostMapper.queryChildCapacity(postId);
        } catch (Exception e) {
            childCapacity=0;
        }
        HumanPost humanPost = humanPostMapper.selectSysPostByPostId(postId);
        humanPost.setChildCapacity((long) childCapacity);
        return humanPost;
    }

    /**
     * 查询岗位信息数据维护列表
     *
     * @param humanPost 岗位信息数据维护
     * @return 岗位信息数据维护
     */
    @Override
    public List<HumanPost> selectSysPostList(HumanPost humanPost)
    {
        return humanPostMapper.selectSysPostList(humanPost);
    }

    /**
     * 新增岗位信息数据维护
     *
     * @param humanPost 岗位信息数据维护
     * @return 结果
     */
    @Override
    public int insertSysPost(HumanPost humanPost) throws Exception
    {
        HumanPost oldsysPost = humanPostMapper.selectSysPostByPostCode(humanPost);
        if(oldsysPost!=null){
            throw new Exception("岗位编码或名称已存在，请重复输入");
        }
        String serialNumber="";
        int addZero = 10;
        try {
            int number = Integer.parseInt(humanPostMapper.querySerialNumber(humanPost).getSerialNumber())+1;
            if(number<addZero){
                serialNumber  ="0"+number;
            }else{
                serialNumber = String.valueOf(number);
            }
        } catch (Exception e) {
            serialNumber= "01";
        }
        humanPost.setSerialNumber(serialNumber);
        String deptCode= (sysDeptService.selectSysDeptByDeptId(humanPost.getOrgId())).getDeptCode();
        String postCode = humanPost.getCompId()+"-"+deptCode+"-"+ humanPost.getJobTitleId()+"-"+ humanPost.getSpecialityId()+"-"+serialNumber;
        humanPost.setPostCode(postCode);

        HumanPostVersion humanPostVersion = new HumanPostVersion();
        humanPost.setCreateTime(DateUtils.getNowDate());
        humanPost.setUpdateTime(DateUtils.getNowDate());
        int insertOk= humanPostMapper.insertSysPost(humanPost);
        if(insertOk>=1){
            BeanUtils.copyProperties(humanPost, humanPostVersion);
            humanPostVersionMapper.insertSysPostVersion(humanPostVersion);
        }
        return insertOk;
    }

    /**
     * 修改岗位信息数据维护
     *
     * @param humanPost 岗位信息数据维护
     * @return 结果
     */
    @Override
    public int updateSysPost(HumanPost humanPost)
    {
        int addZero = 10;
        HumanPostVersion humanPostVersion = new HumanPostVersion();
        humanPost.setUpdateTime(DateUtils.getNowDate());
        String serialNumber="";
        try {
            int number = Integer.parseInt(humanPostMapper.querySerialNumber(humanPost).getSerialNumber())+1;
            if(number<addZero){
                serialNumber  ="0"+number;
            }else{
                serialNumber = String.valueOf(number);
            }
        } catch (Exception e) {
            serialNumber= "01";
        }
        humanPost.setSerialNumber(serialNumber);
        String deptCode= (sysDeptService.selectSysDeptByDeptId(humanPost.getOrgId())).getDeptCode();
        String postCode = humanPost.getCompId()+"-"+deptCode+"-"+ humanPost.getJobTitleId()+"-"+ humanPost.getSpecialityId()+"-"+serialNumber;
        humanPost.setPostCode(postCode);

        int updateOk= humanPostMapper.updateSysPost(humanPost);
        if(updateOk==1){
            BeanUtils.copyProperties(humanPost, humanPostVersion);
            humanPostVersionMapper.updateisNew(humanPostVersion.getPostId());
            humanPostVersionMapper.insertSysPostVersion(humanPostVersion);
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
        return humanPostMapper.deleteSysPostByPostIds(postIds);
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
        return humanPostMapper.deleteSysPostByPostId(postId);
    }

    /**
     * 岗位现员增加
     *
     * @param postId 岗位信息数据维护主键
     * @return 结果
     */
    @Override
    public int updateSysPostNowCapacity(Long postId){
        try{
            HumanPost humanPost = humanPostMapper.selectSysPostByPostId(postId);
            Long number = humanPost.getNowCapacity()+1;
            humanPost.setNowCapacity(number);
            humanPostMapper.updateSysPost(humanPost);
            return Math.toIntExact(number);
        } catch (Exception e) {
            return -1;
        }
    }
}
