package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.human.hd.domain.HomeLeaveHoliday;
import com.jlkj.human.hd.service.IHomeLeaveHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 探亲假天数设定Controller
 *
 * @author jiangbingchen
 * @date 2023-04-08
 */
@RestController
@RequestMapping("/homeLeaveHoliday")
public class HomeLeaveHolidayController extends BaseController
{
    @Autowired
    private IHomeLeaveHolidayService homeLeaveHolidayService;

    /**
     * 查询探亲假天数设定列表
     */
    @RequiresPermissions("human:homeLeaveHoliday:list")
    @GetMapping("/list")
    public TableDataInfo list(HomeLeaveHoliday homeLeaveHoliday)
    {
        startPage();
        List<HomeLeaveHoliday> list = homeLeaveHolidayService.selectHomeLeaveHolidayList(homeLeaveHoliday);
        return getDataTable(list);
    }



    /**
     * 获取探亲假天数设定详细信息
     */
    @RequiresPermissions("human:homeLeaveHoliday:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(homeLeaveHolidayService.selectHomeLeaveHolidayById(id));
    }

    /**
     * 新增探亲假天数设定
     */
    @RequiresPermissions("human:homeLeaveHoliday:add")
    @Log(title = "探亲假天数设定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HomeLeaveHoliday homeLeaveHoliday)
    {
        return toAjax(homeLeaveHolidayService.insertHomeLeaveHoliday(homeLeaveHoliday));
    }

    /**
     * 修改探亲假天数设定
     */
    @RequiresPermissions("human:homeLeaveHoliday:edit")
    @Log(title = "探亲假天数设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HomeLeaveHoliday homeLeaveHoliday)
    {
        return toAjax(homeLeaveHolidayService.updateHomeLeaveHoliday(homeLeaveHoliday));
    }

    @RequiresPermissions("human:homeLeaveHoliday:home")
    @Log(title = "探亲假天数设定", businessType = BusinessType.UPDATE)
    @PutMapping("/home")
    public AjaxResult home(@RequestBody HomeLeaveHoliday homeLeaveHoliday)
    {
        return success(homeLeaveHolidayService.selectHomeLeaveHolidayByempNo(homeLeaveHoliday));
    }

    /**
     * 删除探亲假天数设定
     */
    @RequiresPermissions("human:homeLeaveHoliday:remove")
    @Log(title = "探亲假天数设定", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(homeLeaveHolidayService.deleteHomeLeaveHolidayByIds(ids));
    }

    @Log(title = "探亲假资料导入", businessType = BusinessType.IMPORT)
    @RequiresPermissions("human:homeLeaveHoliday:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<HomeLeaveHoliday> util = new ExcelUtil<HomeLeaveHoliday>(HomeLeaveHoliday.class);
        List<HomeLeaveHoliday> homeLeaveHolidayList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = homeLeaveHolidayService.importUser(homeLeaveHolidayList, updateSupport, operName);
        return success(message);
    }

    /**
     * 导入探亲假数据
     * @param response
     * @throws IOException
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<HomeLeaveHoliday> util = new ExcelUtil<HomeLeaveHoliday>(HomeLeaveHoliday.class);
        util.importTemplateExcel(response, "探亲假数据");
    }
}

