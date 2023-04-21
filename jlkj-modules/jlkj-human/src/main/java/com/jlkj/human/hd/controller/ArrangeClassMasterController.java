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
import com.jlkj.human.hd.domain.ArrangeClassMaster;
import com.jlkj.human.hd.service.IArrangeClassMasterService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 排班记录Controller
 *
 * @author 266861
 * @date 2023-04-10
 */
@RestController
@RequestMapping("/arrangeClassMaster")
public class ArrangeClassMasterController extends BaseController
{
    @Autowired
    private IArrangeClassMasterService arrangeClassMasterService;

    /**
     * 查询排班记录列表
     */
    @RequiresPermissions("human:arrangeClassMaster:list")
    @GetMapping("/list")
    public TableDataInfo list(ArrangeClassMaster arrangeClassMaster)
    {
        startPage();
        List<ArrangeClassMaster> list = arrangeClassMasterService.selectArrangeClassMasterList(arrangeClassMaster);
        return getDataTable(list);
    }

    /**
     * 导出排班记录列表
     */
    @RequiresPermissions("human:arrangeClassMaster:export")
    @Log(title = "排班记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ArrangeClassMaster arrangeClassMaster)
    {
        List<ArrangeClassMaster> list = arrangeClassMasterService.selectArrangeClassMasterList(arrangeClassMaster);
        ExcelUtil<ArrangeClassMaster> util = new ExcelUtil<ArrangeClassMaster>(ArrangeClassMaster.class);
        util.exportExcel(response, list, "排班记录数据");
    }

    /**
     * 获取排班记录详细信息
     */
    @RequiresPermissions("human:arrangeClassMaster:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(arrangeClassMasterService.selectArrangeClassMasterById(id));
    }

    /**
     * 新增排班记录
     */
    @RequiresPermissions("human:arrangeClassMaster:add")
    @Log(title = "排班记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ArrangeClassMaster arrangeClassMaster)
    {
        return toAjax(arrangeClassMasterService.insertArrangeClassMaster(arrangeClassMaster));
    }

    /**
     * 修改排班记录
     */
    @RequiresPermissions("human:arrangeClassMaster:edit")
    @Log(title = "排班记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ArrangeClassMaster arrangeClassMaster)
    {
        return toAjax(arrangeClassMasterService.updateArrangeClassMaster(arrangeClassMaster));
    }

    /**
     * 删除排班记录
     */
    @RequiresPermissions("human:arrangeClassMaster:remove")
    @Log(title = "排班记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(arrangeClassMasterService.deleteArrangeClassMasterByIds(ids));
    }
}
