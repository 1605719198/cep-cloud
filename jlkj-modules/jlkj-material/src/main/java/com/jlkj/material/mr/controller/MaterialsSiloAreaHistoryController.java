package com.jlkj.material.mr.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.core.utils.ValidUtil;
import com.jlkj.material.mr.dto.materialssiloareahistory.PageMaterialsSiloAreaHistoryDTO;
import com.jlkj.material.mr.service.MaterialsSiloAreaHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * 控制器-料仓区域历史
 * @author sudeyou
 */
@Tag(name = "料仓区域历史")
@RestController
@RequestMapping("/materialssiloareahistory")
@Slf4j
public class MaterialsSiloAreaHistoryController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private MaterialsSiloAreaHistoryService materialsSiloAreaHistoryService;

    @Operation(summary = "查询-分页-料仓区域历史",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
                    @Parameter(name = "silo_id", description = "料仓编号"),
                    @Parameter(name = "storage_spaces_id", description = "储位编号"),
                    @Parameter(name = "current", description = "页码-从1开始"),
                    @Parameter(name = "size", description = "每页行数"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(examples = {
                                    @ExampleObject(name = "id", description = "主键标识;UUID"),
                                    @ExampleObject(name = "silo_id", description = "料仓编号"),
                                    @ExampleObject(name = "storage_spaces_id", description = "储位编号"),
                                    @ExampleObject(name = "materials_id", description = "物料编号"),
                                    @ExampleObject(name = "materials_name", description = "物料名称"),
                                    @ExampleObject(name = "start_time", description = "开始时间"),
                                    @ExampleObject(name = "end_time", description = "结束时间"),
                            })
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )

    @Log(title = "查询-分页-料仓区域历史", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getPageMaterialsSiloAreaHistory", method = RequestMethod.GET)
    public Object getPageData(@Validated @ParamModel PageMaterialsSiloAreaHistoryDTO pageMaterialsSiloAreaHistoryDTO) {
        log.info("params => " + pageMaterialsSiloAreaHistoryDTO);
        String errorMsg = ValidUtil.checkValid(pageMaterialsSiloAreaHistoryDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageMaterialsSiloAreaHistoryDTO);
        return AjaxResult.success(materialsSiloAreaHistoryService.getPageData(pageMaterialsSiloAreaHistoryDTO));
    }


}

