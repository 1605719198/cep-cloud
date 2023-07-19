package com.jlkj.human.hr.service.impl;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hp.service.IHumanDeptService;
import com.jlkj.human.hr.domain.RecruitingNeeds;
import com.jlkj.human.hr.mapper.RecruitingNeedsMapper;
import com.jlkj.human.hr.service.IRecruitingNeedsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 招聘需求Service业务层处理
 *
 * @author 266861
 * @date 2023-07-18
 * @version 1.0
 */
@Service
public class RecruitingNeedsServiceImpl implements IRecruitingNeedsService
{
    @Autowired
    private RecruitingNeedsMapper recruitingNeedsMapper;

    @Autowired
    private IHumanDeptService humanDeptService;

    /**
     * 查询招聘需求
     *
     * @author 266861
     * @date 2023-07-18
     * @param uuid 招聘需求主键
     * @return 招聘需求
     */
    @Override
    public RecruitingNeeds selectRecruitingNeedsByUuid(String uuid)
    {
        RecruitingNeeds recruitingNeeds = recruitingNeedsMapper.selectRecruitingNeedsByUuid(uuid);
        recruitingNeeds = setData(recruitingNeeds);
        return recruitingNeeds;
    }

    /**
     * 查询招聘需求列表
     *
     * @author 266861
     * @date 2023-07-18
     * @param recruitingNeeds 招聘需求
     * @return 招聘需求
     */
    @Override
    public List<RecruitingNeeds> selectRecruitingNeedsList(RecruitingNeeds recruitingNeeds)
    {
        List<RecruitingNeeds> list = recruitingNeedsMapper.selectRecruitingNeedsList(recruitingNeeds);
        for(RecruitingNeeds recruitingNeeds1 : list){
            recruitingNeeds1.setHireDeptName(setData(recruitingNeeds1).getHireDeptName());
        }
        return list;
    }

    /**
     * 新增招聘需求
     *
     * @author 266861
     * @date 2023-07-18
     * @param recruitingNeeds 招聘需求
     * @return 结果
     */
    @Override
    public int insertRecruitingNeeds(RecruitingNeeds recruitingNeeds)
    {
        String serial = "00001";
        List<RecruitingNeeds> preDatas = recruitingNeedsMapper.selectRecruitingNeedsList(recruitingNeeds);
        if(preDatas.size()!=0){
            //初始序列号
            serial =String.format("%05d",Integer.parseInt(preDatas.get(0).getHiringId().substring(preDatas.get(0).getHiringId().length()-4))+1);
        }
        recruitingNeeds.setApplyNo(SecurityUtils.getUsername());
        recruitingNeeds.setApplyDate(new Date());
        SimpleDateFormat dateformat = new SimpleDateFormat(DateUtils.YYYYMMDD);
        recruitingNeeds.setHiringId(recruitingNeeds.getUpperposiNo()+"-"+dateformat.format(recruitingNeeds.getApplyDate())+serial);
        recruitingNeeds.setUuid(IdUtils.simpleUUID());
        return recruitingNeedsMapper.insertRecruitingNeeds(recruitingNeeds);
    }

    /**
     * 修改招聘需求
     *
     * @author 266861
     * @date 2023-07-18
     * @param recruitingNeeds 招聘需求
     * @return 结果
     */
    @Override
    public int updateRecruitingNeeds(RecruitingNeeds recruitingNeeds)
    {
        recruitingNeeds.setUpdateNo(SecurityUtils.getUsername());
        recruitingNeeds.setUpdateDate(new Date());
        return recruitingNeedsMapper.updateRecruitingNeeds(recruitingNeeds);
    }

    /**
     * 批量删除招聘需求
     *
     * @author 266861
     * @date 2023-07-18
     * @param uuids 需要删除的招聘需求主键
     * @return 结果
     */
    @Override
    public int deleteRecruitingNeedsByUuids(String[] uuids)
    {
        return recruitingNeedsMapper.deleteRecruitingNeedsByUuids(uuids);
    }

    /**
     * 删除招聘需求信息
     *
     * @author 266861
     * @date 2023-07-18
     * @param uuid 招聘需求主键
     * @return 结果
     */
    @Override
    public int deleteRecruitingNeedsByUuid(String uuid)
    {
        return recruitingNeedsMapper.deleteRecruitingNeedsByUuid(uuid);
    }

    /**
     * 招聘需求岗位部门信息连表查询
     *
     * @param recruitingNeeds 招聘需求
     * @return 招聘需求
     * @author 266861
     * @Date 2023/7/19 9:41
     **/
    public RecruitingNeeds setData(RecruitingNeeds recruitingNeeds){
        recruitingNeeds.setHireDeptName(humanDeptService.selectDeptById(Long.valueOf(recruitingNeeds.getHireDeptNo())).getDeptName());
        return recruitingNeeds;
    }
}
