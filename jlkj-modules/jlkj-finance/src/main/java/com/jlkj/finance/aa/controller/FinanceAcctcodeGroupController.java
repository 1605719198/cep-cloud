package com.jlkj.finance.aa.controller;

import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.finance.aa.domain.FinanceCalcode;
import com.jlkj.system.api.domain.SysDept;
import com.jlkj.system.api.domain.SysUser;
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
import com.jlkj.finance.aa.domain.FinanceAcctcodeGroup;
import com.jlkj.finance.aa.service.IFinanceAcctcodeGroupService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;

/**
 * 会计科目-集团级Controller
 *
 * @author 116514
 * @date 2023-04-15
 */
@RestController
@RequestMapping("/acctcodeGroup")
public class FinanceAcctcodeGroupController extends BaseController
{
    @Autowired
    private IFinanceAcctcodeGroupService financeAcctcodeGroupService;

    /**
     * 查询会计科目-集团级列表
     */
    @RequiresPermissions("finance:acctcodeGroup:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceAcctcodeGroup financeAcctcodeGroup)
    {
        startPage();
        List<FinanceAcctcodeGroup> list = financeAcctcodeGroupService.selectFinanceAcctcodeGroupList(financeAcctcodeGroup);
        return getDataTable(list);
    }

    /**
     * 导出会计科目-集团级列表
     */
    @RequiresPermissions("finance:acctcodeGroup:export")
    @Log(title = "会计科目-集团级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceAcctcodeGroup financeAcctcodeGroup)
    {
        List<FinanceAcctcodeGroup> list = financeAcctcodeGroupService.selectFinanceAcctcodeGroupList(financeAcctcodeGroup);
        ExcelUtil<FinanceAcctcodeGroup> util = new ExcelUtil<FinanceAcctcodeGroup>(FinanceAcctcodeGroup.class);
        util.exportExcel(response, list, "会计科目-集团级数据");
    }

    /**
     * 获取会计科目-集团级详细信息
     */
    @RequiresPermissions("finance:acctcodeGroup:query")
    @GetMapping(value = "/{groupAcctId}")
    public AjaxResult getInfo(@PathVariable("groupAcctId") String groupAcctId)
    {
        return success(financeAcctcodeGroupService.selectFinanceAcctcodeGroupByGroupAcctId(groupAcctId));
    }

    /**
     * 新增会计科目-集团级
     */
    @RequiresPermissions("finance:acctcodeGroup:add")
    @Log(title = "会计科目-集团级", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceAcctcodeGroup financeAcctcodeGroup)
    {
        return toAjax(financeAcctcodeGroupService.insertFinanceAcctcodeGroup(financeAcctcodeGroup));
    }

    /**
     * 修改会计科目-集团级
     */

    @RequiresPermissions("finance:acctcodeGroup:edit")
    @Log(title = "会计科目-集团级", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceAcctcodeGroup financeAcctcodeGroup)
    {
        return toAjax(financeAcctcodeGroupService.updateFinanceAcctcodeGroup(financeAcctcodeGroup));
    }

    /**
     * 删除会计科目-集团级
     */
    @RequiresPermissions("finance:acctcodeGroup:remove")
    @Log(title = "会计科目-集团级", businessType = BusinessType.DELETE)
	@DeleteMapping("/{groupAcctIds}")
    public AjaxResult remove(@PathVariable String[] groupAcctIds)
    {
        return toAjax(financeAcctcodeGroupService.deleteFinanceAcctcodeGroupByGroupAcctIds(groupAcctIds));
    }

    /**
     * 删除会计科目-集团级
     */
    @RequiresPermissions("finance:acctcodeGroup:remove")
    @Log(title = "会计科目-集团级", businessType = BusinessType.DELETE)
    @DeleteMapping("/code/{groupAcctCode}")
    public AjaxResult delAcctcodeGroupByCode(@PathVariable String groupAcctCode)
    {
        return toAjax(financeAcctcodeGroupService.deleteFinanceAcctcodeGroupByGroupAcctCode(groupAcctCode));
    }


    /**
     * 获取部门树列表
     */
    @RequiresPermissions("finance:acctcodeGroup:list")
    @GetMapping("/codeTree")
    public AjaxResult deptTree(FinanceAcctcodeGroup dept)
    {
        return success(financeAcctcodeGroupService.selectCodeTreeList(dept));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("finance:acctcodeGroup:edit")
    @Log(title = "启用禁用-会计科目-集团级", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody FinanceAcctcodeGroup financeAcctcodeGroup)
    {
        return toAjax(financeAcctcodeGroupService.updateFinanceAcctcodeGroup(financeAcctcodeGroup));
    }
}
