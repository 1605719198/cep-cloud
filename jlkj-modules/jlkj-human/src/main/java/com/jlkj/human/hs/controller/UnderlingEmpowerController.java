package com.jlkj.human.hs.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.UnderlingEmpower;
import com.jlkj.human.hs.dto.UnderlingEmpowerDTO;
import com.jlkj.human.hs.service.IUnderlingEmpowerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HuangBing
 * @Description
 * @create 2023-05-11 11:00
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/underlingEmpower")
public class UnderlingEmpowerController extends BaseController {

    private final IUnderlingEmpowerService iUnderlingEmpowerService;

    /**
     * 查询各公司主管查询下属薪资授权设定列表
     */
    @RequiresPermissions("human:underlingEmpower:list")
    @Log(title = "查询各公司主管查询下属薪资授权设定列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询各公司主管查询下属薪资授权设定列表")
    @GetMapping("/list")
    public Object listUnderlingEmpower(UnderlingEmpower underlingEmpower) {
        startPage();
        List<UnderlingEmpower> list = iUnderlingEmpowerService.lambdaQuery()
                .eq(UnderlingEmpower::getCompId, underlingEmpower.getCompId()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 保存各公司主管查询下属薪资授权设定列表
     */
    @RequiresPermissions("human:underlingEmpower:add")
    @Log(title = "保存各公司主管查询下属薪资授权设定列表", businessType = BusinessType.INSERT)
    @Operation(summary = "保存各公司主管查询下属薪资授权设定列表")
    @PostMapping
    public Object addUnderlingEmpower(@RequestBody UnderlingEmpowerDTO underlingEmpowerDTO) {
        List<String> stringList = underlingEmpowerDTO.getUnderlingEmpowerList().stream().map(UnderlingEmpower::getPostTypeId).collect(Collectors.toList());
        long count = stringList.stream().distinct().count();
        if (stringList.size() == count) {
            for (UnderlingEmpower item : underlingEmpowerDTO.getUnderlingEmpowerList()) {
                item.setCreator(SecurityUtils.getNickName());
                item.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
            }
            return iUnderlingEmpowerService.saveOrUpdateBatch(underlingEmpowerDTO.getUnderlingEmpowerList().stream().filter(item -> null != item.getPostTypeId()).filter(item -> !item.getPostTypeId().isEmpty()).collect(Collectors.toList()));
        } else {
            return AjaxResult.error("岗位类别不可重复填写");
        }

    }


    /**
     * 删除各公司主管查询下属薪资授权设定信息
     */
    @RequiresPermissions("human:underlingEmpower:remove")
    @Operation(summary = "删除各公司主管查询下属薪资授权设定信息")
    @DeleteMapping("/{uuid}")
    @Log(title = "删除各公司主管查询下属薪资授权设定信息", businessType = BusinessType.DELETE)
    public Object delUnderlingEmpower(@PathVariable String uuid) {
        iUnderlingEmpowerService.lambdaUpdate().eq(UnderlingEmpower::getUuid, uuid).remove();
        return AjaxResult.success("删除成功");
    }
}
