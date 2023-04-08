package com.jlkj.product.oi.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.datascope.annotation.ParamModel;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.core.web.resp.ValidUtil;
import com.jlkj.product.oi.dto.sysdictdata.ListSysDictDataDTO;
import com.jlkj.product.oi.service.SysDictDataService;
import com.jlkj.product.oi.vo.sysdictdata.ListSysDictDataVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.jlkj.product.oi.constants.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * 控制器-字典数据表
 *
 * @author sudeyou
 * @since 2022-11-02 09:46:29
 */
@Tag(name = "字典数据表")
@RestController
@RequestMapping("/sysdictdata")
@Slf4j
public class SysDictDataController {

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private SysDictDataService sysDictDataService;

    @Operation(summary = "获取字典数据列表",
            parameters = {
                    @Parameter(name = "token", in = ParameterIn.HEADER, description = "token"),
            },
            responses = {
                    @ApiResponse(responseCode = "-1", description = "失败"),
                    @ApiResponse(responseCode = "0", description = "成功",
                            content = @Content(schema = @Schema(implementation = ListSysDictDataVO.class))
                    ),
                    @ApiResponse(responseCode = "406", description = "校验失败内容")
            }
    )
    @Log(title = "获取字典数据列表",businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getSysDictDataList", method = RequestMethod.GET)
    public Object getSysDictDataListData(@Validated @ParamModel ListSysDictDataDTO listSysDictDataDTO) {
        log.info("params => " + listSysDictDataDTO);
        String errorMsg = ValidUtil.checkValid(listSysDictDataDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, listSysDictDataDTO);
        return AjaxResult.success(sysDictDataService.getSysDictDataListData(listSysDictDataDTO));
    }
}

