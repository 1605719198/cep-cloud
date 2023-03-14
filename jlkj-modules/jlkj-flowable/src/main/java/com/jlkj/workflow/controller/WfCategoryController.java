package com.jlkj.workflow.controller;

import com.jlkj.common.core.constant.UserConstants;
import com.jlkj.common.core.domain.R;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.PageQuery;
import com.jlkj.common.core.web.page.TableDataInfoPlus;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.workflow.domain.bo.WfCategoryBo;
import com.jlkj.workflow.domain.vo.WfCategoryVo;
import com.jlkj.workflow.service.IWfCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * 流程分类Controller
 *
 * @author xin
 * @createTime 2022/3/10 00:12
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/workflow/category")
public class WfCategoryController extends BaseController {

    private final IWfCategoryService categoryService;

    /**
     * 查询流程分类列表
     */
    @RequiresPermissions("workflow:category:list")
    @GetMapping("/list")
    public TableDataInfoPlus<WfCategoryVo> list( WfCategoryBo bo, PageQuery pageQuery) {
        return categoryService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询全部的流程分类列表
     */
    @GetMapping("/listAll")
    public R<List<WfCategoryVo>> listAll( WfCategoryBo bo) {
        return R.ok(categoryService.queryList(bo));
    }

    /**
     * 导出流程分类列表
     */
    @RequiresPermissions("workflow:category:export")
    @Log(title = "流程分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(@Validated WfCategoryBo bo, HttpServletResponse response) {
        List<WfCategoryVo> list = categoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "流程分类", WfCategoryVo.class, response);
    }

    /**
     * 获取流程分类详细信息
     * @param categoryId 分类主键
     */
    @RequiresPermissions("workflow:category:query")
    @GetMapping("/{categoryId}")
    public AjaxResult getInfo(@NotNull(message = "主键不能为空") @PathVariable("categoryId") Long categoryId) {
        return AjaxResult.success(categoryService.queryById(categoryId));
    }

    /**
     * 新增流程分类
     */
    @RequiresPermissions("workflow:category:add")
    @Log(title = "流程分类", businessType = BusinessType.INSERT)
    @PostMapping()
    public AjaxResult add(@RequestBody WfCategoryBo categoryBo) {
        if (UserConstants.NOT_UNIQUE.equals(categoryService.checkCategoryCodeUnique(categoryBo.getCode(),categoryBo.getCategoryId()))) {
            return AjaxResult.error("新增流程分类'" + categoryBo.getCategoryName() + "'失败，流程编码已存在");
        }
        return toAjax(categoryService.insertCategory(categoryBo));
    }

    /**
     * 修改流程分类
     */
    @RequiresPermissions("workflow:category:edit")
    @Log(title = "流程分类", businessType = BusinessType.UPDATE)
    @PutMapping()
    public AjaxResult edit(@RequestBody WfCategoryBo categoryBo) {
        if (UserConstants.NOT_UNIQUE.equals(categoryService.checkCategoryCodeUnique(categoryBo.getCode(),categoryBo.getCategoryId()))) {
            return AjaxResult.error("修改流程分类'" + categoryBo.getCategoryName() + "'失败，流程编码已存在");
        }
        return toAjax(categoryService.updateCategory(categoryBo));
    }

    /**
     * 删除流程分类
     * @param categoryIds 分类主键串
     */
    @RequiresPermissions("workflow:category:remove")
    @Log(title = "流程分类" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@NotEmpty(message = "主键不能为空") @PathVariable Long[] categoryIds) {
        return toAjax(categoryService.deleteWithValidByIds(Arrays.asList(categoryIds), true));
    }
}
