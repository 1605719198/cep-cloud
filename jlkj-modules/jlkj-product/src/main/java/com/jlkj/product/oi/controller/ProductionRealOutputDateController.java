package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionrealoutputdate.ListProductionRealOutputDateTargetItemDTO;
import com.jlkj.product.oi.dto.productionrealoutputdate.PageProductionRealOutputDateDTO;
import com.jlkj.product.oi.service.ProductionRealOutputDateService;
import com.jlkj.product.oi.vo.productionrealoutputdate.ListProductionRealOutputDateTargetItemVO;
import com.jlkj.product.oi.vo.productionrealoutputmonth.PageProductionRealOutputMonthVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
*@description: 控制器-生产管理-日产量
*@Author: 265823
*@date: 2023/7/11 11:11
*/
@Tag(name = "生产管理-日产量")
@RestController
@RequestMapping("/real")
@Slf4j
public class ProductionRealOutputDateController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionRealOutputDateService productionRealOutputDateService;

    /**
     * 查询-分页-生产管理-统计分析(日)
     * @param pageProductionRealOutputDateDTO
     * @return
     */
    @Operation(summary = "查询-分页-生产管理-统计分析(日)",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = PageProductionRealOutputMonthVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询-分页-生产管理-统计分析(日)",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getPageProductionRealOutputDate", method = RequestMethod.GET)
    public AjaxResult getPageData(@Validated @ParamModel PageProductionRealOutputDateDTO pageProductionRealOutputDateDTO) {
        log.info("params => " + pageProductionRealOutputDateDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionRealOutputDateDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionRealOutputDateDTO);
        return AjaxResult.success(productionRealOutputDateService.getPageData(pageProductionRealOutputDateDTO));
    }

    /**
     * 生产管理-指标跟踪-图表-指标项(日实际)
     * @param listProductionRealOutputDateTargetItemDTO
     * @return
     */
    @Operation(summary = "生产管理-指标跟踪-图表-指标项(日实际)",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ListProductionRealOutputDateTargetItemVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-指标跟踪-图表-指标项(日实际)",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionRealOutputDateTargetItemChart", method = RequestMethod.GET)
    public AjaxResult getProductionRealOutputDateTargetItemChartData(@Validated @ParamModel ListProductionRealOutputDateTargetItemDTO listProductionRealOutputDateTargetItemDTO) {
        log.info("params => " + listProductionRealOutputDateTargetItemDTO);
        String errorMsg = ValidUtil.checkValid(listProductionRealOutputDateTargetItemDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listProductionRealOutputDateTargetItemDTO);
        return AjaxResult.success(productionRealOutputDateService.getProductionRealOutputDateTargetItemChartData(listProductionRealOutputDateTargetItemDTO));
    }

    /**
     * 生产管理-统计分析-指标分析-日产量指标跟踪-更新统计数据
     * @return
     */
    @Operation(summary = "生产管理-统计分析-指标分析-日产量指标跟踪-更新统计数据",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-统计分析-指标分析-日产量指标跟踪-更新统计数据",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/updateProductionRealOutputDateStatistics", method = RequestMethod.POST, produces = "application/json")
    public void updateProductionRealOutputDateStatisticsData() {
        productionRealOutputDateService.updateProductionRealOutputDateStatisticsData();
    }
}

