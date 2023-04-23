package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.OvertimeWorkSetting;
import com.jlkj.human.hd.service.IOvertimeWorkSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 加班补休参数设定Controller
 *
 * @author 266861
 * @date 2023-03-29
 */
@RestController
@RequestMapping("/overtimeWorksetting")
public class OvertimeWorkSettingController extends BaseController
{
    @Autowired
    private IOvertimeWorkSettingService overtimeWorkSettingService;

    /**
     * 查询加班补休参数设定列表
     */
    @RequiresPermissions("human:overtimeWorksetting:list")
    @GetMapping("/list")
    public TableDataInfo list(OvertimeWorkSetting overtimeWorkSetting)
    {
        startPage();
        List<OvertimeWorkSetting> list = overtimeWorkSettingService.selectOvertimeWorkSettingList(overtimeWorkSetting);
        return getDataTable(list);
    }

    /**
     * 导出加班补休参数设定列表
     */
    @RequiresPermissions("human:overtimeWorksetting:export")
    @Log(title = "加班补休参数设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OvertimeWorkSetting overtimeWorkSetting)
    {
        List<OvertimeWorkSetting> list = overtimeWorkSettingService.selectOvertimeWorkSettingList(overtimeWorkSetting);
        ExcelUtil<OvertimeWorkSetting> util = new ExcelUtil<OvertimeWorkSetting>(OvertimeWorkSetting.class);
        util.exportExcel(response, list, "加班补休参数设定数据");
    }

    /**
     * 获取加班补休参数设定详细信息
     */
    @RequiresPermissions("human:overtimeWorksetting:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(overtimeWorkSettingService.selectOvertimeWorkSettingById(id));
    }

    /**
     * 新增加班补休参数设定
     */
    @RequiresPermissions("human:overtimeWorksetting:add")
    @Log(title = "加班补休参数设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OvertimeWorkSetting overtimeWorkSetting)
    {
        return toAjax(overtimeWorkSettingService.insertOvertimeWorkSetting(overtimeWorkSetting));
    }

    /**
     * 修改加班补休参数设定
     */
    @RequiresPermissions("human:overtimeWorksetting:edit")
    @Log(title = "加班补休参数设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OvertimeWorkSetting overtimeWorkSetting)
    {
        return toAjax(overtimeWorkSettingService.updateOvertimeWorkSetting(overtimeWorkSetting));
    }

    /**
     * 删除加班补休参数设定
     */
    @RequiresPermissions("human:overtimeWorksetting:remove")
    @Log(title = "加班补休参数设定", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(overtimeWorkSettingService.deleteOvertimeWorkSettingByIds(ids));
    }
}
