package com.jlkj.human.hr.mapper;

import com.jlkj.human.hr.domain.RecruitingNeeds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 招聘需求Mapper接口
 * 
 * @author 266861
 * @date 2023-07-18
 */
@Repository
public interface RecruitingNeedsMapper 
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
     * @param recruitingNeeds 招聘需求
     * @return 招聘需求集合
     */
    public List<RecruitingNeeds> selectRecruitingNeedsList(RecruitingNeeds recruitingNeeds);

    /**
     * 新增招聘需求
     * 
     * @param recruitingNeeds 招聘需求
     * @return 结果
     */
    public int insertRecruitingNeeds(RecruitingNeeds recruitingNeeds);

    /**
     * 修改招聘需求
     * 
     * @param recruitingNeeds 招聘需求
     * @return 结果
     */
    public int updateRecruitingNeeds(RecruitingNeeds recruitingNeeds);

    /**
     * 删除招聘需求
     * 
     * @param uuid 招聘需求主键
     * @return 结果
     */
    public int deleteRecruitingNeedsByUuid(String uuid);

    /**
     * 批量删除招聘需求
     * 
     * @param uuids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRecruitingNeedsByUuids(String[] uuids);
}
