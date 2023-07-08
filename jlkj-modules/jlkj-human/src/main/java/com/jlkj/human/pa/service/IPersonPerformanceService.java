package com.jlkj.human.pa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.human.pa.domain.PersonPerformance;
import com.jlkj.human.pa.domain.PersonPerformanceDetail;
import com.jlkj.human.pa.domain.PersonPerformanceFix;
import com.jlkj.human.pa.domain.PersonPerformanceSchedule;
import com.jlkj.human.pa.dto.PersonPerformanceDTO;

import java.util.List;

/**
* @author HuangBing
* @description 针对表【human_pa_person_performance(个人绩效主档)】的数据库操作Service
* @createDate 2023-06-15 11:21:12
*/
public interface IPersonPerformanceService extends IService<PersonPerformance> {

    /**
     * 查询个人绩效主档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDTO 查询参数集
     * @return list 分页数据
     */
    List<PersonPerformance> listPersonPerformance(PersonPerformanceDTO personPerformanceDTO);

    /**
     * 查询固定项目启动档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDTO 查询参数集
     * @return list 分页数据
     */
    List<PersonPerformanceFix> listPersonPerformanceFix(PersonPerformanceDTO personPerformanceDTO);

    /**
     * 查询考评清单列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDTO 查询参数集
     * @return list 分页数据
     */
    List<PersonPerformance> listEvaluation(PersonPerformanceDTO personPerformanceDTO);

    /**
     * 查询个人绩效明细档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformance 查询参数集
     * @return 新增结果
     */
    List<PersonPerformanceDetail> listPersonPerformanceDetail(PersonPerformance personPerformance);

    /**
     * 查询个人绩效进度档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformance 查询参数集
     * @return 新增结果
     */
    List<PersonPerformanceSchedule> listPersonPerformanceSchedule(PersonPerformance personPerformance);

    /**
     * 新增人员绩效主档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformance 新增数据
     * @return 新增结果
     */
    Object addPersonPerformance(PersonPerformance personPerformance);

    /**
     * 新增人员绩效明细档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDetail 新增数据
     * @return 新增结果
     */
    Object addPersonPerformanceDetail(PersonPerformanceDetail personPerformanceDetail);

    /**
     * 新增人员绩效明细档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDetail 新增数据
     * @return 新增结果
     */
    Object addPersonPerformanceKpiDetail(PersonPerformanceDetail personPerformanceDetail);

    /**
     * 修改人员绩效明细档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDetail 修改数据
     * @return 修改结果
     */
    Object updatePersonPerformanceDetail(PersonPerformanceDetail personPerformanceDetail);

    /**
     * 添加人员绩效明细档自评和实际完成情况
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDetail 添加数据
     * @return 添加结果
     */
    Object updateSelfApprDetail(PersonPerformanceDetail personPerformanceDetail);

    /**
     * 添加人员绩效主档自评总结
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformance 添加数据
     * @return 添加结果
     */
    Object updateSelfAppr(PersonPerformance personPerformance);

    /**
     * 添加人员绩效主档主管考评和明细档主管考评分数及备注
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDTO 添加数据
     * @return 添加结果
     */
    Object updateAdmAppr(PersonPerformanceDTO personPerformanceDTO);

    /**
     * 添加考评意见/复核意见、更新明细表主管考评及备注
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDTO 添加数据
     * @return 添加结果
     */
    Object updateAdmEvaluation(PersonPerformanceDTO personPerformanceDTO);

    /**
     * 删除人员绩效主档同时一起删除明细档、进度表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformance 新增数据
     * @return 删除结果
     */
    Object delPersonPerformanceAndDetail(PersonPerformance personPerformance);

    /**
     * 删除人员绩效明细档
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDetail 删除数据
     * @return 删除结果
     */
    Object delPersonPerformanceDetail(PersonPerformanceDetail personPerformanceDetail);

    /**
     * 呈送计划
     * @author HuangBing
     * @date 2023-6-20
     * @param personPerformanceDTO 表格数据
     * @return 呈送结果
     */
    Object sendPersonPerformance(PersonPerformanceDTO personPerformanceDTO);

    /**
     * 呈送固定项目考评
     * @author HuangBing
     * @date 2023-6-20
     * @param personPerformanceDTO 表格数据
     * @return 呈送结果
     */
    Object sendPersonPerformanceFix(PersonPerformanceDTO personPerformanceDTO);

    /**
     * 呈送按钮
     * @author HuangBing
     * @date 2023-6-20
     * @param personPerformanceDTO 表格数据
     * @return 呈送结果
     */
    Object sendSelfAppr(PersonPerformanceDTO personPerformanceDTO);

    /**
     * 最终主管确认
     * @author HuangBing
     * @date 2023-6-20
     * @param personPerformance 表格数据
     * @return 确认结果
     */
    Object confirmPersonalPerformance(PersonPerformance personPerformance);

    /**
     * 固定项目考评最终主管确认
     * @author HuangBing
     * @date 2023-6-20
     * @param personPerformance 表格数据
     * @return 确认结果
     */
    Object confirmPersonalPerformanceFix(PersonPerformance personPerformance);

    /**
     * 启动固定项目考评
     * @author HuangBing
     * @date 2023-6-20
     * @param personPerformanceFix 表格数据
     * @return 启动结果
     */
    Object startPersonPerformanceFix(PersonPerformanceFix personPerformanceFix);
}
