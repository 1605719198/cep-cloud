package com.jlkj.human.pa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.constant.Constants;
import com.jlkj.common.core.utils.bean.BeanUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.human.pa.domain.*;
import com.jlkj.human.pa.dto.TeamPerformanceDTO;
import com.jlkj.human.pa.mapper.TeamPerformanceMapper;
import com.jlkj.human.pa.service.IDeptKpiDetailService;
import com.jlkj.human.pa.service.ITeamPerformanceDetailService;
import com.jlkj.human.pa.service.ITeamPerformanceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author HuangBing
* @description 针对表【human_pa_team_performance(团队绩效主档)】的数据库操作Service实现
* @createDate 2023-06-30 11:34:05
*/
@Service
public class TeamPerformanceServiceImpl extends ServiceImpl<TeamPerformanceMapper, TeamPerformance>
    implements ITeamPerformanceService {

    @Resource
    private IDeptKpiDetailService iDeptKpiDetailService;
    @Resource
    private ITeamPerformanceDetailService iTeamPerformanceDetailService;

    /**
     * 查询团队绩效主档列表
     * @author HuangBing
     * @date 2023-06-27
     * @param teamPerformanceDTO 查询参数集
     * @return list 分页数据
     */
    @Override
    public List<TeamPerformance> listTeamPerformance(TeamPerformanceDTO teamPerformanceDTO){
        List<TeamPerformance> list = lambdaQuery()
                .eq(TeamPerformance::getCompId, teamPerformanceDTO.getCompId())
                .eq(TeamPerformance::getMeritType, teamPerformanceDTO.getMeritType())
                .between(TeamPerformance::getMeritMonth, teamPerformanceDTO.getStartTime(), teamPerformanceDTO.getEndTime()).list();
        return list;
    }

    /**
     * 查询团队绩效明细档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param teamPerformance 查询参数集
     * @return list 分页数据
     */
    @Override
    public List<TeamPerformanceDetail> listTeamPerformanceDetail(TeamPerformance teamPerformance){
        List<TeamPerformanceDetail> list = iTeamPerformanceDetailService.lambdaQuery()
                .eq(TeamPerformanceDetail::getTeamPerformanceId, teamPerformance.getId()).list();
        return list;
    }

    /**
     * 新增团队绩效主档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param teamPerformance 新增数据
     * @return 新增结果
     */
    @Override
    public Object addTeamPerformance(TeamPerformance teamPerformance){
        TeamPerformanceDetail teamPerformanceDetail = new TeamPerformanceDetail();
        List<TeamPerformance> list = lambdaQuery()
                .eq(TeamPerformance::getCompId, teamPerformance.getCompId())
                .eq(TeamPerformance::getMeritType, teamPerformance.getMeritType())
                .eq(TeamPerformance::getMeritMonth, teamPerformance.getMeritMonth()).list();
        if (list.isEmpty()) {
            teamPerformance.setMeritStatus(Constants.STR_ONE);
            save(teamPerformance);
            List<DeptKpiDetail> deptKpiDetailList = iDeptKpiDetailService.lambdaQuery()
                    .eq(DeptKpiDetail::getEffcMonth, teamPerformance.getMeritMonth())
                    .list();
            for (DeptKpiDetail item : deptKpiDetailList){
                BeanUtils.copyProperties(item, teamPerformanceDetail);
                teamPerformanceDetail.setTeamPerformanceId(teamPerformance.getId());
                iTeamPerformanceDetailService.save(teamPerformanceDetail);
            }
            return AjaxResult.success();
        } else {
            return AjaxResult.error("相同考评月别不能重复录入!!!");
        }
    }

    /**
     * 修改团队绩效明细档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param teamPerformanceDTO 修改数据
     * @return 修改结果
     */
    @Override
    public Object updateTeamPerformanceDetail(TeamPerformanceDTO teamPerformanceDTO){
        iTeamPerformanceDetailService.updateBatchById(teamPerformanceDTO.getKeyWorkItemsList());
        return AjaxResult.success("修改成功");
    }

    /**
     * 删除团队绩效主档同时一起删除明细档、进度表
     * @author HuangBing
     * @date 2023-06-20
     * @param teamPerformance 删除数据
     * @return 删除结果
     */
    @Override
    public Object delTeamPerformanceAndDetail(TeamPerformance teamPerformance){
        lambdaUpdate().eq(TeamPerformance::getId, teamPerformance.getId()).remove();
        iTeamPerformanceDetailService.lambdaUpdate().eq(TeamPerformanceDetail::getTeamPerformanceId, teamPerformance.getId()).remove();
        return AjaxResult.success("删除成功");
    }

    /**
     * 状态确认
     * @author HuangBing
     * @date 2023-06-20
     * @param teamPerformance 确认数据
     * @return 确认结果
     */
    @Override
    public Object updateTeamPerformanceMeritStatus(TeamPerformance teamPerformance){
        lambdaUpdate()
                .set(TeamPerformance::getMeritStatus, Constants.STR_TWO)
                .eq(TeamPerformance::getId, teamPerformance.getId()).update();
        return AjaxResult.success("确认成功");
    }
}




