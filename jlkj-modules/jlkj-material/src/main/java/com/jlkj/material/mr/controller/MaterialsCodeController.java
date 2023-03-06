package com.jlkj.material.mr.controller;

import com.jlkj.common.datascope.annotation.ParamModel;

import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.ValidUtil;
import com.jlkj.material.mr.dto.materialscode.*;
import com.jlkj.material.mr.service.MaterialsCodeService;
import com.jlkj.material.mr.vo.materialscode.ListMaterialsCodeByTypeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * 控制器-物料代码维护
 * @author sudeyou
 */
@Tag(name = "物料代码维护")
@RestController
@RequestMapping("/parameter")
@Slf4j
public class MaterialsCodeController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private MaterialsCodeService materialsCodeService;

    @Operation(summary = "查询-分页-物料代码维护",
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
                                    @ExampleObject(name = "category_id", description = "类别编号"),
                                    @ExampleObject(name = "category_name", description = "类别名称"),
                                    @ExampleObject(name = "materials_name", description = "物料名称"),
                                    @ExampleObject(name = "materials_code", description = "物料代码"),
                                    @ExampleObject(name = "measurement_unit", description = "计量单位"),
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

    @Log(title = "查询-分页-物料代码维护", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listMaterials", method = RequestMethod.GET)
    public Object getPageData(@Validated @ParamModel PageMaterialsCodeDTO pageMaterialsCodeDTO) {
        log.info("params => " + pageMaterialsCodeDTO);
        String errorMsg = ValidUtil.checkValid(pageMaterialsCodeDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageMaterialsCodeDTO);
        return AjaxResult.success(materialsCodeService.getPageData(pageMaterialsCodeDTO));
    }

    @Operation(summary = "查询-列表-物料代码-下拉",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "materials_name", description = "物料名称"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "查询-列表-物料代码-下拉", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listMaterialsBox", method = RequestMethod.GET)
    public Object getListBoxData() {
        return materialsCodeService.getListBoxData();
    }

    @Operation(summary = "查询-列表-物料代码-下拉-指定物料类别id",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "category_id", description = "物料类别id"),
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "materials_name", description = "物料名称"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "查询-列表-物料代码-下拉-指定物料类别id", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listMaterialsBoxByCataId", method = RequestMethod.GET)
    public Object getListBoxByCataIdData(@Validated @ParamModel ListMaterialsCodeDTO listMaterialsCodeDTO) {
        log.info("params => " + listMaterialsCodeDTO);
        String errorMsg = ValidUtil.checkValid(listMaterialsCodeDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listMaterialsCodeDTO);
        return materialsCodeService.getListBoxByCataIdData(listMaterialsCodeDTO);
    }

    @Operation(summary = "查询-列表-物料代码-下拉-煤",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "materials_name", description = "物料名称"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询-列表-物料代码-下拉-煤", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listMaterialsBoxM", method = RequestMethod.GET)
    public Object getListBoxDataM() {
        return materialsCodeService.getListBoxDataM();
    }


    @Operation(summary = "查询-列表-物料代码-下拉-焦",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "materials_name", description = "物料名称"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询-列表-物料代码-下拉-焦", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/listMaterialsBoxJ", method = RequestMethod.GET)
    public Object getListBoxDataJ() {
        return materialsCodeService.getListBoxDataJ();
    }

    @Operation(summary = "新增-物料代码维护",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                                    @ExampleObject(name = "category_id", description = "类别编号"),
                                    @ExampleObject(name = "materials_name", description = "物料名称"),
                                    @ExampleObject(name = "materials_code", description = "物料代码"),
                                    @ExampleObject(name = "measurement_unit", description = "计量单位"),
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
    @Log(title = "新增-物料代码维护", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/saveMaterials", method = RequestMethod.POST, produces = "application/json")
    public Object addData(@Valid @RequestBody AddMaterialsCodeDTO addMaterialsCodeDTO) {
        log.info("params => " + addMaterialsCodeDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, addMaterialsCodeDTO);
        return materialsCodeService.addData(addMaterialsCodeDTO);
    }

    @Operation(summary = "修改-物料代码维护",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "category_id", description = "类别编号"),
                                    @ExampleObject(name = "materials_name", description = "物料名称"),
                                    @ExampleObject(name = "materials_code", description = "物料代码"),
                                    @ExampleObject(name = "measurement_unit", description = "计量单位"),
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
    @Log(title = "修改-物料代码维护", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/updateMaterials", method = RequestMethod.PUT, produces = "application/json")
    public Object updateData(@Valid @RequestBody UpdateMaterialsCodeDTO updateMaterialsCodeDTO) {
        log.info("params => " + updateMaterialsCodeDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateMaterialsCodeDTO);
        return materialsCodeService.updateData(updateMaterialsCodeDTO);
    }

    @Operation(summary = "删除-物料代码维护表",
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
    @Log(title = "删除-物料代码维护表", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/delMaterialsCode", method = RequestMethod.DELETE, produces = "application/json")
    public Object deleteData(@Valid @RequestBody DeleteMaterialsCodeDTO deleteMaterialsCodeDTO) {
        log.info("params => " + deleteMaterialsCodeDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteMaterialsCodeDTO);
        return materialsCodeService.deleteData(deleteMaterialsCodeDTO);
    }

    @Operation(summary = "物料代码下拉列表-类型(煤/焦)",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ListMaterialsCodeByTypeVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "查询物料代码下拉列表-类型(煤/焦)", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getMaterialsCodeListByType", method = RequestMethod.GET)
    public Object getMaterialsCodeListByTypeData(@Validated @ParamModel ListMaterialsCodeByTypeDTO listMaterialsCodeByTypeDTO) {
        log.info("params => " + listMaterialsCodeByTypeDTO);
        String errorMsg = ValidUtil.checkValid(listMaterialsCodeByTypeDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listMaterialsCodeByTypeDTO);
        return AjaxResult.success(materialsCodeService.getMaterialsCodeListByTypeData(listMaterialsCodeByTypeDTO));
    }
}

