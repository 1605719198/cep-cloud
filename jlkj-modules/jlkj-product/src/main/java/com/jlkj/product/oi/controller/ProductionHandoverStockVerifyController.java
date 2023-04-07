package com.jlkj.product.oi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionHandoverStockVerify.PageProductionHandoverStockVerifyDTO;
import com.jlkj.product.oi.dto.productionHandoverStockVerify.UpdateProductionHandoverStockVerifyDTO;
import com.jlkj.product.oi.service.impl.ProductionHandoverStockVerifyServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;


/**
 * @author yzl
 * @Description 交班仓存核验
 * @create
 */
@Tag(name = "交班仓存核验")
@RestController
@RequestMapping("/productionHandoverStockVerify")
@Slf4j
public class ProductionHandoverStockVerifyController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    ProductionHandoverStockVerifyServiceImpl productionHandoverStockVerifyService;

    @Operation(summary = "查询交班仓存核验",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "start_time", description = "开始时间yyyy-mm-dd"),
                    @Parameter(name = "end_time", description = "结束时间yyyy-mm-dd"),
                    @Parameter(name = "current", description = "页码-从1开始"),
                    @Parameter(name = "size", description = "每页行数"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "date", description = "日期yyyy-mm-dd"),
                                    @ExampleObject(name = "bn1 ~ bn10", description = "配煤仓1~10号仓"),
                                    @ExampleObject(name = "bn11 ~ bn14", description = "储煤塔1~4号塔"),
                                    @ExampleObject(name = "size", description = "10"),
                                    @ExampleObject(name = "current", description = "1")
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询交班仓存核验",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@Validated @ParamModel PageProductionHandoverStockVerifyDTO pageProductionHandoverStockVerifyDTO) {
        log.info("params => " + pageProductionHandoverStockVerifyDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageProductionHandoverStockVerifyDTO);
        IPage<Map<String, String>> list = productionHandoverStockVerifyService.getListPage(pageProductionHandoverStockVerifyDTO);
        return AjaxResult.success(list);
    }

    @Operation(summary = "修改",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键"),
                            @ExampleObject(name = "coal_blending_level_1", description = "1#配煤仓料位"),
                            @ExampleObject(name = "coal_blending_stock_1", description = "1#配煤仓重量"),
                            @ExampleObject(name = "coal_blending_level_2", description = "2#配煤仓料位"),
                            @ExampleObject(name = "coal_blending_stock_2", description = "2#配煤仓重量"),
                            @ExampleObject(name = "coal_blending_level_3", description = "3#配煤仓料位"),
                            @ExampleObject(name = "coal_blending_stock_3", description = "3#配煤仓重量"),
                            @ExampleObject(name = "coal_blending_level_4", description = "4#配煤仓料位"),
                            @ExampleObject(name = "coal_blending_stock_4", description = "4#配煤仓重量"),
                            @ExampleObject(name = "coal_blending_level_5", description = "5#配煤仓料位"),
                            @ExampleObject(name = "coal_blending_stock_5", description = "5#配煤仓重量"),
                            @ExampleObject(name = "coal_blending_level_6", description = "6#配煤仓料位"),
                            @ExampleObject(name = "coal_blending_stock_6", description = "6#配煤仓重量"),
                            @ExampleObject(name = "coal_blending_level_7", description = "7#配煤仓料位"),
                            @ExampleObject(name = "coal_blending_stock_7", description = "7#配煤仓重量"),
                            @ExampleObject(name = "coal_blending_level_8", description = "8#配煤仓料位"),
                            @ExampleObject(name = "coal_blending_stock_8", description = "8#配煤仓重量"),
                            @ExampleObject(name = "coal_blending_level_9", description = "9#配煤仓料位"),
                            @ExampleObject(name = "coal_blending_stock_9", description = "9#配煤仓重量"),
                            @ExampleObject(name = "coal_blending_level_10", description = "10#配煤仓料位"),
                            @ExampleObject(name = "coal_blending_stock_10", description = "10#配煤仓重量"),
                            @ExampleObject(name = "coal_storage_tower_level_1", description = "1#贮煤塔料位"),
                            @ExampleObject(name = "coal_storage_tower_stock_1", description = "1#贮煤塔重量"),
                            @ExampleObject(name = "coal_storage_tower_level_2", description = "2#贮煤塔料位"),
                            @ExampleObject(name = "coal_storage_tower_stock_2", description = "2#贮煤塔重量"),
                            @ExampleObject(name = "coal_storage_tower_level_3", description = "3#贮煤塔料位"),
                            @ExampleObject(name = "coal_storage_tower_stock_3", description = "3#贮煤塔重量"),
                            @ExampleObject(name = "coal_storage_tower_level_4", description = "4#贮煤塔料位"),
                            @ExampleObject(name = "coal_storage_tower_stock_4", description = "4#贮煤塔重量"),
                            @ExampleObject(name = "coal_storage_tower_level_5", description = "5#贮煤塔料位"),
                            @ExampleObject(name = "coal_storage_tower_stock_5", description = "5#贮煤塔重量"),
                            @ExampleObject(name = "coal_storage_tower_level_6", description = "6#贮煤塔料位"),
                            @ExampleObject(name = "coal_storage_tower_stock_6", description = "6#贮煤塔重量"),
                            @ExampleObject(name = "modify_user_id", description = "修改人id"),
                            @ExampleObject(name = "modify_user_name", description = "修改人")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "修改",businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public Object update(@Valid @RequestBody UpdateProductionHandoverStockVerifyDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return productionHandoverStockVerifyService.update(dto);
    }


}

