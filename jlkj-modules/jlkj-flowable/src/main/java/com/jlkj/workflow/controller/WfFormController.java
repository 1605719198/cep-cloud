package com.jlkj.workflow.controller;

import com.jlkj.common.core.domain.R;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.PageQuery;
import com.jlkj.common.core.web.page.TableDataInfoPlus;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.workflow.domain.WfDeployForm;
import com.jlkj.workflow.domain.bo.WfFormBo;
import com.jlkj.workflow.domain.vo.WfFormVo;
import com.jlkj.workflow.service.IWfDeployFormService;
import com.jlkj.workflow.service.IWfFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 流程表单Controller
 *
 * @author xin
 * @createTime 2022/3/7 22:07
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/form")
public class WfFormController extends BaseController {

    private final IWfFormService formService;

    private final IWfDeployFormService deployFormService;

    /**
     * 查询流程表单列表
     */
    @RequiresPermissions("workflow:form:list")
    @GetMapping("/list")
    public TableDataInfoPlus<WfFormVo> list(WfFormBo bo, PageQuery pageQuery) {
        return formService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出流程表单列表
     */
    @RequiresPermissions("workflow:form:export")
    @Log(title = "流程表单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated WfFormBo bo, HttpServletResponse response) {
        List<WfFormVo> list = formService.queryList(bo);
        ExcelUtil.exportExcel(list, "流程表单", WfFormVo.class, response);
    }

    /**
     * 获取流程表单详细信息
     * @param formId 主键
     */
    @RequiresPermissions("workflow:form:query")
    @GetMapping(value = "/{formId}")
    public R<WfFormVo> getInfo(@NotNull(message = "主键不能为空") @PathVariable("formId") Long formId) {
        return R.ok(formService.queryById(formId));
    }

    /**
     * 新增流程表单
     */
    @RequiresPermissions("workflow:form:add")
    @Log(title = "流程表单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WfFormBo bo) {
        return toAjax(formService.insertForm(bo));
    }

    /**
     * 修改流程表单
     */
    @RequiresPermissions("workflow:form:edit")
    @Log(title = "流程表单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WfFormBo bo) {
        return toAjax(formService.updateForm(bo));
    }

    /**
     * 删除流程表单
     * @param formIds 主键串
     */
    @RequiresPermissions("workflow:form:remove")
    @Log(title = "流程表单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{formIds}")
    public AjaxResult remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] formIds) {
        return toAjax(formService.deleteWithValidByIds(Arrays.asList(formIds)) ? 1 : 0);
    }


    /**
     * 挂载流程表单
     */
    @Log(title = "流程表单", businessType = BusinessType.INSERT)
    @PostMapping("/addDeployForm")
    public AjaxResult addDeployForm(@RequestBody WfDeployForm deployForm) {
        return toAjax(deployFormService.insertWfDeployForm(deployForm));
    }
}
