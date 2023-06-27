package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.PersonHoliday;
import com.jlkj.human.hd.service.IPersonHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 员工请假记录Controller
 *
 * @author jiangbingchen
 * @date 2023-04-14
 */
@RestController
@RequestMapping("/personHoliday")
public class PersonHolidayController extends BaseController
{
    @Autowired
    private IPersonHolidayService personHolidayService;

    /**
     * 查询员工请假记录列表
     */
    @RequiresPermissions("human:personHoliday:list")
    @GetMapping("/list")
    public TableDataInfo list(PersonHoliday personHoliday)
    {
        startPage();
        List<PersonHoliday> list = personHolidayService.selectPersonHolidayList(personHoliday);
        return getDataTable(list);
    }

    /**
     * 导出员工请假记录列表
     */
    @RequiresPermissions("human:personHoliday:export")
    @Log(title = "员工请假记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PersonHoliday personHoliday)
    {
        List<PersonHoliday> list = personHolidayService.selectPersonHolidayList(personHoliday);
        ExcelUtil<PersonHoliday> util = new ExcelUtil<PersonHoliday>(PersonHoliday.class);
        util.exportExcel(response, list, "员工请假记录数据");
    }

    /**
     * 获取员工请假记录详细信息
     */
    @RequiresPermissions("human:personHoliday:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(personHolidayService.selectPersonHolidayById(id));
    }

    /**
     * 新增员工请假记录
     */
    @RequiresPermissions("human:personHoliday:add")
    @Log(title = "员工请假记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PersonHoliday personHoliday)
    {
        return toAjax(personHolidayService.insertPersonHoliday(personHoliday));
    }

    /**
     * 修改员工请假记录
     */
    @RequiresPermissions("human:personHoliday:edit")
    @Log(title = "员工请假记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PersonHoliday personHoliday)
    {
        return toAjax(personHolidayService.updatePersonHoliday(personHoliday));
    }

    /**
     * 删除员工请假记录
     */
    @RequiresPermissions("human:personHoliday:remove")
    @Log(title = "员工请假记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(personHolidayService.deletePersonHolidayByIds(ids));
    }

    /**
    * @Description 获取员工当年当月最新请假信息
    * @param
    * @return
    * @author 266861
    * @date 2023/6/27 10:04
    **/
    @GetMapping("/getEmpData")
    public AjaxResult getEmpData(PersonHoliday personHoliday)
    {
        List<PersonHoliday> list = personHolidayService.selectPersonHolidayList(personHoliday);
        return success(list);
    }
}
