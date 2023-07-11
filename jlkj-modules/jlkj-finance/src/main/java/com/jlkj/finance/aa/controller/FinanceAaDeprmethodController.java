package com.jlkj.finance.aa.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.InnerAuth;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.aa.domain.FinanceAaDeprmethod;
import com.jlkj.finance.aa.service.IFinanceAaDeprmethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 折旧方法维护Controller
 *
 * @author SunXuTong
 * @date 2023-05-10
 */
@RestController
@RequestMapping("/deprMethod")
public class FinanceAaDeprmethodController extends BaseController
{
    @Autowired
    private IFinanceAaDeprmethodService financeAaDeprmethodService;

    /**
     * 查询折旧方法维护列表
     */
    @RequiresPermissions("finance:deprMethod:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaDeprmethod financeAaDeprmethod)
    {
        startPage();
        List<FinanceAaDeprmethod> list = financeAaDeprmethodService.selectFinanceAaDeprmethodList(financeAaDeprmethod);
        return getDataTable(list);
    }

    /**
     * 导出折旧方法维护列表
     */
    @RequiresPermissions("finance:deprMethod:export")
    @Log(title = "折旧方法维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaDeprmethod financeAaDeprmethod)
    {
        List<FinanceAaDeprmethod> list = financeAaDeprmethodService.selectFinanceAaDeprmethodList(financeAaDeprmethod);
        ExcelUtil<FinanceAaDeprmethod> util = new ExcelUtil<FinanceAaDeprmethod>(FinanceAaDeprmethod.class);
        util.exportExcel(response, list, "折旧方法维护数据");
    }

    /**
     * 获取折旧方法维护详细信息
     */
    @RequiresPermissions("finance:deprMethod:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaDeprmethodService.selectFinanceAaDeprmethodById(id));
    }

    /**
     * 新增折旧方法维护
     */
    @RequiresPermissions("finance:deprMethod:add")
    @Log(title = "折旧方法维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaDeprmethod financeAaDeprmethod)
    {
        return toAjax(financeAaDeprmethodService.insertFinanceAaDeprmethod(financeAaDeprmethod));
    }

    /**
     * 修改折旧方法维护
     */
    @RequiresPermissions("finance:deprMethod:edit")
    @Log(title = "折旧方法维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaDeprmethod financeAaDeprmethod)
    {
        return toAjax(financeAaDeprmethodService.updateFinanceAaDeprmethod(financeAaDeprmethod));
    }

    /**
     * 删除折旧方法维护
     */
    @RequiresPermissions("finance:deprMethod:remove")
    @Log(title = "折旧方法维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaDeprmethodService.deleteFinanceAaDeprmethodByIds(ids));
    }

    /**
     * 查询折旧方法(下拉选单用)- 远程调用暴露接口
     */
    @InnerAuth
    @GetMapping("/getdeprMethodFeign")
    public List<Map<String,String>> getdeprMethodFeign()
    {
        return financeAaDeprmethodService.selectDeprMethodList();
    }
    /**
     * 查询折旧方法(下拉选单用)
     */
    @GetMapping("/deprMethodList")
    public List<Map<String,String>> selectDeprMethodList()
    {
        return financeAaDeprmethodService.selectDeprMethodList();
    }
}
