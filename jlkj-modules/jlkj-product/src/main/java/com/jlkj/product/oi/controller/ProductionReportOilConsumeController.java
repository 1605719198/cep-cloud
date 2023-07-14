package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionreportoilconsume.AddProductionReportOilConsumeDTO;
import com.jlkj.product.oi.dto.productionreportoilconsume.DeleteProductionReportOilConsumeDTO;
import com.jlkj.product.oi.dto.productionreportoilconsume.PageProductionReportOilConsumeDTO;
import com.jlkj.product.oi.dto.productionreportoilconsume.UpdateProductionReportOilConsumeDTO;
import com.jlkj.product.oi.service.ProductionReportOilConsumeService;
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
*@description: 生产管理-报表管理-油品消耗
*@Author: 265823
*@date: 2023/7/12 8:39
*/
@Tag(name = "生产管理-报表管理-油品消耗")
@RestController
@RequestMapping("/productionReportOilConsume")
@Slf4j
public class ProductionReportOilConsumeController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    ProductionReportOilConsumeService productionReportOilConsumeService;

    /**
     * 查询生产管理-报表管理-油品消耗
     * @param pageProductionReportOilConsumeDTO
     * @return
     */
    @Operation(summary = "查询生产管理-报表管理-油品消耗",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
                    @Parameter(name = "device_name", description = "设备名称"),
                    @Parameter(name = "oil_type", description = "油品类型"),
                    @Parameter(name = "current", description = "页码-从1开始"),
                    @Parameter(name = "size", description = "每页行数"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则字段")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "id"),
                                    @ExampleObject(name = "consume_date", description = "消耗日期"),
                                    @ExampleObject(name = "device_name", description = "设备名称"),
                                    @ExampleObject(name = "oil_type", description = "油品类型"),
                                    @ExampleObject(name = "daily_consumption", description = "日耗用量(L)"),
                                    @ExampleObject(name = "create_user_id", description = "创建人"),
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
    @Log(title = "查询-分页-生产管理-统计分析-指标跟踪(月)",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public AjaxResult list(@Validated @ParamModel PageProductionReportOilConsumeDTO pageProductionReportOilConsumeDTO) {
        log.info("params => " + pageProductionReportOilConsumeDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionReportOilConsumeDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionReportOilConsumeDTO);
        return AjaxResult.success(productionReportOilConsumeService.getListPage(pageProductionReportOilConsumeDTO));
    }

    /**
     * 新增
     * @param addProductionReportOilConsumeDTO
     */
    @Operation(summary = "新增",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "consume_date", description = "消耗日期"),
                            @ExampleObject(name = "device_name", description = "设备名称"),
                            @ExampleObject(name = "oil_type", description = "油品类型"),
                            @ExampleObject(name = "daily_consumption", description = "日耗用量(L)"),
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
    @Log(title = "新增",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
    public void save(@Valid @RequestBody AddProductionReportOilConsumeDTO addProductionReportOilConsumeDTO) {
        log.info("params => " + addProductionReportOilConsumeDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, addProductionReportOilConsumeDTO);
        productionReportOilConsumeService.saveCustom(addProductionReportOilConsumeDTO);
    }

    /**
     * 修改
     * @param updateProductionReportOilConsumeDTO
     */
    @Operation(summary = "修改",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键"),
                            @ExampleObject(name = "consume_date", description = "消耗日期"),
                            @ExampleObject(name = "device_name", description = "设备名称"),
                            @ExampleObject(name = "oil_type", description = "油品类型"),
                            @ExampleObject(name = "daily_consumption", description = "日耗用量(L)"),
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
    @Log(title = "修改",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public void update(@Valid @RequestBody UpdateProductionReportOilConsumeDTO updateProductionReportOilConsumeDTO) {
        log.info("params => " + updateProductionReportOilConsumeDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateProductionReportOilConsumeDTO);
        productionReportOilConsumeService.updateCustom(updateProductionReportOilConsumeDTO);
    }

    /**
     * 删除生产管理-报表管理-油品消耗
     * @param deleteProductionReportOilConsumeDTO
     */
    @Operation(summary = "删除生产管理-报表管理-油品消耗",
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
    @Log(title = "删除",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
    public void delete(@Valid @RequestBody DeleteProductionReportOilConsumeDTO deleteProductionReportOilConsumeDTO) {
        log.info("params => " + deleteProductionReportOilConsumeDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionReportOilConsumeDTO);
        productionReportOilConsumeService.delete(deleteProductionReportOilConsumeDTO);
    }

}

