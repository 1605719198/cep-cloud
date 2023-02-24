package com.jlkj.energy.ee.controller;

import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.energy.ee.dto.energyconsumptionoutputaccounting.InsertEnergyConsumptionOutputAccountingDTO;
import com.jlkj.energy.ee.service.EnergyConsumptionOutputAccountingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * 控制器-能源耗用产出账务信息
 * @author sudeyou
 * @since 2022-09-16 16:10:10
 */
@Tag(name = "能源耗用产出账务信息")
@RestController
@RequestMapping("/energyconsumptionoutputaccounting")
@Slf4j
public class EnergyConsumptionOutputAccountingController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private EnergyConsumptionOutputAccountingService energyConsumptionOutputAccountingService;

    @Operation(summary = "能源耗用产出账务信息-新增",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "能源耗用产出账务信息-新增", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/insertEnergyConsumptionOutputAccounting", method = RequestMethod.POST, produces = "application/json")
    public Object insertEnergyConsumptionOutputAccountingData(@Valid @RequestBody InsertEnergyConsumptionOutputAccountingDTO insertEnergyConsumptionOutputAccountingDTO) {
        log.info("params => " + insertEnergyConsumptionOutputAccountingDTO);
//        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, insertEnergyConsumptionOutputAccountingDTO);
        return energyConsumptionOutputAccountingService.insertEnergyConsumptionOutputAccountingData(insertEnergyConsumptionOutputAccountingDTO);
    }
}

