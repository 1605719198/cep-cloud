package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionoutputperformancemanualbake.DeleteProductionOutputPerformanceManualBakeDTO;
import com.jlkj.product.oi.dto.productionoutputperformancemanualbake.InsertProductionOutputPerformanceManualBakeDTO;
import com.jlkj.product.oi.dto.productionoutputperformancemanualbake.PageProductionOutputPerformanceManualBakeDTO;
import com.jlkj.product.oi.service.ProductionOutputPerformanceManualBakeService;
import com.jlkj.product.oi.vo.productionoutputperformancemanualbake.PageProductionOutputPerformanceManualBakeVO;
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
 * 控制器-生产管理-炼焦实绩-出炉实绩-手动出炉
 *
 * @author sudeyou
 * @since 2022-12-28 13:35:48
 */
@Tag(name = "生产管理-炼焦实绩-出炉实绩-手动出炉")
@RestController
@RequestMapping("/productionoutputperformancemanualbake")
@Slf4j
public class ProductionOutputPerformanceManualBakeController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionOutputPerformanceManualBakeService productionOutputPerformanceManualBakeService;

    @Operation(summary = "生产管理-炼焦实绩-出炉实绩-手动出炉-查询-分页",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = PageProductionOutputPerformanceManualBakeVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-炼焦实绩-出炉实绩-手动出炉-查询-分页",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionOutputPerformanceManualBakePage", method = RequestMethod.GET)
    public Object getProductionOutputPerformanceManualBakePageData(@Validated @ParamModel PageProductionOutputPerformanceManualBakeDTO pageProductionOutputPerformanceManualBakeDTO) {
        log.info("params => " + pageProductionOutputPerformanceManualBakeDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionOutputPerformanceManualBakeDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionOutputPerformanceManualBakeDTO);
        return AjaxResult.success(productionOutputPerformanceManualBakeService.getProductionOutputPerformanceManualBakePageData(pageProductionOutputPerformanceManualBakeDTO));
    }

    @Operation(summary = "生产管理-炼焦实绩-出炉实绩-手动出炉-新增",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-炼焦实绩-出炉实绩-手动出炉-新增",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/insertProductionOutputPerformanceManualBake", method = RequestMethod.POST, produces = "application/json")
    public Object insertProductionOutputPerformanceManualBakeData(@Valid @RequestBody InsertProductionOutputPerformanceManualBakeDTO insertProductionOutputPerformanceManualBakeDTO) {
        log.info("params => " + insertProductionOutputPerformanceManualBakeDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, insertProductionOutputPerformanceManualBakeDTO);
        return productionOutputPerformanceManualBakeService.insertProductionOutputPerformanceManualBakeData(insertProductionOutputPerformanceManualBakeDTO);
    }

    @Operation(summary = "生产管理-炼焦实绩-出炉实绩-手动出炉-删除",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-炼焦实绩-出炉实绩-手动出炉-删除",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/deleteProductionOutputPerformanceManualBake", method = RequestMethod.DELETE, produces = "application/json")
    public Object deleteProductionOutputPerformanceManualBakeData(@Valid @RequestBody DeleteProductionOutputPerformanceManualBakeDTO deleteProductionOutputPerformanceManualBakeDTO) {
        log.info("params => " + deleteProductionOutputPerformanceManualBakeDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionOutputPerformanceManualBakeDTO);
        return productionOutputPerformanceManualBakeService.deleteProductionOutputPerformanceManualBakeData(deleteProductionOutputPerformanceManualBakeDTO);
    }
}
