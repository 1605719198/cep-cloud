package com.jlkj.material.mr.controller;

import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.ValidUtil;
import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;
import com.jlkj.material.mr.dto.materialscategory.AddMaterialsCategoryDTO;
import com.jlkj.material.mr.dto.materialscategory.DeleteMaterialsCategoryDTO;
import com.jlkj.material.mr.dto.materialscategory.PageMaterialsCategoryDTO;
import com.jlkj.material.mr.dto.materialscategory.UpdateMaterialsCategoryDTO;
import com.jlkj.material.mr.service.MaterialsCategoryService;
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


/**
 * 控制器-物料类别维护
 * @author sudeyou
 */
@Tag(name = "物料类别维护")
@RestController
@RequestMapping("/parameter")
@Slf4j
public class MaterialsCategoryController {
    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private MaterialsCategoryService materialsCategoryService;

    @Operation(summary = "查询-分页-物料类别维护",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
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
                                    @ExampleObject(name = "category_name", description = "类别名称"),
                                    @ExampleObject(name = "category_code", description = "类别代码"),
                                    @ExampleObject(name = "delete_flag", description = "删除标识"),
                                    @ExampleObject(name = "create_user_id", description = "创建人"),
                                    @ExampleObject(name = "create_user_name", description = "创建人"),
                                    @ExampleObject(name = "create_time", description = "创建时间"),
                                    @ExampleObject(name = "modify_user_id", description = "修改人id"),
                                    @ExampleObject(name = "modify_user_name", description = "修改人"),
                                    @ExampleObject(name = "modify_time", description = "修改时间"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "查询-分页-物料类别维护", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listMaterialsCategory", method = RequestMethod.GET)
    public Object getPageData(@Validated @ParamModel PageMaterialsCategoryDTO pageMaterialsCategoryDTO) {
        log.info("params => " + pageMaterialsCategoryDTO);
        String errorMsg = ValidUtil.checkValid(pageMaterialsCategoryDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageMaterialsCategoryDTO);
        return AjaxResult.success(materialsCategoryService.getPageData(pageMaterialsCategoryDTO));
    }

    @Operation(summary = "查询-列表-物料类别-下拉",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "category_name", description = "类别名称"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "查询-列表-物料类别-下拉", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listMaterialsCategoryBox", method = RequestMethod.GET)
    public Object getListBoxData() {
        return materialsCategoryService.getListBoxData();
    }

    @Operation(summary = "新增-物料类别维护",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "category_name", description = "类别名称"),
                            @ExampleObject(name = "category_code", description = "类别代码"),
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
    @Log(title = "新增-物料类别维护", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/saveMaterialsCategory", method = RequestMethod.POST, produces = "application/json")
    public Object addData(@Valid @RequestBody AddMaterialsCategoryDTO addMaterialsCategoryDTO) {
        log.info("params => " + addMaterialsCategoryDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, addMaterialsCategoryDTO);
        return materialsCategoryService.addData(addMaterialsCategoryDTO);
    }

    @Operation(summary = "修改-物料类别维护",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键标识;UUID"),
                            @ExampleObject(name = "category_name", description = "类别名称"),
                            @ExampleObject(name = "category_code", description = "类别代码"),
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
    @Log(title = "修改-物料类别维护", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/updateMaterialsCategory", method = RequestMethod.PUT, produces = "application/json")
    public Object updateData(@Valid @RequestBody UpdateMaterialsCategoryDTO updateMaterialsCategoryDTO) {
        log.info("params => " + updateMaterialsCategoryDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateMaterialsCategoryDTO);
        return materialsCategoryService.updateData(updateMaterialsCategoryDTO);
    }

    @Operation(summary = "删除-物料类别维护",
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
    @Log(title = "删除-物料类别维护", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/deleteMaterialsCategory", method = RequestMethod.DELETE, produces = "application/json")
    public Object deleteData(@Valid @RequestBody DeleteMaterialsCategoryDTO deleteMaterialsCategoryDTO) {
        log.info("params => " + deleteMaterialsCategoryDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteMaterialsCategoryDTO);
        return materialsCategoryService.deleteData(deleteMaterialsCategoryDTO);
    }
}

