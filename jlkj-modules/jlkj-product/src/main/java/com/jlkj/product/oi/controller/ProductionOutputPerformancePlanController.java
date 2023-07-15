package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionoutputperformanceplan.PageProductionOutputPerformancePlanDTO;
import com.jlkj.product.oi.service.ProductionOutputPerformancePlanService;
import com.jlkj.product.oi.vo.productionoutputperformanceplan.PageProductionOutputPerformancePlanVO;
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
*@description: 控制器-生产管理-炼焦实绩-推焦计划-数据同步
*@Author: 265823
*@date: 2023/7/12 8:21
*/
@Tag(name = "生产管理-炼焦实绩-推焦计划-数据同步")
@RestController
@RequestMapping("/productionoutputperformanceplan")
@Slf4j
public class ProductionOutputPerformancePlanController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionOutputPerformancePlanService productionOutputPerformancePlanService;

    /**
     * 生产管理-炼焦实绩-推焦计划-数据同步-查询-分页
     * @param pageProductionOutputPerformancePlanDTO
     * @return
     */
    @Operation(summary = "生产管理-炼焦实绩-推焦计划-数据同步-查询-分页",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = PageProductionOutputPerformancePlanVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-炼焦实绩-推焦计划-数据同步-查询-分页",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionOutputPerformancePlanPage", method = RequestMethod.GET)
    public AjaxResult getProductionOutputPerformancePlanPageData(@Validated @ParamModel PageProductionOutputPerformancePlanDTO pageProductionOutputPerformancePlanDTO) {
        log.info("params => " + pageProductionOutputPerformancePlanDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionOutputPerformancePlanDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionOutputPerformancePlanDTO);
        return AjaxResult.success(productionOutputPerformancePlanService.getProductionOutputPerformancePlanPageData(pageProductionOutputPerformancePlanDTO));
    }
}
