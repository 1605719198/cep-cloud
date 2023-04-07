package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.service.ProductionCoalWarehouseStockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;


/**
 * @author yzl
 * @Description 配煤仓存量
 * @create 2022-12-08 14:32:11.532028
 */
@Tag(name = "配煤仓存量")
@RestController
@RequestMapping("/productionCoalWarehouseStock")
@Slf4j
public class ProductionCoalWarehouseStockController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    ProductionCoalWarehouseStockService productionCoalWarehouseStockService;

    @Operation(summary = "上煤仓余量-物料看板-图表",
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "coal_warehouse_number", description = "配煤仓号"),
                                    @ExampleObject(name = "coal_surplus", description = "剩余煤量(KG)"),
                                    @ExampleObject(name = "material_level", description = "料位"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "上煤仓余量-物料看板-图表",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getMaterialsCoalWarehouseStock", method = RequestMethod.GET)
    public Object getMaterialsCoalWarehouseStock() {
        log.info("params => ");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        List<Map<String, String>> list = productionCoalWarehouseStockService.getMaterialsCoalWarehouseStock();
        return AjaxResult.success(list);
    }

}

