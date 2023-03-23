package com.jlkj.system.controller;

import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.system.dto.department.GetDepartmentListDTO;
import com.jlkj.system.service.impl.HumanresourceOrganizationServiceImpl;
import com.jlkj.system.swaggerdto.HumanresourceOrganizationSwagger;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author zyf
 * @Description
 * @create 2022-07-05 8:29
 */
@Tag(name = "组织机构")
@RestController
@RequestMapping("/department")
@Slf4j
public class DepartmentController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    HumanresourceOrganizationServiceImpl organizationService;

    @Operation(summary = "部门树",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "label", description = "树名称"),
                            @ExampleObject(name = "children", description = "子节点集合"),
                            @ExampleObject(name = "data", description = "部门详细")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = HumanresourceOrganizationSwagger.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "部门树", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public Object getDepartmentTree() {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return organizationService.getDepartmentTree();
    }

    @Operation(summary = "部门分页列表",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "department_id", description = "节点id，全查传空字符"),
                    @Parameter(name = "department_name", description = "部门名称"),
                    @Parameter(name = "size", description = "10"),
                    @Parameter(name = "current", description = "1")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = HumanresourceOrganizationSwagger.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "部门分页列表", businessType = BusinessType.OTHER)
    @GetMapping(value = "/list")
    public Object get(@Validated GetDepartmentListDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return organizationService.getDepartmentList(dto);
    }
}
