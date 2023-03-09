package com.jlkj.flowable.controller;

import java.util.List;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.flowable.domain.SysDeployForm;
import com.jlkj.flowable.domain.SysForm;
import com.jlkj.flowable.service.ISysDeployFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jlkj.flowable.service.ISysFormService;

import javax.servlet.http.HttpServletResponse;


/**
 * 流程表单Controller
 *
 * @author xin
 * @date 2021-04-03
 */
@RestController
@RequestMapping("/flowable/form")
public class SysFormController extends BaseController {
    @Autowired
    private ISysFormService SysFormService;

    @Autowired
    private ISysDeployFormService sysDeployFormService;

    /**
     * 查询流程表单列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysForm sysForm) {
        startPage();
        List<SysForm> list = SysFormService.selectSysFormList(sysForm);
        return getDataTable(list);
    }

    @GetMapping("/formList")
    public AjaxResult formList(SysForm sysForm) {
        List<SysForm> list = SysFormService.selectSysFormList(sysForm);
        return AjaxResult.success(list);
    }
    /**
     * 导出流程表单列表
     */
    @Log(title = "流程表单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response, SysForm sysForm) {
        List<SysForm> list = SysFormService.selectSysFormList(sysForm);
        ExcelUtil<SysForm> util = new ExcelUtil<SysForm>(SysForm.class);
        util.exportExcel(response,list, "form");
    }

    /**
     * 获取流程表单详细信息
     */
    @GetMapping(value = "/{formId}")
    public AjaxResult getInfo(@PathVariable("formId") Long formId) {
        return AjaxResult.success(SysFormService.selectSysFormById(formId));
    }

    /**
     * 新增流程表单
     */
    @Log(title = "流程表单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysForm sysForm) {
        return toAjax(SysFormService.insertSysForm(sysForm));
    }

    /**
     * 修改流程表单
     */
    @Log(title = "流程表单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysForm sysForm) {
        return toAjax(SysFormService.updateSysForm(sysForm));
    }

    /**
     * 删除流程表单
     */
    @Log(title = "流程表单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{formIds}")
    public AjaxResult remove(@PathVariable Long[] formIds) {
        return toAjax(SysFormService.deleteSysFormByIds(formIds));
    }


    /**
     * 挂载流程表单
     */
    @Log(title = "流程表单", businessType = BusinessType.INSERT)
    @PostMapping("/addDeployForm")
    public AjaxResult addDeployForm(@RequestBody SysDeployForm sysDeployForm) {
        return toAjax(sysDeployFormService.insertSysDeployForm(sysDeployForm));
    }
}
