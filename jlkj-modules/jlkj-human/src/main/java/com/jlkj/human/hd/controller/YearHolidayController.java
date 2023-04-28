package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hd.domain.YearHoliday;
import com.jlkj.human.hd.service.IYearHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 年休假天数设定Controller
 *
 * @author jiangbingchen
 * @date 2023-04-12
 */
@RestController
@RequestMapping("/holiday/year")
public class YearHolidayController extends BaseController
{
    @Autowired
    private IYearHolidayService yearHolidayService;

    /**
     * 查询年休假天数设定列表
     */
    @RequiresPermissions("human:yearHoliday:list")
    @GetMapping("/list")
    public TableDataInfo list(YearHoliday yearHoliday)
    {
        startPage();
        List<YearHoliday> list = yearHolidayService.selectYearHolidayList(yearHoliday);
        return getDataTable(list);
    }

    /**
     * 导出年休假天数设定列表
     */
    @RequiresPermissions("human:yearHoliday:export")
    @Log(title = "年休假天数设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, YearHoliday yearHoliday)
    {
        List<YearHoliday> list = yearHolidayService.selectYearHolidayList(yearHoliday);
        ExcelUtil<YearHoliday> util = new ExcelUtil<YearHoliday>(YearHoliday.class);
        util.exportExcel(response, list, "年休假天数设定数据");
    }

    /**
     * 获取年休假天数设定详细信息
     */
    @RequiresPermissions("human:yearHoliday:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(yearHolidayService.selectYearHolidayById(id));
    }

    /**
     * 新增年休假天数设定
     */
    @RequiresPermissions("human:yearHoliday:add")
    @Log(title = "年休假天数设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add( YearHoliday yearHoliday)
    {
        return toAjax(yearHolidayService.insertYearHoliday(yearHoliday));
    }

    /**
     * 查询员工年休假信息
     */
    @GetMapping("/year")
    public AjaxResult year( YearHoliday yearHoliday)
    {
        return success(yearHolidayService.selectYearHolidayByempNo(yearHoliday));
    }

    /**
     * 修改年休假天数设定
     */
    @RequiresPermissions("human:yearHoliday:edit")
    @Log(title = "年休假天数设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody YearHoliday yearHoliday)
    {
        return toAjax(yearHolidayService.updateYearHoliday(yearHoliday));
    }

    /**
     * 删除年休假天数设定
     */
    @RequiresPermissions("human:yearHoliday:remove")
    @Log(title = "年休假天数设定", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(yearHolidayService.deleteYearHolidayByIds(ids));
    }

    @Log(title = "年休假资料导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("human:yearHoliday:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<YearHoliday> util = new ExcelUtil<YearHoliday>(YearHoliday.class);
        List<YearHoliday> yearHolidayList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = yearHolidayService.importUser(yearHolidayList, updateSupport, operName);
        return success(message);
    }

    /**
     * 导入年休假数据
     * @param response
     * @throws IOException
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<YearHoliday> util = new ExcelUtil<YearHoliday>(YearHoliday.class);
        util.importTemplateExcel(response, "年休假数据");
    }
}
