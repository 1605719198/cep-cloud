package com.jlkj.material.mr.controller;

import com.jlkj.common.datascope.annotation.ParamModel;

import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.ValidUtil;
import com.jlkj.material.mr.dto.materialssilo.AddMaterialsSiloDTO;
import com.jlkj.material.mr.dto.materialssilo.DeleteMaterialsSiloDTO;
import com.jlkj.material.mr.dto.materialssilo.PageMaterialsSiloDTO;
import com.jlkj.material.mr.dto.materialssilo.UpdateMaterialsSiloDTO;
import com.jlkj.material.mr.service.MaterialsSiloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * 控制器-料仓维护
 * @author sudeyou
 */
@Tag(name = "料仓维护")
@RestController
@RequestMapping("/parameter")
@Slf4j
public class MaterialsSiloController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private MaterialsSiloService materialsSiloService;

    @Operation(summary = "查询-分页-料仓维护",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "current", description = "页码-从1开始"),
                    @Parameter(name = "size", description = "每页行数"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则字段")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "silo_name", description = "料仓名称"),
                                    @ExampleObject(name = "silo_length", description = "料仓长度"),
                                    @ExampleObject(name = "create_user_id", description = "创建人"),
                                    @ExampleObject(name = "create_user_name", description = "创建人"),
                                    @ExampleObject(name = "create_time", description = "创建时间"),
                                    @ExampleObject(name = "modify_user_id", description = "修改人id"),
                                    @ExampleObject(name = "modify_user_name", description = "修改人"),
                                    @ExampleObject(name = "modify_time", description = "修改时间"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "查询-分页-料仓维护", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listSilos", method = RequestMethod.GET)
    public Object getPageData(@Validated @ParamModel PageMaterialsSiloDTO pageMaterialsSiloDTO) {
        log.info("params => " + pageMaterialsSiloDTO);
        String errorMsg = ValidUtil.checkValid(pageMaterialsSiloDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageMaterialsSiloDTO);
        return AjaxResult.success(materialsSiloService.getPageData(pageMaterialsSiloDTO));
    }

    @Operation(summary = "查询-列表-料仓-下拉",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "料仓id"),
                                    @ExampleObject(name = "silo_name", description = "料仓名称"),
                                    @ExampleObject(name = "silo_length", description = "料仓长度")
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "查询-列表-料仓-下拉", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listSilosBox", method = RequestMethod.GET)
    public Object getListBoxData() {
        return materialsSiloService.getListBoxData();
    }

    @Operation(summary = "新增-料仓维护",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                                    @ExampleObject(name = "silo_name", description = "料仓名称"),
                                    @ExampleObject(name = "create_user_id", description = "创建人"),
                                    @ExampleObject(name = "create_user_name", description = "创建人"),
                                    @ExampleObject(name = "silo_length", description = "料仓长度")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "新增-料仓维护", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/saveSilo", method = RequestMethod.POST, produces = "application/json")
    public Object addData(@Valid @RequestBody AddMaterialsSiloDTO addMaterialsSiloDTO) {
        log.info("params => " + addMaterialsSiloDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, addMaterialsSiloDTO);
        return materialsSiloService.addData(addMaterialsSiloDTO);
    }

    @Operation(summary = "修改-料仓维护",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "silo_name", description = "料仓名称"),
                                    @ExampleObject(name = "modify_user_id", description = "修改人id"),
                                    @ExampleObject(name = "modify_user_name", description = "修改人"),
                                    @ExampleObject(name = "silo_length", description = "料仓长度")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "修改-料仓维护", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/updateSilo", method = RequestMethod.PUT, produces = "application/json")
    public Object updateData(@Valid @RequestBody UpdateMaterialsSiloDTO updateMaterialsSiloDTO) {
        log.info("params => " + updateMaterialsSiloDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateMaterialsSiloDTO);
        return materialsSiloService.updateData(updateMaterialsSiloDTO);
    }

    @Operation(summary = "删除-料仓维护",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "删除-料仓维护", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/deleteSilos", method = RequestMethod.DELETE, produces = "application/json")
    public Object deleteData(@Valid @RequestBody DeleteMaterialsSiloDTO deleteMaterialsSiloDTO) {
        log.info("params => " + deleteMaterialsSiloDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteMaterialsSiloDTO);
        return materialsSiloService.deleteData(deleteMaterialsSiloDTO);
    }
}

