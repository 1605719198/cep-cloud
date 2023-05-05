package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.PersonHolidayCancel;
import com.jlkj.human.hd.service.IPersonHolidayCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 员工销假Controller
 *
 * @author 266861
 * @date 2023-04-21
 */
@RestController
@RequestMapping("/cancelHoliday")
public class PersonHolidayCancelController extends BaseController
{
    @Autowired
    private IPersonHolidayCancelService personHolidayCancelService;

    /**
     * 查询员工销假列表
     */
    @RequiresPermissions("human:cancelHoliday:list")
    @GetMapping("/list")
    public TableDataInfo list(PersonHolidayCancel personHolidayCancel)
    {
        startPage();
        List<PersonHolidayCancel> list = personHolidayCancelService.selectPersonHolidayCancelList(personHolidayCancel);
        return getDataTable(list);
    }

    /**
     * 导出员工销假列表
     */
    @RequiresPermissions("human:cancelHoliday:export")
    @Log(title = "员工销假", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PersonHolidayCancel personHolidayCancel)
    {
        List<PersonHolidayCancel> list = personHolidayCancelService.selectPersonHolidayCancelList(personHolidayCancel);
        ExcelUtil<PersonHolidayCancel> util = new ExcelUtil<PersonHolidayCancel>(PersonHolidayCancel.class);
        util.exportExcel(response, list, "员工销假数据");
    }

    /**
     * 获取员工销假详细信息
     */
    @RequiresPermissions("human:cancelHoliday:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(personHolidayCancelService.selectPersonHolidayCancelById(id));
    }

    /**
     * 新增员工销假
     */
    @RequiresPermissions("human:cancelHoliday:add")
    @Log(title = "员工销假", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PersonHolidayCancel personHolidayCancel)
    {
        return toAjax(personHolidayCancelService.insertPersonHolidayCancel(personHolidayCancel));
    }

    /**
     * 修改员工销假
     */
    @RequiresPermissions("human:cancelHoliday:edit")
    @Log(title = "员工销假", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PersonHolidayCancel personHolidayCancel)
    {
        return toAjax(personHolidayCancelService.updatePersonHolidayCancel(personHolidayCancel));
    }

    /**
     * 删除员工销假
     */
    @RequiresPermissions("human:cancelHoliday:remove")
    @Log(title = "员工销假", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(personHolidayCancelService.deletePersonHolidayCancelByIds(ids));
    }

    /**
     * 撤回员工销假
     */
    @RequiresPermissions("human:cancelHoliday:withdraw")
    @Log(title = "员工销假", businessType = BusinessType.OTHER)
    @PostMapping("/withdraw")
    public AjaxResult withdraw(@RequestBody String ids)
    {
        System.out.println(ids);
        return toAjax(personHolidayCancelService.otherPersonHolidayCancelByIds(ids));
    }
}
