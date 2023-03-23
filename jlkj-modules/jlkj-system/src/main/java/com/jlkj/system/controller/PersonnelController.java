package com.jlkj.system.controller;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jlkj.common.core.constant.HttpStatus;
import com.jlkj.common.core.utils.ValidUtil;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.system.dto.user.*;
import com.jlkj.system.entity.HumanresourcePersonnel;
import com.jlkj.system.service.impl.HumanresourcePersonnelServiceImpl;
import com.jlkj.system.swaggerdto.HumanresourcePersonnelSwagger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @description:
 * @author: sdy
 * @time: 2022/4/19 8:53
 */
@Tag(name = "人员管理")
@RestController
@RequestMapping("/personnel")
@EnableAspectJAutoProxy(exposeProxy = true)
@Slf4j
public class PersonnelController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    HumanresourcePersonnelServiceImpl humanresourcePersonnelService;

    @Operation(summary = "修改密码",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "user_id", description = "用户Id"),
                            @ExampleObject(name = "old_password", description = "原密码"),
                            @ExampleObject(name = "new_password", description = "新密码"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "修改密码", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/password/edit", method = RequestMethod.POST, produces = "application/json")
    @Transactional(rollbackFor = Exception.class)
    public Object editPassword(@Validated @RequestBody UpdatePasswordDTO updatePasswordDTO, BindingResult bindingResult) {
        log.info("params => " + updatePasswordDTO);
        if (bindingResult.hasErrors()) {
            return AjaxResult.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updatePasswordDTO);
        HumanresourcePersonnel humanresourcePersonnel = humanresourcePersonnelService.getOne(new QueryWrapper<HumanresourcePersonnel>().lambda()
                .eq(HumanresourcePersonnel::getId, updatePasswordDTO.getUserId()));
        if (humanresourcePersonnel == null) {
            return AjaxResult.error(HttpStatus.ERROR, "账号不存在");
        }
        String oldPassword = SecureUtil.md5(updatePasswordDTO.getOldPassword() + humanresourcePersonnel.getJobNumber());
        String newPassword = SecureUtil.md5(updatePasswordDTO.getNewPassword() + humanresourcePersonnel.getJobNumber());
        if (!oldPassword.equals(humanresourcePersonnel.getPassword())) {
            return AjaxResult.error(HttpStatus.ERROR, "原密码错误");
        }
        if (oldPassword.equals(newPassword)) {
            return AjaxResult.error(HttpStatus.ERROR, "新密码和原密码相同");
        }
        humanresourcePersonnel.setPassword(newPassword);
        humanresourcePersonnelService.updateById(humanresourcePersonnel);
        return AjaxResult.success("密码修改成功");
    }

    @Operation(summary = "根据部门id获取人员列表",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "department_id", description = "部门id")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = HumanresourcePersonnelSwagger.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "根据部门id获取人员列表", businessType = BusinessType.OTHER)
    @GetMapping(value = "/getUserListByDepartment", produces = "application/json")
    public Object getUserListByDepartment(@Valid GetUserListByDepartmentDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return humanresourcePersonnelService.getUserListByDepartment(dto);
    }

    @Operation(summary = "根据部门、姓名、工号获取人员列表",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "department_id", description = "部门id"),
                    @Parameter(name = "keywords", description = "关键词"),
                    @Parameter(name = "size", description = "10"),
                    @Parameter(name = "current", description = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = HumanresourcePersonnelSwagger.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "根据部门、姓名、工号获取人员列表", businessType = BusinessType.OTHER)
    @GetMapping(value = "/getUserListByDepartmentOrNameOrJobnumber", produces = "application/json")
    public Object getUserListByDepartmentOrNameOrJobnumber(@Valid GetUserListByNameOrJobNumberDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return humanresourcePersonnelService.getUserListByDepartmentOrNameOrJobnumber(dto);
    }

    @Operation(summary = "根据人员id集合获取人员列表",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "user_ids", description = "id数组：[id1, id2]")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = HumanresourcePersonnelSwagger.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "根据人员id集合获取人员列表", businessType = BusinessType.OTHER)
    @PostMapping(value = "/getUserListByUserIds")
    public Object getUserListByUserIds(@Valid @RequestBody GetUsersByIdsDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return humanresourcePersonnelService.getUsersByids(dto);
    }

    @Operation(summary = "重置密码",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "user_id", description = "用户id")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping(value = "/password/reset", produces = "application/json")
    @Transactional(rollbackFor = Exception.class)
    public Object resetPassword(@Validated @RequestBody ResetPasswordDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        HumanresourcePersonnel humanresourcePersonnel = humanresourcePersonnelService.getOne(new QueryWrapper<HumanresourcePersonnel>().lambda()
                .eq(HumanresourcePersonnel::getId, dto.getUserId()));
        if (humanresourcePersonnel == null) {
            return AjaxResult.error(HttpStatus.ERROR, "账号不存在");
        }
        String newPassword = SecureUtil.md5("123456" + humanresourcePersonnel.getJobNumber());
        humanresourcePersonnel.setPassword(newPassword);
        humanresourcePersonnelService.updateById(humanresourcePersonnel);
        return AjaxResult.success("密码重置成功");
    }




    @Operation(summary = "获取人员排班信息列表",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "user_ids", description = "id数组：[id1, id2]")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = HumanresourcePersonnelSwagger.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "获取人员排班信息列表", businessType = BusinessType.OTHER)
    @GetMapping(value = "/getPersonnelScheduling")
    public Object getPersonnelScheduling(@Validated @ParamModel GetPersonnelSchedulingDTO dto) {
        log.info("params => " + dto);
        String errorMsg = ValidUtil.checkValid(dto);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        IPage<Map<String, String>> list = humanresourcePersonnelService.getPersonnelScheduling(dto);
        return AjaxResult.success(list);
    }


}
