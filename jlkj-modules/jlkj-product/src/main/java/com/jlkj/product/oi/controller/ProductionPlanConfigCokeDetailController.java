package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionplanconfigcokedetail.*;
import com.jlkj.product.oi.service.ProductionPlanConfigCokeDetailService;
import com.jlkj.product.oi.vo.productionplanconfigcokedetail.InfoProductionPlanConfigCokeDetailByWarehouseNumberVO;
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
*@description: 配煤计划配煤记录
*@Author: 265823
*@date: 2023/7/10 8:49
*/
@Tag(name = "配煤计划配煤记录")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionPlanConfigCokeDetailController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionPlanConfigCokeDetailService productionPlanConfigCokeDetailService;

    /**
     * 查询-分页-配煤计划配煤记录
     * @param pageProductionPlanConfigCokeDetailDTO
     * @return
     */
    @Operation(summary = "查询-分页-配煤计划配煤记录",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "plan_id", description = "计划编号"),
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
                                    @ExampleObject(name = "plan_id", description = "计划编号"),
                                    @ExampleObject(name = "warehouse_number", description = "配煤仓号"),
                                    @ExampleObject(name = "coal_type_id", description = "煤种编号"),
                                    @ExampleObject(name = "coal_type_name", description = "煤种名称"),
                                    @ExampleObject(name = "proportion", description = "配置比例"),
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

    @Log(title = "查询-分页-配煤计划配煤记录",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listProductionPlanCfgCokeDitail", method = RequestMethod.GET)
    public AjaxResult getPageData(@Validated @ParamModel PageProductionPlanConfigCokeDetailDTO pageProductionPlanConfigCokeDetailDTO) {
        log.info("params => " + pageProductionPlanConfigCokeDetailDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionPlanConfigCokeDetailDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionPlanConfigCokeDetailDTO);
        return AjaxResult.success(productionPlanConfigCokeDetailService.getPageData(pageProductionPlanConfigCokeDetailDTO));
    }

    /**
     * 新增-配煤计划配煤记录
     * @param addProductionPlanConfigCokeDetailDTO
     */
    @Operation(summary = "新增-配煤计划配煤记录",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                                    @ExampleObject(name = "plan_id", description = "计划编号"),
                                    @ExampleObject(name = "warehouse_number", description = "配煤仓号"),
                                    @ExampleObject(name = "coal_type_id", description = "煤种编号"),
                                    @ExampleObject(name = "coal_type_name", description = "煤种名称"),
                                    @ExampleObject(name = "proportion", description = "配置比例"),
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
    @Log(title = "新增-配煤计划配煤记录",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/addProductionPlanCfgCokeDetail", method = RequestMethod.POST, produces = "application/json")
    public AjaxResult addData(@Valid @RequestBody AddProductionPlanConfigCokeDetailDTO addProductionPlanConfigCokeDetailDTO) {
        log.info("params => " + addProductionPlanConfigCokeDetailDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, addProductionPlanConfigCokeDetailDTO);
        return productionPlanConfigCokeDetailService.addData(addProductionPlanConfigCokeDetailDTO);
    }

    /**
     * 修改-配煤计划配煤记录
     * @param updateProductionPlanConfigCokeDetailDTO
     */
    @Operation(summary = "修改-配煤计划配煤记录",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "warehouse_number", description = "配煤仓号"),
                                    @ExampleObject(name = "coal_type_id", description = "煤种编号"),
                                    @ExampleObject(name = "coal_type_name", description = "煤种名称"),
                                    @ExampleObject(name = "proportion", description = "配置比例"),
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
    @Log(title = "修改-配煤计划配煤记录",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/editProductionPlanCfgCokeDetail", method = RequestMethod.POST, produces = "application/json")
    public AjaxResult updateData(@Valid @RequestBody UpdateProductionPlanConfigCokeDetailDTO updateProductionPlanConfigCokeDetailDTO) {
        log.info("params => " + updateProductionPlanConfigCokeDetailDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateProductionPlanConfigCokeDetailDTO);
        return productionPlanConfigCokeDetailService.updateData(updateProductionPlanConfigCokeDetailDTO);
    }

    /**
     * 删除-配煤计划配煤记录
     * @param deleteProductionPlanConfigCokeDetailDTO
     */
    @Operation(summary = "删除-配煤计划配煤记录",
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
    @Log(title = "删除-配煤计划配煤记录",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/delProductionPlanCfgCokeDetail", method = RequestMethod.POST, produces = "application/json")
    public void deleteData(@Valid @RequestBody DeleteProductionPlanConfigCokeDetailDTO deleteProductionPlanConfigCokeDetailDTO) {
        log.info("params => " + deleteProductionPlanConfigCokeDetailDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionPlanConfigCokeDetailDTO);
        productionPlanConfigCokeDetailService.deleteData(deleteProductionPlanConfigCokeDetailDTO);
    }

    /**
     * 获取配煤仓配煤计划信息
     * @param infoProductionPlanConfigCokeDetailByWarehouseNumberDTO
     * @return
     */
    @Operation(summary = "获取配煤仓配煤计划信息",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = InfoProductionPlanConfigCokeDetailByWarehouseNumberVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "获取配煤仓配煤计划信息",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionPlanConfigCokeDetailInfoByWarehouseNumber", method = RequestMethod.GET)
    public AjaxResult getProductionPlanConfigCokeDetailInfoByWarehouseNumberData(@Validated @ParamModel InfoProductionPlanConfigCokeDetailByWarehouseNumberDTO infoProductionPlanConfigCokeDetailByWarehouseNumberDTO) {
        log.info("params => " + infoProductionPlanConfigCokeDetailByWarehouseNumberDTO);
        String errorMsg = ValidUtil.checkValid(infoProductionPlanConfigCokeDetailByWarehouseNumberDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, infoProductionPlanConfigCokeDetailByWarehouseNumberDTO);
        return AjaxResult.success(productionPlanConfigCokeDetailService.getProductionPlanConfigCokeDetailInfoByWarehouseNumberData(infoProductionPlanConfigCokeDetailByWarehouseNumberDTO));
    }
}
