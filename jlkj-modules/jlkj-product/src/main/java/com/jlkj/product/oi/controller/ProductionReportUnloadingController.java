package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionreportunloading.AddProductionReportUnloadingDTO;
import com.jlkj.product.oi.dto.productionreportunloading.DeleteProductionReportUnloadingDTO;
import com.jlkj.product.oi.dto.productionreportunloading.PageProductionReportUnloadingDTO;
import com.jlkj.product.oi.dto.productionreportunloading.UpdateProductionReportUnloadingDTO;
import com.jlkj.product.oi.service.ProductionReportUnloadingService;
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

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;



/**
*@description: 生产管理-报表管理-煤场卸车情况
*@Author: 265823
*@date: 2023/7/12 9:24
*/
@Tag(name = "生产管理-报表管理-煤场卸车情况")
@RestController
@RequestMapping("/productionReportUnloading")
@Slf4j
public class ProductionReportUnloadingController {
    @Resource
    HttpServletRequest httpServletRequest;
    @Resource
    ProductionReportUnloadingService productionReportUnloadingService;

    /**
     * 查询生产管理-报表管理-煤场卸车情况
     * @param dto
     * @return
     */
    @Operation(summary = "查询生产管理-报表管理-煤场卸车情况",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
                    @Parameter(name = "coal_way", description = "来煤方式"),
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
                                    @ExampleObject(name = "statistics_date", description = "统计日期"),
                                    @ExampleObject(name = "coal_way", description = "来煤方式"),
                                    @ExampleObject(name = "coal_num", description = "来煤数量"),
                                    @ExampleObject(name = "stock", description = "库存(KG)"),
                                    @ExampleObject(name = "unloading_coal_screw", description = "卸煤-螺旋卸"),
                                    @ExampleObject(name = "unloading_coal_part", description = "卸煤-分卸"),
                                    @ExampleObject(name = "unloaded_screw", description = "待卸-螺旋卸"),
                                    @ExampleObject(name = "unloaded_part", description = "待卸-分卸"),
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
    @Log(title = "查询生产管理-报表管理-煤场卸车情况",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public AjaxResult list(@Validated @ParamModel PageProductionReportUnloadingDTO dto) {
        log.info("params => " + dto);
        String errorMsg = ValidUtil.checkValid(dto);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(productionReportUnloadingService.getListPage(dto));
    }

    /**
     * 新增
     * @param dto
     */
    @Operation(summary = "新增",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "statistics_date", description = "统计日期"),
                            @ExampleObject(name = "coal_way", description = "来煤方式"),
                            @ExampleObject(name = "coal_num", description = "来煤数量"),
                            @ExampleObject(name = "stock", description = "库存(KG)"),
                            @ExampleObject(name = "unloading_coal_screw", description = "卸煤-螺旋卸"),
                            @ExampleObject(name = "unloading_coal_part", description = "卸煤-分卸"),
                            @ExampleObject(name = "unloaded_screw", description = "待卸-螺旋卸"),
                            @ExampleObject(name = "unloaded_part", description = "待卸-分卸"),
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
    @Log(title = "新增-煤场卸车情况",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
    public void save(@Valid @RequestBody AddProductionReportUnloadingDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        productionReportUnloadingService.saveCustom(dto);
    }

    /**
     * 修改
     * @param dto
     * @return
     */
    @Operation(summary = "修改",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键"),
                            @ExampleObject(name = "statistics_date", description = "统计日期"),
                            @ExampleObject(name = "coal_way", description = "来煤方式"),
                            @ExampleObject(name = "coal_num", description = "来煤数量"),
                            @ExampleObject(name = "stock", description = "库存(KG)"),
                            @ExampleObject(name = "unloading_coal_screw", description = "卸煤-螺旋卸"),
                            @ExampleObject(name = "unloading_coal_part", description = "卸煤-分卸"),
                            @ExampleObject(name = "unloaded_screw", description = "待卸-螺旋卸"),
                            @ExampleObject(name = "unloaded_part", description = "待卸-分卸"),
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
    @Log(title = "修改-煤场卸车情况",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public void update(@Valid @RequestBody UpdateProductionReportUnloadingDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        productionReportUnloadingService.updateCustom(dto);
    }

    /**
     * 删除生产管理-报表管理-煤场卸车情况
     * @param deleteProductionReportUnloadingDTO
     */
    @Operation(summary = "删除生产管理-报表管理-煤场卸车情况",
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
    @Log(title = "删除生产管理-报表管理-煤场卸车情况",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
    public void delete(@Valid @RequestBody DeleteProductionReportUnloadingDTO deleteProductionReportUnloadingDTO) {
        log.info("params => " + deleteProductionReportUnloadingDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionReportUnloadingDTO);
        productionReportUnloadingService.delete(deleteProductionReportUnloadingDTO);
    }

}

