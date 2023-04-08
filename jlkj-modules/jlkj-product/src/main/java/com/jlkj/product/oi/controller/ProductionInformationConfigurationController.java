package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productioninformationconfiguration.DeleteProductionInformationConfigurationDTO;
import com.jlkj.product.oi.dto.productioninformationconfiguration.InsertProductionInformationConfigurationDTO;
import com.jlkj.product.oi.dto.productioninformationconfiguration.PageProductionInformationConfigurationDTO;
import com.jlkj.product.oi.dto.productioninformationconfiguration.UpdateProductionInformationConfigurationDTO;
import com.jlkj.product.oi.service.ProductionInformationConfigurationService;
import com.jlkj.product.oi.vo.productioninformationconfiguration.PageProductionInformationConfigurationVO;
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
 * 控制器-生产信息配置
 *
 * @author sudeyou
 * @since 2022-10-18 14:47:44
 */
@Tag(name = "生产信息配置")
@RestController
@RequestMapping("/productioninformationconfiguration")
@Slf4j
public class ProductionInformationConfigurationController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionInformationConfigurationService productionInformationConfigurationService;

    @Operation(summary = "生产信息配置-查询-分页",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = PageProductionInformationConfigurationVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产信息配置-查询-分页",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionInformationConfigurationPage", method = RequestMethod.GET)
    public Object getProductionInformationConfigurationPageData(@Validated @ParamModel PageProductionInformationConfigurationDTO pageProductionInformationConfigurationDTO) {
        log.info("params => " + pageProductionInformationConfigurationDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionInformationConfigurationDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionInformationConfigurationDTO);
        return AjaxResult.success(productionInformationConfigurationService.getProductionInformationConfigurationPageData(pageProductionInformationConfigurationDTO));
    }

    @Operation(summary = "生产信息配置-新增",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产信息配置-新增",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/insertProductionInformationConfiguration", method = RequestMethod.POST, produces = "application/json")
    public Object insertProductionInformationConfigurationData(@Valid @RequestBody InsertProductionInformationConfigurationDTO insertProductionInformationConfigurationDTO) {
        log.info("params => " + insertProductionInformationConfigurationDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, insertProductionInformationConfigurationDTO);
        return productionInformationConfigurationService.insertProductionInformationConfigurationData(insertProductionInformationConfigurationDTO);
    }

    @Operation(summary = "生产信息配置-修改",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产信息配置-修改",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/updateProductionInformationConfiguration", method = RequestMethod.PUT, produces = "application/json")
    public Object updateProductionInformationConfigurationData(@Valid @RequestBody UpdateProductionInformationConfigurationDTO updateProductionInformationConfigurationDTO) {
        log.info("params => " + updateProductionInformationConfigurationDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateProductionInformationConfigurationDTO);
        return productionInformationConfigurationService.updateProductionInformationConfigurationData(updateProductionInformationConfigurationDTO);
    }

    @Operation(summary = "生产信息配置-删除",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产信息配置-删除",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/deleteProductionInformationConfiguration", method = RequestMethod.DELETE, produces = "application/json")
    public Object deleteProductionInformationConfigurationData(@Valid @RequestBody DeleteProductionInformationConfigurationDTO deleteProductionInformationConfigurationDTO) {
        log.info("params => " + deleteProductionInformationConfigurationDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionInformationConfigurationDTO);
        return productionInformationConfigurationService.deleteProductionInformationConfigurationData(deleteProductionInformationConfigurationDTO);
    }
}

