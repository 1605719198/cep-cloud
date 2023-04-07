package com.jlkj.product.oi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionabnormalledger.AddProductionAbnormalLedgerDTO;
import com.jlkj.product.oi.dto.productionabnormalledger.DelProductionAbnormalLedgerDTO;
import com.jlkj.product.oi.dto.productionabnormalledger.GetProductionAbnormalLedgerDTO;
import com.jlkj.product.oi.dto.productionabnormalledger.UpdateProductionAbnormalLedgerDTO;
import com.jlkj.product.oi.service.impl.ProductionAbnormalLedgerServiceImpl;
import com.jlkj.product.oi.swaggerdto.ProductionAbnormalLedgerSwagger;
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
import java.util.Map;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author zyf
 * @Description
 * @create 2022-05-12 8:31
 */
@Tag(name = "生产异常台账")
@RestController
@RequestMapping("/actualPerformance")
@Slf4j
public class ProductionAbnormalLedgerController {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionAbnormalLedgerServiceImpl abnormalLedgerService;

    @Operation(summary = "生产异常查询",
            parameters = {
                    @Parameter(name = "departmentName", description = "主体单位（模糊匹配）"),
                    @Parameter(name = "planStartTime", description = "开始时间"),
                    @Parameter(name = "planEndTime", description = "结束时间"),
                    @Parameter(name = "size", description = "10"),
                    @Parameter(name = "current", description = "1"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ProductionAbnormalLedgerSwagger.class)))
            }
    )
    @Log(title = "生产异常查询",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getProductionAbnormalLedger", method = RequestMethod.GET)
    public Object get(@Valid GetProductionAbnormalLedgerDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);

        IPage<Map<String, Object>> list = abnormalLedgerService.get(dto);
        return AjaxResult.success(list);
    }

    @Operation(summary = "生产异常台账新增",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "departmentId", description = "主体单位id"),
                            @ExampleObject(name = "departmentName", description = "主体单位"),
                            @ExampleObject(name = "happenTime", description = "发生时间"),
                            @ExampleObject(name = "accidentName", description = "事故名称"),
                            @ExampleObject(name = "preventiveMeasures", description = "防范措施"),
                            @ExampleObject(name = "measuresCompletionTime", description = "计划完成时间yyyy-MM-dd"),
                            @ExampleObject(name = "measuresImplementation", description = "措施落实情况"),
                            @ExampleObject(name = "professionalResponsible_person", description = "专业责任人"),
                            @ExampleObject(name = "trackResponsiblePerson", description = "跟踪责任人"),
                            @ExampleObject(name = "accidentReportFile", description = "事故报告"),
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
    @Log(title = "生产异常台账新增",businessType = BusinessType.INSERT)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/addProductionAbnormalLedger", method = RequestMethod.POST, produces = "application/json")
    public Object save(@Valid @RequestBody AddProductionAbnormalLedgerDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return abnormalLedgerService.save(dto);
    }

    @Operation(summary = "生产异常台账修改",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键"),
                            @ExampleObject(name = "departmentId", description = "主体单位id"),
                            @ExampleObject(name = "departmentName", description = "主体单位"),
                            @ExampleObject(name = "happenTime", description = "发生时间"),
                            @ExampleObject(name = "accidentName", description = "事故名称"),
                            @ExampleObject(name = "preventiveMeasures", description = "防范措施"),
                            @ExampleObject(name = "measuresCompletionTime", description = "计划完成时间yyyy-MM-dd"),
                            @ExampleObject(name = "measuresImplementation", description = "措施落实情况"),
                            @ExampleObject(name = "professionalResponsible_person", description = "专业责任人"),
                            @ExampleObject(name = "trackResponsiblePerson", description = "跟踪责任人"),
                            @ExampleObject(name = "accidentReportFile", description = "事故报告"),
                            @ExampleObject(name = "modifyUserId", description = "用户编号"),
                            @ExampleObject(name = "modifyUserName", description = "用户名称")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "1", description = "已存在或不存在消息"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产异常台账修改",businessType = BusinessType.UPDATE)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/updateProductionAbnormalLedger", method = RequestMethod.POST, produces = "application/json")
    public Object update(@Valid @RequestBody UpdateProductionAbnormalLedgerDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return abnormalLedgerService.update(dto);
    }

    @Operation(summary = "生产异常台账删除",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "1", description = "已存在或不存在消息"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产异常台账删除",businessType = BusinessType.DELETE)
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/delProductionAbnormalLedger", method = RequestMethod.POST, produces = "application/json")
    public Object delete(@Valid @RequestBody DelProductionAbnormalLedgerDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return abnormalLedgerService.delete(dto);
    }
}
