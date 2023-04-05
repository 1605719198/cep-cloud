package com.jlkj.product.oi.controller;

import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionmaterialunloadingperformance.AddProductionMaterialUnloadingPerformanceDTO;
import com.jlkj.product.oi.dto.productionmaterialunloadingperformance.GetProductionMaterialUnloadingPerformanceDTO;
import com.jlkj.product.oi.dto.productionmaterialunloadingperformance.UpdateProductionMaterialUnloadingPerformanceDTO;
import com.jlkj.product.oi.dto.productionmaterialunloadingperformance.UpdateProductionMaterialUnloadingPerformanceWeightDTO;
import com.jlkj.product.oi.domain.*;
import com.jlkj.product.oi.service.impl.MaterialsCodeServiceImpl;
import com.jlkj.product.oi.service.impl.ProductionMaterialUnloadingPerformanceServiceImpl;
import com.jlkj.product.oi.swaggerdto.ProductionMaterialUnloadingPerformanceSwagger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author zyf
 * @Description
 * @create 2022-05-10 8:46
 */
@Tag(name = "卸车实绩")
@RestController
@RequestMapping("/performance")
@Slf4j
public class ProductionMaterialUnloadingPerformanceController {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionMaterialUnloadingPerformanceServiceImpl materialUnloadingPerformanceService;

    @Autowired
    MaterialsCodeServiceImpl materialsCodeService;

    @Operation(summary = "物料卸货实绩查询",
            parameters = {
                    @Parameter(name = "shift_name", description = "班次"),
                    @Parameter(name = "class_name", description = "班别"),
                    @Parameter(name = "unloading_time_start", description = "开始时间"),
                    @Parameter(name = "unloading_time_end", description = "结束时间"),
                    @Parameter(name = "shipping_method", description = "运输方式"),
                    @Parameter(name = "material_code", description = "物料代码"),
                    @Parameter(name = "is_done", description = "0全部1未完成2已完成"),
                    @Parameter(name = "size", description = "10"),
                    @Parameter(name = "current", description = "1"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ProductionMaterialUnloadingPerformanceSwagger.class))
                    )
            }
    )
    @Log(title = "物料卸货实绩查询",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/listMaterialUnloadingPerformance", method = RequestMethod.GET)
    public Object get(@Valid @ParamModel GetProductionMaterialUnloadingPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return materialUnloadingPerformanceService.get(dto);
    }

    @Operation(summary = "物料卸货实绩新增",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "编号"),
                            @ExampleObject(name = "planId", description = "检斤计划编号"),
                            @ExampleObject(name = "planDate", description = "检斤计划日期"),
                            @ExampleObject(name = "supplierNumber", description = "供货单位编号"),
                            @ExampleObject(name = "supplierName", description = "供货单位名称"),
                            @ExampleObject(name = "shippingMethod", description = "运输方式"),
                            @ExampleObject(name = "vehicleNumber", description = "车号"),
                            @ExampleObject(name = "storageSpacesId", description = "储位编号"),
                            @ExampleObject(name = "materialNumber", description = "物料编号"),
                            @ExampleObject(name = "materialName", description = "物料名称"),
                            @ExampleObject(name = "materialCode", description = "物料代码"),
                            @ExampleObject(name = "materialWeight", description = "物料重量"),
                            @ExampleObject(name = "shiftName", description = "班次"),
                            @ExampleObject(name = "className", description = "班别"),
                            @ExampleObject(name = "unloadingTime", description = "卸车时间"),
                            @ExampleObject(name = "createUserId", description = "创建人编号"),
                            @ExampleObject(name = "createUserNname", description = "创建人姓名")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "1", description = ""),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "物料卸货实绩新增",businessType = BusinessType.INSERT)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/saveMaterialUnloadingPerformance", method = RequestMethod.POST, produces = "application/json")
    public Object save(@Valid @RequestBody AddProductionMaterialUnloadingPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return materialUnloadingPerformanceService.save(dto);
    }

    @Operation(summary = "物料卸货实绩修改",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "记录id"),
                            @ExampleObject(name = "storage_maintain_id", description = "仓号id"),
                            @ExampleObject(name = "storage_maintain_space_name", description = "仓号名"),
                            @ExampleObject(name = "unloading_time", description = "卸车时间"),
                            @ExampleObject(name = "modify_user_id", description = "用户id"),
                            @ExampleObject(name = "modify_user_name", description = "用户名")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "1", description = "已存在或不存在消息"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "物料卸货实绩修改",businessType = BusinessType.UPDATE)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/updateMaterialUnloadingPerformance", method = RequestMethod.POST, produces = "application/json")
    public Object update(@Valid @RequestBody UpdateProductionMaterialUnloadingPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return materialUnloadingPerformanceService.update(dto);
    }

    @Operation(summary = "物料卸货实绩修改重量",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "planListNo", description = "计划单号"),
                            @ExampleObject(name = "carrierNo", description = "车号"),
                            @ExampleObject(name = "carrierType", description = "运数方式"),
                            @ExampleObject(name = "mtrlNo", description = "料号"),
                            @ExampleObject(name = "netWgt", description = "净重"),
                            @ExampleObject(name = "userId", description = "用户id"),
                            @ExampleObject(name = "userName", description = "用户名")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "1", description = "已存在或不存在消息"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "物料卸货实绩修改重量",businessType = BusinessType.UPDATE)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/updateMaterialUnloadingPerformanceWeight", method = RequestMethod.POST, produces = "application/json")
    public Object updateMaterialUnloadingPerformanceWeight(@Valid @RequestBody UpdateProductionMaterialUnloadingPerformanceWeightDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return materialUnloadingPerformanceService.updateMaterialUnloadingPerformanceWeight(dto);
    }

    @Log(title = "物料卸货实绩删除重量",businessType = BusinessType.DELETE)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/delMaterialUnloadingPerformance", method = RequestMethod.POST, produces = "application/json")
    public Object del(@Valid @RequestBody AddProductionMaterialUnloadingPerformanceDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return materialUnloadingPerformanceService.del(dto);
    }
}
