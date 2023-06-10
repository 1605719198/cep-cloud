package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.HumanHsAmt;
import com.jlkj.human.hs.domain.HumanHsAmtDetail;
import com.jlkj.human.hs.service.IHumanHsAmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 薪资应付作业Controller
 *
 * @author jlkj
 * @date 2023-05-24
 */
@RestController
@RequestMapping("/payAmt")
public class HumanHsAmtController extends BaseController
{
    @Autowired
    private IHumanHsAmtService humanHsAmtService;

    /**
     * 查询薪资应付作业列表
     */
    @RequiresPermissions("human:payAmt:list")
    @GetMapping("/list")
    public TableDataInfo list(HumanHsAmt humanHsAmt)
    {
        startPage();
        List<HumanHsAmt> list = humanHsAmtService.selectHumanHsAmtList(humanHsAmt);
        return getDataTable(list);
    }

    /**
     * 导出薪资应付作业列表
     */
    @RequiresPermissions("human:payAmt:export")
    @Log(title = "薪资应付作业", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HumanHsAmt humanHsAmt)
    {
        List<HumanHsAmt> list = humanHsAmtService.selectHumanHsAmtList(humanHsAmt);
        ExcelUtil<HumanHsAmt> util = new ExcelUtil<HumanHsAmt>(HumanHsAmt.class);
        util.exportExcel(response, list, "薪资应付作业数据");
    }

    /**
     * 获取薪资应付作业详细信息
     */
    @RequiresPermissions("human:payAmt:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(humanHsAmtService.selectHumanHsAmtById(id));
    }

    /**
     * 新增薪资应付作业
     */
    @RequiresPermissions("human:payAmt:add")
    @Log(title = "薪资应付作业", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HumanHsAmt humanHsAmt)
    {
        return toAjax(humanHsAmtService.insertHumanHsAmt(humanHsAmt));
    }

    /**
     * 修改薪资应付作业
     */
    @RequiresPermissions("human:payAmt:edit")
    @Log(title = "薪资应付作业", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HumanHsAmt humanHsAmt)
    {
        return toAjax(humanHsAmtService.updateHumanHsAmt(humanHsAmt));
    }

    /**
     * 删除薪资应付作业
     */
    @RequiresPermissions("human:payAmt:remove")
    @Log(title = "薪资应付作业", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(humanHsAmtService.deleteHumanHsAmtByIds(ids));
    }




    /**
     * 查询薪资应付明细项
     */
    @RequiresPermissions("human:payAmt:add")
    @PostMapping(value = "/detaillist")
    public List<HumanHsAmtDetail> detaillist(@RequestBody HumanHsAmt humanHsAmt)
    {
        List<HumanHsAmtDetail> list =  humanHsAmtService.getDetailAmtList(humanHsAmt);
        return  list;
    }
}
