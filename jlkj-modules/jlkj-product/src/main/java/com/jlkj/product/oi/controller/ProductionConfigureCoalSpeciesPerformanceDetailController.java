package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.PageResult;
import com.jlkj.common.core.web.resp.Result;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformancedetail.PageProductionConfigureCoalSpeciesPerformanceDetailDTO;
import com.jlkj.product.oi.dto.productionconfigurecoalspeciesperformancedetail.SaveOrUpdateDTO;
import com.jlkj.product.oi.service.impl.ProductionConfigureCoalSpeciesPerformanceDetailServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * 控制器-配煤实绩明细
 *
 * @author sudeyou
 */
@Tag(name = "配煤实绩明细")
@RestController
@RequestMapping("/performance")
@Slf4j
public class ProductionConfigureCoalSpeciesPerformanceDetailController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionConfigureCoalSpeciesPerformanceDetailServiceImpl productionConfigureCoalSpeciesPerformanceDetailService;

    @Operation(summary = "查询-分页-配煤实绩明细",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "main_id", description = "主记录编号"),
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
                                    @ExampleObject(name = "main_id", description = "主记录编号"),
                                    @ExampleObject(name = "shift_name", description = "班次"),
                                    @ExampleObject(name = "class_name", description = "班别"),
                                    @ExampleObject(name = "plan_id", description = "计划编号"),
                                    @ExampleObject(name = "bunker_number", description = "上煤仓号"),
                                    @ExampleObject(name = "coal_tower_number", description = "储煤塔号"),
                                    @ExampleObject(name = "before_material_level", description = "配煤前料位"),
                                    @ExampleObject(name = "after_material_level", description = "配煤后料位"),
                                    @ExampleObject(name = "before_stock", description = "配煤前存量"),
                                    @ExampleObject(name = "after_stock", description = "配煤后存量"),
                                    @ExampleObject(name = "coal_delivery_date", description = "配煤日期"),
                                    @ExampleObject(name = "start_time", description = "开始时间"),
                                    @ExampleObject(name = "end_time", description = "结束时间"),
                                    @ExampleObject(name = "duration", description = "配煤时长"),
                                    @ExampleObject(name = "material_weight", description = "配煤重量"),
                                    @ExampleObject(name = "material_number", description = "物料编号"),
                                    @ExampleObject(name = "material_name", description = "物料名称"),
                                    @ExampleObject(name = "material_code", description = "物料代码"),
                                    @ExampleObject(name = "create_time", description = "创建时间"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "查询-分页-配煤实绩明细",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listConfigureCoalSpeciesPerformanceDetail", method = RequestMethod.GET)
    public Object getPageData(@Validated @ParamModel PageProductionConfigureCoalSpeciesPerformanceDetailDTO pageProductionConfigureCoalSpeciesPerformanceDetailDTO) {
        log.info("params => " + pageProductionConfigureCoalSpeciesPerformanceDetailDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionConfigureCoalSpeciesPerformanceDetailDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionConfigureCoalSpeciesPerformanceDetailDTO);
        return AjaxResult.success(productionConfigureCoalSpeciesPerformanceDetailService.getPageData(pageProductionConfigureCoalSpeciesPerformanceDetailDTO));
    }

    @Operation(summary = "配煤实绩明细-新增",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "main_id", description = "主记录编号"),
                    @Parameter(name = "shift_name", description = "班次"),
                    @Parameter(name = "class_name", description = "班别"),
                    @Parameter(name = "plan_id", description = "计划编号"),
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
                    @Parameter(name = "duration", description = "配煤时长"),
                    @Parameter(name = "material_weight", description = "配煤重量"),
                    @Parameter(name = "material_number", description = "物料编号"),
                    @Parameter(name = "material_name", description = "物料名称"),
                    @Parameter(name = "material_code", description = "物料代码"),
                    @Parameter(name = "user_id", description = "用户id"),
                    @Parameter(name = "user_name", description = "用户名称")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "配煤实绩明细-新增",businessType = BusinessType.INSERT)
    @PostMapping(value = "/save")
    public Object save(@Validated @RequestBody SaveOrUpdateDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return productionConfigureCoalSpeciesPerformanceDetailService.save(dto);
    }


    @Operation(summary = "配煤实绩明细-修改",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "id", description = "主键标识;UUID"),
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
                    @Parameter(name = "duration", description = "配煤时长"),
                    @Parameter(name = "material_weight", description = "配煤重量"),
                    @Parameter(name = "material_number", description = "物料编号"),
                    @Parameter(name = "material_name", description = "物料名称"),
                    @Parameter(name = "material_code", description = "物料代码"),
                    @Parameter(name = "user_id", description = "用户id"),
                    @Parameter(name = "user_name", description = "用户名称")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "配煤实绩明细-修改",businessType = BusinessType.UPDATE)
    @PutMapping(value = "/update")
    public Object update(@Validated @RequestBody SaveOrUpdateDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return productionConfigureCoalSpeciesPerformanceDetailService.update(dto);
    }


    @Operation(summary = "配煤实绩明细-删除",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "id", description = "表id"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "配煤实绩明细-删除",businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/del")
    public Object del(@Validated @RequestBody SaveOrUpdateDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return productionConfigureCoalSpeciesPerformanceDetailService.del(dto);
    }
}

