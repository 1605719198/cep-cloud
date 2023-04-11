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
import com.jlkj.finance.gp.domain.FinanceGpAddfile;
import com.jlkj.finance.gp.service.IFinanceGpAddfileService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

import static com.jlkj.common.security.utils.SecurityUtils.getUsername;

/**
 * 厂商增户证照资料Controller
 *
 * @author 265799
 * @date 2023-04-10
 */
@RestController
@RequestMapping("/finance/addfile")
public class FinanceGpAddfileController extends BaseController
{
    @Autowired
    private IFinanceGpAddfileService financeGpAddfileService;

    /**
     * 查询厂商增户证照资料列表
     */
    @RequiresPermissions("gp:addfile:list")
    @GetMapping("/list")
    public TableDataInfo list(FinanceGpAddfile financeGpAddfile)
    {
        startPage();
        List<FinanceGpAddfile> list = financeGpAddfileService.selectFinanceGpAddfileList(financeGpAddfile);
        return getDataTable(list);
    }

    /**
     * 导出厂商增户证照资料列表
     */
    @RequiresPermissions("gp:addfile:export")
    @Log(title = "厂商增户证照资料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceGpAddfile financeGpAddfile)
    {
        List<FinanceGpAddfile> list = financeGpAddfileService.selectFinanceGpAddfileList(financeGpAddfile);
        ExcelUtil<FinanceGpAddfile> util = new ExcelUtil<FinanceGpAddfile>(FinanceGpAddfile.class);
        util.exportExcel(response, list, "厂商增户证照资料数据");
    }

    /**
     * 获取厂商增户证照资料详细信息
     */
    @RequiresPermissions("gp:addfile:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(financeGpAddfileService.selectFinanceGpAddfileByUuid(uuid));
    }

    /**
     * 新增厂商增户证照资料
     */
    @RequiresPermissions("gp:addfile:add")
    @Log(title = "厂商增户证照资料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FinanceGpAddfile financeGpAddfile)
    {

        financeGpAddfile.setCreateBy(getUsername());
        return toAjax(financeGpAddfileService.insertFinanceGpAddfile(financeGpAddfile));
    }

    /**
     * 修改厂商增户证照资料
     */
    @RequiresPermissions("gp:addfile:edit")
    @Log(title = "厂商增户证照资料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FinanceGpAddfile financeGpAddfile)
    {
        financeGpAddfile.setUpdateBy(getUsername());
        return toAjax(financeGpAddfileService.updateFinanceGpAddfile(financeGpAddfile));
    }

    /**
     * 删除厂商增户证照资料
     */
    @RequiresPermissions("gp:addfile:remove")
    @Log(title = "厂商增户证照资料", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(financeGpAddfileService.deleteFinanceGpAddfileByUuids(uuids));
    }
}
