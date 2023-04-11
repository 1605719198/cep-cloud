package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.HomeLeaveHoliday;
import com.jlkj.human.hd.service.IHomeLeaveHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 探亲假天数设定Controller
 *
 * @author jiangbingchen
 * @date 2023-04-08
 */
@RestController
@RequestMapping("/holiday")
public class HomeLeaveHolidayController extends BaseController
{
    @Autowired
    private IHomeLeaveHolidayService homeLeaveHolidayService;

    /**
     * 查询探亲假天数设定列表
     */
    @RequiresPermissions("human:holiday:list")
    @GetMapping("/list")
    public TableDataInfo list(HomeLeaveHoliday homeLeaveHoliday)
    {
        startPage();
        List<HomeLeaveHoliday> list = homeLeaveHolidayService.selectHomeLeaveHolidayList(homeLeaveHoliday);
        return getDataTable(list);
    }

    /**
     * 导出探亲假天数设定列表
     */
    @RequiresPermissions("human:holiday:export")
    @Log(title = "探亲假天数设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HomeLeaveHoliday homeLeaveHoliday)
    {
        List<HomeLeaveHoliday> list = homeLeaveHolidayService.selectHomeLeaveHolidayList(homeLeaveHoliday);
        ExcelUtil<HomeLeaveHoliday> util = new ExcelUtil<HomeLeaveHoliday>(HomeLeaveHoliday.class);
        util.exportExcel(response, list, "探亲假天数设定数据");
    }

    /**
     * 获取探亲假天数设定详细信息
     */
    @RequiresPermissions("human:holiday:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(homeLeaveHolidayService.selectHomeLeaveHolidayById(id));
    }

    /**
     * 新增探亲假天数设定
     */
    @RequiresPermissions("human:holiday:add")
    @Log(title = "探亲假天数设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HomeLeaveHoliday homeLeaveHoliday)
    {
        return toAjax(homeLeaveHolidayService.insertHomeLeaveHoliday(homeLeaveHoliday));
    }

    /**
     * 修改探亲假天数设定
     */
    @RequiresPermissions("human:holiday:edit")
    @Log(title = "探亲假天数设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HomeLeaveHoliday homeLeaveHoliday)
    {
        return toAjax(homeLeaveHolidayService.updateHomeLeaveHoliday(homeLeaveHoliday));
    }

    /**
     * 删除探亲假天数设定
     */
    @RequiresPermissions("human:holiday:remove")
    @Log(title = "探亲假天数设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(homeLeaveHolidayService.deleteHomeLeaveHolidayByIds(ids));
    }
}
