package com.jlkj.human.hd.controller;

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
import com.jlkj.human.hd.domain.TripAccountRule;
import com.jlkj.human.hd.service.ITripAccountRuleService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 出差会计科目Controller
 *
 * @author 266861
 * @date 2023-04-23
 */
@RestController
@RequestMapping("/tripAccountRule")
public class TripAccountRuleController extends BaseController
{
    @Autowired
    private ITripAccountRuleService tripAccountRuleService;

    /**
     * 查询出差会计科目列表
     */
    @RequiresPermissions("human:tripAccountRule:list")
    @GetMapping("/list")
    public TableDataInfo list(TripAccountRule tripAccountRule)
    {
        startPage();
        List<TripAccountRule> list = tripAccountRuleService.selectTripAccountRuleList(tripAccountRule);
        return getDataTable(list);
    }

    /**
     * 导出出差会计科目列表
     */
    @RequiresPermissions("human:tripAccountRule:export")
    @Log(title = "出差会计科目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TripAccountRule tripAccountRule)
    {
        List<TripAccountRule> list = tripAccountRuleService.selectTripAccountRuleList(tripAccountRule);
        ExcelUtil<TripAccountRule> util = new ExcelUtil<TripAccountRule>(TripAccountRule.class);
        util.exportExcel(response, list, "出差会计科目数据");
    }

    /**
     * 获取出差会计科目详细信息
     */
    @RequiresPermissions("human:tripAccountRule:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(tripAccountRuleService.selectTripAccountRuleById(id));
    }

    /**
     * 新增出差会计科目
     */
    @RequiresPermissions("human:tripAccountRule:add")
    @Log(title = "出差会计科目", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TripAccountRule tripAccountRule)
    {
        return toAjax(tripAccountRuleService.insertTripAccountRule(tripAccountRule));
    }

    /**
     * 修改出差会计科目
     */
    @RequiresPermissions("human:tripAccountRule:edit")
    @Log(title = "出差会计科目", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TripAccountRule tripAccountRule)
    {
        return toAjax(tripAccountRuleService.updateTripAccountRule(tripAccountRule));
    }

    /**
     * 删除出差会计科目
     */
    @RequiresPermissions("human:tripAccountRule:remove")
    @Log(title = "出差会计科目", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(tripAccountRuleService.deleteTripAccountRuleByIds(ids));
    }
}
