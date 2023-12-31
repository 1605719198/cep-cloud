package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.product.oi.dto.productioncoefficientrecord.GetProductionCoefficientRecordDTO;
import com.jlkj.product.oi.dto.productioncokerecords.ListHomeCokeProportionDTO;
import com.jlkj.product.oi.service.ProductionCokeRecordsService;
import com.jlkj.product.oi.swaggerdto.ProductionCokeRecordsSwagger;
import com.jlkj.product.oi.vo.productioncokerecords.ListHomeCokeProportionVO;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import io.lettuce.core.output.ValueValueListOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
*@description: 焦炭产量记录
*@Author: 265823
*@date: 2023/7/10 14:12
*/
@Tag(name = " 焦炭产量记录")
@RestController
@RequestMapping("/cokeRecords")
@Slf4j
public class ProductionCokeRecordsController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    ProductionCokeRecordsService cokeRecordsService;

    @Resource
    ProductionCokeRecordsService productionCokeRecordsService;

    /**
     * 焦炭产量记录查询
     * @param dto
     * @return
     */
    @Operation(summary = "焦炭产量记录查询",
            parameters = {
                    @Parameter(name = "start_time", description = "开始时间"),
                    @Parameter(name = "end_time", description = "结束时间"),
                    @Parameter(name = "shift_name", description = "班次"),
                    @Parameter(name = "class_name", description = "班别"),
                    @Parameter(name = "size", description = "10"),
                    @Parameter(name = "current", description = "1"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ProductionCokeRecordsSwagger.class)))
            }
    )
    @Log(title = "焦炭产量记录查询",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getProductionCokeRecords", method = RequestMethod.GET)
    public AjaxResult get(@Valid @ParamModel GetProductionCoefficientRecordDTO dto) {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(cokeRecordsService.get(dto));
    }

    /**
     * 首页-焦碳比例
     * @param listHomeCokeProportionDTO
     * @return
     */
    @Operation(summary = "首页-焦碳比例",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ListHomeCokeProportionVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "首页-焦碳比例",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getHomeCokeProportionList", method = RequestMethod.GET)
    public AjaxResult getHomeCokeProportionListData(@Validated @ParamModel ListHomeCokeProportionDTO listHomeCokeProportionDTO) {
        log.info("params => " + listHomeCokeProportionDTO);
        String errorMsg = ValidUtil.checkValid(listHomeCokeProportionDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listHomeCokeProportionDTO);
        return AjaxResult.success(productionCokeRecordsService.getHomeCokeProportionListData(listHomeCokeProportionDTO));
    }
}
