package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionyieldanalysisyear.ListProductionYieldAnalysisYearDTO;
import com.jlkj.product.oi.service.ProductionYieldAnalysisYearService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
*@description: 控制器-产量分析日表
*@Author: 265823
*@date: 2023/7/11 14:21
*/
@Tag(name = "产量分析年表")
@RestController
@RequestMapping("/yield")
@Slf4j
public class ProductionYieldAnalysisYearController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionYieldAnalysisYearService productionYieldAnalysisYearService;

    /**
     * 生产管理-产量跟踪-查询-年
     * @param listProductionYieldAnalysisYearDTO
     * @return
     */
    @Operation(summary = "生产管理-产量跟踪-查询-年",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "year", description = "年度"),
                                    @ExampleObject(name = "产量数据", description = "产量数据【动态列】"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-产量跟踪-查询-年",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionYieldAnalysisYear", method = RequestMethod.GET)
    public AjaxResult getProductionYieldAnalysisYear(@Validated @ParamModel ListProductionYieldAnalysisYearDTO listProductionYieldAnalysisYearDTO) {
        log.info("params => " + listProductionYieldAnalysisYearDTO);
        String errorMsg = ValidUtil.checkValid(listProductionYieldAnalysisYearDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listProductionYieldAnalysisYearDTO);
        return AjaxResult.success(productionYieldAnalysisYearService.getProductionYieldAnalysisYearList(listProductionYieldAnalysisYearDTO));
    }

}

