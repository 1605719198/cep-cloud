package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionrealoutputmonth.ListProductionRealOutputMonthRangeDTO;
import com.jlkj.product.oi.dto.productionrealoutputmonth.ListProductionRealOutputMonthTargetItemDTO;
import com.jlkj.product.oi.dto.productionrealoutputmonth.PageProductionRealOutputMonthDTO;
import com.jlkj.product.oi.service.ProductionRealOutputMonthService;
import com.jlkj.product.oi.vo.productionrealoutputmonth.ListProductionRealOutputMonthRangeVO;
import com.jlkj.product.oi.vo.productionrealoutputmonth.ListProductionRealOutputMonthTargetItemVO;
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
 * 控制器-生产管理-月产量
 * @author sudeyou
 * @since 2022-07-13 14:18:51
 */
@Tag(name = "生产管理-月产量")
@RestController
@RequestMapping("/real")
@Slf4j
public class ProductionRealOutputMonthController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionRealOutputMonthService productionRealOutputMonthService;

    @Operation(summary = "查询-分页-生产管理-统计分析-指标跟踪(月)",
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
    @Log(title = "查询-分页-生产管理-统计分析-指标跟踪(月)",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getPageProductionRealOutputMonth", method = RequestMethod.GET)
    public Object getPageData(@Validated @ParamModel PageProductionRealOutputMonthDTO pageProductionRealOutputMonthDTO) {
        log.info("params => " + pageProductionRealOutputMonthDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionRealOutputMonthDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionRealOutputMonthDTO);
        return AjaxResult.success(productionRealOutputMonthService.getPageData(pageProductionRealOutputMonthDTO));
    }

    @Operation(summary = "生产管理-指标跟踪-图表-指标项(月实际)",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ListProductionRealOutputMonthTargetItemVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-指标跟踪-图表-指标项(月实际)",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionRealOutputMonthTargetItemChart", method = RequestMethod.GET)
    public Object getProductionRealOutpumonthTargetItemChartData(@Validated @ParamModel ListProductionRealOutputMonthTargetItemDTO listProductionRealOutputMonthTargetItemDTO) {
        log.info("params => " + listProductionRealOutputMonthTargetItemDTO);
        String errorMsg = ValidUtil.checkValid(listProductionRealOutputMonthTargetItemDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listProductionRealOutputMonthTargetItemDTO);
        return productionRealOutputMonthService.getProductionRealOutputMonthTargetItemChartData(listProductionRealOutputMonthTargetItemDTO);
    }

    @Operation(summary = "生产管理-指标跟踪-图表-日期(同环比)",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ListProductionRealOutputMonthRangeVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-指标跟踪-图表-日期(同环比)",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionRealOutputMonthRangeChart", method = RequestMethod.GET)
    public Object getProductionRealOutpumonthRangeChartData(@Validated @ParamModel ListProductionRealOutputMonthRangeDTO listProductionRealOutputMonthRangeDTO) {
        log.info("params => " + listProductionRealOutputMonthRangeDTO);
        String errorMsg = ValidUtil.checkValid(listProductionRealOutputMonthRangeDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listProductionRealOutputMonthRangeDTO);
        return productionRealOutputMonthService.getProductionRealOutputMonthRangeChartData(listProductionRealOutputMonthRangeDTO);
    }

    @Operation(summary = "生产管理-统计分析-指标分析-月产量指标跟踪-更新统计数据",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-统计分析-指标分析-月产量指标跟踪-更新统计数据",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/updateProductionRealOutputMonthStatistics", method = RequestMethod.POST, produces = "application/json")
    public Object updateProductionRealOutputMonthStatisticsData() {
        return productionRealOutputMonthService.updateProductionRealOutputMonthStatisticsData();
    }
}

