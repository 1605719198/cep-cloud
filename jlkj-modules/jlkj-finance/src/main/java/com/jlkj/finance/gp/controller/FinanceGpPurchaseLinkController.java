package com.jlkj.finance.gp.controller;

import java.util.List;
import java.io.IOException;
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
import com.jlkj.finance.gp.domain.FinanceGpPurchaseLink;
import com.jlkj.finance.gp.service.IFinanceGpPurchaseLinkService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 厂商采购关系联系人Controller
 *
 * @author 265799
 * @date 2023-04-07
 */
@RestController
@RequestMapping("/link")
public class FinanceGpPurchaseLinkController extends BaseController
{
    @Autowired
    private IFinanceGpPurchaseLinkService financeGpPurchaseLinkService;

    /**
     * 查询厂商采购关系联系人列表
     */
    @RequiresPermissions("finance:link:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceGpPurchaseLink financeGpPurchaseLink)
    {
        startPage();
        List<FinanceGpPurchaseLink> list = financeGpPurchaseLinkService.selectFinanceGpPurchaseLinkList(financeGpPurchaseLink);
        return getDataTable(list);
    }

    /**
     * 导出厂商采购关系联系人列表
     */
    @RequiresPermissions("finance:link:export")
    @Log(title = "厂商采购关系联系人", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceGpPurchaseLink financeGpPurchaseLink)
    {
        List<FinanceGpPurchaseLink> list = financeGpPurchaseLinkService.selectFinanceGpPurchaseLinkList(financeGpPurchaseLink);
        ExcelUtil<FinanceGpPurchaseLink> util = new ExcelUtil<FinanceGpPurchaseLink>(FinanceGpPurchaseLink.class);
        util.exportExcel(response, list, "厂商采购关系联系人数据");
    }

    /**
     * 获取厂商采购关系联系人详细信息
     */
    @RequiresPermissions("finance:link:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(financeGpPurchaseLinkService.selectFinanceGpPurchaseLinkByUuid(uuid));
    }

    /**
     * 新增厂商采购关系联系人
     */
    @RequiresPermissions("finance:link:add")
    @Log(title = "厂商采购关系联系人", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceGpPurchaseLink financeGpPurchaseLink)
    {
        return toAjax(financeGpPurchaseLinkService.insertFinanceGpPurchaseLink(financeGpPurchaseLink));
    }

    /**
     * 修改厂商采购关系联系人
     */
    @RequiresPermissions("finance:link:edit")
    @Log(title = "厂商采购关系联系人", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceGpPurchaseLink financeGpPurchaseLink)
    {
        return toAjax(financeGpPurchaseLinkService.updateFinanceGpPurchaseLink(financeGpPurchaseLink));
    }

    /**
     * 删除厂商采购关系联系人
     */
    @RequiresPermissions("finance:link:remove")
    @Log(title = "厂商采购关系联系人", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(financeGpPurchaseLinkService.deleteFinanceGpPurchaseLinkByUuids(uuids));
    }
}
