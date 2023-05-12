package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.financeAaDeprmethod;
import com.jlkj.finance.aa.service.IfinanceAaDeprmethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 折旧方法维护Controller
 *
 * @author SunXuTong
 * @date 2023-05-10
 */
@RestController
@RequestMapping("/deprMethod")
public class financeAaDeprmethodController extends BaseController
{
    @Autowired
    private IfinanceAaDeprmethodService financeAaDeprmethodService;

    /**
     * 查询折旧方法维护列表
     */
    @RequiresPermissions("finance:deprMethod:list")
    @GetMapping("/list")
    public TableDataInfo list(financeAaDeprmethod financeAaDeprmethod)
    {
        startPage();
        List<financeAaDeprmethod> list = financeAaDeprmethodService.selectfinanceAaDeprmethodList(financeAaDeprmethod);
        return getDataTable(list);
    }

    /**
     * 导出折旧方法维护列表
     */
    @RequiresPermissions("finance:deprMethod:export")
    @Log(title = "折旧方法维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, financeAaDeprmethod financeAaDeprmethod)
    {
        List<financeAaDeprmethod> list = financeAaDeprmethodService.selectfinanceAaDeprmethodList(financeAaDeprmethod);
        ExcelUtil<financeAaDeprmethod> util = new ExcelUtil<financeAaDeprmethod>(financeAaDeprmethod.class);
        util.exportExcel(response, list, "折旧方法维护数据");
    }

    /**
     * 获取折旧方法维护详细信息
     */
    @RequiresPermissions("finance:deprMethod:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaDeprmethodService.selectfinanceAaDeprmethodById(id));
    }

    /**
     * 新增折旧方法维护
     */
    @RequiresPermissions("finance:deprMethod:add")
    @Log(title = "折旧方法维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody financeAaDeprmethod financeAaDeprmethod)
    {
        return toAjax(financeAaDeprmethodService.insertfinanceAaDeprmethod(financeAaDeprmethod));
    }

    /**
     * 修改折旧方法维护
     */
    @RequiresPermissions("finance:deprMethod:edit")
    @Log(title = "折旧方法维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody financeAaDeprmethod financeAaDeprmethod)
    {
        return toAjax(financeAaDeprmethodService.updatefinanceAaDeprmethod(financeAaDeprmethod));
    }

    /**
     * 删除折旧方法维护
     */
    @RequiresPermissions("finance:deprMethod:remove")
    @Log(title = "折旧方法维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaDeprmethodService.deletefinanceAaDeprmethodByIds(ids));
    }
}
