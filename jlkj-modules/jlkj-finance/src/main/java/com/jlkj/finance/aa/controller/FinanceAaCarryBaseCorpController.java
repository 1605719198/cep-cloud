package com.jlkj.finance.aa.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaCarryBaseCorp;
import com.jlkj.finance.aa.service.IFinanceAaCarryBaseCorpService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 自动结转规则-基本资料-集团Controller
 *
 * @author 116514
 * @date 2023-04-24
 */
@RestController
@RequestMapping("/financeAaCarryBaseCorp")
public class FinanceAaCarryBaseCorpController extends BaseController
{
    @Autowired
    private IFinanceAaCarryBaseCorpService financeAaCarryBaseCorpService;

    /**
     * 查询自动结转规则-基本资料-集团列表
     */
    @RequiresPermissions("finance:financeAaCarryBaseCorp:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaCarryBaseCorp financeAaCarryBaseCorp)
    {
        startPage();
        List<FinanceAaCarryBaseCorp> list = financeAaCarryBaseCorpService.selectFinanceAaCarryBaseCorpList(financeAaCarryBaseCorp);
        return getDataTable(list);
    }

    /**
     * 导出自动结转规则-基本资料-集团列表
     */
    @RequiresPermissions("finance:financeAaCarryBaseCorp:export")
    @Log(title = "自动结转规则-基本资料-集团", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaCarryBaseCorp financeAaCarryBaseCorp)
    {
        List<FinanceAaCarryBaseCorp> list = financeAaCarryBaseCorpService.selectFinanceAaCarryBaseCorpList(financeAaCarryBaseCorp);
        ExcelUtil<FinanceAaCarryBaseCorp> util = new ExcelUtil<FinanceAaCarryBaseCorp>(FinanceAaCarryBaseCorp.class);
        util.exportExcel(response, list, "自动结转规则-基本资料-集团数据");
    }

    /**
     * 获取自动结转规则-基本资料-集团详细信息
     */
    @RequiresPermissions("finance:financeAaCarryBaseCorp:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaCarryBaseCorpService.selectFinanceAaCarryBaseCorpById(id));
    }

    /**
     * 新增自动结转规则-基本资料-集团
     */
    @RequiresPermissions("finance:financeAaCarryBaseCorp:add")
    @Log(title = "自动结转规则-基本资料-集团", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaCarryBaseCorp financeAaCarryBaseCorp)
    {
        return toAjax(financeAaCarryBaseCorpService.insertFinanceAaCarryBaseCorp(financeAaCarryBaseCorp));
    }

    /**
     * 修改自动结转规则-基本资料-集团
     */
    @RequiresPermissions("finance:financeAaCarryBaseCorp:edit")
    @Log(title = "自动结转规则-基本资料-集团", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaCarryBaseCorp financeAaCarryBaseCorp)
    {
        return toAjax(financeAaCarryBaseCorpService.updateFinanceAaCarryBaseCorp(financeAaCarryBaseCorp));
    }

    /**
     * 删除自动结转规则-基本资料-集团
     */
    @RequiresPermissions("finance:financeAaCarryBaseCorp:remove")
    @Log(title = "自动结转规则-基本资料-集团", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaCarryBaseCorpService.deleteFinanceAaCarryBaseCorpByIds(ids));
    }
}
