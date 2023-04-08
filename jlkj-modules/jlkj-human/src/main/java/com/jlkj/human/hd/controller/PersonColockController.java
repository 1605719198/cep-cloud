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
import com.jlkj.human.hd.domain.PersonColock;
import com.jlkj.human.hd.service.IPersonColockService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 人员卡钟Controller
 *
 * @author 266861
 * @date 2023-03-29
 */
@RestController
@RequestMapping("/personColock")
public class PersonColockController extends BaseController
{
    @Autowired
    private IPersonColockService personColockService;

    /**
     * 查询人员卡钟列表
     */
    @RequiresPermissions("human:personColock:list")
    @GetMapping("/list")
    public TableDataInfo list(PersonColock personColock)
    {
        startPage();
        List<PersonColock> list = personColockService.selectPersonColockList(personColock);
        return getDataTable(list);
    }

    /**
     * 导出人员卡钟列表
     */
    @RequiresPermissions("human:personColock:export")
    @Log(title = "人员卡钟", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PersonColock personColock)
    {
        List<PersonColock> list = personColockService.selectPersonColockList(personColock);
        ExcelUtil<PersonColock> util = new ExcelUtil<PersonColock>(PersonColock.class);
        util.exportExcel(response, list, "人员卡钟数据");
    }

    /**
     * 获取人员卡钟详细信息
     */
    @RequiresPermissions("human:personColock:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(personColockService.selectPersonColockById(id));
    }

    /**
     * 新增人员卡钟
     */
    @RequiresPermissions("human:personColock:add")
    @Log(title = "人员卡钟", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PersonColock personColock) throws Exception
    {
        return toAjax(personColockService.insertPersonColock(personColock));
    }

    /**
     * 修改人员卡钟
     */
    @RequiresPermissions("human:personColock:edit")
    @Log(title = "人员卡钟", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PersonColock personColock)throws Exception
    {
        return toAjax(personColockService.updatePersonColock(personColock));
    }

    /**
     * 删除人员卡钟
     */
    @RequiresPermissions("human:personColock:remove")
    @Log(title = "人员卡钟", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(personColockService.deletePersonColockByIds(ids));
    }
}
