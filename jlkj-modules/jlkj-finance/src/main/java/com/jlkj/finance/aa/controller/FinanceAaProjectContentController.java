package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaProjectContent;
import com.jlkj.finance.aa.service.IFinanceAaProjectContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 集团管理-报表内容资料Controller
 *
 * @author 116514
 * @date 2023-05-16
 */
@RestController
@RequestMapping("/projectContent")
public class FinanceAaProjectContentController extends BaseController
{
    @Autowired
    private IFinanceAaProjectContentService financeAaProjectContentService;

    /**
     * 查询集团管理-报表内容资料列表
     */
    @RequiresPermissions("finance:reportDefin:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaProjectContent financeAaProjectContent)
    {
        startPage();
        List<FinanceAaProjectContent> list = financeAaProjectContentService.selectFinanceAaProjectContentList(financeAaProjectContent);
        return getDataTable(list);
    }

    /**
     * 导出集团管理-报表内容资料列表
     */
    @RequiresPermissions("finance:reportDefin:export")
    @Log(title = "集团管理-报表内容资料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaProjectContent financeAaProjectContent)
    {
        List<FinanceAaProjectContent> list = financeAaProjectContentService.selectFinanceAaProjectContentList(financeAaProjectContent);
        ExcelUtil<FinanceAaProjectContent> util = new ExcelUtil<FinanceAaProjectContent>(FinanceAaProjectContent.class);
        util.exportExcel(response, list, "集团管理-报表内容资料数据");
    }

    /**
     * 获取集团管理-报表内容资料详细信息
     */
    @RequiresPermissions("finance:reportDefin:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaProjectContentService.selectFinanceAaProjectContentById(id));
    }

    /**
     * 新增集团管理-报表内容资料
     */
    @RequiresPermissions("finance:reportDefin:add")
    @Log(title = "集团管理-报表内容资料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaProjectContent financeAaProjectContent)
    {
        List<FinanceAaProjectContent> dataList = financeAaProjectContent.getDataList();
        return toAjax(financeAaProjectContentService.insertFinanceAaProjectContentItem(dataList));
    }

    /**
     * 修改集团管理-报表内容资料
     */
    @RequiresPermissions("finance:reportDefin:edit")
    @Log(title = "集团管理-报表内容资料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaProjectContent financeAaProjectContent)
    {
        return toAjax(financeAaProjectContentService.updateFinanceAaProjectContent(financeAaProjectContent));
    }

    /**
     * 删除集团管理-报表内容资料
     */
    @RequiresPermissions("finance:reportDefin:remove")
    @Log(title = "集团管理-报表内容资料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaProjectContentService.deleteFinanceAaProjectContentByIds(ids));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("finance:reportDefin:edit")
    @Log(title = "核算项目-内容维护", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody FinanceAaProjectContent financeAaProjectContent)
    {
        return toAjax(financeAaProjectContentService.updateFinanceAaProjectContent(financeAaProjectContent));
    }
}
