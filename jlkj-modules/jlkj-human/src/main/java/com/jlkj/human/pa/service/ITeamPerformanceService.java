package com.jlkj.human.pa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.human.pa.domain.TeamPerformance;
import com.jlkj.human.pa.domain.TeamPerformanceDetail;
import com.jlkj.human.pa.dto.TeamPerformanceDTO;

import java.util.List;

/**
* @author HuangBing
* @description 针对表【human_pa_team_performance(团队绩效主档)】的数据库操作Service
* @createDate 2023-06-27 11:34:05
*/
public interface ITeamPerformanceService extends IService<TeamPerformance> {

    /**
     * 查询团队绩效主档列表
     * @author HuangBing
     * @date 2023-06-27
     * @param teamPerformanceDTO 查询参数集
     * @return list 分页数据
     */
    List<TeamPerformance> listTeamPerformance(TeamPerformanceDTO teamPerformanceDTO);

    /**
     * 查询团队绩效明细档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param teamPerformance 查询参数集
     * @return 新增结果
     */
    List<TeamPerformanceDetail> listTeamPerformanceDetail(TeamPerformance teamPerformance);

    /**
     * 新增团队绩效主档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param teamPerformance 新增数据
     * @return 新增结果
     */
    Object addTeamPerformance(TeamPerformance teamPerformance);

    /**
     * 修改团队绩效明细档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param teamPerformanceDTO 修改数据
     * @return 修改结果
     */
    Object updateTeamPerformanceDetail(TeamPerformanceDTO teamPerformanceDTO);

    /**
     * 删除团队绩效主档同时一起删除明细档
     * @author HuangBing
     * @date 2023-06-20
     * @param teamPerformance 删除数据
     * @return 删除结果
     */
    Object delTeamPerformanceAndDetail(TeamPerformance teamPerformance);

    /**
     * 状态确认
     * @author HuangBing
     * @date 2023-06-20
     * @param teamPerformance 确认数据
     * @return 确认结果
     */
    Object updateTeamPerformanceMeritStatus(TeamPerformance teamPerformance);
}
