package com.jlkj.product.oi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import com.jlkj.product.oi.dto.productionplantarget.GetProductionPlanDayDTO;
import com.jlkj.product.oi.service.impl.ProductionParameterTargetItemServiceImpl;
import com.jlkj.product.oi.service.impl.ProductionPlanTargetDateServiceImpl;
import com.jlkj.product.oi.service.impl.ProductionPlanTargetMonthServiceImpl;
import com.jlkj.product.oi.service.impl.ProductionPlanTargetYearServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author zyf
 * @Description
 * @create 2022-04-20 10:00
 */
@Tag(name = "日指标计划")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionPlanTargetDayController {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionPlanTargetYearServiceImpl planTargetYearService;

    @Autowired
    ProductionPlanTargetMonthServiceImpl planTargetMonthService;

    @Autowired
    ProductionPlanTargetDateServiceImpl planTargetDateService;

    @Autowired
    ProductionParameterTargetItemServiceImpl productionParameterTargetItemService;

    @Operation(summary = "查询日生产指标计划",
            parameters = {
                    @Parameter(name = "planYear", description = "计划年度"),
                    @Parameter(name = "planMonth", description = "计划月份")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "查询日生产指标计划",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/listDateProductionTargetPlans", method = RequestMethod.GET)
    public Object get(@Valid GetProductionPlanDayDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        List<ProductionParameterTargetItem> itemlist =
                productionParameterTargetItemService.list(new QueryWrapper<ProductionParameterTargetItem>().lambda()
                        .eq(ProductionParameterTargetItem::getTargetItemTypeId, 1));
        return planTargetYearService.getDate(dto, itemlist);
    }
}
