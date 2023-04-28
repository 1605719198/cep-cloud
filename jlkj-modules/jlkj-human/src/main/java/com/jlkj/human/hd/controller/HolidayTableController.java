package com.jlkj.human.hd.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.HolidayTable;
import com.jlkj.human.hd.dto.HolidayTableDTO;
import com.jlkj.human.hd.service.IHolidayTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 假日设定Controller
 *
 * @author 266861
 * @date 2023-04-10
 */
@RestController
@RequestMapping("/holidayTable")
public class HolidayTableController extends BaseController
{
    @Autowired
    private IHolidayTableService holidayTableService;

    /**
     * 查询假日设定列表
     */
    @RequiresPermissions("human:holidayTable:list")
    @GetMapping("/list")
    public TableDataInfo list(HolidayTable holidayTable)
    {
        List<HolidayTable> list = holidayTableService.selectHolidayTableList(holidayTable);
        return getDataTable(list);
    }

    /**
     * 查询时间段内假日设定
     */
    @GetMapping("/getHolidayTable")
    public TableDataInfo getHolidayTable(HolidayTableDTO holidayTable)
    {
        List<HolidayTableDTO> list = holidayTableService.selectHolidayTable(holidayTable);
        return getDataTable(list);
    }

    /**
     * 新增假日设定
     */
    @RequiresPermissions("human:holidayTable:add")
    @Log(title = "假日设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HolidayTable holidayTable)
    {
        return toAjax(holidayTableService.insertHolidayTable(holidayTable));
    }

    /**
     * 设置年度数据
     */
    @RequiresPermissions("human:holidayTable:add")
    @Log(title = "设置年度数据", businessType = BusinessType.INSERT)
    @PostMapping("/addYear")
    public AjaxResult addYear(@RequestBody HolidayTable holidayTable)
    {
        return toAjax(holidayTableService.setYearData(holidayTable));
    }

}
