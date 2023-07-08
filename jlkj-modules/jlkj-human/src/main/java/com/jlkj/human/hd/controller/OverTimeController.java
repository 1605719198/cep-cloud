package com.jlkj.human.hd.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.OverTime;
import com.jlkj.human.hd.service.IOverTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 加班汇总（薪酬计算用）Controller
 * @Description: 加班汇总（薪酬计算用）Controller
 * @author 266861
 * @date 2023-06-28
 * @version:V1.0
 */
@RestController
@RequestMapping("/overTime")
public class OverTimeController extends BaseController
{
    @Autowired
    private IOverTimeService overTimeService;

    /**
     * 查询加班汇总（薪酬计算用）列表
     * @author 266861
     * @date 2023-06-28
     * @param overTime 加班汇总表查询参数
     * @return 加班汇总列表
     */
    @GetMapping("/list")
    public TableDataInfo list(OverTime overTime)
    {
        startPage();
        List<OverTime> list = overTimeService.selectOverTimeList(overTime);
        return getDataTable(list);
    }

    /**
     * 获取加班汇总（薪酬计算用）详细信息
     * @author 266861
     * @date 2023-06-28
     * @param id 加班汇总表id
     * @return 加班汇总数据详情
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(overTimeService.selectOverTimeById(id));
    }

    /**
     * 新增加班汇总（薪酬计算用）
     * @author 266861
     * @date 2023-06-28
     * @param overTimeList 新增加班汇总数据
     * @return 新增结果
     */
    @Log(title = "加班汇总（薪酬计算用）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody List<OverTime> overTimeList)
    {
        return toAjax(overTimeService.insertOverTime(overTimeList));
    }

    /**
     * 修改加班汇总（薪酬计算用）
     * @author 266861
     * @date 2023-06-28
     * @param overTime 修改加班汇总数据
     * @return 修改结果
     */
    @Log(title = "加班汇总（薪酬计算用）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OverTime overTime)
    {
        return toAjax(overTimeService.updateOverTime(overTime));
    }

    /**
     * 删除加班汇总（薪酬计算用）
     * @author 266861
     * @date 2023-06-28
     * @param ids 加班汇总删除id数组
     * @return 删除结果
     */
    @Log(title = "加班汇总（薪酬计算用）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(overTimeService.deleteOverTimeByIds(ids));
    }
}
