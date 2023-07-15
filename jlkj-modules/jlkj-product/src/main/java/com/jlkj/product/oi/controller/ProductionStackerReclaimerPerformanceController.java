package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionstackerreclaimerperformance.DeleteProductionStackerReclaimerPerformanceDTO;
import com.jlkj.product.oi.dto.productionstackerreclaimerperformance.InfoSchedulingDTO;
import com.jlkj.product.oi.dto.productionstackerreclaimerperformance.InsertProductionStackerReclaimerPerformanceDTO;
import com.jlkj.product.oi.dto.productionstackerreclaimerperformance.UpdateProductionStackerReclaimerPerformanceDTO;
import com.jlkj.product.oi.service.ProductionStackerReclaimerPerformanceService;
import com.jlkj.product.oi.vo.productionstackerreclaimerperformance.InfoSchedulingVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
*@description: 控制器-物料管理-堆取料机实绩
*@Author: 265823
*@date: 2023/7/11 11:36
*/
@Tag(name = "物料管理-堆取料机实绩")
@RestController
@RequestMapping("/productionstackerreclaimerperformance")
@Slf4j
public class ProductionStackerReclaimerPerformanceController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionStackerReclaimerPerformanceService productionStackerReclaimerPerformanceService;

    /**
     * 堆取料机实绩-新增
     * @param insertProductionStackerReclaimerPerformanceDTO
     */
    @Operation(summary = "堆取料机实绩-新增",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "堆取料机实绩-新增",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/insertProductionStackerReclaimerPerformance", method = RequestMethod.POST, produces = "application/json")
    public void insertProductionStackerReclaimerPerformanceData(@Valid @RequestBody InsertProductionStackerReclaimerPerformanceDTO insertProductionStackerReclaimerPerformanceDTO) {
        log.info("params => " + insertProductionStackerReclaimerPerformanceDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, insertProductionStackerReclaimerPerformanceDTO);
        productionStackerReclaimerPerformanceService.insertProductionStackerReclaimerPerformanceData(insertProductionStackerReclaimerPerformanceDTO);
    }

    /**
     * 堆取料机实绩-修改
     * @param updateProductionStackerReclaimerPerformanceDTO
     */
    @Operation(summary = "堆取料机实绩-修改",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "堆取料机实绩-修改",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/updateProductionStackerReclaimerPerformance", method = RequestMethod.PUT, produces = "application/json")
    public void updateProductionStackerReclaimerPerformanceData(@Valid @RequestBody UpdateProductionStackerReclaimerPerformanceDTO updateProductionStackerReclaimerPerformanceDTO) {
        log.info("params => " + updateProductionStackerReclaimerPerformanceDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateProductionStackerReclaimerPerformanceDTO);
        productionStackerReclaimerPerformanceService.updateProductionStackerReclaimerPerformanceData(updateProductionStackerReclaimerPerformanceDTO);
    }

    /**
     * 堆取料机实绩-删除
     * @param deleteProductionStackerReclaimerPerformanceDTO
     */
    @Operation(summary = "堆取料机实绩-删除",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "堆取料机实绩-删除",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/deleteProductionStackerReclaimerPerformance", method = RequestMethod.DELETE, produces = "application/json")
    public void deleteProductionStackerReclaimerPerformanceData(@Valid @RequestBody DeleteProductionStackerReclaimerPerformanceDTO deleteProductionStackerReclaimerPerformanceDTO) {
        log.info("params => " + deleteProductionStackerReclaimerPerformanceDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionStackerReclaimerPerformanceDTO);
        productionStackerReclaimerPerformanceService.deleteProductionStackerReclaimerPerformanceData(deleteProductionStackerReclaimerPerformanceDTO);
    }

    /**
     * 获取排班信息
     * @param infoSchedulingDTO
     * @return
     */
    @Operation(summary = "获取排班信息",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = InfoSchedulingVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "获取排班信息",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getUserSchedulingInfo", method = RequestMethod.GET)
    public AjaxResult getUserSchedulingInfoData(@Validated @ParamModel InfoSchedulingDTO infoSchedulingDTO) {
        log.info("params => " + infoSchedulingDTO);
        String errorMsg = ValidUtil.checkValid(infoSchedulingDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, infoSchedulingDTO);
        return AjaxResult.success(productionStackerReclaimerPerformanceService.getUserSchedulingInfoData(infoSchedulingDTO));
    }
}
