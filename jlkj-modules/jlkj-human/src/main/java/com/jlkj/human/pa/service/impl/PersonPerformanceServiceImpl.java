package com.jlkj.human.pa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.constant.Constants;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.pa.domain.*;
import com.jlkj.human.pa.dto.PersonPerformanceDTO;
import com.jlkj.human.pa.mapper.PersonPerformanceMapper;
import com.jlkj.human.pa.service.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
* @author HuangBing
* @description 针对表【human_pa_person_performance(个人绩效主档)】的数据库操作Service实现
* @createDate 2023-06-15 11:21:12
*/
@Service
public class PersonPerformanceServiceImpl extends ServiceImpl<PersonPerformanceMapper, PersonPerformance>
    implements IPersonPerformanceService {

    @Resource
    private IDeptTypeService deptTypeService;
    @Resource
    private IDeptTypeDetailService deptTypeDetailService;
    @Resource
    private IPersonPerformanceDetailService personPerformanceDetailService;
    @Resource
    private IPersonPerformanceScheduleService personPerformanceScheduleService;

    /**
     * 查询个人绩效主档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDTO 查询参数集
     * @return list 分页数据
     */
    @Override
    public List<PersonPerformance> listPersonPerformance(PersonPerformanceDTO personPerformanceDTO){
        List<PersonPerformance> list = lambdaQuery()
                .eq(PersonPerformance::getCompId, personPerformanceDTO.getCompId())
                .eq(PersonPerformance::getMeritType, personPerformanceDTO.getMeritType())
                .eq(StringUtils.isNotBlank(personPerformanceDTO.getEmpId()), PersonPerformance::getEmpId, personPerformanceDTO.getEmpId())
                .between(PersonPerformance::getMeritMonth, personPerformanceDTO.getStartTime(), personPerformanceDTO.getEndTime()).list();
        return list;
    }

    /**
     * 查询个人绩效明细档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformance 查询参数集
     * @return list 分页数据
     */
    @Override
    public List<PersonPerformanceDetail> listPersonPerformanceDetail(PersonPerformance personPerformance){
        List<PersonPerformanceDetail> list = personPerformanceDetailService.lambdaQuery()
                .eq(PersonPerformanceDetail::getPersonPerformanceId, personPerformance.getId()).list();
        return list;
    }

    /**
     * 查询个人绩效进度档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformance 查询参数集
     * @return list 列表数据
     */
    @Override
    public List<PersonPerformanceSchedule> listPersonPerformanceSchedule(PersonPerformance personPerformance){
        List<PersonPerformanceSchedule> list = personPerformanceScheduleService.lambdaQuery()
                .eq(PersonPerformanceSchedule::getPersonPerformanceId, personPerformance.getId())
                .orderByAsc(PersonPerformanceSchedule::getNum).list();
        return list;
    }

    /**
     * 新增人员绩效主档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformance 新增数据
     * @return 新增结果
     */
    @Override
    public Object addPersonPerformance(PersonPerformance personPerformance){
        DeptType deptType = deptTypeService.lambdaQuery()
                .eq(DeptType::getCompId, personPerformance.getCompId())
                .eq(DeptType::getDeptType, personPerformance.getDeptType())
                .eq(DeptType::getEffcMonth, personPerformance.getMeritMonth()).one();
        personPerformance.setIsTeam(deptType.getIsTeam());
        personPerformance.setIsPoint(deptType.getIsPoint());
        personPerformance.setIsFix(deptType.getIsFix());
        personPerformance.setIsKpi(deptType.getIsKpi());
        personPerformance.setTeamRatio(deptType.getTeamRatio());
        personPerformance.setPointRatio(deptType.getPointRatio());
        personPerformance.setFixRatio(deptType.getFixRatio());
        personPerformance.setKpiRatio(deptType.getKpiRatio());
        personPerformance.setMeritStatus(Constants.STR_ZERO);
        personPerformance.setCreator(SecurityUtils.getNickName());
        personPerformance.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
        save(personPerformance);
        if (Constants.STR_ZERO.equals(deptType.getIsFix())) {
            PersonPerformanceDetail personPerformanceDetail = new PersonPerformanceDetail();
            PersonPerformanceSchedule personPerformanceSchedule = new PersonPerformanceSchedule();
            String[] project = {"计划输入","计划审批","自评","主管考评","最终主管复核"};
            List<DeptTypeDetail> list = deptTypeDetailService.lambdaQuery()
                    .eq(DeptTypeDetail::getDeptTypeId, deptType.getId()).list();
            PersonPerformance performance = lambdaQuery()
                    .eq(PersonPerformance::getCompId, personPerformance.getCompId())
                    .eq(PersonPerformance::getMeritMonth, personPerformance.getMeritMonth())
                    .eq(PersonPerformance::getEmpId, personPerformance.getEmpId())
                    .eq(PersonPerformance::getDeptType, personPerformance.getDeptType()).one();
            for (int i = 0; i < list.size(); i++){
                personPerformanceDetail.setNum(String.valueOf(i+1));
                personPerformanceDetail.setPersonPerformanceId(performance.getId());
                personPerformanceDetail.setItem(list.get(i).getItem());
                personPerformanceDetail.setItemGoal(list.get(i).getDataDept());
                personPerformanceDetail.setWeight(list.get(i).getWeight());
                personPerformanceDetail.setCompleteTime(DateUtils.getMaxMonthDate1(personPerformance.getMeritMonth()));
                personPerformanceDetail.setType(Constants.STR_THREE);
                personPerformanceDetailService.save(personPerformanceDetail);
            }
            for (int i = 0; i < project.length; i++) {
                personPerformanceSchedule.setId(IdUtils.fastSimpleUUID());
                personPerformanceSchedule.setPersonPerformanceId(performance.getId());
                personPerformanceSchedule.setCompId(performance.getCompId());
                personPerformanceSchedule.setProject(project[i]);
                personPerformanceSchedule.setNum(String.valueOf(i+1));
                personPerformanceSchedule.setApprStatus(Constants.STR_ZERO);
                personPerformanceScheduleService.save(personPerformanceSchedule);
            }
        }
        return AjaxResult.success();
    }

    /**
     * 新增人员绩效明细档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDetail 新增数据
     * @return 新增结果
     */
    @Override
    public Object addPersonPerformanceDetail(@Validated PersonPerformanceDetail personPerformanceDetail){
        personPerformanceDetail.setType(Constants.STR_ONE);
        personPerformanceDetailService.save(personPerformanceDetail);
        return AjaxResult.success();
    }

    /**
     * 新增人员绩效明细档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDetail 新增数据
     * @return 新增结果
     */
    @Override
    public Object addPersonPerformanceKpiDetail(@Validated PersonPerformanceDetail personPerformanceDetail){
        personPerformanceDetail.setType(Constants.STR_TWO);
        personPerformanceDetailService.save(personPerformanceDetail);
        return AjaxResult.success();
    }

    /**
     * 修改人员绩效明细档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDetail 修改数据
     * @return 修改结果
     */
    @Override
    public Object updatePersonPerformanceDetail(@Validated PersonPerformanceDetail personPerformanceDetail){
        personPerformanceDetailService.lambdaUpdate()
                .eq(PersonPerformanceDetail::getId, personPerformanceDetail.getId())
                .update(personPerformanceDetail);
        return AjaxResult.success("修改成功");
    }

    /**
     * 删除人员绩效主档同时一起删除明细档、进度表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformance 删除数据
     * @return 删除结果
     */
    @Override
    public Object delPersonPerformanceAndDetail(PersonPerformance personPerformance){
        PersonPerformanceSchedule performanceSchedule = personPerformanceScheduleService.lambdaQuery()
                .eq(PersonPerformanceSchedule::getPersonPerformanceId, personPerformance.getId())
                .eq(PersonPerformanceSchedule::getNum, "1").one();
        if (Constants.STR_ZERO.equals(performanceSchedule.getApprStatus())) {
            lambdaUpdate()
                    .eq(PersonPerformance::getId, personPerformance.getId()).remove();
            personPerformanceDetailService.lambdaUpdate()
                    .eq(PersonPerformanceDetail::getPersonPerformanceId, personPerformance.getId()).remove();
            personPerformanceScheduleService.lambdaUpdate()
                    .eq(PersonPerformanceSchedule::getPersonPerformanceId, personPerformance.getId()).remove();
            return AjaxResult.success("删除成功");
        } else if (Constants.STR_ONE.equals(performanceSchedule.getApprStatus())) {
            return AjaxResult.error("计划输入状态已完成，不可以进行计划删除");
        } else {
            return AjaxResult.error("删除失败");
        }
    }

    /**
     * 删除人员绩效明细档
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDetail 删除数据
     * @return 删除结果
     */
    @Override
    public Object delPersonPerformanceDetail(PersonPerformanceDetail personPerformanceDetail){
        personPerformanceDetailService.lambdaUpdate()
                .eq(PersonPerformanceDetail::getId, personPerformanceDetail.getId()).remove();
        return AjaxResult.success("删除成功");
    }

    /**
     * 呈送计划
     * @author HuangBing
     * @date 2023-6-20
     * @param personPerformanceDTO 表格数据
     * @return 呈送结果
     */
    @Override
    public Object sendPersonPerformance(PersonPerformanceDTO personPerformanceDTO){
        BigDecimal count = BigDecimal.ZERO;
        BigDecimal kpiCount = BigDecimal.ZERO;
        for (PersonPerformanceDetail item : personPerformanceDTO.getKeyWorkItemsList()){
            count = count.add(item.getWeight());
        }
        for (PersonPerformanceDetail item : personPerformanceDTO.getKpiList()){
            kpiCount = kpiCount.add(item.getWeight());
        }
        if (count.compareTo(Constants.BIG_DECIMAL_HUNDRED) == Constants.INT_ZERO && kpiCount.compareTo(Constants.BIG_DECIMAL_HUNDRED) == Constants.INT_ZERO){
            personPerformanceScheduleService.lambdaUpdate()
                    .set(PersonPerformanceSchedule::getApprStatus, Constants.STR_ONE)
                    .set(PersonPerformanceSchedule::getApprTime, DateUtils.getTime())
                    .set(PersonPerformanceSchedule::getApprEmp, SecurityUtils.getUsername())
                    .set(PersonPerformanceSchedule::getApprName, SecurityUtils.getNickName())
                    .eq(PersonPerformanceSchedule::getPersonPerformanceId, personPerformanceDTO.getId())
                    .eq(PersonPerformanceSchedule::getNum, Constants.STR_ONE).update();
            personPerformanceScheduleService.lambdaUpdate()
                    .set(PersonPerformanceSchedule::getApprStatus, Constants.STR_TWO)
                    .set(PersonPerformanceSchedule::getApprTime, DateUtils.getTime())
                    .set(PersonPerformanceSchedule::getApprEmp, SecurityUtils.getUsername())
                    .set(PersonPerformanceSchedule::getApprName, SecurityUtils.getNickName())
                    .eq(PersonPerformanceSchedule::getPersonPerformanceId, personPerformanceDTO.getId())
                    .eq(PersonPerformanceSchedule::getNum, Constants.STR_TWO).update();
            return AjaxResult.success("呈送成功");
        } else {
            return AjaxResult.error("计划权重不为100%，请调整！！！");
        }
    }
}




