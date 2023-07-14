package com.jlkj.material.mr.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jlkj.common.core.constant.HttpStatus;
import com.jlkj.common.core.utils.ValidUtil;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.material.mr.domain.MaterialsCodeSmall;
import com.jlkj.material.mr.dto.materialscodesmall.PageMaterialsCodeSmallDTO;
import com.jlkj.material.mr.dto.materialscodesmall.UpdateMaterialsCodeSmallDTO;
import com.jlkj.material.mr.service.MaterialsCodeSmallService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author yzl
 * @Description 物料小煤种
 * @create 2023-03-22 09:27:37.931375
 */
@Tag(name = "物料小煤种")
@RestController
@RequestMapping("/materialsCodeSmall")
@Slf4j
public class MaterialsCodeSmallController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    MaterialsCodeSmallService materialsCodeSmallService;

    @Operation(summary = "查询物料小煤种",
            parameters = {
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
                                    @ExampleObject(name = "category_code", description = "物料类别"),
                                    @ExampleObject(name = "category_name", description = "物料类别名称"),
                                    @ExampleObject(name = "materials_code", description = "物料代码"),
                                    @ExampleObject(name = "materials_name", description = "物料名称"),
                                    @ExampleObject(name = "materials_small_code", description = "物料小煤种代码"),
                                    @ExampleObject(name = "materials_small_name", description = "物料小煤种简称(煤管手动维护)"),
                                    @ExampleObject(name = "vendor_no", description = "供应商编码"),
                                    @ExampleObject(name = "vendor_name", description = "供应商名称"),
                                    @ExampleObject(name = "send_vendor_no", description = "发货地编码"),
                                    @ExampleObject(name = "send_vendor_name", description = "发货地名称"),
                                    @ExampleObject(name = "measurement_unit", description = "计量单位"),
                                    @ExampleObject(name = "mtrl_type", description = "00：煤   01：焦炭"),
                                    @ExampleObject(name = "delete_flag", description = "删除标识"),
                                    @ExampleObject(name = "create_user_id", description = "创建人"),
                                    @ExampleObject(name = "create_user_name", description = "创建人"),
                                    @ExampleObject(name = "create_time", description = "创建时间"),
                                    @ExampleObject(name = "modify_user_id", description = "修改人id"),
                                    @ExampleObject(name = "modify_user_name", description = "修改人"),
                                    @ExampleObject(name = "modify_time", description = "修改时间"),
                                    @ExampleObject(name = "size", description = "10"),
                                    @ExampleObject(name = "current", description = "1")
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "物料小煤种-查询", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object list(@Validated @ParamModel PageMaterialsCodeSmallDTO pageMaterialsCodeSmallDTO) {
        log.info("params => " + pageMaterialsCodeSmallDTO);
        String errorMsg = ValidUtil.checkValid(pageMaterialsCodeSmallDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageMaterialsCodeSmallDTO);
        IPage<Map<String, String>> list = materialsCodeSmallService.getListPage(pageMaterialsCodeSmallDTO);
        return AjaxResult.success(list);
    }



    @Operation(summary = "修改",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                    @Content(examples = {
                            @ExampleObject(name = "id", description = "主键"),
                            @ExampleObject(name = "materials_small_name", description = "物料小煤种简称(煤管手动维护)"),
                            @ExampleObject(name = "modify_user_id", description = "修改人id"),
                            @ExampleObject(name = "modify_user_name", description = "修改人")
                    })
            }),
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败[具体原因]"),
                    @ApiResponse(responseCode = "0", description = "成功"),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "修改物料小煤种", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    public Object update(@Valid @RequestBody UpdateMaterialsCodeSmallDTO updateMaterialsCodeSmallDTO) {
        log.info("params => " + updateMaterialsCodeSmallDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateMaterialsCodeSmallDTO);
        MaterialsCodeSmall materialsCodeSmall = materialsCodeSmallService.getById(updateMaterialsCodeSmallDTO.getId());
        if (null != materialsCodeSmall) {
            materialsCodeSmall.setMaterialsSmallName(updateMaterialsCodeSmallDTO.getMaterialsSmallName());
            materialsCodeSmall.setModifyUserId(updateMaterialsCodeSmallDTO.getModifyUserId());
            materialsCodeSmall.setModifyUserName(updateMaterialsCodeSmallDTO.getModifyUserName());
            materialsCodeSmall.setModifyTime(new Date());
            materialsCodeSmallService.updateById(materialsCodeSmall);
            return AjaxResult.success("物料小煤种修改成功");
        } else {
            return AjaxResult.error(HttpStatus.ERROR, "物料小煤种不存在");
        }
    }


    /**
     * 物料小煤种-下拉列表
     * @return
     */
    @Operation(summary = "物料小煤种-下拉列表",
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "物料小煤种-下拉列表", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getMaterialsCodeSmallDic", method = RequestMethod.GET)
    public AjaxResult getMaterialsCodeSmallDic() {
        return AjaxResult.success(materialsCodeSmallService.getMaterialsCodeSmallDic());
    }
}

