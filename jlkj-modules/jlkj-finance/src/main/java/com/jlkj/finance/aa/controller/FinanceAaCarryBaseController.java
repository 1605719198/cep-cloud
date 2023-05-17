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
import com.jlkj.finance.aa.domain.FinanceAaCarryBase;
import com.jlkj.finance.aa.service.IFinanceAaCarryBaseService;
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
@RequestMapping("/financeAaCarryBase")
public class FinanceAaCarryBaseController extends BaseController
{
    @Autowired
    private IFinanceAaCarryBaseService financeAaCarryBaseService;

    /**
     * 查询自动结转规则-基本资料-集团列表
     */
    @RequiresPermissions("finance:financeAaCarryBase:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaCarryBase financeAaCarryBase)
    {
        startPage();
        List<FinanceAaCarryBase> list = financeAaCarryBaseService.selectFinanceAaCarryBaseList(financeAaCarryBase);
        return getDataTable(list);
    }

    /**
     * 导出自动结转规则-基本资料-集团列表
     */
    @RequiresPermissions("finance:financeAaCarryBase:export")
    @Log(title = "自动结转规则-基本资料-集团", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaCarryBase financeAaCarryBase)
    {
        List<FinanceAaCarryBase> list = financeAaCarryBaseService.selectFinanceAaCarryBaseList(financeAaCarryBase);
        ExcelUtil<FinanceAaCarryBase> util = new ExcelUtil<FinanceAaCarryBase>(FinanceAaCarryBase.class);
        util.exportExcel(response, list, "自动结转规则-基本资料-集团数据");
    }

    /**
     * 获取自动结转规则-基本资料-集团详细信息
     */
    @RequiresPermissions("finance:financeAaCarryBase:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaCarryBaseService.selectFinanceAaCarryBaseById(id));
    }

    /**
     * 新增自动结转规则-基本资料-集团
     */
    @RequiresPermissions("finance:financeAaCarryBase:add")
    @Log(title = "自动结转规则-基本资料-集团", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaCarryBase financeAaCarryBase)
    {
        return toAjax(financeAaCarryBaseService.insertFinanceAaCarryBase(financeAaCarryBase));
    }

    /**
     * 修改自动结转规则-基本资料-集团
     */
    @RequiresPermissions("finance:financeAaCarryBase:edit")
    @Log(title = "自动结转规则-基本资料-集团", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaCarryBase financeAaCarryBase)
    {
        return toAjax(financeAaCarryBaseService.updateFinanceAaCarryBase(financeAaCarryBase));
    }

    /**
     * 删除自动结转规则-基本资料-集团
     */
    @RequiresPermissions("finance:financeAaCarryBase:remove")
    @Log(title = "自动结转规则-基本资料-集团", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaCarryBaseService.deleteFinanceAaCarryBaseByIds(ids));
    }
}
