package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.EnterpriseRevenueRate;
import com.jlkj.human.hs.service.IEnterpriseRevenueRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 企业效益系数设定Controller
 *
 * @author 267383
 * @date 2023-06-07
 */
@RestController
@RequestMapping("/enterpriseRevenueRate")
public class EnterpriseRevenueRateController extends BaseController
{
    @Autowired
    private IEnterpriseRevenueRateService enterpriseRevenueRateService;

    /**
     * 查询企业效益系数设定列表
     */
    @RequiresPermissions("human:enterpriseRevenueRate:list")
    @GetMapping("/list")
    public TableDataInfo list(EnterpriseRevenueRate enterpriseRevenueRate)
    {
        startPage();
        List<EnterpriseRevenueRate> list = enterpriseRevenueRateService.selectEnterpriseRevenueRateList(enterpriseRevenueRate);
        return getDataTable(list);
    }

    /**
     * 导出企业效益系数设定列表
     */
    @RequiresPermissions("human:enterpriseRevenueRate:export")
    @Log(title = "企业效益系数设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EnterpriseRevenueRate enterpriseRevenueRate)
    {
        List<EnterpriseRevenueRate> list = enterpriseRevenueRateService.selectEnterpriseRevenueRateList(enterpriseRevenueRate);
        ExcelUtil<EnterpriseRevenueRate> util = new ExcelUtil<EnterpriseRevenueRate>(EnterpriseRevenueRate.class);
        util.exportExcel(response, list, "企业效益系数设定数据");
    }

    /**
     * 获取企业效益系数设定详细信息
     */
    @RequiresPermissions("human:enterpriseRevenueRate:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(enterpriseRevenueRateService.selectEnterpriseRevenueRateById(id));
    }

    /**
     * 新增企业效益系数设定
     */
    @RequiresPermissions("human:enterpriseRevenueRate:add")
    @Log(title = "企业效益系数设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EnterpriseRevenueRate enterpriseRevenueRate)
    {
        return toAjax(enterpriseRevenueRateService.insertEnterpriseRevenueRate(enterpriseRevenueRate));
    }

    /**
     * 修改企业效益系数设定
     */
    @RequiresPermissions("human:enterpriseRevenueRate:edit")
    @Log(title = "企业效益系数设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EnterpriseRevenueRate enterpriseRevenueRate)
    {
        return toAjax(enterpriseRevenueRateService.updateEnterpriseRevenueRate(enterpriseRevenueRate));
    }

    /**
     * 删除企业效益系数设定
     */
    @RequiresPermissions("human:enterpriseRevenueRate:remove")
    @Log(title = "企业效益系数设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(enterpriseRevenueRateService.deleteEnterpriseRevenueRateByIds(ids));
    }
}
