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
import com.jlkj.safety.si.dto.safesiteenvironmentalgovernance.*;
import com.jlkj.safety.si.entity.SafeSiteEnvironmentalGovernance;
import com.jlkj.safety.si.entity.SafeSiteEnvironmentalGovernanceProportion;
import com.jlkj.safety.si.service.SafeSiteEnvironmentalGovernanceProportionService;
import com.jlkj.safety.si.service.SafeSiteEnvironmentalGovernanceService;
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
 * @Description 现场环境治理
 * @create 2022年6月8日
 */
@Api(tags = "现场环境治理")
@RestController
@RequestMapping("/safe/safeSiteEnvironmentalGovernance")
@Slf4j
public class SafeSiteEnvironmentalGovernanceController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    SafeSiteEnvironmentalGovernanceService safeSiteEnvironmentalGovernanceService;
    @Autowired
    SafeSiteEnvironmentalGovernanceProportionService safeSiteEnvironmentalGovernanceProportionService;

    @ApiOperation(value = "现场环境治理-查询列表", notes = "")
    @ApiImplicitParams({
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
                                            "work_items:工作事项\n" +
                                            "responsible_user_id:负责人id\n" +
                                            "responsible_user_name:负责人\n" +
                                            "job_number:工号\n" +
                                            "complete_state:完成情况\n" +
                                            "proportion:比例\n" +
                                            "work_target:工作目标\n" +
                                            "rectification_before_url:整改前照片\n" +
                                            "rectification_after_url:整改后照片\n" +
                                            "create_user_id:创建人\n" +
                                            "create_user_name:创建人\n" +
                                            "create_time:创建时间\n" +
                                            "modify_user_id:修改人id\n" +
                                            "modify_user_name:修改人\n" +
                                            "modify_time:修改时间\n"
                            )
                    )
            )
    )
    @Log(title = "现场环境治理-查询列表", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/list", method = RequestMethod.GET)

    public Object list(@Validated @ParamModel PageSafeSiteEnvironmentalGovernanceDTO pageSafeSiteEnvironmentalGovernanceDTO) {
        log.info("params => " + pageSafeSiteEnvironmentalGovernanceDTO);
        String errorMsg = ValidUtil.checkValid(pageSafeSiteEnvironmentalGovernanceDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageSafeSiteEnvironmentalGovernanceDTO);
        IPage<Map<String, String>> list = safeSiteEnvironmentalGovernanceService.getListPage(pageSafeSiteEnvironmentalGovernanceDTO);
        return AjaxResult.success(list);
    }


    @ApiOperation(value = "现场环境治理-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "department_id", value = "单位"),
            @ApiImplicitParam(name = "work_items", value = "工作事项"),
            @ApiImplicitParam(name = "complete_state", value = "完成情况"),
            @ApiImplicitParam(name = "work_target", value = "工作目标"),
            @ApiImplicitParam(name = "rectification_before_url", value = "整改前照片"),
            @ApiImplicitParam(name = "rectification_after_url", value = "整改后照片"),
            @ApiImplicitParam(name = "proportions", value = "奖励比例=》[{\"id\":\"\",\"jobId\" :\"\",\"responsibleUserId\" :\"\",\"responsibleUserName\" :\"\",\"responsibleUserNo\" :\"\",\"rewardProportion\" :\"\"}]"),
            @ApiImplicitParam(name = "create_user_id", value = "创建人id"),
            @ApiImplicitParam(name = "create_user_name", value = "创建人")
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @Log(title = "现场环境治理-新增", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")

    public Object save(@Valid @RequestBody AddSafeSiteEnvironmentalGovernanceDTO addSafeSiteEnvironmentalGovernanceDTO) {
        log.info("params => " + addSafeSiteEnvironmentalGovernanceDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, addSafeSiteEnvironmentalGovernanceDTO);
        SafeSiteEnvironmentalGovernance safeSiteEnvironmentalGovernance = new SafeSiteEnvironmentalGovernance();
        safeSiteEnvironmentalGovernance.setId(IdUtil.randomUUID());
        safeSiteEnvironmentalGovernance.setDepartmentId(addSafeSiteEnvironmentalGovernanceDTO.getDepartmentId());
        safeSiteEnvironmentalGovernance.setWorkItems(addSafeSiteEnvironmentalGovernanceDTO.getWorkItems());
        safeSiteEnvironmentalGovernance.setCompleteState(addSafeSiteEnvironmentalGovernanceDTO.getCompleteState());
        safeSiteEnvironmentalGovernance.setWorkTarget(addSafeSiteEnvironmentalGovernanceDTO.getWorkTarget());
        safeSiteEnvironmentalGovernance.setRectificationBeforeUrl(addSafeSiteEnvironmentalGovernanceDTO.getRectificationBeforeUrl());
        safeSiteEnvironmentalGovernance.setRectificationAfterUrl(addSafeSiteEnvironmentalGovernanceDTO.getRectificationAfterUrl());
        safeSiteEnvironmentalGovernance.setCreateUserId(addSafeSiteEnvironmentalGovernanceDTO.getCreateUserId());
        safeSiteEnvironmentalGovernance.setCreateUserName(addSafeSiteEnvironmentalGovernanceDTO.getCreateUserName());
        safeSiteEnvironmentalGovernance.setCreateTime(new Date());
        safeSiteEnvironmentalGovernance.setModifyUserId(addSafeSiteEnvironmentalGovernanceDTO.getCreateUserId());
        safeSiteEnvironmentalGovernance.setModifyUserName(addSafeSiteEnvironmentalGovernanceDTO.getCreateUserName());
        safeSiteEnvironmentalGovernance.setModifyTime(new Date());
        safeSiteEnvironmentalGovernanceService.save(safeSiteEnvironmentalGovernance);

        List<ProportionGovernanceDTO> list = addSafeSiteEnvironmentalGovernanceDTO.getProportions();
        for (ProportionGovernanceDTO dto : list) {
            SafeSiteEnvironmentalGovernanceProportion gp = new SafeSiteEnvironmentalGovernanceProportion();
            gp.setId(IdUtil.randomUUID());
            gp.setJobId(safeSiteEnvironmentalGovernance.getId());
            gp.setResponsibleUserId(dto.getResponsibleUserId());
            gp.setResponsibleUserName(dto.getResponsibleUserName());
            gp.setResponsibleUserNo(dto.getResponsibleUserNo());
            gp.setRewardProportion(dto.getRewardProportion());
            safeSiteEnvironmentalGovernanceProportionService.save(gp);
        }
        return AjaxResult.success(ResponseUtil.toResult("现场环境治理增加成功"));
    }


    @ApiOperation(value = "现场环境治理-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键"),
            @ApiImplicitParam(name = "department_id", value = "单位"),
            @ApiImplicitParam(name = "work_items", value = "工作事项"),
            @ApiImplicitParam(name = "complete_state", value = "完成情况"),
            @ApiImplicitParam(name = "work_target", value = "工作目标"),
            @ApiImplicitParam(name = "rectification_before_url", value = "整改前照片"),
            @ApiImplicitParam(name = "rectification_after_url", value = "整改后照片"),
            @ApiImplicitParam(name = "proportions", value = "【奖励比例】【新增id传空字符串】[{\"id\":\"\",\"responsible_user_id\" :\"负责人\",\"responsible_user_name\" :\"负责人\",\"responsible_user_no\" :\"工号\",\"reward_proportion\" :\"比例\"}]"),
            @ApiImplicitParam(name = "modify_user_id", value = "修改人id"),
            @ApiImplicitParam(name = "modify_user_name", value = "修改人")
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @Log(title = "现场环境治理-修改", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")

    public Object update(@Valid @RequestBody UpdateSafeSiteEnvironmentalGovernanceDTO updateSafeSiteEnvironmentalGovernanceDTO) {
        log.info("params => " + updateSafeSiteEnvironmentalGovernanceDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateSafeSiteEnvironmentalGovernanceDTO);
        SafeSiteEnvironmentalGovernance safeSiteEnvironmentalGovernance = safeSiteEnvironmentalGovernanceService.getById(updateSafeSiteEnvironmentalGovernanceDTO.getId());
        if (null != safeSiteEnvironmentalGovernance) {
            safeSiteEnvironmentalGovernance.setDepartmentId(updateSafeSiteEnvironmentalGovernanceDTO.getDepartmentId());
            safeSiteEnvironmentalGovernance.setWorkItems(updateSafeSiteEnvironmentalGovernanceDTO.getWorkItems());
            safeSiteEnvironmentalGovernance.setCompleteState(updateSafeSiteEnvironmentalGovernanceDTO.getCompleteState());
            safeSiteEnvironmentalGovernance.setWorkTarget(updateSafeSiteEnvironmentalGovernanceDTO.getWorkTarget());
            safeSiteEnvironmentalGovernance.setRectificationBeforeUrl(updateSafeSiteEnvironmentalGovernanceDTO.getRectificationBeforeUrl());
            safeSiteEnvironmentalGovernance.setRectificationAfterUrl(updateSafeSiteEnvironmentalGovernanceDTO.getRectificationAfterUrl());
            safeSiteEnvironmentalGovernance.setModifyUserId(updateSafeSiteEnvironmentalGovernanceDTO.getModifyUserId());
            safeSiteEnvironmentalGovernance.setModifyUserName(updateSafeSiteEnvironmentalGovernanceDTO.getModifyUserName());
            safeSiteEnvironmentalGovernance.setModifyTime(new Date());
            safeSiteEnvironmentalGovernanceService.updateById(safeSiteEnvironmentalGovernance);

            List<ProportionGovernanceDTO> list = updateSafeSiteEnvironmentalGovernanceDTO.getProportions();
            List idList = list.stream().map(ProportionGovernanceDTO::getId).collect(Collectors.toList());
            QueryWrapper<SafeSiteEnvironmentalGovernanceProportion> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("job_id", safeSiteEnvironmentalGovernance.getId()).notIn("id", idList);
            safeSiteEnvironmentalGovernanceProportionService.remove(queryWrapper);
            for (ProportionGovernanceDTO dto : list) {
                SafeSiteEnvironmentalGovernanceProportion gp = new SafeSiteEnvironmentalGovernanceProportion();
                gp.setJobId(safeSiteEnvironmentalGovernance.getId());
                gp.setResponsibleUserId(dto.getResponsibleUserId());
                gp.setResponsibleUserName(dto.getResponsibleUserName());
                gp.setResponsibleUserNo(dto.getResponsibleUserNo());
                gp.setRewardProportion(dto.getRewardProportion());
                if ("".equals(dto.getId())) {
                    gp.setId(IdUtil.randomUUID());
                    safeSiteEnvironmentalGovernanceProportionService.save(gp);
                } else {
                    gp.setId(dto.getId());
                    safeSiteEnvironmentalGovernanceProportionService.updateById(gp);
                }
            }

            return AjaxResult.success(ResponseUtil.toResult("现场环境治理修改成功"));
        } else {
            return AjaxResult.error(HttpStatus.ERROR, "现场环境治理不存在");
        }
    }

    @ApiOperation(value = "现场环境治理-删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @Log(title = "现场环境治理-删除", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")

    public Object delete(@Valid @RequestBody DeleteSafeSiteEnvironmentalGovernanceDTO deleteSafeSiteEnvironmentalGovernanceDTO) {
        log.info("params => " + deleteSafeSiteEnvironmentalGovernanceDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteSafeSiteEnvironmentalGovernanceDTO);
        SafeSiteEnvironmentalGovernance safeSiteEnvironmentalGovernance = safeSiteEnvironmentalGovernanceService.getById(deleteSafeSiteEnvironmentalGovernanceDTO.getId());
        if (null != safeSiteEnvironmentalGovernance) {
            safeSiteEnvironmentalGovernanceService.removeById(safeSiteEnvironmentalGovernance);
            QueryWrapper<SafeSiteEnvironmentalGovernanceProportion> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("job_id", deleteSafeSiteEnvironmentalGovernanceDTO.getId());
            safeSiteEnvironmentalGovernanceProportionService.remove(queryWrapper);
            return AjaxResult.success(ResponseUtil.toResult("现场环境治理删除成功"));
        } else {
            return AjaxResult.error(HttpStatus.ERROR, "现场环境治理不存在或已被删除");
        }
    }

    @ApiOperation(value = "现场环境治理-奖励比例列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "现场环境治理id"),
    })
    @ApiResponses(
            @ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:主键标识\n" +
                                            "job_id:单位id\n" +
                                            "responsible_user_id:负责人id\n" +
                                            "responsible_user_name:负责人姓名\n" +
                                            "responsible_user_no:工号\n" +
                                            "reward_proportion:奖励比例\n"
                            )
                    )
            )
    )
    @Log(title = "现场环境治理-奖励比例列表", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listProportions", method = RequestMethod.GET)
    public Object proportions(@Validated @ParamModel ListProportionsGovernanceDTO dto) {
        log.info("params => " + dto);
        String errorMsg = ValidUtil.checkValid(dto);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        QueryWrapper<SafeSiteEnvironmentalGovernanceProportion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("job_id", dto.getId());
        List list = safeSiteEnvironmentalGovernanceProportionService.list(queryWrapper);
        return AjaxResult.success(list);
    }


}

