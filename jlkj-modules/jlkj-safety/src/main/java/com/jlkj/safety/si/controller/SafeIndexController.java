package com.jlkj.safety.si.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jlkj.common.core.constant.HttpStatus;
import com.jlkj.common.datascope.annotation.ParamModel;


import com.jlkj.common.log.annotation.Log;

import com.jlkj.common.core.utils.ValidUtil;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.safety.si.dto.safeindex.AddSafeIndexDTO;
import com.jlkj.safety.si.dto.safeindex.DeleteSafeIndexDTO;
import com.jlkj.safety.si.dto.safeindex.PageSafeIndexDTO;
import com.jlkj.safety.si.dto.safeindex.UpdateSafeIndexDTO;
import com.jlkj.safety.si.entity.SafeIndex;
import com.jlkj.safety.si.service.SafeIndexService;
import com.jlkj.safety.si.utils.ResponseUtil;
import io.swagger.annotations.*;
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
import java.util.Map;

import static com.jlkj.common.core.constant.SysLogConstant.SYS_LOG_PARAM_KEY;


/**
 * @author yzl
 * @Description 环保指标项
 * @create 2022年7月19日 08:51:54
 */
@Api(tags = "环保指标项")
@RestController
@RequestMapping("/safe/safeIndex")
@Slf4j
public class SafeIndexController {
    @Autowired
    HttpServletRequest httpServletRequest;
    @Autowired
    SafeIndexService safeIndexService;


    @ApiOperation(value = "环保指标项-查询列表", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "页码-从1开始"),
            @ApiImplicitParam(name = "size", value = "每页行数"),
            @ApiImplicitParam(name = "order", value = "排序字段"),
            @ApiImplicitParam(name = "orderby", value = "排序规则字段")
    })
    @ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 0, message = "调用成功", response = String.class,
                    examples = @Example(
                            @ExampleProperty(
                                    "id:主键标识\n" +
                                            "rule_number:规则编号\n" +
                                            "rule_describe:规则描述\n" +
                                            "rule_level:规则等级\n" +
                                            "rule_up:数值上限\n" +
                                            "rule_down:数值下限\n" +
                                            "unit:单位\n" +
                                            "status:状态\n" +
                                            "status_text:状态名称\n" +
                                            "create_user_id:创建人\n" +
                                            "create_user_name:创建人\n" +
                                            "create_time:创建时间\n" +
                                            "modify_user_id:修改人id\n" +
                                            "modify_user_name:修改人\n" +
                                            "modify_time:修改时间\n"
                            )
                    )
            )
    )
    @Log(title = "业务操作-查询", businessType = BusinessType.OTHER)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    //@RoleAndPermission(permission = "safeIndex_list")
    public Object list(@Validated @ParamModel PageSafeIndexDTO pageSafeIndexDTO) {
        log.info("params => " + pageSafeIndexDTO);
        String errorMsg = ValidUtil.checkValid(pageSafeIndexDTO);
        if (!"".equals(errorMsg)) {
            return AjaxResult.error(errorMsg);
        }
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, pageSafeIndexDTO);
        IPage<Map<String, String>> list = safeIndexService.getListPage(pageSafeIndexDTO);
        return AjaxResult.success(list);
    }

    @ApiOperation(value = "环保指标项-新增", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rule_number", value = "规则编号"),
            @ApiImplicitParam(name = "rule_describe", value = "规则描述"),
            @ApiImplicitParam(name = "rule_level", value = "规则等级"),
            @ApiImplicitParam(name = "rule_up", value = "数值上限"),
            @ApiImplicitParam(name = "rule_down", value = "数值下限"),
            @ApiImplicitParam(name = "unit", value = "单位"),
            @ApiImplicitParam(name = "status", value = "状态"),
            @ApiImplicitParam(name = "create_user_id", value = "创建人id"),
            @ApiImplicitParam(name = "create_user_name", value = "创建人")
    })
    @ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @Log(title = "业务操作-新增", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "safeIndex_insert")
    public Object save(@Valid @RequestBody AddSafeIndexDTO addSafeIndexDTO) {
        log.info("params => " + addSafeIndexDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, addSafeIndexDTO);
        SafeIndex safeIndex = new SafeIndex();
        safeIndex.setId(IdUtil.randomUUID());
        safeIndex.setRuleNumber(addSafeIndexDTO.getRuleNumber());
        safeIndex.setRuleDescribe(addSafeIndexDTO.getRuleDescribe());
        safeIndex.setRuleLevel(addSafeIndexDTO.getRuleLevel());
        safeIndex.setRuleUp(addSafeIndexDTO.getRuleUp());
        safeIndex.setRuleDown(addSafeIndexDTO.getRuleDown());
        safeIndex.setUnit(addSafeIndexDTO.getUnit());
        safeIndex.setStatus(addSafeIndexDTO.getStatus());
        safeIndex.setCreateUserId(addSafeIndexDTO.getCreateUserId());
        safeIndex.setCreateUserName(addSafeIndexDTO.getCreateUserName());
        safeIndex.setCreateTime(new Date());
        safeIndex.setModifyUserId(addSafeIndexDTO.getCreateUserId());
        safeIndex.setModifyUserName(addSafeIndexDTO.getCreateUserName());
        safeIndex.setModifyTime(new Date());
        safeIndexService.save(safeIndex);
        return AjaxResult.success(ResponseUtil.toResult("环保指标项增加成功"));
    }

    @ApiOperation(value = "环保指标项-修改", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键"),
            @ApiImplicitParam(name = "rule_number", value = "规则编号"),
            @ApiImplicitParam(name = "rule_describe", value = "规则描述"),
            @ApiImplicitParam(name = "rule_level", value = "规则等级"),
            @ApiImplicitParam(name = "rule_up", value = "数值上限"),
            @ApiImplicitParam(name = "rule_down", value = "数值下限"),
            @ApiImplicitParam(name = "unit", value = "单位"),
            @ApiImplicitParam(name = "status", value = "状态"),
            @ApiImplicitParam(name = "modify_user_id", value = "修改人id"),
            @ApiImplicitParam(name = "modify_user_name", value = "修改人")
    })
    @ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @Log(title = "业务操作-修改", businessType = BusinessType.UPDATE)
    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "safeIndex_update")
    public Object update(@Valid @RequestBody UpdateSafeIndexDTO updateSafeIndexDTO) {
        log.info("params => " + updateSafeIndexDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, updateSafeIndexDTO);
        SafeIndex safeIndex = safeIndexService.getById(updateSafeIndexDTO.getId());
        if (null != safeIndex) {
            safeIndex.setRuleNumber(updateSafeIndexDTO.getRuleNumber());
            safeIndex.setRuleDescribe(updateSafeIndexDTO.getRuleDescribe());
            safeIndex.setRuleLevel(updateSafeIndexDTO.getRuleLevel());
            safeIndex.setRuleUp(updateSafeIndexDTO.getRuleUp());
            safeIndex.setRuleDown(updateSafeIndexDTO.getRuleDown());
            safeIndex.setUnit(updateSafeIndexDTO.getUnit());
            safeIndex.setStatus(updateSafeIndexDTO.getStatus());
	        safeIndex.setModifyUserId(updateSafeIndexDTO.getModifyUserId());
	        safeIndex.setModifyUserName(updateSafeIndexDTO.getModifyUserName());
	        safeIndex.setModifyTime(new Date());
            safeIndexService.updateById(safeIndex);
            return AjaxResult.success(ResponseUtil.toResult("环保指标项修改成功"));
        }
        else {
            return AjaxResult.error(HttpStatus.ERROR, "环保指标项不存在");
        }
    }

    @ApiOperation(value = "环保指标项-删除", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = false, dataTypeClass = String.class),
    })
    @ApiResponses(
            @io.swagger.annotations.ApiResponse(code = 0, message = "调用成功", response = String.class)
    )
    @Log(title = "业务操作-删除", businessType = BusinessType.DELETE)
    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json")
    //@RoleAndPermission(permission = "safeIndex_delete")
    public Object delete(@Valid @RequestBody DeleteSafeIndexDTO deleteSafeIndexDTO) {
        log.info("params => " + deleteSafeIndexDTO);
        httpServletRequest.setAttribute(SYS_LOG_PARAM_KEY, deleteSafeIndexDTO);
        SafeIndex safeIndex = safeIndexService.getById(deleteSafeIndexDTO.getId());
        if (null != safeIndex) {
            safeIndexService.removeById(safeIndex);
            return AjaxResult.success(ResponseUtil.toResult("环保指标项删除成功"));
        }
        else {
            return AjaxResult.error(HttpStatus.ERROR, "环保指标项不存在或已被删除");
        }
    }
}

