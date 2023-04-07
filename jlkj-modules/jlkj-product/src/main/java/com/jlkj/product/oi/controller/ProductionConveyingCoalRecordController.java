package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionconveyingcoalrecord.DeleteProductionConveyingCoalRecordDTO;
import com.jlkj.product.oi.dto.productionconveyingcoalrecord.InsertProductionConveyingCoalRecordDTO;
import com.jlkj.product.oi.dto.productionconveyingcoalrecord.PageProductionConveyingCoalRecordDTO;
import com.jlkj.product.oi.service.ProductionConveyingCoalRecordService;
import com.jlkj.product.oi.vo.productionconveyingcoalrecord.PageProductionConveyingCoalRecordVO;
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
 * 控制器-生产实绩-上煤记录
 *
 * @author sudeyou
 * @since 2022-11-17 15:18:05
 */
@Tag(name = "生产实绩-上煤记录")
@RestController
@RequestMapping("/productionconveyingcoalrecord")
@Slf4j
public class ProductionConveyingCoalRecordController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private ProductionConveyingCoalRecordService productionConveyingCoalRecordService;

    @Operation(summary = "生产实绩-上煤记录-新增",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产实绩-上煤记录-新增",businessType = BusinessType.INSERT)
    @RequestMapping(value = "/insertProductionConveyingCoalRecord", method = RequestMethod.POST, produces = "application/json")
    public Object insertProductionConveyingCoalRecordData(@Valid @RequestBody InsertProductionConveyingCoalRecordDTO insertProductionConveyingCoalRecordDTO) {
        log.info("params => " + insertProductionConveyingCoalRecordDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, insertProductionConveyingCoalRecordDTO);
        return productionConveyingCoalRecordService.insertProductionConveyingCoalRecordData(insertProductionConveyingCoalRecordDTO);
    }

    @Operation(summary = "生产实绩-上煤记录-删除",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产实绩-上煤记录-删除",businessType = BusinessType.DELETE)
    @RequestMapping(value = "/deleteProductionConveyingCoalRecord", method = RequestMethod.DELETE, produces = "application/json")
    public Object deleteProductionConveyingCoalRecordData(@Valid @RequestBody DeleteProductionConveyingCoalRecordDTO deleteProductionConveyingCoalRecordDTO) {
        log.info("params => " + deleteProductionConveyingCoalRecordDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteProductionConveyingCoalRecordDTO);
        return productionConveyingCoalRecordService.deleteProductionConveyingCoalRecordData(deleteProductionConveyingCoalRecordDTO);
    }

    @Operation(summary = "生产实绩-上煤记录-查询-分页",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = PageProductionConveyingCoalRecordVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产实绩-上煤记录-查询-分页",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionConveyingCoalRecordPage", method = RequestMethod.GET)
    public Object getProductionConveyingCoalRecordPageData(@Validated @ParamModel PageProductionConveyingCoalRecordDTO pageProductionConveyingCoalRecordDTO) {
        log.info("params => " + pageProductionConveyingCoalRecordDTO);
        String errorMsg = ValidUtil.checkValid(pageProductionConveyingCoalRecordDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionConveyingCoalRecordDTO);
        return AjaxResult.success(productionConveyingCoalRecordService.getProductionConveyingCoalRecordPageData(pageProductionConveyingCoalRecordDTO));
    }
}
