package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionconveyingcoalactual.PageProductionConveyingCoalActualDTO;
import com.jlkj.product.oi.service.ProductionConveyingCoalActualService;
import com.jlkj.product.oi.vo.productionconveyingcoalactual.PageProductionConveyingCoalActualVO;
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
*@description: 控制器-生产实绩-上煤实绩
*@Author: 265823
*@date: 2023/7/10 14:32
*/
@Tag(name = "生产实绩-上煤实绩")
@RestController
@RequestMapping("/productionconveyingcoalactual")
@Slf4j
public class ProductionConveyingCoalActualController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionConveyingCoalActualService productionConveyingCoalActualService;

    /**
     * 生产实绩-上煤实绩-查询-分页
     * @param pageProductionConveyingCoalActualDTO
     * @return
     */
    @Operation(summary = "生产实绩-上煤实绩-查询-分页",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = PageProductionConveyingCoalActualVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产实绩-上煤实绩-查询-分页",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionConveyingCoalActualPage", method = RequestMethod.GET)
    public AjaxResult getProductionConveyingCoalActualPageData(@Validated @ParamModel PageProductionConveyingCoalActualDTO pageProductionConveyingCoalActualDTO) {
        log.info("params => " + pageProductionConveyingCoalActualDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionConveyingCoalActualDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionConveyingCoalActualDTO);
        return AjaxResult.success(productionConveyingCoalActualService.getProductionConveyingCoalActualPageData(pageProductionConveyingCoalActualDTO));
    }
}
