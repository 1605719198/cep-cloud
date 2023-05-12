package com.jlkj.finance.aa.controller;

import java.util.List;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.jlkj.common.security.annotation.InnerAuth;
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
import com.jlkj.finance.aa.domain.FinanceAaCompanyGroup;
import com.jlkj.finance.aa.service.IFinanceAaCompanyGroupService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 集团管理-会计公司维护Controller
 *
 * @author 116514
 * @date 2023-04-23
 */
@RestController
@RequestMapping("/companyGroup")
public class FinanceAaCompanyGroupController extends BaseController
{
    @Autowired
    private IFinanceAaCompanyGroupService financeAaCompanyGroupService;

    /**
     * 查询集团管理-会计公司维护列表
     */
    @RequiresPermissions("finance:companyGroup:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAaCompanyGroup financeAaCompanyGroup)
    {
        startPage();
        List<FinanceAaCompanyGroup> list = financeAaCompanyGroupService.selectFinanceAaCompanyGroupList(financeAaCompanyGroup);
        return getDataTable(list);
    }

    /**
     * 导出集团管理-会计公司维护列表
     */
    @RequiresPermissions("finance:companyGroup:export")
    @Log(title = "集团管理-会计公司维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAaCompanyGroup financeAaCompanyGroup)
    {
        List<FinanceAaCompanyGroup> list = financeAaCompanyGroupService.selectFinanceAaCompanyGroupList(financeAaCompanyGroup);
        ExcelUtil<FinanceAaCompanyGroup> util = new ExcelUtil<FinanceAaCompanyGroup>(FinanceAaCompanyGroup.class);
        util.exportExcel(response, list, "集团管理-会计公司维护数据");
    }

    /**
     * 获取集团管理-会计公司维护详细信息
     */
    @RequiresPermissions("finance:companyGroup:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(financeAaCompanyGroupService.selectFinanceAaCompanyGroupById(id));
    }

    /**
     * 新增集团管理-会计公司维护
     */
    @RequiresPermissions("finance:companyGroup:add")
    @Log(title = "集团管理-会计公司维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAaCompanyGroup financeAaCompanyGroup)
    {
        return toAjax(financeAaCompanyGroupService.insertFinanceAaCompanyGroup(financeAaCompanyGroup));
    }

    /**
     * 修改集团管理-会计公司维护
     */
    @RequiresPermissions("finance:companyGroup:edit")
    @Log(title = "集团管理-会计公司维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAaCompanyGroup financeAaCompanyGroup)
    {
        return toAjax(financeAaCompanyGroupService.updateFinanceAaCompanyGroup(financeAaCompanyGroup));
    }

    /**
     * 删除集团管理-会计公司维护
     */
    @RequiresPermissions("finance:companyGroup:remove")
    @Log(title = "集团管理-会计公司维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(financeAaCompanyGroupService.deleteFinanceAaCompanyGroupByIds(ids));
    }


    /**
     * 查询核算项目类别(下拉选单用)- 远程调用暴露接口
     */
    @InnerAuth
    @GetMapping("/getCompanyFeign")
    public List<Map<String,String>> getCompanyFeign()
    {
        return financeAaCompanyGroupService.selectCompanyList();
    }
    /**
     * 查询核算项目类别(下拉选单用)
     */
    @GetMapping("/companyList")
    public List<Map<String,String>> selectCompanyList()
    {
        return financeAaCompanyGroupService.selectCompanyList();
    }
}
