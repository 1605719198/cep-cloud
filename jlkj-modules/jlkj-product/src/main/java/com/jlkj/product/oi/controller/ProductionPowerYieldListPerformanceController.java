package com.jlkj.product.oi.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.core.web.resp.PageResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productionpowerlistperformance.ProductionPowerYieldListPerformanceDTO;
import com.jlkj.product.oi.service.impl.ProductionPowerYieldListPerformanceServiceImpl;
import com.jlkj.product.oi.swaggerdto.ProductionPowerYieldListPerformanceSwagger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author zzh
 * @Description
 * @create 2022年9月8日07:47:17
 */
@Tag(name = "发电产量实绩")
@RestController
@RequestMapping("/powerYieldPerformance")
@Slf4j
public class ProductionPowerYieldListPerformanceController {

    @Autowired
    private HttpServletRequest httpServletRequest ;

    @Autowired
    private ProductionPowerYieldListPerformanceServiceImpl service ;


    @Operation(summary = "发电量实绩列表查询",
            parameters = {
                    @Parameter(name = "shift", description = "班次"),
                    @Parameter(name = "classes", description = "班别"),
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ProductionPowerYieldListPerformanceSwagger.class))
                    )
            }
    )
    @Log(title = "发电量实绩列表查询",businessType = BusinessType.OTHER)
    @Transactional(readOnly = true)
    @RequestMapping(value = "/getProductionPowerYieldListPerformanceList", method = RequestMethod.GET)
    public Object getList(@Valid @ParamModel ProductionPowerYieldListPerformanceDTO dto){
        log.info("params => " + dto.toString());
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        IPage<Map<String, Object>> list = service.get(dto);
        return AjaxResult.success(list);
    }

}
