package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.uuid.IdUtils;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hs.domain.PayParamStandard;
import com.jlkj.human.hs.dto.PayParamStandardDTO;
import com.jlkj.human.hs.service.IPayParamStandardService;
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
@RequestMapping("/payParamStandard")
public class PayParamStandardController extends BaseController {

    private final IPayParamStandardService iPayParamStandardService;

    /**
     * 查询各公司薪资参数设定列表
     */
    @RequiresPermissions("human:payParamStandard:list")
    @Log(title = "查询各公司薪资参数设定列表",businessType = BusinessType.OTHER)
    @Operation(summary = "查询各公司薪资参数设定列表")
    @GetMapping("/list")
    public Object listPayParamStandard(PayParamStandard payParamStandard) {
        startPage();
        List<PayParamStandard> list = iPayParamStandardService.lambdaQuery()
                .eq(PayParamStandard::getCompId, payParamStandard.getCompId())
                .eq(PayParamStandard::getPayParaId, payParamStandard.getPayParaId()).list();
        return AjaxResult.success("查询成功", getDataTable(list));
    }

    /**
     * 保存各公司薪资参数设定列表
     */
    @RequiresPermissions("human:payParamStandard:add")
    @Log(title = "保存各公司薪资参数设定列表", businessType = BusinessType.INSERT)
    @Operation(summary = "保存各公司薪资参数设定列表")
    @PostMapping
    public Object addPayParamStandard(@RequestBody PayParamStandardDTO payParamStandardDTO) {
        for (PayParamStandard item : payParamStandardDTO.getPayParamStandardList()) {
            item.setCreator(SecurityUtils.getNickName());
            item.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
        }
        return iPayParamStandardService.saveOrUpdateBatch(payParamStandardDTO.getPayParamStandardList().stream().filter(item -> null != item.getStandardCode() && null != item.getStandard()).filter(item -> !item.getStandardCode().isEmpty() && !item.getStandard().isEmpty()).collect(Collectors.toList()));
    }
    /**
     * 复制各公司薪资参数设定列表
     */
    @RequiresPermissions("human:payParamStandard:copy")
    @Log(title = "复制各公司薪资参数设定列表", businessType = BusinessType.INSERT)
    @Operation(summary = "复制各公司薪资参数设定列表")
    @PostMapping("/copyPayParamStandard")
    public Object copyPayParamStandard(@RequestBody PayParamStandardDTO payParamStandardDTO) {
        for (PayParamStandard item : payParamStandardDTO.getPayParamStandardList()) {
            item.setUuid(IdUtils.fastSimpleUUID());
            item.setCreator(SecurityUtils.getNickName());
            item.setCreatorId(String.valueOf(SecurityUtils.getUserId()));
        }
        return iPayParamStandardService.saveBatch(payParamStandardDTO.getPayParamStandardList().stream().filter(item -> null != item.getStandardCode() && null != item.getStandard()).filter(item -> !item.getStandardCode().isEmpty() && !item.getStandard().isEmpty()).collect(Collectors.toList()));
    }
}
