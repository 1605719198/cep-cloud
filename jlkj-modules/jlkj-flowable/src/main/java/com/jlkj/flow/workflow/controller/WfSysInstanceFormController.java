package com.jlkj.flow.workflow.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.flow.workflow.domain.SysInstanceForm;
import com.jlkj.flow.workflow.service.SysInstanceFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流程分类Controller
 *
 * @author xin
 * @createTime 2022/3/10 00:12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/instanceform")
public class WfSysInstanceFormController extends BaseController {

    @Autowired
    private SysInstanceFormService instanceFormService;

    /**
     * 根据流程实例获取绑定表单信息
     */
    @Log(title = "根据流程实例获取绑定表单信息", businessType = BusinessType.OTHER)
    @GetMapping("/getFromByInsId")
    public AjaxResult getFromByInsId(String insId) {
        LambdaQueryWrapper<SysInstanceForm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysInstanceForm::getInstanceId,insId);
        return AjaxResult.success("查询表单成功",instanceFormService.getOne(wrapper));
    }
    /**
     * 根据表单id查询表单是否存在启动流程
     */
    @Log(title = "根据流程实例获取绑定表单信息", businessType = BusinessType.OTHER)
    @GetMapping("/getTaskByFormId/{formId}")
    public AjaxResult getTaskByFormId(@PathVariable String formId) {
        LambdaQueryWrapper<SysInstanceForm> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysInstanceForm::getFormId,formId);
        return AjaxResult.success("查询表单成功",instanceFormService.getOne(wrapper));
    }

}
