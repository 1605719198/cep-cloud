package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productioncokeovens.DeleteProductionCokeOvensDTO;
import com.jlkj.product.oi.dto.productioncokeovens.InsertProductionCokeOvensDTO;
import com.jlkj.product.oi.dto.productioncokeovens.PageProductionCokeOvensDTO;
import com.jlkj.product.oi.dto.productioncokeovens.UpdateProductionCokeOvensDTO;
import com.jlkj.product.oi.service.ProductionCokeOvensService;
import com.jlkj.product.oi.vo.productioncokeovens.PageProductionCokeOvensVO;
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
 * 控制器-每日推焦炉数维护
 *
 * @author sudeyou
 * @since 2022-10-18 14:50:42
 */
@Tag(name = "每日推焦炉数维护")
@RestController
@RequestMapping("/productioncokeovens")
@Slf4j
public class ProductionCokeOvensController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionCokeOvensService productionCokeOvensService;

    @Operation(summary = "每日推焦炉数维护-查询-分页",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = PageProductionCokeOvensVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "每日推焦炉数维护-查询-分页",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionCokeOvensPage", method = RequestMethod.GET)
    public Object getProductionCokeOvensPageData(@Validated @ParamModel PageProductionCokeOvensDTO pageProductionCokeOvensDTO) {
        log.info("params => " + pageProductionCokeOvensDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionCokeOvensDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionCokeOvensDTO);
        return AjaxResult.success(productionCokeOvensService.getProductionCokeOvensPageData(pageProductionCokeOvensDTO));
    }

    @Operation(summary = "每日推焦炉数维护-新增",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "每日推焦炉数维护-新增",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/insertProductionCokeOvens", method = RequestMethod.POST, produces = "application/json")
    public Object insertProductionCokeOvensData(@Valid @RequestBody InsertProductionCokeOvensDTO insertProductionCokeOvensDTO) {
        log.info("params => " + insertProductionCokeOvensDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, insertProductionCokeOvensDTO);
        return productionCokeOvensService.insertProductionCokeOvensData(insertProductionCokeOvensDTO);
    }

    @Operation(summary = "每日推焦炉数维护-修改",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "每日推焦炉数维护-修改",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/updateProductionCokeOvens", method = RequestMethod.PUT, produces = "application/json")
    public Object updateProductionCokeOvensData(@Valid @RequestBody UpdateProductionCokeOvensDTO updateProductionCokeOvensDTO) {
        log.info("params => " + updateProductionCokeOvensDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateProductionCokeOvensDTO);
        return productionCokeOvensService.updateProductionCokeOvensData(updateProductionCokeOvensDTO);
    }

    @Operation(summary = "每日推焦炉数维护-抛送ERP",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "每日推焦炉数维护-抛送ERP",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/confirmProductionCokeOvens", method = RequestMethod.POST, produces = "application/json")
    public Object confirmProductionCokeOvensData() {
        return productionCokeOvensService.confirmProductionCokeOvensData();
    }

    @Operation(summary = "每日推焦炉数维护-删除",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "每日推焦炉数维护-删除",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/deleteProductionCokeOvens", method = RequestMethod.DELETE, produces = "application/json")
    public Object deleteProductionCokeOvensData(@Valid @RequestBody DeleteProductionCokeOvensDTO deleteProductionCokeOvensDTO) {
        log.info("params => " + deleteProductionCokeOvensDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionCokeOvensDTO);
        return productionCokeOvensService.deleteProductionCokeOvensData(deleteProductionCokeOvensDTO);
    }
}

