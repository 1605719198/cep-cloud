package com.jlkj.finance.ft.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.jlkj.common.core.utils.DateUtils;
import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.finance.ao.domain.FinanceAoItemComp;
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
import com.jlkj.finance.ft.domain.FinanceFtChange;
import com.jlkj.finance.ft.service.IFinanceFtChangeService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;


/**
 * 资产变动单主档Controller
 * @Description:
 * @author 116524
 * @date 2023-07-04
 * @version:V1.0
 */
@RestController
@RequestMapping("/ft/orgValueAdd")
public class FinanceFtOrgValueAddController extends BaseController
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


    @RequiresPermissions("finance:ft:orgValueAdd:list")
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
    @RequiresPermissions("finance:ft:orgValueAdd:export")
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
    @RequiresPermissions("finance:ft:orgValueAdd:query")
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
    @RequiresPermissions("finance:ft:orgValueAdd:add")
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
    @RequiresPermissions("finance:ft:orgValueAdd:edit")
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
    @RequiresPermissions("finance:ft:orgValueAdd:remove")
    @Log(title = "资产变动单主档", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(financeFtChangeService.deleteFinanceFtChangeByUuids(uuids));
    }

}
