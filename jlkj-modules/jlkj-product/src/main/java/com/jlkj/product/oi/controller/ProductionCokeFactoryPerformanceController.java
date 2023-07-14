package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productioncokefactoryperformance.PageProductionCokeFactoryPerformanceDTO;
import com.jlkj.product.oi.service.ProductionCokeFactoryPerformanceService;
import com.jlkj.product.oi.vo.productioncokefactoryperformance.PageProductionCokeFactoryPerformanceVO;
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
*@description: 控制器-焦炭出厂实绩
*@Author: 265823
*@date: 2023/7/10 11:33
*/
@Tag(name = "焦炭出厂实绩")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionCokeFactoryPerformanceController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionCokeFactoryPerformanceService productionCokeFactoryPerformanceService;

    /**
     * 焦炭出厂实绩-查询-分页
     * @param pageProductionCokeFactoryPerformanceDTO
     * @return
     */
    @Operation(summary = "焦炭出厂实绩-查询-分页",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = PageProductionCokeFactoryPerformanceVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "焦炭出厂实绩-查询-分页",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionCokeFactoryPerformancePage", method = RequestMethod.GET)
    public AjaxResult getProductionCokeFactoryPerformancePageData(@Validated @ParamModel PageProductionCokeFactoryPerformanceDTO pageProductionCokeFactoryPerformanceDTO) {
        log.info("params => " + pageProductionCokeFactoryPerformanceDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionCokeFactoryPerformanceDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionCokeFactoryPerformanceDTO);
        return AjaxResult.success(productionCokeFactoryPerformanceService.getProductionCokeFactoryPerformancePageData(pageProductionCokeFactoryPerformanceDTO));
    }
}

