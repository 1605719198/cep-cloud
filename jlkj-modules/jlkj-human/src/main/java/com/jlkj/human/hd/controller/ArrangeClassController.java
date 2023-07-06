package com.jlkj.human.hd.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.ArrangeClass;
import com.jlkj.human.hd.service.IArrangeClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 排班明细Controller
 *
 * @author 266861
 * @date 2023-04-10
 */
@RestController
@RequestMapping("/arrangeClass")
public class ArrangeClassController extends BaseController
{
    @Autowired
    private IArrangeClassService arrangeClassService;

    /**
     * 查询排班明细列表
     */
    @RequiresPermissions("human:shiftMode:list")
    @GetMapping("/list")
    public TableDataInfo list(ArrangeClass arrangeClass)
    {
        List<ArrangeClass> list = arrangeClassService.selectArrangeClassList(arrangeClass);
        return getDataTable(list);
    }


    /**
     * 获取排班明细详细信息
     */
    @RequiresPermissions("human:shiftMode:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(arrangeClassService.selectArrangeClassById(id));
    }

    /**
     * 新增排班明细
     */
    @RequiresPermissions("human:shiftMode:add")
    @Log(title = "排班明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ArrangeClass arrangeClass)
    {
        return toAjax(arrangeClassService.insertArrangeClass(arrangeClass));
    }

    /**
     * 修改排班明细
     */
    @RequiresPermissions("human:shiftMode:edit")
    @Log(title = "排班明细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ArrangeClass arrangeClass)
    {
        return toAjax(arrangeClassService.updateArrangeClass(arrangeClass));
    }

    /**
     * 删除排班明细
     */
    @RequiresPermissions("human:shiftMode:remove")
    @Log(title = "排班明细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(arrangeClassService.deleteArrangeClassByIds(ids));
    }
}
