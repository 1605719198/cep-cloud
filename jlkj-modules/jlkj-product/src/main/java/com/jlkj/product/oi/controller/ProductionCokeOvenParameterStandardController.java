package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productioncokeovenparameterstandard.AddProductionCokeOvenParameterStandardDTO;
import com.jlkj.product.oi.dto.productioncokeovenparameterstandard.PageProductionCokeOvenParameterStandardDTO;
import com.jlkj.product.oi.dto.productioncokeovenparameterstandard.UpdateProductionCokeOvenParameterStandardDTO;
import com.jlkj.product.oi.dto.productionparametertargetitem.DeleteProductionParameterTargetItemDTO;
import com.jlkj.product.oi.service.ProductionCokeOvenParameterStandardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
*@description: 参数维护-K值标准
*@Author: 265823
*@date: 2023/7/10 13:05
*/
@Tag(name = "参数维护-K值标准")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionCokeOvenParameterStandardController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionCokeOvenParameterStandardService productionCokeOvenParameterStandardService;

    @Autowired
    ProductionCokeOvenParameterStandardService cokeOvenParameterStandardService;

    /**
     * 查询K值标准
     * @param pageProductionCokeOvenParameterStandardDTO
     * @return
     */
    @Operation(summary = "查询K值标准",
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
                                    @ExampleObject(name = "id", description = "主键标识"),
                                    @ExampleObject(name = "standard_coal_loading", description = "规定装煤量"),
                                    @ExampleObject(name = "coking_cycle", description = "规定结焦时间"),
                                    @ExampleObject(name = "fire_temperature", description = "焦侧标准火道温度"),
                                    @ExampleObject(name = "zhi_hang_temperature", description = "直行标准温度"),
                                    @ExampleObject(name = "coal_loading_factor", description = "K装正负"),
                                    @ExampleObject(name = "planning_factor", description = "K计正负"),
                                    @ExampleObject(name = "average_coefficient", description = "K均正负"),
                                    @ExampleObject(name = "stability_factor", description = "K安正负"),
                                    @ExampleObject(name = "create_user_id", description = "创建人id"),
                                    @ExampleObject(name = "create_user_name", description = "创建人"),
                                    @ExampleObject(name = "create_time", description = "创建时间"),
                                    @ExampleObject(name = "modify_user_id", description = "修改人id"),
                                    @ExampleObject(name = "modify_user_name", description = "修改人"),
                                    @ExampleObject(name = "modify_time", description = "修改时间"),
                                    @ExampleObject(name = "size", description = "10"),
                                    @ExampleObject(name = "current", description = "1")
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "查询K值标准",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listCokeOvenParameterStandards", method = RequestMethod.GET)
    public AjaxResult listCokeOvenParameterStandards(@Validated @ParamModel PageProductionCokeOvenParameterStandardDTO pageProductionCokeOvenParameterStandardDTO) {
        log.info("params => " + pageProductionCokeOvenParameterStandardDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionCokeOvenParameterStandardDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionCokeOvenParameterStandardDTO);
        return AjaxResult.success(productionCokeOvenParameterStandardService.getListPage(pageProductionCokeOvenParameterStandardDTO));
    }

    /**
     * 新增K值标准
     * @param addProductionCokeOvenParameterStandardDTO
     * @return
     */
    @Operation(summary = "新增K值标准",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "standard_coal_loading", description = "规定装煤量"),
                            @ExampleObject(name = "coking_cycle", description = "规定结焦时间"),
                            @ExampleObject(name = "fire_temperature", description = "焦侧标准火道温度"),
                            @ExampleObject(name = "zhi_hang_temperature", description = "直行标准温度"),
                            @ExampleObject(name = "coal_loading_factor", description = "K装正负"),
                            @ExampleObject(name = "planning_factor", description = "K计正负"),
                            @ExampleObject(name = "average_coefficient", description = "K均正负"),
                            @ExampleObject(name = "stability_factor", description = "K安正负"),
                            @ExampleObject(name = "create_user_id", description = "创建人id"),
                            @ExampleObject(name = "create_user_name", description = "创建人")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "新增K值标准",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/saveCokeOvenParameterStandard", method = RequestMethod.POST, produces = "application/json")
    public void saveCokeOvenParameterStandard(@Valid @RequestBody AddProductionCokeOvenParameterStandardDTO addProductionCokeOvenParameterStandardDTO) {
        log.info("params => " + addProductionCokeOvenParameterStandardDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, addProductionCokeOvenParameterStandardDTO);
        cokeOvenParameterStandardService.saveCustom(addProductionCokeOvenParameterStandardDTO);
    }

    /**
     * 修改K值标准
     * @param updateProductionCokeOvenParameterStandardDTO
     * @return
     */
    @Operation(summary = "修改K值标准",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键"),
                            @ExampleObject(name = "standard_coal_loading", description = "规定装煤量"),
                            @ExampleObject(name = "coking_cycle", description = "规定结焦时间"),
                            @ExampleObject(name = "fire_temperature", description = "焦侧标准火道温度"),
                            @ExampleObject(name = "zhi_hang_temperature", description = "直行标准温度"),
                            @ExampleObject(name = "coal_loading_factor", description = "K装正负"),
                            @ExampleObject(name = "planning_factor", description = "K计正负"),
                            @ExampleObject(name = "average_coefficient", description = "K均正负"),
                            @ExampleObject(name = "stability_factor", description = "K安正负"),
                            @ExampleObject(name = "modify_user_id", description = "修改人id"),
                            @ExampleObject(name = "modify_user_name", description = "修改人")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "修改K值标准",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/updateCokeOvenParameterStandard", method = RequestMethod.POST, produces = "application/json")
    public void updateCokeOvenParameterStandard(@Valid @RequestBody UpdateProductionCokeOvenParameterStandardDTO updateProductionCokeOvenParameterStandardDTO) {
        log.info("params => " + updateProductionCokeOvenParameterStandardDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateProductionCokeOvenParameterStandardDTO);
        cokeOvenParameterStandardService.updateCustom(updateProductionCokeOvenParameterStandardDTO);
    }

    /**
     * 删除k值标准
     * @param deleteProductionParameterTargetItemDTO
     * @return
     */
    @Operation(summary = "删除K值标准",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "删除K值标准",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/deleteCokeOvenParameterStandard", method = RequestMethod.POST, produces = "application/json")
    public void deleteCokeOvenParameterStandard(@Valid @RequestBody DeleteProductionParameterTargetItemDTO deleteProductionParameterTargetItemDTO) {
        log.info("params => " + deleteProductionParameterTargetItemDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionParameterTargetItemDTO);
        cokeOvenParameterStandardService.delete(deleteProductionParameterTargetItemDTO);
    }
}
