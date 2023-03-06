package com.jlkj.material.mr.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.core.utils.ValidUtil;
import com.jlkj.material.mr.dto.materialssiloarea.DeleteMaterialsSiloAreaDTO;
import com.jlkj.material.mr.dto.materialssiloarea.ListMaterialsSiloAreaDTO;
import com.jlkj.material.mr.dto.materialssiloarea.SaveMaterialsSiloAreaDTO;
import com.jlkj.material.mr.service.MaterialsSiloAreaService;
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

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * 控制器-料仓区域维护
 * @author sudeyou
 */
@Tag(name = "料仓区域维护")
@RestController
@RequestMapping("/storageSpaces")
@Slf4j
public class MaterialsSiloAreaController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private MaterialsSiloAreaService materialsSiloAreaService;

    @Operation(summary = "查询-列表-料仓区域维护",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "silo_id", description = "料仓编号")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "silo_id", description = "料仓编号"),
                                    @ExampleObject(name = "storage_spaces_id", description = "储位编号"),
                                    @ExampleObject(name = "storage_spaces_name", description = "储位名称"),
                                    @ExampleObject(name = "start_location", description = "起始位置"),
                                    @ExampleObject(name = "end_location", description = "结束位置"),
                                    @ExampleObject(name = "color_value", description = "颜色值"),
                                    @ExampleObject(name = "materials_id", description = "物料编号"),
                                    @ExampleObject(name = "materials_name", description = "物料名称"),
                                    @ExampleObject(name = "storage_quantity", description = "储量"),
                                    @ExampleObject(name = "create_user_id", description = "创建人id"),
                                    @ExampleObject(name = "create_user_name", description = "创建人"),
                                    @ExampleObject(name = "create_time", description = "创建时间"),
                                    @ExampleObject(name = "modify_user_id", description = "修改人id"),
                                    @ExampleObject(name = "modify_user_name", description = "修改人"),
                                    @ExampleObject(name = "modify_time", description = "修改时间"),
                                    @ExampleObject(name = "material_category_id", description = "物料类别id")
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "查询-列表-料仓区域维护", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listMaterialsSiloArea", method = RequestMethod.GET)
    public Object getPageData(@Validated @ParamModel ListMaterialsSiloAreaDTO listMaterialsSiloAreaDTO) {
        log.info("params => " + listMaterialsSiloAreaDTO);
        String errorMsg = ValidUtil.checkValid(listMaterialsSiloAreaDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listMaterialsSiloAreaDTO);
        return materialsSiloAreaService.getListData(listMaterialsSiloAreaDTO);
    }

    @Operation(summary = "保存-料仓区域维护",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "silo_id", description = "料仓编号"),
                            @ExampleObject(name = "create_user_id", description = "创建人id"),
                            @ExampleObject(name = "create_user_name", description = "创建人"),
                            @ExampleObject(name = "storage_spaces", description = "储位列表[{storage_spaces_id:储位编号,start_location:起始位置,end_location:结束位置,materials_id:物料编号,materials_name:物料名称,color_value:颜色值}]")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "保存-料仓区域维护", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/saveMaterialsSiloArea", method = RequestMethod.PUT, produces = "application/json")
    public Object updateData(@Valid @RequestBody SaveMaterialsSiloAreaDTO saveMaterialsSiloAreaDTO) {
        log.info("params => " + saveMaterialsSiloAreaDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, saveMaterialsSiloAreaDTO);
        return materialsSiloAreaService.updateData(saveMaterialsSiloAreaDTO);
    }

    @Operation(summary = "料仓区域维护-删除",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "料仓区域维护-删除", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/deleteMaterialsSiloArea", method = RequestMethod.DELETE, produces = "application/json")
    public Object deleteMaterialsSiloAreaData(@Valid @RequestBody DeleteMaterialsSiloAreaDTO deleteMaterialsSiloAreaDTO) {
        log.info("params => " + deleteMaterialsSiloAreaDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteMaterialsSiloAreaDTO);
        return materialsSiloAreaService.deleteMaterialsSiloAreaData(deleteMaterialsSiloAreaDTO);
    }
}

