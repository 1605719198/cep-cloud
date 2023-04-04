package com.jlkj.product.oi.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.Result;
import com.jlkj.common.core.web.resp.ResultCode;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.domain.*;
import com.jlkj.product.oi.dto.changelog.InsertChangeLogDTO;
import com.jlkj.product.oi.dto.productionplantarget.AddProductionPlanYearDTO;
import com.jlkj.product.oi.dto.productionplantarget.DeleteProductionPlanYearDTO;
import com.jlkj.product.oi.service.ChangeLogService;
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

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author zyf
 * @Description
 * @create 2022-04-19 8:41
 */
@Tag(name = "年指标计划")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionPlanTargetYearController {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionPlanTargetYearServiceImpl planTargetYearService;

    @Autowired
    ProductionPlanTargetMonthServiceImpl planTargetMonthService;

    @Autowired
    ProductionPlanTargetDateServiceImpl planTargetDateService;

    @Autowired
    ProductionRealOutputYearServiceImpl realOutputYearService;

    @Autowired
    ProductionRealOutputMonthServiceImpl realOutputMonthService;

    @Autowired
    ProductionRealOutputDateServiceImpl realOutputDateService;

    @Autowired
    ProductionParameterTargetItemServiceImpl productionParameterTargetItemService;

    @Resource
    ChangeLogService changeLogService;

    @Operation(summary = "年指标计划查询",
            parameters = {
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "年指标计划查询",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/listYearProductionTargetPlans", method = RequestMethod.GET)
    public Object get() {
        log.info("params => listYearProductionTargetPlans");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        List<ProductionParameterTargetItem> itemlist =
                productionParameterTargetItemService.list(new QueryWrapper<ProductionParameterTargetItem>().lambda()
                        .eq(ProductionParameterTargetItem::getTargetItemTypeId, 1));
        return planTargetYearService.get(itemlist);
    }

    @Operation(summary = "新增年生产指标计划",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "planYear", description = "计划年度"),
                            @ExampleObject(name = "targetItems", description = "指标项",
                                    externalValue = "[{'id': 'item_id', 'value': 'item_value'}]"
                            ),
                            @ExampleObject(name = "userId", description = "创建人id"),
                            @ExampleObject(name = "userName", description = "创建人")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "1", description = "当年计划已存在"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "新增年生产指标计划",businessType = BusinessType.INSERT)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/saveYearProductionTargetPlan", method = RequestMethod.POST, produces = "application/json")
    public Object save(@Valid @RequestBody AddProductionPlanYearDTO productionPlanTargetYearDTO) {
        log.info("params => " + productionPlanTargetYearDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, productionPlanTargetYearDTO);
        return planTargetYearService.save(productionPlanTargetYearDTO);
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
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/deleteYearProductionTargetPlan", method = RequestMethod.POST, produces = "application/json")
    public Object delete(@Valid @RequestBody DeleteProductionPlanYearDTO deleteProductionPlanYearDTO) {
        log.info("params => " + deleteProductionPlanYearDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionPlanYearDTO);
        List<ProductionPlanTargetYear> yearList = planTargetYearService.list(new QueryWrapper<ProductionPlanTargetYear>().lambda()
                .eq(ProductionPlanTargetYear::getPlanYear, deleteProductionPlanYearDTO.getPlanYear()));
        if (yearList.size() < 1) {
            return AjaxResult.error("当前年份计划不存在");
        }
        if (deleteProductionPlanYearDTO.getPlanYear() <= DateUtil.year(DateUtil.date())) {
            return AjaxResult.error("往年计划不能删除");
        }

        StringBuilder content = new StringBuilder();
        content.append("删除：" + "[计划年度：").append(deleteProductionPlanYearDTO.getPlanYear()).append("],");
        for (ProductionPlanTargetYear year: yearList) {
            ProductionParameterTargetItem productionParameterTargetItem = productionParameterTargetItemService.getById(year.getTargetItemId());
            content.append("[").append(productionParameterTargetItem.getTargetItemName()).append("：").append(year.getTargetItemValue().stripTrailingZeros().toPlainString()).append("],");
        }
        InsertChangeLogDTO insertChangeLogDTO = new InsertChangeLogDTO();
        insertChangeLogDTO.setModuleName("生产管理");
        insertChangeLogDTO.setFunctionName("生产计划->焦化指标");
        insertChangeLogDTO.setContent(content.toString());
        insertChangeLogDTO.setCreateUserId(deleteProductionPlanYearDTO.getDeleteUserId());
        insertChangeLogDTO.setCreateUserName(deleteProductionPlanYearDTO.getDeleteUserName());
        changeLogService.insertChangeLogData(insertChangeLogDTO);

        List<ProductionPlanTargetMonth> monthList = planTargetMonthService.list(new QueryWrapper<ProductionPlanTargetMonth>().lambda()
                .eq(ProductionPlanTargetMonth::getPlanYear, deleteProductionPlanYearDTO.getPlanYear()));
        List<ProductionPlanTargetDate> dateList = planTargetDateService.list(new QueryWrapper<ProductionPlanTargetDate>().lambda()
                .eq(ProductionPlanTargetDate::getPlanYear, deleteProductionPlanYearDTO.getPlanYear()));
        planTargetDateService.removeBatchByIds(dateList, dateList.size());
        planTargetMonthService.removeBatchByIds(monthList, monthList.size());
        planTargetYearService.removeBatchByIds(yearList, yearList.size());
        realOutputYearService.remove(new LambdaQueryWrapper<ProductionRealOutputYear>()
                .eq(ProductionRealOutputYear::getYear, deleteProductionPlanYearDTO.getPlanYear()));
        realOutputMonthService.remove(new LambdaQueryWrapper<ProductionRealOutputMonth>()
                .eq(ProductionRealOutputMonth::getYear, deleteProductionPlanYearDTO.getPlanYear()));
        realOutputDateService.remove(new LambdaQueryWrapper<ProductionRealOutputDate>()
                .eq(ProductionRealOutputDate::getYear, deleteProductionPlanYearDTO.getPlanYear()));
        return AjaxResult.success();
    }
}
