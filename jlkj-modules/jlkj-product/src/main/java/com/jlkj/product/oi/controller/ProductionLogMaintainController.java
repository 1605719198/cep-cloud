package com.jlkj.product.oi.controller;

import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.logmaintain.GetDto;
import com.jlkj.product.oi.dto.logmaintain.SaveOrUpdateDTO;
import com.jlkj.product.oi.service.impl.ProductionLogMaintainServiceImpl;
import com.jlkj.product.oi.swaggerdto.ProductionLogMaintainSwagger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author zyf
 * @Description
 * @create 2022-10-10 9:12
 */
@Tag(name = "日志维护")
@RestController
@RequestMapping("/logmaintain")
@Slf4j
public class ProductionLogMaintainController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionLogMaintainServiceImpl productionLogMaintainService;


    @Operation(summary = "日志查询",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
                    @Parameter(name = "item_id", description = "日志项id"),
                    @Parameter(name = "size", description = "10"),
                    @Parameter(name = "current", description = "1"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ProductionLogMaintainSwagger.class)))
            }
    )
    @Log(title = "日志查询",businessType = BusinessType.OTHER)
    @GetMapping(value = "/list")
    public Object get(@Validated @ParamModel GetDto dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return productionLogMaintainService.get(dto);
    }


    @Operation(summary = "日志新增",
            parameters = {
                    @Parameter(name = "item_id", description = "日志项id"),
                    @Parameter(name = "item_name", description = "日志项名称"),
                    @Parameter(name = "content", description = "日志内容"),
                    @Parameter(name = "time", description = "填报时间"),
                    @Parameter(name = "user_id", description = "用户id"),
                    @Parameter(name = "user_name", description = "用户id")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ProductionLogMaintainSwagger.class)))
            }
    )
    @Log(title = "日志新增",businessType = BusinessType.INSERT)
    @PostMapping(value = "/save")
    public Object save(@Validated @RequestBody SaveOrUpdateDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return productionLogMaintainService.save(dto);
    }


    @Operation(summary = "日志修改",
            parameters = {
                    @Parameter(name = "id", description = "主键"),
                    @Parameter(name = "item_id", description = "日志项id"),
                    @Parameter(name = "item_name", description = "日志项名称"),
                    @Parameter(name = "content", description = "日志内容"),
                    @Parameter(name = "time", description = "填报时间"),
                    @Parameter(name = "user_id", description = "用户id"),
                    @Parameter(name = "user_name", description = "用户id")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ProductionLogMaintainSwagger.class)))
            }
    )
    @Log(title = "日志修改",businessType = BusinessType.UPDATE)
    @PostMapping(value = "/update")
    public Object update(@Validated @RequestBody SaveOrUpdateDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return productionLogMaintainService.update(dto);
    }


    @Operation(summary = "日志删除",
            parameters = {
                    @Parameter(name = "id", description = "主键")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ProductionLogMaintainSwagger.class)))
            }
    )
    @Log(title = "日志删除",businessType = BusinessType.DELETE)
    @PostMapping(value = "/del")
    public Object del(@Validated @RequestBody SaveOrUpdateDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return productionLogMaintainService.del(dto);
    }
}
