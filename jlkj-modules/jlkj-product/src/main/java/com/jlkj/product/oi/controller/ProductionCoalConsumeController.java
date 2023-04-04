package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.resp.PageResult;
import com.jlkj.common.core.web.resp.Result;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productioncoalconsume.*;
import com.jlkj.product.oi.service.ProductionCoalConsumeService;
import com.jlkj.product.oi.vo.productioncoalconsume.PageProductionCoalConsumeVO;
import com.jlkj.product.oi.service.ProductionCoalConsumeService;
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
 * 控制器-炼焦煤消耗维护
 *
 * @author sudeyou
 * @since 2022-11-03 17:10:54
 */
@Tag(name = "炼焦煤消耗维护")
@RestController
@RequestMapping("/productioncoalconsume")
@Slf4j
public class ProductionCoalConsumeController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionCoalConsumeService productionCoalConsumeService;

    @Operation(summary = "炼焦煤消耗维护-查询-分页",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = PageProductionCoalConsumeVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "炼焦煤消耗维护-查询-分页",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionCoalConsumePage", method = RequestMethod.GET)
    public Object getProductionCoalConsumePageData(@Validated @ParamModel PageProductionCoalConsumeDTO pageProductionCoalConsumeDTO) {
        log.info("params => " + pageProductionCoalConsumeDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionCoalConsumeDTO);
        if (!"".equals(errorMsg)) {
            return Result.validatedFailure(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionCoalConsumeDTO);
        return PageResult.success(productionCoalConsumeService.getProductionCoalConsumePageData(pageProductionCoalConsumeDTO));
    }

    @Operation(summary = "炼焦煤消耗维护-新增",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "炼焦煤消耗维护-新增",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/insertProductionCoalConsume", method = RequestMethod.POST, produces = "application/json")
    public Object insertProductionCoalConsumeData(@Valid @RequestBody InsertProductionCoalConsumeDTO insertProductionCoalConsumeDTO) {
        log.info("params => " + insertProductionCoalConsumeDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, insertProductionCoalConsumeDTO);
        return productionCoalConsumeService.insertProductionCoalConsumeData(insertProductionCoalConsumeDTO);
    }

    @Operation(summary = "炼焦煤消耗维护-修改",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "炼焦煤消耗维护-修改",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/updateProductionCoalConsume", method = RequestMethod.PUT, produces = "application/json")
    public Object updateProductionCoalConsumeData(@Valid @RequestBody UpdateProductionCoalConsumeDTO updateProductionCoalConsumeDTO) {
        log.info("params => " + updateProductionCoalConsumeDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateProductionCoalConsumeDTO);
        return productionCoalConsumeService.updateProductionCoalConsumeData(updateProductionCoalConsumeDTO);
    }

    @Operation(summary = "炼焦煤消耗维护-抛送ERP",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "炼焦煤消耗维护-抛送ERP",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/confirmProductionCoalConsume", method = RequestMethod.PUT, produces = "application/json")
    public Object confirmProductionCoalConsume(@Valid @RequestBody ConfirmProductionCoalConsumeDTO confirmProductionCoalConsumeDTO) {
        log.info("params => " + confirmProductionCoalConsumeDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, confirmProductionCoalConsumeDTO);
        return productionCoalConsumeService.confirmProductionCoalConsumeData(confirmProductionCoalConsumeDTO);
    }

    @Operation(summary = "炼焦煤消耗维护-删除",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "炼焦煤消耗维护-删除",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/deleteProductionCoalConsume", method = RequestMethod.DELETE, produces = "application/json")
    public Object deleteProductionCoalConsumeData(@Valid @RequestBody DeleteProductionCoalConsumeDTO deleteProductionCoalConsumeDTO) {
        log.info("params => " + deleteProductionCoalConsumeDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionCoalConsumeDTO);
        return productionCoalConsumeService.deleteProductionCoalConsumeData(deleteProductionCoalConsumeDTO);
    }
}

