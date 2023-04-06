package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionplanconfigcoke.*;
import com.jlkj.product.oi.service.ProductionPlanConfigCokeService;
import com.jlkj.product.oi.vo.productionplanconfigcoke.PlanNumberVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * 控制器-配煤计划主记录
 * @author sudeyou
 */
@Tag(name = "配煤计划主记录")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionPlanConfigCokeController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionPlanConfigCokeService productionPlanConfigCokeService;

    @Operation(summary = "查询-分页-配煤计划主记录",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "plan_start_time", description = "开始时间[开始]"),
                    @Parameter(name = "plan_end_time", description = "开始时间[结束]"),
                    @Parameter(name = "plan_state", description = "计划状态"),
                    @Parameter(name = "materialId", description = "焦炭等级"),
                    @Parameter(name = "plan_number", description = "计划编号"),
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
                                    @ExampleObject(name = "plan_number", description = "计划编号"),
                                    @ExampleObject(name = "plan_start_time", description = "开始时间"),
                                    @ExampleObject(name = "plan_end_time", description = "结束时间"),
                                    @ExampleObject(name = "plan_state", description = "计划状态"),
                                    @ExampleObject(name = "receive_user_id", description = "接收人编号"),
                                    @ExampleObject(name = "receive_user_name", description = "接收人姓名"),
                                    @ExampleObject(name = "receive_time", description = "接收确认时间"),
                                    @ExampleObject(name = "create_user_id", description = "创建人编号"),
                                    @ExampleObject(name = "create_user_name", description = "创建人"),
                                    @ExampleObject(name = "create_time", description = "创建时间"),
                                    @ExampleObject(name = "modify_user_id", description = "修改人编号"),
                                    @ExampleObject(name = "modify_user_name", description = "修改人"),
                                    @ExampleObject(name = "modify_time", description = "修改时间"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "查询-分页-配煤计划主记录",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listProductionCfgCokePlans", method = RequestMethod.GET)
    public Object getPageData(@Validated @ParamModel PageProductionPlanConfigCokeDTO pageProductionPlanConfigCokeDTO) {
        log.info("params => " + pageProductionPlanConfigCokeDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionPlanConfigCokeDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionPlanConfigCokeDTO);
        return AjaxResult.success(productionPlanConfigCokeService.getPageData(pageProductionPlanConfigCokeDTO));
    }

    @Operation(summary = "新增-配煤计划主记录",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                                    @ExampleObject(name = "plan_number", description = "计划编号"),
                                    @ExampleObject(name = "plan_start_time", description = "开始时间"),
                                    @ExampleObject(name = "create_user_id", description = "创建人编号"),
                                    @ExampleObject(name = "create_user_name", description = "创建人"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "新增-配煤计划主记录",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/addProductionPlanCfgCoke", method = RequestMethod.POST, produces = "application/json")
    public Object addData(@Valid @RequestBody AddProductionPlanConfigCokeDTO addProductionPlanConfigCokeDTO) {
        log.info("params => " + addProductionPlanConfigCokeDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, addProductionPlanConfigCokeDTO);
        return productionPlanConfigCokeService.addData(addProductionPlanConfigCokeDTO);
    }

    @Operation(summary = "修改-配煤计划主记录",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "plan_number", description = "计划编号"),
                                    @ExampleObject(name = "plan_start_time", description = "开始时间"),
                                    @ExampleObject(name = "modify_user_id", description = "修改人编号"),
                                    @ExampleObject(name = "modify_user_name", description = "修改人"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "修改-配煤计划主记录",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/editProductionPlanCfgCoke", method = RequestMethod.POST, produces = "application/json")
    public Object updateData(@Valid @RequestBody UpdateProductionPlanConfigCokeDTO updateProductionPlanConfigCokeDTO) {
        log.info("params => " + updateProductionPlanConfigCokeDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateProductionPlanConfigCokeDTO);
        return productionPlanConfigCokeService.updateData(updateProductionPlanConfigCokeDTO);
    }

    @Operation(summary = "删除-配煤计划主记录",
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
    @Log(title = "删除-配煤计划主记录",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/delProductionPlanCfgCoke", method = RequestMethod.POST, produces = "application/json")
    public Object deleteData(@Valid @RequestBody DeleteProductionPlanConfigCokeDTO deleteProductionPlanConfigCokeDTO) {
        log.info("params => " + deleteProductionPlanConfigCokeDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionPlanConfigCokeDTO);
        return productionPlanConfigCokeService.deleteData(deleteProductionPlanConfigCokeDTO);
    }

    @Operation(summary = "确认-配煤计划主记录",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键标识;UUID"),
                            @ExampleObject(name = "receive_user_id", description = "接收人编号"),
                            @ExampleObject(name = "receive_user_name", description = "接收人姓名"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "确认-配煤计划主记录",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/updateProductionPlanCfgCokeConfirm", method = RequestMethod.POST, produces = "application/json")
    public Object confirmData(@Valid @RequestBody ConfirmProductionPlanConfigCokeDTO confirmProductionPlanConfigCokeDTO) {
        log.info("params => " + confirmProductionPlanConfigCokeDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, confirmProductionPlanConfigCokeDTO);
        return productionPlanConfigCokeService.confirmData(confirmProductionPlanConfigCokeDTO);
    }

    @Operation(summary = "配煤计划主记录-计划编号",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = PlanNumberVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "配煤计划主记录-计划编号",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionPlanConfigCokePlanNumber", method = RequestMethod.GET)
    public Object getPlanCodeData() {
        return AjaxResult.success(productionPlanConfigCokeService.getPlanCodeData());
    }
    @Operation(summary = "手动切配煤计划-配煤计划主记录",
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
    @Log(title = "手动切配煤计划-配煤计划主记录",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/changeProductionPlanCfgCokeConfirm", method = RequestMethod.POST, produces = "application/json")
    public Object changeProductionPlanCfgCokeConfirm(@Valid @RequestBody ChangeProductionPlanCfgCokeDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return productionPlanConfigCokeService.changeProductionPlanCfgCokeConfirm(dto);
    }
}
