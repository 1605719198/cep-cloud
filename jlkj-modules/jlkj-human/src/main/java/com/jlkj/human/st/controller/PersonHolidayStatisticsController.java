package com.jlkj.human.st.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.st.domain.PersonHolidayStatistics;
import com.jlkj.human.st.service.IPersonHolidayStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 员工请假统计Controller
 * @Description:
 * @author jiangbingchen
 * @date 2023-07-03
 * @version:V1.0
 */
@RestController
@RequestMapping("/personHolidayStatistics")
public class PersonHolidayStatisticsController extends BaseController
{
    @Autowired
    private IPersonHolidayStatisticsService personHolidayStatisticsService;

    /**
     * 查询员工请假统计列表
     * @author jiangbingchen
     * @date 2023-07-03
     * @param personHolidayStatistics
     * @return
     */
    @RequiresPermissions("human:personHolidayStatistics:list")
    @GetMapping("/list")
    public TableDataInfo list(PersonHolidayStatistics personHolidayStatistics)
    {
        startPage();
        List<PersonHolidayStatistics> list = personHolidayStatisticsService.selectPersonHolidayStatisticsList(personHolidayStatistics);
        return getDataTable(list);
    }

    /**
     * 导出员工请假统计列表
     * @author jiangbingchen
     * @date 2023-07-03
     * @param personHolidayStatistics
     * @return
     */
    @RequiresPermissions("human:personHolidayStatistics:export")
    @Log(title = "员工请假统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PersonHolidayStatistics personHolidayStatistics)
    {
        List<PersonHolidayStatistics> list = personHolidayStatisticsService.selectPersonHolidayStatisticsList(personHolidayStatistics);
        ExcelUtil<PersonHolidayStatistics> util = new ExcelUtil<PersonHolidayStatistics>(PersonHolidayStatistics.class);
        util.exportExcel(response, list, "员工请假统计数据");
    }

    /**
     * 获取员工请假统计详细信息
     * @author jiangbingchen
     * @date 2023-07-03
     * @param id
     * @return
     */
    @RequiresPermissions("human:personHolidayStatistics:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(personHolidayStatisticsService.selectPersonHolidayStatisticsById(id));
    }

    /**
     * 新增员工请假统计
     * @author jiangbingchen
     * @date 2023-07-03
     * @param personHolidayStatistics
     * @return
     */
    @RequiresPermissions("human:personHolidayStatistics:add")
    @Log(title = "员工请假统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PersonHolidayStatistics personHolidayStatistics)
    {
        return toAjax(personHolidayStatisticsService.insertPersonHolidayStatistics(personHolidayStatistics));
    }

    /**
     * 修改员工请假统计
     * @author jiangbingchen
     * @date 2023-07-03
     * @param personHolidayStatistics
     * @return
     */
    @RequiresPermissions("human:personHolidayStatistics:edit")
    @Log(title = "员工请假统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PersonHolidayStatistics personHolidayStatistics)
    {
        return toAjax(personHolidayStatisticsService.updatePersonHolidayStatistics(personHolidayStatistics));
    }

    /**
     * 删除员工请假统计
     * @author jiangbingchen
     * @date 2023-07-03
     * @param ids
     * @return
     */
    @RequiresPermissions("human:personHolidayStatistics:remove")
    @Log(title = "员工请假统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(personHolidayStatisticsService.deletePersonHolidayStatisticsByIds(ids));
    }
}
