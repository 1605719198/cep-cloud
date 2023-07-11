package com.jlkj.human.pa.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlkj.common.core.constant.Constants;
import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.bean.BeanUtils;
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
    @Resource
    private IPersonPerformanceFixService personPerformanceFixService;
    @Resource
    private IPositionPersonnelService positionPersonnelService;
    @Resource
    private PersonPerformanceMapper personPerformanceMapper;

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
                .eq(StringUtils.isNotBlank(personPerformanceDTO.getIsFix()), PersonPerformance::getIsFix, personPerformanceDTO.getIsFix())
                .between(PersonPerformance::getMeritMonth, personPerformanceDTO.getStartTime(), personPerformanceDTO.getEndTime()).list();
        return list;
    }

    /**
     * 查询固定项目启动档列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDTO 查询参数集
     * @return list 分页数据
     */
    @Override
    public List<PersonPerformanceFix> listPersonPerformanceFix(PersonPerformanceDTO personPerformanceDTO){
        List<PersonPerformanceFix> list = personPerformanceFixService.lambdaQuery()
                .eq(PersonPerformanceFix::getCompId, personPerformanceDTO.getCompId())
                .eq(PersonPerformanceFix::getMeritType, personPerformanceDTO.getMeritType())
                .eq(StringUtils.isNotBlank(personPerformanceDTO.getEmpId()), PersonPerformanceFix::getEmpId, personPerformanceDTO.getEmpId())
                .between(PersonPerformanceFix::getMeritMonth, personPerformanceDTO.getStartTime(), personPerformanceDTO.getEndTime()).list();
        return list;
    }

    /**
     * 查询考评清单列表
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDTO 查询参数集
     * @return list 分页数据
     */
    @Override
    public List<PersonPerformance> listEvaluation(PersonPerformanceDTO personPerformanceDTO){
        List<PersonPerformance> list = lambdaQuery()
                .eq(PersonPerformance::getCompId, personPerformanceDTO.getCompId())
                .eq(PersonPerformance::getMeritType, personPerformanceDTO.getMeritType())
                .eq(StringUtils.isNotBlank(personPerformanceDTO.getDept()), PersonPerformance::getDept, personPerformanceDTO.getDept())
                .eq(StringUtils.isNotBlank(personPerformanceDTO.getAdmEmpId()), PersonPerformance::getAdmEmpId, personPerformanceDTO.getAdmEmpId())
                .eq(StringUtils.isNotBlank(personPerformanceDTO.getDeptType()), PersonPerformance::getDeptType, personPerformanceDTO.getDeptType())
                .eq(StringUtils.isNotBlank(personPerformanceDTO.getMeritStatus()), PersonPerformance::getMeritStatus, personPerformanceDTO.getMeritStatus())
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
                personPerformanceDetail.setCompId(personPerformance.getCompId());
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
     * 添加人员绩效明细档自评和实际完成情况
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDetail 添加数据
     * @return 添加结果
     */
    @Override
    public Object updateSelfApprDetail(@Validated PersonPerformanceDetail personPerformanceDetail){
        personPerformanceDetailService.lambdaUpdate()
                .eq(PersonPerformanceDetail::getId, personPerformanceDetail.getId())
                .update(personPerformanceDetail);
        personPerformanceScheduleService.lambdaUpdate()
                .set(PersonPerformanceSchedule::getApprStatus, Constants.STR_THREE)
                .eq(PersonPerformanceSchedule::getNum, Constants.STR_THREE)
                .eq(PersonPerformanceSchedule::getPersonPerformanceId, personPerformanceDetail.getPersonPerformanceId()).update();
        return AjaxResult.success("修改成功");
    }

    /**
     * 添加人员绩效主档自评总结
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformance 添加数据
     * @return 添加结果
     */
    @Override
    public Object updateSelfAppr(@Validated PersonPerformance personPerformance){
        lambdaUpdate()
                .eq(PersonPerformance::getId, personPerformance.getId())
                .update(personPerformance);
        personPerformanceScheduleService.lambdaUpdate()
                .set(PersonPerformanceSchedule::getApprStatus, Constants.STR_THREE)
                .eq(PersonPerformanceSchedule::getNum, Constants.STR_THREE)
                .eq(PersonPerformanceSchedule::getPersonPerformanceId, personPerformance.getId()).update();
        return AjaxResult.success("修改成功");
    }

    /**
     * 添加人员绩效主档主管考评和明细档主管考评分数及备注
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDTO 添加数据
     * @return 添加结果
     */
    @Override
    public Object updateAdmAppr(@Validated PersonPerformanceDTO personPerformanceDTO){
        BigDecimal count = BigDecimal.ZERO;
        BigDecimal kpiCount = BigDecimal.ZERO;
        PersonPerformance personPerformance = new PersonPerformance();
        BeanUtils.copyProperties(personPerformanceDTO, personPerformance);
        for (PersonPerformanceDetail item : personPerformanceDTO.getKeyWorkItemsList()){
            count = count.add(item.getWeight());
        }
        for (PersonPerformanceDetail item : personPerformanceDTO.getKpiList()){
            kpiCount = kpiCount.add(item.getWeight());
        }
        if (count.compareTo(Constants.BIG_DECIMAL_HUNDRED) == Constants.INT_ZERO && kpiCount.compareTo(Constants.BIG_DECIMAL_HUNDRED) == Constants.INT_ZERO){
            lambdaUpdate()
                    .eq(PersonPerformance::getId, personPerformanceDTO.getId())
                    .update(personPerformance);
            personPerformanceDetailService.updateBatchById(personPerformanceDTO.getKeyWorkItemsList());
            personPerformanceDetailService.updateBatchById(personPerformanceDTO.getKpiList());
            return AjaxResult.success("修改成功");
        } else {
            return AjaxResult.error("计划权重不为100%，请调整！！！");
        }
    }

    /**
     * 添加考评意见/复核意见、更新明细表主管考评及备注
     * @author HuangBing
     * @date 2023-06-20
     * @param personPerformanceDTO 添加数据
     * @return 添加结果
     */
    @Override
    public Object updateAdmEvaluation(@Validated PersonPerformanceDTO personPerformanceDTO){
        BigDecimal count = BigDecimal.ZERO;
        PersonPerformance personPerformance = new PersonPerformance();
        BeanUtils.copyProperties(personPerformanceDTO, personPerformance);
        for (PersonPerformanceDetail item : personPerformanceDTO.getKeyWorkItemsList()){
            count = count.add(item.getWeight());
        }
        if (count.compareTo(Constants.BIG_DECIMAL_HUNDRED) == Constants.INT_ZERO){
            lambdaUpdate()
                    .set(PersonPerformance::getMeritStatus, Constants.STR_ONE)
                    .eq(PersonPerformance::getId, personPerformanceDTO.getId())
                    .update(personPerformance);
            personPerformanceDetailService.updateBatchById(personPerformanceDTO.getKeyWorkItemsList());
            return AjaxResult.success("修改成功");
        } else {
            return AjaxResult.error("计划权重不为100%，请调整！！！");
        }
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

    /**
     * 呈送固定项目考评
     * @author HuangBing
     * @date 2023-6-20
     * @param personPerformanceDTO 表格数据
     * @return 呈送结果
     */
    @Override
    public Object sendPersonPerformanceFix(PersonPerformanceDTO personPerformanceDTO){
        for (PersonPerformance item : personPerformanceDTO.getFixedProjectList()){
            if (!Constants.STR_ONE.equals(item.getMeritStatus())){
                return AjaxResult.error("主管考评未完成，请检查！");
            }
        }
        personPerformanceScheduleService.lambdaUpdate()
                .set(PersonPerformanceSchedule::getApprStatus, Constants.STR_ONE)
                .set(PersonPerformanceSchedule::getApprTime, DateUtils.getTime())
                .set(PersonPerformanceSchedule::getApprEmp, SecurityUtils.getUsername())
                .set(PersonPerformanceSchedule::getApprName, SecurityUtils.getNickName())
                .eq(PersonPerformanceSchedule::getPersonPerformanceId, personPerformanceDTO.getId())
                .eq(PersonPerformanceSchedule::getNum, Constants.STR_ONE).update();
        return AjaxResult.success("呈送成功");
    }

    /**
     * 呈送按钮
     * @author HuangBing
     * @date 2023-6-20
     * @param personPerformanceDTO 表格数据
     * @return 呈送结果
     */
    @Override
    public Object sendSelfAppr(PersonPerformanceDTO personPerformanceDTO){
        if (personPerformanceDTO.getFinalSupervisor().equals(false)){
            personPerformanceScheduleService.lambdaUpdate()
                    .set(PersonPerformanceSchedule::getApprStatus, Constants.STR_ONE)
                    .set(PersonPerformanceSchedule::getApprTime, DateUtils.getTime())
                    .set(PersonPerformanceSchedule::getApprEmp, SecurityUtils.getUsername())
                    .set(PersonPerformanceSchedule::getApprName, SecurityUtils.getNickName())
                    .eq(PersonPerformanceSchedule::getPersonPerformanceId, personPerformanceDTO.getId())
                    .eq(PersonPerformanceSchedule::getNum, Constants.STR_THREE).update();
            personPerformanceScheduleService.lambdaUpdate()
                    .set(PersonPerformanceSchedule::getApprStatus, Constants.STR_FOUR)
                    .set(PersonPerformanceSchedule::getApprTime, DateUtils.getTime())
                    .set(PersonPerformanceSchedule::getApprEmp, SecurityUtils.getUsername())
                    .set(PersonPerformanceSchedule::getApprName, SecurityUtils.getNickName())
                    .eq(PersonPerformanceSchedule::getPersonPerformanceId, personPerformanceDTO.getId())
                    .eq(PersonPerformanceSchedule::getNum, Constants.STR_FOUR).update();
            return AjaxResult.success("呈送成功");
        } else {
            BigDecimal pointSelfCount = BigDecimal.ZERO;
            BigDecimal pointAdmCount = BigDecimal.ZERO;
            BigDecimal kpiSelfCount = BigDecimal.ZERO;
            BigDecimal kpiAdmCount = BigDecimal.ZERO;
            BigDecimal selfCount;
            BigDecimal admCount;
            PersonPerformance personPerformance = new PersonPerformance();
            if (StringUtils.isNotNull(personPerformanceDTO.getSelfAppr()) && StringUtils.isNotNull(personPerformanceDTO.getAdmAppr())){
                for (PersonPerformanceDetail item : personPerformanceDTO.getKeyWorkItemsList()){
                    if (item.getSelfScore().compareTo(Constants.BIG_DECIMAL_ZERO) > 0 && item.getAdmScore().compareTo(Constants.BIG_DECIMAL_ZERO) > 0){
                        pointSelfCount = pointSelfCount.add(item.getSelfScore());
                        pointAdmCount = pointAdmCount.add(item.getAdmScore());
                    } else {
                        return AjaxResult.error("明细项缺少自评/考核分数");
                    }
                }
                for (PersonPerformanceDetail item : personPerformanceDTO.getKpiList()){
                    if (item.getSelfScore().compareTo(Constants.BIG_DECIMAL_ZERO) > 0 && item.getAdmScore().compareTo(Constants.BIG_DECIMAL_ZERO) > 0){
                        kpiSelfCount = kpiSelfCount.add(item.getSelfScore());
                        kpiAdmCount = kpiAdmCount.add(item.getAdmScore());
                    } else {
                        return AjaxResult.error("明细项缺少自评/考核分数");
                    }
                }
                personPerformanceScheduleService.lambdaUpdate()
                        .set(PersonPerformanceSchedule::getApprStatus, Constants.STR_ONE)
                        .set(PersonPerformanceSchedule::getApprTime, DateUtils.getTime())
                        .set(PersonPerformanceSchedule::getApprEmp, SecurityUtils.getUsername())
                        .set(PersonPerformanceSchedule::getApprName, SecurityUtils.getNickName())
                        .eq(PersonPerformanceSchedule::getPersonPerformanceId, personPerformanceDTO.getId())
                        .eq(PersonPerformanceSchedule::getNum, Constants.STR_FOUR).update();
                personPerformanceScheduleService.lambdaUpdate()
                        .set(PersonPerformanceSchedule::getApprStatus, Constants.STR_FIVE)
                        .set(PersonPerformanceSchedule::getApprTime, DateUtils.getTime())
                        .set(PersonPerformanceSchedule::getApprEmp, SecurityUtils.getUsername())
                        .set(PersonPerformanceSchedule::getApprName, SecurityUtils.getNickName())
                        .eq(PersonPerformanceSchedule::getPersonPerformanceId, personPerformanceDTO.getId())
                        .eq(PersonPerformanceSchedule::getNum, Constants.STR_FIVE).update();
                pointSelfCount = pointSelfCount.multiply(BigDecimal.valueOf(personPerformanceDTO.getPointRatio()));
                pointAdmCount = pointAdmCount.multiply(BigDecimal.valueOf(personPerformanceDTO.getPointRatio()));
                kpiSelfCount = kpiSelfCount.multiply(BigDecimal.valueOf(personPerformanceDTO.getKpiRatio()));
                kpiAdmCount = kpiAdmCount.multiply(BigDecimal.valueOf(personPerformanceDTO.getKpiRatio()));
                selfCount = pointSelfCount.add(kpiSelfCount);
                admCount = pointAdmCount.add(kpiAdmCount);
                personPerformance.setPointScore(pointSelfCount);
                personPerformance.setKpiScore(kpiSelfCount);
                personPerformance.setSelfScore(selfCount.add(personPerformanceDTO.getOtherAdjust()));
                personPerformance.setAdmScore(admCount.add(personPerformanceDTO.getOtherAdjust()));
                lambdaUpdate().eq(PersonPerformance::getId, personPerformanceDTO.getId()).update(personPerformance);
                return AjaxResult.success("呈送成功");
            } else {
                return AjaxResult.error("计划明细未自评/考评");
            }
        }
    }

    /**
     * 最终主管确认
     * @author HuangBing
     * @date 2023-6-20
     * @param personPerformance 表格数据
     * @return 确认结果
     */
    @Override
    public Object confirmPersonalPerformance(PersonPerformance personPerformance){
        personPerformanceScheduleService.lambdaUpdate()
                .set(PersonPerformanceSchedule::getApprStatus, Constants.STR_ONE)
                .set(PersonPerformanceSchedule::getApprTime, DateUtils.getTime())
                .set(PersonPerformanceSchedule::getApprEmp, SecurityUtils.getUsername())
                .set(PersonPerformanceSchedule::getApprName, SecurityUtils.getNickName())
                .eq(PersonPerformanceSchedule::getPersonPerformanceId, personPerformance.getId())
                .eq(PersonPerformanceSchedule::getNum, Constants.STR_FIVE).update();
        return AjaxResult.success("确认成功");
    }

    /**
     * 固定项目考评最终主管确认
     * @author HuangBing
     * @date 2023-6-20
     * @param personPerformance 表格数据
     * @return 确认结果
     */
    @Override
    public Object confirmPersonalPerformanceFix(PersonPerformance personPerformance){
        personPerformanceScheduleService.lambdaUpdate()
                .set(PersonPerformanceSchedule::getApprStatus, Constants.STR_ONE)
                .set(PersonPerformanceSchedule::getApprTime, DateUtils.getTime())
                .set(PersonPerformanceSchedule::getApprEmp, SecurityUtils.getUsername())
                .set(PersonPerformanceSchedule::getApprName, SecurityUtils.getNickName())
                .eq(PersonPerformanceSchedule::getPersonPerformanceId, personPerformance.getId())
                .eq(PersonPerformanceSchedule::getNum, Constants.STR_TWO).update();
        return AjaxResult.success("确认成功");
    }

    /**
     * 启动固定项目考评
     * @author HuangBing
     * @date 2023-6-20
     * @param personPerformanceFix 表格数据
     * @return 启动结果
     */
    @Override
    public Object startPersonPerformanceFix(PersonPerformanceFix personPerformanceFix){
        PersonPerformance personPerformance = new PersonPerformance();
        PersonPerformanceSchedule personPerformanceSchedule = new PersonPerformanceSchedule();
        PersonPerformanceDetail personPerformanceDetail = new PersonPerformanceDetail();
        String[] project = {"主管考评","最终主管复核"};
        personPerformanceFix.setId(IdUtils.fastSimpleUUID());
        personPerformanceFix.setMeritStatus(Constants.STR_ONE);
        personPerformanceFixService.save(personPerformanceFix);
        DeptType deptType = deptTypeService.lambdaQuery()
                .eq(DeptType::getCompId, personPerformanceFix.getCompId())
                .eq(DeptType::getDeptType, personPerformanceFix.getDeptType())
                .eq(DeptType::getEffcMonth, personPerformanceFix.getMeritMonth()).one();
        List<PositionPersonnel> list = positionPersonnelService.lambdaQuery()
                .eq(PositionPersonnel::getLeaderNo, personPerformanceFix.getEmpId())
                .eq(PositionPersonnel::getDeptType, personPerformanceFix.getDeptType()).list();
        List<DeptTypeDetail> deptTypeDetailList = deptTypeDetailService.lambdaQuery()
                .eq(DeptTypeDetail::getDeptTypeId, deptType.getId()).list();
        if (list.isEmpty()){
            return AjaxResult.error("该人员没有下级人员！");
        } else {
            for (PositionPersonnel item : list){
                personPerformance.setId(IdUtils.fastSimpleUUID());
                personPerformance.setEmpId(item.getEmpNo());
                personPerformance.setPostId(String.valueOf(item.getPostId()));
                personPerformance.setDeptType(item.getDeptType());
                personPerformance.setCompId(personPerformanceFix.getCompId());
                personPerformance.setMeritType(personPerformanceFix.getMeritType());
                personPerformance.setMeritMonth(personPerformanceFix.getMeritMonth());
                personPerformance.setDept(personPerformanceFix.getDept());
                personPerformance.setIsFix(Constants.STR_ZERO);
                personPerformance.setFixId(personPerformanceFix.getId());
                personPerformance.setMeritStatus(Constants.STR_FOUR);
                save(personPerformance);
                for (int i = 0; i < deptTypeDetailList.size(); i++){
                    personPerformanceDetail.setNum(String.valueOf(i+1));
                    personPerformanceDetail.setCompId(personPerformanceFix.getCompId());
                    personPerformanceDetail.setPersonPerformanceId(personPerformance.getId());
                    personPerformanceDetail.setItem(deptTypeDetailList.get(i).getItem());
                    personPerformanceDetail.setItemGoal(deptTypeDetailList.get(i).getDataDept());
                    personPerformanceDetail.setWeight(deptTypeDetailList.get(i).getWeight());
                    personPerformanceDetail.setCompleteTime(DateUtils.getMaxMonthDate1(personPerformanceFix.getMeritMonth()));
                    personPerformanceDetail.setType(Constants.STR_THREE);
                    personPerformanceDetailService.save(personPerformanceDetail);
                }
            }
            for (int i = 0; i < project.length; i++) {
                personPerformanceSchedule.setId(IdUtils.fastSimpleUUID());
                personPerformanceSchedule.setPersonPerformanceId(personPerformanceFix.getId());
                personPerformanceSchedule.setCompId(personPerformanceFix.getCompId());
                personPerformanceSchedule.setProject(project[i]);
                personPerformanceSchedule.setNum(String.valueOf(i+1));
                personPerformanceSchedule.setApprStatus(Constants.STR_ZERO);
                personPerformanceSchedule.setApprTime(DateUtils.getNowDate());
                personPerformanceSchedule.setApprEmp(SecurityUtils.getUsername());
                personPerformanceSchedule.setApprName(SecurityUtils.getNickName());
                personPerformanceScheduleService.save(personPerformanceSchedule);
            }
            return AjaxResult.success("确认成功");
        }
    }
}




