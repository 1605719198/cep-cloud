package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productioncokeshedoutput.*;
import com.jlkj.product.oi.service.ProductionCokeShedOutputService;
import com.jlkj.product.oi.vo.productioncokeshedoutput.PageProductionCokeShedOutputVO;
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
*@description: 焦棚产量维护
*@Author: 265823
*@date: 2023/7/10 14:15
*/
@Tag(name = "焦棚产量维护")
@RestController
@RequestMapping("/productioncokeshedoutput")
@Slf4j
public class ProductionCokeShedOutputController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionCokeShedOutputService productionCokeShedOutputService;

    /**
     * 焦棚产量维护-查询-分页
     * @param pageProductionCokeShedOutputDTO
     * @return
     */
    @Operation(summary = "焦棚产量维护-查询-分页",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = PageProductionCokeShedOutputVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "焦棚产量维护-查询-分页",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionCokeShedOutputPage", method = RequestMethod.GET)
    public AjaxResult getProductionCokeShedOutputPageData(@Validated @ParamModel PageProductionCokeShedOutputDTO pageProductionCokeShedOutputDTO) {
        log.info("params => " + pageProductionCokeShedOutputDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionCokeShedOutputDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionCokeShedOutputDTO);
        return AjaxResult.success(productionCokeShedOutputService.getProductionCokeShedOutputPageData(pageProductionCokeShedOutputDTO));
    }

    /**
     * 焦棚产量维护-新增
     * @param insertProductionCokeShedOutputDTO
     */
    @Operation(summary = "焦棚产量维护-新增",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "焦棚产量维护-新增",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/insertProductionCokeShedOutput", method = RequestMethod.POST, produces = "application/json")
    public void insertProductionCokeShedOutputData(@Valid @RequestBody InsertProductionCokeShedOutputDTO insertProductionCokeShedOutputDTO) {
        log.info("params => " + insertProductionCokeShedOutputDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, insertProductionCokeShedOutputDTO);
        productionCokeShedOutputService.insertProductionCokeShedOutputData(insertProductionCokeShedOutputDTO);
    }

    /**
     * 焦棚产量维护-修改
     * @param updateProductionCokeShedOutputDTO
     */
    @Operation(summary = "焦棚产量维护-修改",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "焦棚产量维护-修改",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/updateProductionCokeShedOutput", method = RequestMethod.PUT, produces = "application/json")
    public void updateProductionCokeShedOutputData(@Valid @RequestBody UpdateProductionCokeShedOutputDTO updateProductionCokeShedOutputDTO) {
        log.info("params => " + updateProductionCokeShedOutputDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateProductionCokeShedOutputDTO);
        productionCokeShedOutputService.updateProductionCokeShedOutputData(updateProductionCokeShedOutputDTO);
    }

    /**
     * 焦棚产量维护-抛送ERP
     * @param confirmProductionCokeShedOutputDTO
     */
    @Operation(summary = "焦棚产量维护-抛送ERP",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "焦棚产量维护-抛送ERP",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/confirmProductionCokeShedOutput", method = RequestMethod.PUT, produces = "application/json")
    public void confirmProductionCokeShedOutputData(@Valid @RequestBody ConfirmProductionCokeShedOutputDTO confirmProductionCokeShedOutputDTO) {
        log.info("params => " + confirmProductionCokeShedOutputDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, confirmProductionCokeShedOutputDTO);
        productionCokeShedOutputService.confirmProductionCokeShedOutputData(confirmProductionCokeShedOutputDTO);
    }

    /**
     * 焦棚产量维护-删除
     * @param deleteProductionCokeShedOutputDTO
     */
    @Operation(summary = "焦棚产量维护-删除",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "焦棚产量维护-删除",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/deleteProductionCokeShedOutput", method = RequestMethod.DELETE, produces = "application/json")
    public void deleteProductionCokeShedOutputData(@Valid @RequestBody DeleteProductionCokeShedOutputDTO deleteProductionCokeShedOutputDTO) {
        log.info("params => " + deleteProductionCokeShedOutputDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionCokeShedOutputDTO);
        productionCokeShedOutputService.deleteProductionCokeShedOutputData(deleteProductionCokeShedOutputDTO);
    }
}

