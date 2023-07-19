package com.jlkj.human.hr.service;

import java.util.List;
import com.jlkj.human.hr.domain.RecruitingNeeds;

/**
 * 招聘需求Service接口
 * 
 * @author 266861
 * @date 2023-07-18
 */
public interface IRecruitingNeedsService 
{
    /**
     * 查询招聘需求
     * 
     * @param uuid 招聘需求主键
     * @return 招聘需求
     */
    public RecruitingNeeds selectRecruitingNeedsByUuid(String uuid);

    /**
     * 查询招聘需求列表
     *
     * @author 266861
     * @date 2023-07-18
     * @param recruitingNeeds 招聘需求
     * @return 招聘需求集合
     */
    public List<RecruitingNeeds> selectRecruitingNeedsList(RecruitingNeeds recruitingNeeds);

    /**
     * 新增招聘需求
     *
     * @author 266861
     * @date 2023-07-18
     * @param recruitingNeeds 招聘需求
     * @return 结果
     */
    public int insertRecruitingNeeds(RecruitingNeeds recruitingNeeds);

    /**
     * 修改招聘需求
     *
     * @author 266861
     * @date 2023-07-18
     * @param recruitingNeeds 招聘需求
     * @return 结果
     */
    public int updateRecruitingNeeds(RecruitingNeeds recruitingNeeds);

    /**
     * 批量删除招聘需求
     *
     * @author 266861
     * @date 2023-07-18
     * @param uuids 需要删除的招聘需求主键集合
     * @return 结果
     */
    public int deleteRecruitingNeedsByUuids(String[] uuids);

    /**
     * 删除招聘需求信息
     *
     * @author 266861
     * @date 2023-07-18
     * @param uuid 招聘需求主键
     * @return 结果
     */
    public int deleteRecruitingNeedsByUuid(String uuid);
}
