package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionyieldanalysismonth.ListProductionYieldAnalysisMonthDTO;
import com.jlkj.product.oi.dto.productionyieldanalysismonth.ListProductionYieldAnalysisMonthMaterialDTO;
import com.jlkj.product.oi.service.ProductionYieldAnalysisMonthService;
import com.jlkj.product.oi.vo.productionyieldanalysismonth.ListProductionYieldAnalysisMonthMaterialVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
 * 控制器-产量分析月表
 * @author sudeyou
 * @since 2022-07-15 15:04:00
 */
@Tag(name = "产量分析月表")
@RestController
@RequestMapping("/yield")
@Slf4j
public class ProductionYieldAnalysisMonthController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionYieldAnalysisMonthService productionYieldAnalysisMonthService;

    @Operation(summary = "生产管理-产量跟踪-查询-月",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "year", description = "年度"),
                                    @ExampleObject(name = "month", description = "月份"),
                                    @ExampleObject(name = "产量数据", description = "产量数据【动态列】"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-产量跟踪-查询-月",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionYieldAnalysisMonth", method = RequestMethod.GET)
    public Object getProductionYieldAnalysisMonth(@Validated @ParamModel ListProductionYieldAnalysisMonthDTO listProductionYieldAnalysisMonthDTO) {
        log.info("params => " + listProductionYieldAnalysisMonthDTO);
        String errorMsg = ValidUtil.checkValid(listProductionYieldAnalysisMonthDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listProductionYieldAnalysisMonthDTO);
        return productionYieldAnalysisMonthService.getProductionYieldAnalysisMonthList(listProductionYieldAnalysisMonthDTO);
    }

    @Operation(summary = "生产管理-产量跟踪-图表-物料名称(月)",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ListProductionYieldAnalysisMonthMaterialVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-产量跟踪-图表-物料名称(月)",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionYieldAnalysisMonthChart", method = RequestMethod.GET)
    public Object getProductionYieldAnalysisChartMonth(@Validated @ParamModel ListProductionYieldAnalysisMonthMaterialDTO listProductionYieldAnalysisMonthMaterialDTO) {
        log.info("params => " + listProductionYieldAnalysisMonthMaterialDTO);
        String errorMsg = ValidUtil.checkValid(listProductionYieldAnalysisMonthMaterialDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listProductionYieldAnalysisMonthMaterialDTO);
        return productionYieldAnalysisMonthService.getProductionYieldAnalysisChartMonth(listProductionYieldAnalysisMonthMaterialDTO);
    }
}

