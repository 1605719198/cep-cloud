package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionoutputperformancetemperature.PageProductionOutputPerformanceTemperatureDTO;
import com.jlkj.product.oi.service.ProductionOutputPerformanceTemperatureService;
import com.jlkj.product.oi.vo.productionoutputperformancetemperature.PageProductionOutputPerformanceTemperatureVO;
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
 * 控制器-出炉实绩测温记录
 * @author sudeyou
 * @since 2022-08-11 14:06:15
 */
@Tag(name = "出炉实绩测温记录")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionOutputPerformanceTemperatureController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionOutputPerformanceTemperatureService productionOutputPerformanceTemperatureService;

    @Operation(summary = "出炉实绩测温记录-查询-分页",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = PageProductionOutputPerformanceTemperatureVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "出炉实绩测温记录-查询-分页",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionOutputPerformanceTemperaturePage", method = RequestMethod.GET)
    public Object getProductionOutputPerformanceTemperaturePageData(@Validated @ParamModel PageProductionOutputPerformanceTemperatureDTO pageProductionOutputPerformanceTemperatureDTO) {
        log.info("params => " + pageProductionOutputPerformanceTemperatureDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionOutputPerformanceTemperatureDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionOutputPerformanceTemperatureDTO);
        return AjaxResult.success(productionOutputPerformanceTemperatureService.getProductionOutputPerformanceTemperaturePageData(pageProductionOutputPerformanceTemperatureDTO));
    }
}

