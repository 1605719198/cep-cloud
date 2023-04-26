package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.TripDayRule;
import com.jlkj.human.hd.service.ITripDayRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 出差日标准维护Controller
 *
 * @author 266861
 * @date 2023-04-22
 */
@RestController
@RequestMapping("/tripDayRule")
public class TripDayRuleController extends BaseController
{
    @Autowired
    private ITripDayRuleService tripDayRuleService;

    /**
     * 查询出差日标准维护列表
     */
    @RequiresPermissions("human:tripDayRule:list")
    @GetMapping("/list")
    public TableDataInfo list(TripDayRule tripDayRule)
    {
        startPage();
        List<TripDayRule> list = tripDayRuleService.selectTripDayRuleList(tripDayRule);
        return getDataTable(list);
    }

    /**
     * 查询出差日标准地区列表
     */
    @RequiresPermissions("human:tripDayRule:list")
    @GetMapping("/listArea")
    public TableDataInfo listArea(TripDayRule tripDayRule)
    {
        List<TripDayRule> list = tripDayRuleService.selectTripDayRuleArea(tripDayRule);
        return getDataTable(list);
    }

    /**
     * 导出出差日标准维护列表
     */
    @RequiresPermissions("human:tripDayRule:export")
    @Log(title = "出差日标准维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TripDayRule tripDayRule)
    {
        List<TripDayRule> list = tripDayRuleService.selectTripDayRuleList(tripDayRule);
        ExcelUtil<TripDayRule> util = new ExcelUtil<TripDayRule>(TripDayRule.class);
        util.exportExcel(response, list, "出差日标准维护数据");
    }

    /**
     * 获取出差日标准维护详细信息
     */
    @RequiresPermissions("human:tripDayRule:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(tripDayRuleService.selectTripDayRuleById(id));
    }

    /**
     * 新增出差日标准维护
     */
    @RequiresPermissions("human:tripDayRule:add")
    @Log(title = "出差日标准维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TripDayRule tripDayRule)
    {
        return toAjax(tripDayRuleService.insertTripDayRule(tripDayRule));
    }

    /**
     * 修改出差日标准维护
     */
    @RequiresPermissions("human:tripDayRule:edit")
    @Log(title = "出差日标准维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TripDayRule tripDayRule)
    {
        return toAjax(tripDayRuleService.updateTripDayRule(tripDayRule));
    }

    /**
     * 删除出差日标准维护
     */
    @RequiresPermissions("human:tripDayRule:remove")
    @Log(title = "出差日标准维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(tripDayRuleService.deleteTripDayRuleByIds(ids));
    }
}
