package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.YearRate;
import com.jlkj.human.hs.service.IYearRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 年度奖金比例设定Controller
 *
 * @author 267383
 * @date 2023-06-04
 */
@RestController
@RequestMapping("/yearRate")
public class YearRateController extends BaseController
{
    @Autowired
    private IYearRateService yearRateService;

    /**
     * 查询年度奖金比例设定列表
     */
    @RequiresPermissions("human:yearRate:list")
    @GetMapping("/list")
    public TableDataInfo list(YearRate yearRate)
    {
        startPage();
        List<YearRate> list = yearRateService.selectYearRateList(yearRate);
        return getDataTable(list);
    }

    /**
     * 导出年度奖金比例设定列表
     */
    @RequiresPermissions("human:yearRate:export")
    @Log(title = "年度奖金比例设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, YearRate yearRate)
    {
        List<YearRate> list = yearRateService.selectYearRateList(yearRate);
        ExcelUtil<YearRate> util = new ExcelUtil<YearRate>(YearRate.class);
        util.exportExcel(response, list, "年度奖金比例设定数据");
    }

    /**
     * 获取年度奖金比例设定详细信息
     */
    @RequiresPermissions("human:yearRate:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(yearRateService.selectYearRateById(id));
    }

    /**
     * 保存年度奖金比例设定
     */
    @RequiresPermissions("human:yearRate:add")
    @Log(title = "年度奖金比例设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody YearRate yearRate)
    {
        return toAjax(yearRateService.insertYearRate(yearRate));
    }

    /**
     * 修改年度奖金比例设定
     */
    @RequiresPermissions("human:yearRate:edit")
    @Log(title = "年度奖金比例设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody YearRate yearRate)
    {
        return toAjax(yearRateService.updateYearRate(yearRate));
    }

    /**
     * 删除年度奖金比例设定
     */
    @RequiresPermissions("human:yearRate:remove")
    @Log(title = "年度奖金比例设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(yearRateService.deleteYearRateByIds(ids));
    }
}
