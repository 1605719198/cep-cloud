package com.jlkj.material.mr.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;

import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.material.mr.dto.materialscokesaledeliverplan.DeleteMaterialsCokeSaleDeliverPlanDTO;
import com.jlkj.material.mr.dto.materialscokesaledeliverplan.InsertMaterialsCokeSaleDeliverPlanDTO;
import com.jlkj.material.mr.dto.materialscokesaledeliverplan.PageMaterialsCokeSaleDeliverPlanDTO;
import com.jlkj.material.mr.service.MaterialsCokeSaleDeliverPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * 控制器-焦炭外售发货计划
 *
 * @author sudeyou
 * @since 2022-08-19 14:00:15
 */
@Tag(name = "焦炭外售发货计划")
@RestController
@RequestMapping("/cokesaledeliverplan")
@Slf4j
public class MaterialsCokeSaleDeliverPlanController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private MaterialsCokeSaleDeliverPlanService materialsCokeSaleDeliverPlanService;

    @Operation(summary = "焦炭外售发货计划-新增",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "焦炭外售发货计划-新增", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/insertMaterialsCokeSaleDeliverPlan", method = RequestMethod.POST, produces = "application/json")
    public Object insertMaterialsCokeSaleDeliverPlanData(@Valid @RequestBody InsertMaterialsCokeSaleDeliverPlanDTO insertMaterialsCokeSaleDeliverPlanDTO) {
        log.info("params => " + insertMaterialsCokeSaleDeliverPlanDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, insertMaterialsCokeSaleDeliverPlanDTO);
        return materialsCokeSaleDeliverPlanService.insertMaterialsCokeSaleDeliverPlanData(insertMaterialsCokeSaleDeliverPlanDTO);
    }

    @Operation(summary = "焦炭外售发货计划-删除",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "焦炭外售发货计划-删除", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/deleteMaterialsCokeSaleDeliverPlan", method = RequestMethod.POST, produces = "application/json")
    public Object deleteMaterialsCokeSaleDeliverPlanData(@Valid @RequestBody DeleteMaterialsCokeSaleDeliverPlanDTO deleteMaterialsCokeSaleDeliverPlanDTO) {
        log.info("params => " + deleteMaterialsCokeSaleDeliverPlanDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteMaterialsCokeSaleDeliverPlanDTO);
        return materialsCokeSaleDeliverPlanService.deleteMaterialsCokeSaleDeliverPlanData(deleteMaterialsCokeSaleDeliverPlanDTO);
    }


    @Operation(summary = "焦炭外售发货计划-列表",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "焦炭外售发货计划列表-删除", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@Validated @ParamModel PageMaterialsCokeSaleDeliverPlanDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        IPage<Map<String, String>> list = materialsCokeSaleDeliverPlanService.getListPage(dto);
        return AjaxResult.success(list);
    }


}

