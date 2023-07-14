package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionsteamperformance.ListHomeProductionSteamPerformanceYieldDTO;
import com.jlkj.product.oi.service.ProductionSteamPerformanceService;
import com.jlkj.product.oi.vo.productionsteamperformance.ListHomeProductionSteamPerformanceYieldVO;
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
*@description: 控制器-蒸汽实绩
*@Author: 265823
*@date: 2023/7/11 13:48
*/
@Tag(name = "蒸汽实绩")
@RestController
@RequestMapping("/productionsteamperformance")
@Slf4j
public class ProductionSteamPerformanceController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionSteamPerformanceService productionSteamPerformanceService;

    /**
     * 首页-蒸汽产量
     * @param listHomeProductionSteamPerformanceYieldDTO
     * @return
     */
    @Operation(summary = "首页-蒸汽产量",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ListHomeProductionSteamPerformanceYieldVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "首页-蒸汽产量",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getHomeProductionSteamPerformanceYieldList", method = RequestMethod.GET)
    public AjaxResult getHomeProductionSteamPerformanceYieldListData(@Validated @ParamModel ListHomeProductionSteamPerformanceYieldDTO listHomeProductionSteamPerformanceYieldDTO) {
        log.info("params => " + listHomeProductionSteamPerformanceYieldDTO);
        String errorMsg = ValidUtil.checkValid(listHomeProductionSteamPerformanceYieldDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listHomeProductionSteamPerformanceYieldDTO);
        return AjaxResult.success(productionSteamPerformanceService.getHomeProductionSteamPerformanceYieldListData(listHomeProductionSteamPerformanceYieldDTO));
    }
}

