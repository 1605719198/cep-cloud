package com.jlkj.material.mr.controller;

import com.jlkj.common.datascope.annotation.ParamModel;

import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.ValidUtil;
import com.jlkj.material.mr.dto.materialsstoragespaces.*;
import com.jlkj.material.mr.service.MaterialsStorageSpacesService;
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
 * 控制器-储位维护表
 * @author sudeyou
 */
@Tag(name = "储位维护表")
@RestController
@RequestMapping("/parameter")
@Slf4j
public class MaterialsStorageSpacesController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private MaterialsStorageSpacesService materialsStorageSpacesService;

    @Operation(summary = "查询-分页-储位维护",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "silo_id", description = "料仓编号"),
                    @Parameter(name = "current", description = "页码-从1开始"),
                    @Parameter(name = "size", description = "每页行数"),
                    @Parameter(name = "order", description = "排序字段"),
                    @Parameter(name = "orderby", description = "排序规则字段")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "silo_id", description = "料仓编号"),
                                    @ExampleObject(name = "silo_name", description = "料仓名称"),
                                    @ExampleObject(name = "storage_spaces_name", description = "储位名称"),
                                    @ExampleObject(name = "create_user_id", description = "创建人"),
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

    @Log(title = "查询-分页-储位维护", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listStorageSpaces", method = RequestMethod.GET)
    public Object getPageData(@Validated @ParamModel PageMaterialsStorageSpacesDTO pageMaterialsStorageSpacesDTO) {
        log.info("params => " + pageMaterialsStorageSpacesDTO);
        String errorMsg = ValidUtil.checkValid(pageMaterialsStorageSpacesDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageMaterialsStorageSpacesDTO);
        return AjaxResult.success(materialsStorageSpacesService.getPageData(pageMaterialsStorageSpacesDTO));
    }

    @Operation(summary = "查询-列表-储位",
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
                                    @ExampleObject(name = "silo_name", description = "料仓名称"),
                                    @ExampleObject(name = "storage_spaces_name", description = "储位名称"),
                                    @ExampleObject(name = "create_user_id", description = "创建人"),
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

    @Log(title = "查询-列表-储位", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listStorageSpacesBySiloId", method = RequestMethod.GET)
    public Object getListData(@Validated @ParamModel ListMaterialsStorageSpacesDTO listMaterialsStorageSpacesDTO) {
        log.info("params => " + listMaterialsStorageSpacesDTO);
        String errorMsg = ValidUtil.checkValid(listMaterialsStorageSpacesDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listMaterialsStorageSpacesDTO);
        return materialsStorageSpacesService.getListData(listMaterialsStorageSpacesDTO);
    }

    @Operation(summary = "新增-储位维护",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "silo_id", description = "料仓编号"),
                            @ExampleObject(name = "storage_spaces_name", description = "储位名称"),
                            @ExampleObject(name = "create_user_id", description = "创建人"),
                            @ExampleObject(name = "create_user_name", description = "创建人"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "新增-储位维护", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/saveStorageSpaces", method = RequestMethod.POST, produces = "application/json")
    public Object addData(@Valid @RequestBody AddMaterialsStorageSpacesDTO addMaterialsStorageSpacesDTO) {
        log.info("params => " + addMaterialsStorageSpacesDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, addMaterialsStorageSpacesDTO);
        return materialsStorageSpacesService.addData(addMaterialsStorageSpacesDTO);
    }

    @Operation(summary = "修改-储位维护",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键标识;UUID"),
                            @ExampleObject(name = "silo_id", description = "料仓编号"),
                            @ExampleObject(name = "storage_spaces_name", description = "储位名称"),
                            @ExampleObject(name = "modify_user_id", description = "修改人id"),
                            @ExampleObject(name = "modify_user_name", description = "修改人"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "修改-储位维护", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/updateStorageSpaces", method = RequestMethod.PUT, produces = "application/json")
    public Object updateData(@Valid @RequestBody UpdateMaterialsStorageSpacesDTO updateMaterialsStorageSpacesDTO) {
        log.info("params => " + updateMaterialsStorageSpacesDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateMaterialsStorageSpacesDTO);
        return materialsStorageSpacesService.updateData(updateMaterialsStorageSpacesDTO);
    }

    @Operation(summary = "删除-储位维护",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键标识;UUID"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "删除-储位维护", businessType = BusinessType.DELETE)
    @RequestMapping(value = "delStorageSpaces", method = RequestMethod.DELETE, produces = "application/json")
    public Object deleteData(@Valid @RequestBody DeleteMaterialsStorageSpacesDTO deleteMaterialsStorageSpacesDTO) {
        log.info("params => " + deleteMaterialsStorageSpacesDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteMaterialsStorageSpacesDTO);
        return materialsStorageSpacesService.deleteData(deleteMaterialsStorageSpacesDTO);
    }

    @Operation(summary = "查询-列表-储位-下拉",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "storage_spaces_name", description = "储位名称"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询-列表-储位-下拉", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listMaterialsStorageSpacesBox", method = RequestMethod.GET)
    public Object getListDataBox() {
        return materialsStorageSpacesService.getListDataBox();
    }
}

