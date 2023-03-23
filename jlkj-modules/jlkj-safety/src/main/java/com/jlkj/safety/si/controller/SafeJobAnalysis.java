package com.jlkj.safety.si.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jlkj.common.datascope.annotation.ParamModel;


import com.jlkj.common.log.annotation.Log;


import com.jlkj.common.core.utils.ValidUtil;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.safety.si.dto.safeindex.SafeAnalysisListDTO;
import com.jlkj.safety.si.service.impl.SafeIndexServiceImpl;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;

/**
 * @author zyf
 * @Description
 * @create 2022-07-27 9:41
 */
@Api(tags = "安全作业分析")
@RestController
@RequestMapping("/safe/safeAnalysis")
@Slf4j
public class SafeJobAnalysis {

    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    SafeIndexServiceImpl safeIndexService;


    @ApiOperation(value = "安全作业分析-查询列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "yearmonth", value = "年或年月"),
            @ApiImplicitParam(name = "safe_area_id", value = "区域id"),
            @ApiImplicitParam(name = "type", value = "类型:动火作业票、高处作业票、吊装作业票、有限空间作业票、动土作业票、抽堵盲板作业票、断路安全作业票、临时用电作业票"),
            @ApiImplicitParam(name = "current", value = "页码-从1开始"),
            @ApiImplicitParam(name = "size", value = "每页行数"),
            @ApiImplicitParam(name = "order", value = "排序字段"),
            @ApiImplicitParam(name = "orderby", value = "排序规则字段")
    })
    @ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "datetime:日期\n" +
                                    "safe_area_name:作业区\n" +
                                    "type:作业票类型\n" +
                                    "appr:审批中\n" +
                                    "working:作业中\n" +
                                    "done:已验收\n"
                            )
                    )
            )
    )
    @Log(title = "业务操作-查询", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    //@RoleAndPermission(permission = "safeAnalysis_list")
    public Object list(@Validated @ParamModel SafeAnalysisListDTO dto) {
        log.info("params => " + dto);
        String errorMsg = ValidUtil.checkValid(dto);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        IPage<Map<String, String>> list = safeIndexService.getAnalysisListPage(dto);
        return AjaxResult.success(list);
    }

    @ApiOperation(value = "安全作业分析-查询饼图", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "yearmonth", value = "年或年月")
    })
    @ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "value:数量\n" +
                                    "name:名称\n"
                            )
                    )
            )
    )
    @Log(title = "业务操作-查询", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/pie", method = RequestMethod.GET)
    public Object pie(@ParamModel SafeAnalysisListDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        List<Map<String, String>> list = safeIndexService.getAnalysisPie(dto);
        return AjaxResult.success(list);
    }

    @ApiOperation(value = "安全作业分析-查询折线图", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "yearmonth", value = "年份")
    })
    @ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "name:数量\n" +
                                    "type:line\n" +
                                    "stack:total\n" +
                                    "data:统计集合\n"
                            )
                    )
            )
    )
    @Log(title = "业务操作-查询", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/line", method = RequestMethod.GET)
    //@RoleAndPermission(permission = "safeAnalysis_line")
    public Object line(@ParamModel SafeAnalysisListDTO dto) {
        log.info("params => " + dto);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, dto);
        return AjaxResult.success(safeIndexService.getAnalysisLine(dto));
    }

    @ApiOperation(value = "安全看板-进行中作业票统计", notes = "")
    @ApiImplicitParams({

    })
    @ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "value:数量\n" +
                                    "name:作业票名称\n"
                            )
                    )
            )
    )
    @Log(title = "业务操作-查询", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/getAnalysisBoardTicketTotal", method = RequestMethod.GET)
    public Object pie() {
        log.info("params => " + "");
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, "");
        List<Map<String, String>> list = safeIndexService.getAnalysisBoardTicketTotal();
        return AjaxResult.success(list);
    }
}
