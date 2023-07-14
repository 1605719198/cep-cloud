package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionsingleholeyield.AddProductionSingleHoleYieldDTO;
import com.jlkj.product.oi.dto.productionsingleholeyield.DeleteProductionSingleHoleYieldDTO;
import com.jlkj.product.oi.dto.productionsingleholeyield.PageProductionSingleHoleYieldDTO;
import com.jlkj.product.oi.dto.productionsingleholeyield.UpdateProductionSingleHoleYieldDTO;
import com.jlkj.product.oi.service.ProductionSingleHoleYieldService;
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
*@description: 参数维护-焦炉单孔产量
*@Author: 265823
*@date: 2023/7/11 11:30
*/
@Tag(name = "参数维护-焦炉单孔产量")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionSingleHoleYieldController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionSingleHoleYieldService productionSingleHoleYieldService;

    /**
     * 查询焦炉单孔产量
     * @param pageProductionSingleHoleYieldDTO
     * @return
     */
    @Operation(summary = "查询焦炉单孔产量",
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
                                    @ExampleObject(name = "yield", description = "产量"),
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

    @Log(title = "查询焦炉单孔产量",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listProductionSingleHoleYield", method = RequestMethod.GET)
    public AjaxResult listProductionSingleHoleYield(@Validated @ParamModel PageProductionSingleHoleYieldDTO pageProductionSingleHoleYieldDTO) {
        log.info("params => " + pageProductionSingleHoleYieldDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionSingleHoleYieldDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionSingleHoleYieldDTO);
        return AjaxResult.success(productionSingleHoleYieldService.getListPage(pageProductionSingleHoleYieldDTO));
    }

    /**
     * 新增焦炉单孔产量
     * @param addProductionSingleHoleYieldDTO
     */
    @Operation(summary = "新增焦炉单孔产量",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "yield", description = "产量"),
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
    @Log(title = "新增焦炉单孔产量",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/addProductionSingleHoleYield", method = RequestMethod.POST, produces = "application/json")
    public void addProductionSingleHoleYield(@Valid @RequestBody AddProductionSingleHoleYieldDTO addProductionSingleHoleYieldDTO) {
        log.info("params => " + addProductionSingleHoleYieldDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, addProductionSingleHoleYieldDTO);
        productionSingleHoleYieldService.addProductionSingleHoleYield(addProductionSingleHoleYieldDTO);
    }

    /**
     * 修改焦炉单孔产量
     * @param updateProductionSingleHoleYieldDTO
     */
    @Operation(summary = "修改焦炉单孔产量",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键"),
                            @ExampleObject(name = "yield", description = "产量"),
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
    @Log(title = "修改焦炉单孔产量",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/editProductionSingleHoleYield", method = RequestMethod.POST, produces = "application/json")
    public void editProductionSingleHoleYield(@Valid @RequestBody UpdateProductionSingleHoleYieldDTO updateProductionSingleHoleYieldDTO) {
        log.info("params => " + updateProductionSingleHoleYieldDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateProductionSingleHoleYieldDTO);
        productionSingleHoleYieldService.editProductionSingleHoleYield(updateProductionSingleHoleYieldDTO);
    }

    /**
     * 删除焦炉单孔产量
     * @param deleteProductionSingleHoleYieldDTO
     * @return
     */
    @Operation(summary = "删除焦炉单孔产量",
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
    @Log(title = "删除焦炉单孔产量",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/delProductionSingleHoleYield", method = RequestMethod.POST, produces = "application/json")
    public void delProductionSingleHoleYield(@Valid @RequestBody DeleteProductionSingleHoleYieldDTO deleteProductionSingleHoleYieldDTO) {
        log.info("params => " + deleteProductionSingleHoleYieldDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionSingleHoleYieldDTO);
        productionSingleHoleYieldService.delProductionSingleHoleYield(deleteProductionSingleHoleYieldDTO);
    }
}
