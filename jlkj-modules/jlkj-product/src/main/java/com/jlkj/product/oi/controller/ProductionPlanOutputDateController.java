package com.jlkj.product.oi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import com.jlkj.product.oi.dto.productionplanoutputdate.ListProductionPlanOutputDateTargetItemDTO;
import com.jlkj.product.oi.dto.productionplantarget.GetProductionPlanDayDTO;
import com.jlkj.product.oi.service.ProductionParameterTargetItemService;
import com.jlkj.product.oi.service.ProductionPlanOutputDateService;
import com.jlkj.product.oi.vo.productionplanoutputdate.ListProductionPlanOutputDateTargetItemVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
*@description: 日产量计划
*@Author: 265823
*@date: 2023/7/10 8:18
*/
@Tag(name = "日产量计划")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionPlanOutputDateController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionPlanOutputDateService planOutputDateService;

    /**
     * 查询日生产产量计划
     * @param dto
     * @return
     */
    @Operation(summary = "查询日生产产量计划",
            parameters = {
                    @Parameter(name = "planYear", description = "计划年度"),
                    @Parameter(name = "planMonth", description = "计划月份")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "查询日生产产量计划",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listDateProductionOutputPlans", method = RequestMethod.GET)
    public AjaxResult get(@Valid GetProductionPlanDayDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(planOutputDateService.get(dto));
    }

    /**
     * 生产管理-指标跟踪-图表-指标项(日计划)
     * @param listProductionPlanOutputDateTargetItemDTO
     * @return
     */
    @Operation(summary = "生产管理-指标跟踪-图表-指标项(日计划)",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ListProductionPlanOutputDateTargetItemVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-指标跟踪-图表-指标项(日计划)",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionPlanOutputDateTargetItemChart", method = RequestMethod.GET)
    public AjaxResult getProductionPlanOutputDateTargetItemChartData(@Validated @ParamModel ListProductionPlanOutputDateTargetItemDTO listProductionPlanOutputDateTargetItemDTO) {
        log.info("params => " + listProductionPlanOutputDateTargetItemDTO);
        String errorMsg = ValidUtil.checkValid(listProductionPlanOutputDateTargetItemDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listProductionPlanOutputDateTargetItemDTO);
        return AjaxResult.success(planOutputDateService.getProductionPlanOutputDateTargetItemChartData(listProductionPlanOutputDateTargetItemDTO));
    }
}
