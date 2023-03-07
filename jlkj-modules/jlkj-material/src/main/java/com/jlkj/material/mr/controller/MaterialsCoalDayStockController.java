package com.jlkj.material.mr.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.core.utils.ValidUtil;
import com.jlkj.material.mr.dto.materialscoaldaystock.ListHomeCoalStockDTO;
import com.jlkj.material.mr.service.MaterialsCoalDayStockService;
import com.jlkj.material.mr.vo.materialscoaldaystock.ListHomeCoalStockVO;
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

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * 控制器-物料煤场每日库存表
 * @author sudeyou
 * @since 2022-09-02 10:55:24
 */
@Tag(name = "物料煤场每日库存表")
@RestController
@RequestMapping("/materialscoaldaystock")
@Slf4j
public class MaterialsCoalDayStockController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private MaterialsCoalDayStockService materialsCoalDayStockService;

    @Operation(summary = "首页-原煤库存",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ListHomeCoalStockVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查首页-原煤库存", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getHomeCoalStockList", method = RequestMethod.GET)
    public Object getHomeCoalStockListData(@Validated @ParamModel ListHomeCoalStockDTO listHomeCoalStockDTO) {
        log.info("params => " + listHomeCoalStockDTO);
        String errorMsg = ValidUtil.checkValid(listHomeCoalStockDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listHomeCoalStockDTO);
        return materialsCoalDayStockService.getHomeCoalStockListData(listHomeCoalStockDTO);
    }

    @Operation(summary = "原煤库存-每日初始化",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "原煤库存-每日初始化", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/initMaterialsCoalDayStock", method = RequestMethod.POST, produces = "application/json")
    public Object initMaterialsCoalDayStockData() {
        return materialsCoalDayStockService.initMaterialsCoalDayStockData();
    }
}

