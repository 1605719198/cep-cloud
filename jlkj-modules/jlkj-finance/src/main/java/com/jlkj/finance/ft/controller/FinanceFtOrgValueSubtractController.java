package com.jlkj.finance.ft.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.finance.ft.domain.FinanceFtChange;
import com.jlkj.finance.ft.service.IFinanceFtChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 资产变动单主档Controller
 * @Description:固定资产原值减少单维护
 * @author 116524
 * @date 2023-07-04
 * @version:V1.0
 */
@RestController
@RequestMapping("/ft/orgValueSubtract")
public class FinanceFtOrgValueSubtractController extends BaseController
{
    @Autowired
    private IFinanceFtChangeService financeFtChangeService;

    /**
     * 查询资产变动单主档列表
     * @author 116524
     * @date 2023-07-04
     * @param financeFtChange
     * @return
     */


    @RequiresPermissions("finance:ft:orgValueSubtract:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceFtChange financeFtChange)
    {
        startPage();
        List<FinanceFtChange> list = financeFtChangeService.selectFinanceFtChangeList(financeFtChange);
        return getDataTable(list);
    }

    /**
     * 导出资产变动单主档列表
     * @author 116524
     * @date 2023-07-04
     * @param financeFtChange
     * @return
     */
    @RequiresPermissions("finance:ft:orgValueSubtract:export")
    @Log(title = "资产变动单主档", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceFtChange financeFtChange)
    {
        List<FinanceFtChange> list = financeFtChangeService.selectFinanceFtChangeList(financeFtChange);
        ExcelUtil<FinanceFtChange> util = new ExcelUtil<FinanceFtChange>(FinanceFtChange.class);
        util.exportExcel(response, list, "资产变动单主档数据");
    }

    /**
     * 获取资产变动单主档详细信息
     * @author 116524
     * @date 2023-07-04
     * @param uuid
     * @return
     */
    @RequiresPermissions("finance:ft:orgValueSubtract:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(financeFtChangeService.selectFinanceFtChangeByUuid(uuid));
    }

    /**
     * 新增资产变动单主档
     * @author 116524
     * @date 2023-07-04
     * @param financeFtChange
     * @return
     */
    @RequiresPermissions("finance:ft:orgValueSubtract:add")
    @Log(title = "资产变动单主档", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceFtChange financeFtChange)
    {
        return toAjax(financeFtChangeService.insertFinanceFtChange(financeFtChange));
    }

    /**
     * 修改资产变动单主档
     * @author 116524
     * @date 2023-07-04
     * @param financeFtChange
     * @return
     */
    @RequiresPermissions("finance:ft:orgValueSubtract:edit")
    @Log(title = "资产变动单主档", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceFtChange financeFtChange)
    {
        return toAjax(financeFtChangeService.updateFinanceFtChange(financeFtChange));
    }

    /**
     * 删除资产变动单主档
     * @author 116524
     * @date 2023-07-04
     * @param uuids
     * @return
     */
    @RequiresPermissions("finance:ft:orgValueSubtract:remove")
    @Log(title = "资产变动单主档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(financeFtChangeService.deleteFinanceFtChangeByUuids(uuids));
    }

}
