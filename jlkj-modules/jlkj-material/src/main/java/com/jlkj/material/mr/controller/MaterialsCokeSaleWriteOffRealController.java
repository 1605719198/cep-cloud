package com.jlkj.material.mr.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jlkj.common.datascope.annotation.ParamModel;

import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.material.mr.dto.materialscokesalewriteoffreal.DeleteMaterialsCokeSaleWriteOffRealDTO;
import com.jlkj.material.mr.dto.materialscokesalewriteoffreal.InsertMaterialsCokeSaleWriteOffRealDTO;
import com.jlkj.material.mr.dto.materialscokesalewriteoffreal.ListHomeCokeSaleWriteOffRealDTO;
import com.jlkj.material.mr.dto.materialscokesalewriteoffreal.PageMaterialsCokeSaleWriteOffRealDto;
import com.jlkj.material.mr.service.MaterialsCokeSaleWriteOffRealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
import java.util.List;
import java.util.Map;

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * 控制器-焦炭外售销账实际
 * @author sudeyou
 * @since 2022-08-19 13:58:42
 */
@Tag(name = "焦炭外售销账实际")
@RestController
@RequestMapping("/cokesalewriteoffreal")
@Slf4j
public class MaterialsCokeSaleWriteOffRealController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private MaterialsCokeSaleWriteOffRealService materialsCokeSaleWriteOffRealService;

    @Operation(summary = "焦炭外售销账实际-新增",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "焦炭外售销账实际-新增", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/insertMaterialsCokeSaleWriteOffReal", method = RequestMethod.POST, produces = "application/json")
    public Object insertMaterialsCokeSaleWriteOffRealData(@Valid @RequestBody InsertMaterialsCokeSaleWriteOffRealDTO insertMaterialsCokeSaleWriteOffRealDTO) {
        log.info("params => " + insertMaterialsCokeSaleWriteOffRealDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, insertMaterialsCokeSaleWriteOffRealDTO);
        return materialsCokeSaleWriteOffRealService.insertMaterialsCokeSaleWriteOffRealData(insertMaterialsCokeSaleWriteOffRealDTO);
    }

    @Operation(summary = "焦炭外售销账实际表-删除",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "焦炭外售销账实际表-删除", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/deleteMaterialsCokeSaleWriteOffReal", method = RequestMethod.POST, produces = "application/json")
    public Object deleteMaterialsCokeSaleWriteOffRealData(@Valid @RequestBody DeleteMaterialsCokeSaleWriteOffRealDTO deleteMaterialsCokeSaleWriteOffRealDTO) {
        log.info("params => " + deleteMaterialsCokeSaleWriteOffRealDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteMaterialsCokeSaleWriteOffRealDTO);
        return materialsCokeSaleWriteOffRealService.deleteMaterialsCokeSaleWriteOffRealData(deleteMaterialsCokeSaleWriteOffRealDTO);
    }



    @Operation(summary = "焦炭外售销账实际表-列表",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询焦炭外售销账实际表-列表", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@Validated @ParamModel PageMaterialsCokeSaleWriteOffRealDto dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        IPage<Map<String, String>> list = materialsCokeSaleWriteOffRealService.getListPage(dto);
        return AjaxResult.success(list);
    }


    @Operation(summary = "焦炭外发-首页-图表",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "start_date", description = "开始日期"),
                    @Parameter(name = "end_date", description = "结束日期")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "query_date", description = "日期"),
                                    @ExampleObject(name = "net_wgt", description = "外售实绩"),
                                    @ExampleObject(name = "complete_pound_num", description = "耗用量"),
                            })),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询焦炭外发-首页-图表", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getHomeCokeOut", method = RequestMethod.GET)
    public Object getHomeCokeOut(@Validated @ParamModel ListHomeCokeSaleWriteOffRealDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        List<Map<String, String>> list = materialsCokeSaleWriteOffRealService.getHomeCokeOut(dto);
        return AjaxResult.success(list);
    }



}

