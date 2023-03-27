package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.Clockwork;
import com.jlkj.human.hd.service.IClockworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 公司卡钟设定Controller
 *
 * @author 266861
 * @date 2023-03-21
 */
@RestController
@RequestMapping("/clockwork")
public class ClockworkController extends BaseController
{
    @Autowired
    private IClockworkService clockworkService;

    /**
     * 查询公司卡钟设定列表
     */
    @RequiresPermissions("human:clockwork:list")
    @GetMapping("/list")
    public TableDataInfo list(Clockwork clockwork)
    {
        startPage();
        List<Clockwork> list = clockworkService.selectClockworkList(clockwork);
        return getDataTable(list);
    }

    /**
     * 导出公司卡钟设定列表
     */
    @RequiresPermissions("human:clockwork:export")
    @Log(title = "公司卡钟设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Clockwork clockwork)
    {
        List<Clockwork> list = clockworkService.selectClockworkList(clockwork);
        ExcelUtil<Clockwork> util = new ExcelUtil<Clockwork>(Clockwork.class);
        util.exportExcel(response, list, "公司卡钟设定数据");
    }

    /**
     * 获取公司卡钟设定详细信息
     */
    @RequiresPermissions("human:clockwork:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(clockworkService.selectClockworkById(id));
    }

    /**
     * 新增公司卡钟设定
     */
    @RequiresPermissions("human:clockwork:add")
    @Log(title = "公司卡钟设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Clockwork clockwork) throws Exception
    {
        return toAjax(clockworkService.insertClockwork(clockwork));
    }

    /**
     * 修改公司卡钟设定
     */
    @RequiresPermissions("human:clockwork:edit")
    @Log(title = "公司卡钟设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Clockwork clockwork) throws Exception
    {
        return toAjax(clockworkService.updateClockwork(clockwork));
    }

    /**
     * 删除公司卡钟设定
     */
    @RequiresPermissions("human:clockwork:remove")
    @Log(title = "公司卡钟设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(clockworkService.deleteClockworkByIds(ids));
    }
}
