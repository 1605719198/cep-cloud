package com.jlkj.product.oi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.domain.ProductionParameterTargetItem;
import com.jlkj.product.oi.dto.productionplanoutputdate.ListProductionPlanOutputDateTargetItemDTO;
import com.jlkj.product.oi.dto.productionplantarget.GetProductionPlanDayDTO;
import com.jlkj.product.oi.service.impl.ProductionParameterTargetItemServiceImpl;
import com.jlkj.product.oi.service.impl.ProductionPlanOutputDateServiceImpl;
import com.jlkj.product.oi.vo.productionplanoutputdate.ListProductionPlanOutputDateTargetItemVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
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
 * @create 2022-04-26 10:35
 */
@Tag(name = "日产量计划")
@RestController
@RequestMapping("/plan")
@Slf4j
public class ProductionPlanOutputDateController {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionParameterTargetItemServiceImpl productionParameterTargetItemService;

    @Autowired
    ProductionPlanOutputDateServiceImpl planOutputDateService;

    @Operation(summary = "查询日生产产量计划",
            parameters = {
                    @Parameter(name = "planYear", description = "计划年度"),
                    @Parameter(name = "planMonth", description = "计划月份")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功")
            }
    )
    @Log(title = "查询日生产产量计划",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/listDateProductionOutputPlans", method = RequestMethod.GET)
    public Object get(@Valid GetProductionPlanDayDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        List<ProductionParameterTargetItem> itemlist =
                productionParameterTargetItemService.list(new QueryWrapper<ProductionParameterTargetItem>().lambda()
                        .eq(ProductionParameterTargetItem::getTargetItemTypeId, 1));
        return planOutputDateService.get(dto, itemlist);
    }

    @Operation(summary = "生产管理-指标跟踪-图表-指标项(日计划)",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ListProductionPlanOutputDateTargetItemVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "生产管理-指标跟踪-图表-指标项(日计划)",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionPlanOutputDateTargetItemChart", method = RequestMethod.GET)
    public Object getProductionPlanOutputDateTargetItemChartData(@Validated @ParamModel ListProductionPlanOutputDateTargetItemDTO listProductionPlanOutputDateTargetItemDTO) {
        log.info("params => " + listProductionPlanOutputDateTargetItemDTO);
        String errorMsg = ValidUtil.checkValid(listProductionPlanOutputDateTargetItemDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listProductionPlanOutputDateTargetItemDTO);
        return planOutputDateService.getProductionPlanOutputDateTargetItemChartData(listProductionPlanOutputDateTargetItemDTO);
    }
}
