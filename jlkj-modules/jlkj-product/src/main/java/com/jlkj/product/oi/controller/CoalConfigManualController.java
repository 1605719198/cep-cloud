package com.jlkj.product.oi.controller;

import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.coalconfigmanual.*;
import com.jlkj.product.oi.service.impl.ProductionCoalTowerStockServiceImpl;
import com.jlkj.product.oi.swaggerdto.ProductionCoalTowerBlendedCoalRecordSwagger;
import com.jlkj.product.oi.swaggerdto.ProductionCoalTowerStockSwagger;
import com.jlkj.product.oi.swaggerdto.ProductionCoalWarehouseBlendedCoalRecordSwagger;
import com.jlkj.product.oi.swaggerdto.ProductionPlanConfigCokeSwagger;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author zyf
 * @Description
 * @create 2022-12-06 10:47
 */
@Tag(name = "手动配煤")
@RestController
@RequestMapping("/manual_config")
@Slf4j
public class CoalConfigManualController {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionCoalTowerStockServiceImpl coalTowerStockService;

    @Operation(summary = "手动配煤-储煤塔列表查询",
            parameters = {

            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ProductionCoalTowerStockSwagger.class)))
            }
    )
    @Log(title = "手动配煤-储煤塔列表查询",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getTowerList", method = RequestMethod.GET)
    public Object getList() {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return coalTowerStockService.getTowerList();
    }

    @Operation(summary = "手动配煤-储煤塔配煤详细列表查询",
            parameters = {

                    @Parameter(name = "tower", description = "储煤塔号"),
                    @Parameter(name = "size", description = "10"),
                    @Parameter(name = "current", description = "1"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ProductionCoalTowerBlendedCoalRecordSwagger.class))
                    )
            }
    )
    @Log(title = "手动配煤-储煤塔配煤详细列表查询",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getTowerBlendDetailList", method = RequestMethod.GET)
    public Object getTowerBlendDetailList(@Validated GetPageDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return coalTowerStockService.getTowerBlendDetailList(dto);
    }

    @Operation(summary = "手动配煤-储煤塔配煤记录对应详细配煤仓配煤列表查询",
            parameters = {

                    @Parameter(name = "tower_id", description = "储煤塔记录id"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ProductionCoalWarehouseBlendedCoalRecordSwagger.class))
                    )
            }
    )
    @Log(title = "手动配煤-储煤塔配煤记录对应详细配煤仓配煤列表查询",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getBlendWareDetailList", method = RequestMethod.GET)
    public Object getBlendWareDetailList(@Validated GetWareHouseDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return coalTowerStockService.getBlendWareDetailList(dto);
    }

    @Operation(summary = "手动配煤-根据配煤开始时间，查询前置数据",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "返回", description = "{\n" +
                                            "    \"plan\": {\n" +
                                            "        \"id\": \"配煤计划编号\",\n" +
                                            "        \"material_id\": \"物料id(焦炭等级id)\",\n" +
                                            "        \"material_name\": \"物料名称(焦炭等级名称)\",\n" +
                                            "        \"material_code\": \"物料代码(焦炭等级code)\"\n" +
                                            "        \"tower_number\": \"储煤塔号\"\n" +
                                            "    },\n" +
                                            "    \"tower\": {\n" +
                                            "        \"coal_weight_after\": \"储煤塔的配煤前重量\",\n" +
                                            "        \"coal_material_level_after\": \"储煤塔的配煤前料位\"\n" +
                                            "    },\n" +
                                            "    \"config\":[{\n" +
                                            "        \"id\": \"id\",\n" +
                                            "        \"plan_id\": \"计划编号\",\n" +
                                            "        \"warehouse_number\": \"配煤仓号\",\n" +
                                            "        \"coal_type_id\": \"煤种编号\",\n" +
                                            "        \"coal_type_name\": \"煤种名称\",\n" +
                                            "        \"materials_code\": \"物料代码\",\n" +
                                            "        \"proportion\": \"配置比例\",\n" +
                                            "        \"before_stock\": \"配煤前重量\"\n" +
                                            "        \"coke_material_level_before\": \"配煤前料位\"\n" +
                                            "    }]\n" +
                                            "}"),
                            }))
            }
    )
    @Log(title = "手动配煤-根据配煤开始时间，查询前置数据",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getLastConfigPlan", method = RequestMethod.GET)
    public Object get(@Valid GetDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return coalTowerStockService.getLastConfigPlan(dto);
    }

    @Operation(summary = "手动配煤-新增",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "start_time", description = "开始时间"),
                            @ExampleObject(name = "end_time", description = "结束时间"),
                            @ExampleObject(name = "shift_name", description = "班次"),
                            @ExampleObject(name = "class_name", description = "班别"),
                            @ExampleObject(name = "shift_start_time", description = "上班时间"),
                            @ExampleObject(name = "shift_end_time", description = "下班时间"),
                            @ExampleObject(name = "plan_id", description = "配煤计划id"),
                            @ExampleObject(name = "coal_level_materials_code", description = "焦炭等级料号"),
                            @ExampleObject(name = "coal_level_materials_name", description = "焦炭等级名称"),
                            @ExampleObject(name = "coal_tower_number", description = "储煤塔号"),
                            @ExampleObject(name = "coal_weight", description = "配煤重量"),
                            @ExampleObject(name = "coal_weight_befor", description = "配煤前重量"),
                            @ExampleObject(name = "coal_weight_after", description = "配煤后重量"),
                            @ExampleObject(name = "coal_material_level_befor", description = "配煤前料位"),
                            @ExampleObject(name = "coal_material_level_after", description = "配煤后料位"),
                            @ExampleObject(name = "duration", description = "配煤时长"),
                            @ExampleObject(name = "user_id", description = "user_id"),
                            @ExampleObject(name = "user_name", description = "user_name"),
                            @ExampleObject(name = "coal_blendings", description = "=====配煤仓对象集合，详细如下====="),
                            @ExampleObject(name = "bunker_number", description = "配煤仓号"),
                            @ExampleObject(name = "before_material_level", description = "配煤前料位"),
                            @ExampleObject(name = "after_material_level", description = "配煤后料位"),
                            @ExampleObject(name = "before_stock", description = "配煤前存量"),
                            @ExampleObject(name = "after_stock", description = "配煤后存量"),
                            @ExampleObject(name = "duration", description = "配煤时长"),
                            @ExampleObject(name = "material_weight", description = "配煤重量"),
                            @ExampleObject(name = "material_number", description = "物料编号"),
                            @ExampleObject(name = "material_name", description = "物料名称"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "1", description = ""),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "手动配煤-新增",businessType = BusinessType.INSERT)
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/addManualConfigCoal", produces = "application/json")
    public Object save(@Valid @RequestBody SaveOrUpdateManualDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return coalTowerStockService.save(dto);
    }

    @Operation(summary = "手动配煤-删除",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键"),
                            @ExampleObject(name = "user_id", description = "user_id"),
                            @ExampleObject(name = "user_name", description = "user_name"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "1", description = "已存在或不存在消息"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "手动配煤-删除",businessType = BusinessType.DELETE)
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/delManualConfigCoal", produces = "application/json")
    public Object delete(@Valid @RequestBody DelDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return coalTowerStockService.del(dto);
    }

    @Operation(summary = "获取配煤计划状态2或3列表",
            parameters = {
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ProductionPlanConfigCokeSwagger.class))
                    )
            }
    )
    @Log(title = "获取配煤计划状态2或3列表",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @GetMapping(value = "/getPlanConfigCoke")
    public Object getPlanConfigCoke() {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        return coalTowerStockService.getPlanConfigCoke();
    }
}
