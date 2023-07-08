package com.jlkj.product.oi.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import com.jlkj.product.oi.domain.ProductionPlanPowerGenerationDate;
import com.jlkj.product.oi.domain.ProductionPlanPowerGenerationMonth;
import com.jlkj.product.oi.domain.ProductionPlanPowerGenerationYear;
import com.jlkj.product.oi.dto.changelog.InsertChangeLogDTO;
import com.jlkj.product.oi.dto.productionplantarget.AddProductionPowerPlanYearDTO;
import com.jlkj.product.oi.dto.productionplantarget.DeleteProductionPlanYearDTO;
import com.jlkj.product.oi.service.ChangeLogService;
import com.jlkj.product.oi.service.ProductionPlanPowerGenerationYearService;
import com.jlkj.product.oi.service.impl.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author zyf
 * @Description
 * @create 2022-04-29 14:02
 */
@Tag(name = "年发电计划")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionPlanPowerGenerationYearController {


    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionPlanPowerGenerationYearService planPowerGenerationYearService;

    @Operation(summary = "年发电计划查询",
            parameters = {
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "年发电计划查询",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listYearPowerGenerationTargetPlans", method = RequestMethod.GET)
    public Object get() {
        log.info("params => listYearPowerGenerationTargetPlans");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        List<Map<String, String>> list = planPowerGenerationYearService.getColToRowList("");
        return AjaxResult.success(list);
    }

    @Operation(summary = "新增年发电计划",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "planYear", description = "计划年度"),
                            @ExampleObject(name = "targetItems", description = "指标项",
                                    externalValue = "[{'id': 'item_id', " +
                                            "'value': 'item_value', " +
                                            "'calcUnitId': 'item_calc_unit_id_value', " +
                                            "'measures_and_goals': 'measures_and_goals_value', " +
                                            "'responsible_person': 'responsible_person_value'}]"
                            ),
                            @ExampleObject(name = "userId", description = "创建人id"),
                            @ExampleObject(name = "userName", description = "创建人")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "1", description = "当年产量计划已存在/当年指标计划不存在"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "新增年发电计划",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/saveYearPowerGenerationTargetPlan", method = RequestMethod.POST, produces = "application/json")
    public Object save(@Valid @RequestBody AddProductionPowerPlanYearDTO productionPlanYearDTO) {
        log.info("params => " + productionPlanYearDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, productionPlanYearDTO);
        return planPowerGenerationYearService.saveCustom(productionPlanYearDTO);
    }

    @Operation(summary = "删除年计划",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "planYear", description = "年份")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "1", description = "错误信息"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "删除年计划",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/deleteYearPowerGenerationTargetPlan", method = RequestMethod.POST, produces = "application/json")
    public Object delete(@Valid @RequestBody DeleteProductionPlanYearDTO deleteProductionPlanYearDTO) {
        log.info("params => " + deleteProductionPlanYearDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionPlanYearDTO);
        return planPowerGenerationYearService.delete(deleteProductionPlanYearDTO);
    }
}
