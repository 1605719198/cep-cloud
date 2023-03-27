package com.jlkj.safety.si.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jlkj.common.core.constant.HttpStatus;
import com.jlkj.common.core.utils.ValidUtil;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.safety.si.dto.safeimportantwork.*;
import com.jlkj.safety.si.entity.SafeImportantWork;
import com.jlkj.safety.si.entity.SafeImportantWorkProportion;
import com.jlkj.safety.si.service.SafeImportantWorkProportionService;
import com.jlkj.safety.si.service.SafeImportantWorkService;
import com.jlkj.safety.si.utils.ResponseUtil;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author yzl
 * @Description 安全重点工作
 * @create 2022年6月6日
 */
@Api(tags = "安全重点工作")
@RestController
@RequestMapping("/safe/safeImportantWork")
@Slf4j
public class SafeImportantWorkController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    SafeImportantWorkService safeImportantWorkService;
    @Autowired
    SafeImportantWorkProportionService safeImportantWorkProportionService;

    @ApiOperation(value = "安全重点工作-查询列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "planStartTime", value = "开始时间"),
            @ApiImplicitParam(name = "planEndTime", value = "结束时间"),
            @ApiImplicitParam(name = "departmentId", value = "单位"),
            @ApiImplicitParam(name = "completeState", value = "完成情况"),
            @ApiImplicitParam(name = "current", value = "页码-从1开始"),
            @ApiImplicitParam(name = "size", value = "每页行数"),
            @ApiImplicitParam(name = "order", value = "排序字段"),
            @ApiImplicitParam(name = "orderby", value = "排序规则字段")
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:主键标识\n" +
                                            "department_id:单位id\n" +
                                            "department_name:单位\n" +
                                            "subject:主体\n" +
                                            "major:专业\n" +
                                            "complete_target:完成目标\n" +
                                            "complete_state:完成情况\n" +
                                            "plan_complete_time:计划完成时间\n" +
                                            "remarks:备注\n" +
                                            "create_user_id:创建人id\n" +
                                            "create_user_name:创建人\n" +
                                            "create_time:创建时间\n" +
                                            "modify_user_id:修改人id\n" +
                                            "modify_user_name:修改人\n" +
                                            "modify_time:修改时间\n"
                            )
                    )
            )
    )
    @Log(title = "安全重点工作-查询列表", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@Validated @ParamModel PageSafeImportantWorkDTO pageSafeImportantWorkDTO) {
        log.info("params => " + pageSafeImportantWorkDTO);
        String errorMsg = ValidUtil.checkValid(pageSafeImportantWorkDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageSafeImportantWorkDTO);
        IPage<Map<String, String>> list = safeImportantWorkService.getPageData(pageSafeImportantWorkDTO);
        return AjaxResult.success(list);
    }


    @ApiOperation(value = "安全重点工作-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "department_id", value = "单位"),
            @ApiImplicitParam(name = "subject", value = "主体"),
            @ApiImplicitParam(name = "complete_target", value = "完成目标"),
            @ApiImplicitParam(name = "complete_state", value = "完成情况"),
            @ApiImplicitParam(name = "plan_complete_time", value = "计划完成时间"),
            @ApiImplicitParam(name = "remarks", value = "备注"),
            @ApiImplicitParam(name = "proportions", value = "【奖励比例】【新增id传空字符串】[{\"id\":\"\",\"major\" :\"专业\",\"responsible_user_id\" :\"负责人\",\"responsible_user_name\" :\"负责人\",\"job_number\" :\"工号\",\"reward_proportion\" :\"比例\"}]"),
            @ApiImplicitParam(name = "create_user_id", value = "创建人id"),
            @ApiImplicitParam(name = "create_user_name", value = "创建人")
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @Log(title = "安全重点工作-新增", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
    public Object save(@Valid @RequestBody AddSafeImportantWorkDTO addSafeImportantWorkDTO) {
        log.info("params => " + addSafeImportantWorkDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, addSafeImportantWorkDTO);
        SafeImportantWork safeImportantWork = new SafeImportantWork();
        safeImportantWork.setId(IdUtil.randomUUID());
        safeImportantWork.setDepartmentId(addSafeImportantWorkDTO.getDepartmentId());
        safeImportantWork.setSubject(addSafeImportantWorkDTO.getSubject());
        safeImportantWork.setCompleteTarget(addSafeImportantWorkDTO.getCompleteTarget());
        safeImportantWork.setCompleteState(addSafeImportantWorkDTO.getCompleteState());
        safeImportantWork.setPlanCompleteTime(addSafeImportantWorkDTO.getPlanCompleteTime());
        safeImportantWork.setRemarks(addSafeImportantWorkDTO.getRemarks());
        safeImportantWork.setCreateUserId(addSafeImportantWorkDTO.getCreateUserId());
        safeImportantWork.setCreateUserName(addSafeImportantWorkDTO.getCreateUserName());
        safeImportantWork.setCreateTime(new Date());
        safeImportantWork.setModifyUserId(addSafeImportantWorkDTO.getCreateUserId());
        safeImportantWork.setModifyUserName(addSafeImportantWorkDTO.getCreateUserName());
        safeImportantWork.setModifyTime(new Date());
        safeImportantWorkService.save(safeImportantWork);

        List<ProportionDTO> list = addSafeImportantWorkDTO.getProportions();
        for (ProportionDTO dto : list) {
            SafeImportantWorkProportion gp = new SafeImportantWorkProportion();
            gp.setId(IdUtil.randomUUID());
            gp.setJobId(safeImportantWork.getId());
            gp.setMajor(dto.getMajor());
            gp.setResponsibleUserId(dto.getResponsibleUserId());
            gp.setResponsibleUserName(dto.getResponsibleUserName());
            gp.setJobNumber(dto.getJobNumber());
            gp.setRewardProportion(dto.getRewardProportion());
            safeImportantWorkProportionService.save(gp);
        }

        return AjaxResult.success(ResponseUtil.toResult("安全重点工作增加成功"));
    }


    @ApiOperation(value = "安全重点工作-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键"),
            @ApiImplicitParam(name = "department_id", value = "单位"),
            @ApiImplicitParam(name = "subject", value = "主体"),
            @ApiImplicitParam(name = "complete_target", value = "完成目标"),
            @ApiImplicitParam(name = "complete_state", value = "完成情况"),
            @ApiImplicitParam(name = "plan_complete_time", value = "计划完成时间"),
            @ApiImplicitParam(name = "remarks", value = "备注"),
            @ApiImplicitParam(name = "proportions", value = "【奖励比例】【新增id传空字符串】[{\"id\":\"\",\"jobId\" :\"安全重点工作id\",\"major\" :\"专业\",\"responsibleUserId\" :\"负责人\",\"responsibleUserName\" :\"负责人\",\"jobNumber\" :\"工号\",\"rewardProportion\" :\"比例\"}]"),
            @ApiImplicitParam(name = "modify_user_id", value = "修改人id"),
            @ApiImplicitParam(name = "modify_user_name", value = "修改人")
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @Log(title = "安全重点工作-修改", businessType = BusinessType.UPDATE)

    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public Object update(@Valid @RequestBody UpdateSafeImportantWorkDTO updateSafeImportantWorkDTO) {
        log.info("params => " + updateSafeImportantWorkDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateSafeImportantWorkDTO);
        SafeImportantWork safeImportantWork = safeImportantWorkService.getById(updateSafeImportantWorkDTO.getId());
        if (null != safeImportantWork) {
            safeImportantWork.setDepartmentId(updateSafeImportantWorkDTO.getDepartmentId());
            safeImportantWork.setSubject(updateSafeImportantWorkDTO.getSubject());
            safeImportantWork.setCompleteTarget(updateSafeImportantWorkDTO.getCompleteTarget());
            safeImportantWork.setCompleteState(updateSafeImportantWorkDTO.getCompleteState());
            safeImportantWork.setPlanCompleteTime(updateSafeImportantWorkDTO.getPlanCompleteTime());
            safeImportantWork.setRemarks(updateSafeImportantWorkDTO.getRemarks());
            safeImportantWork.setModifyUserId(updateSafeImportantWorkDTO.getModifyUserId());
            safeImportantWork.setModifyUserName(updateSafeImportantWorkDTO.getModifyUserName());
            safeImportantWork.setModifyTime(new Date());
            safeImportantWorkService.updateById(safeImportantWork);

            List<ProportionDTO> list = updateSafeImportantWorkDTO.getProportions();
            List idList = list.stream().map(ProportionDTO::getId).collect(Collectors.toList());
            QueryWrapper<SafeImportantWorkProportion> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("job_id", safeImportantWork.getId()).notIn("id", idList);
            safeImportantWorkProportionService.remove(queryWrapper);
            for (ProportionDTO dto : list) {
                SafeImportantWorkProportion gp = new SafeImportantWorkProportion();
                gp.setJobId(safeImportantWork.getId());
                gp.setMajor(dto.getMajor());
                gp.setResponsibleUserId(dto.getResponsibleUserId());
                gp.setResponsibleUserName(dto.getResponsibleUserName());
                gp.setJobNumber(dto.getJobNumber());
                gp.setRewardProportion(dto.getRewardProportion());
                if ("".equals(dto.getId())) {
                    gp.setId(IdUtil.randomUUID());
                    safeImportantWorkProportionService.save(gp);
                } else {
                    gp.setId(dto.getId());
                    safeImportantWorkProportionService.updateById(gp);
                }
            }
            return AjaxResult.success(ResponseUtil.toResult("安全重点工作修改成功"));
        } else {
            return AjaxResult.error(HttpStatus.ERROR, "安全重点工作不存在");
        }
    }

    @ApiOperation(value = "安全重点工作-删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @Log(title = "安全重点工作-删除", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")

    public Object delete(@Valid @RequestBody DeleteSafeImportantWorkDTO deleteSafeImportantWorkDTO) {
        log.info("params => " + deleteSafeImportantWorkDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteSafeImportantWorkDTO);
        SafeImportantWork safeImportantWork = safeImportantWorkService.getById(deleteSafeImportantWorkDTO.getId());
        if (null != safeImportantWork) {
            safeImportantWorkService.removeById(safeImportantWork);
            QueryWrapper<SafeImportantWorkProportion> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("job_id", deleteSafeImportantWorkDTO.getId());
            safeImportantWorkProportionService.remove(queryWrapper);

            return AjaxResult.success(ResponseUtil.toResult("安全重点工作删除成功"));
        } else {
            return AjaxResult.error(HttpStatus.ERROR, "安全重点工作不存在或已被删除");
        }
    }


    @ApiOperation(value = "安全重点工作-奖励比例列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "安全重点工作id"),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:主键标识\n" +
                                            "job_id:单位id\n" +
                                            "major:专业\n" +
                                            "responsible_user_id:负责人id\n" +
                                            "responsible_user_name:负责人姓名\n" +
                                            "job_number:工号\n" +
                                            "reward_proportion:奖励比例\n"
                            )
                    )
            )
    )
    @Log(title = "安全重点工作-奖励比例列表", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listProportions", method = RequestMethod.GET)

    public Object proportions(@Validated @ParamModel ListProportionsDTO dto) {
        log.info("params => " + dto);
        String errorMsg = ValidUtil.checkValid(dto);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        QueryWrapper<SafeImportantWorkProportion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("job_id", dto.getId());
        List list = safeImportantWorkProportionService.list(queryWrapper);
        return AjaxResult.success(list);
    }


}
