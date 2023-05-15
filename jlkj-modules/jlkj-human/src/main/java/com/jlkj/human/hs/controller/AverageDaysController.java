package com.jlkj.human.hs.controller;

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
import com.jlkj.human.hs.domain.AverageDays;
import com.jlkj.human.hs.service.IAverageDaysService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 各公司平均天数设定Controller
 *
 * @author 266861
 * @date 2023-05-12
 */
@RestController
@RequestMapping("/averageDays")
public class AverageDaysController extends BaseController
{
    @Autowired
    private IAverageDaysService averageDaysService;

    /**
     * 查询各公司平均天数设定列表
     */
    @RequiresPermissions("human:averageDays:list")
    @GetMapping("/list")
    public TableDataInfo list(AverageDays averageDays)
    {
        startPage();
        List<AverageDays> list = averageDaysService.selectAverageDaysList(averageDays);
        return getDataTable(list);
    }

    /**
     * 导出各公司平均天数设定列表
     */
    @RequiresPermissions("human:averageDays:export")
    @Log(title = "各公司平均天数设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AverageDays averageDays)
    {
        List<AverageDays> list = averageDaysService.selectAverageDaysList(averageDays);
        ExcelUtil<AverageDays> util = new ExcelUtil<AverageDays>(AverageDays.class);
        util.exportExcel(response, list, "各公司平均天数设定数据");
    }

    /**
     * 获取各公司平均天数设定详细信息
     */
    @RequiresPermissions("human:averageDays:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(averageDaysService.selectAverageDaysByUuid(uuid));
    }

    /**
     * 新增各公司平均天数设定
     */
    @RequiresPermissions("human:averageDays:add")
    @Log(title = "各公司平均天数设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AverageDays averageDays)
    {
        return toAjax(averageDaysService.insertAverageDays(averageDays));
    }

    /**
     * 修改各公司平均天数设定
     */
    @RequiresPermissions("human:averageDays:edit")
    @Log(title = "各公司平均天数设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AverageDays averageDays)
    {
        return toAjax(averageDaysService.updateAverageDays(averageDays));
    }

    /**
     * 删除各公司平均天数设定
     */
    @RequiresPermissions("human:averageDays:remove")
    @Log(title = "各公司平均天数设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(averageDaysService.deleteAverageDaysByUuids(uuids));
    }
}
