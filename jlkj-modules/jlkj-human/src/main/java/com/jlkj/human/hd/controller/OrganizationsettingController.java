package com.jlkj.human.hd.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.Organizationsetting;
import com.jlkj.human.hd.service.IOrganizationsettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 机构参数Controller
 *
 * @author 266861
 * @date 2023-03-28
 */
@RestController
@RequestMapping("/organizationSetting")
public class OrganizationsettingController extends BaseController
{
    @Autowired
    private IOrganizationsettingService organizationsettingService;

    /**
     * 查询机构参数列表
     */
    @RequiresPermissions("human:organizationSetting:list")
    @GetMapping("/list")
    public TableDataInfo list(Organizationsetting organizationsetting)
    {
        startPage();
        List<Organizationsetting> list = organizationsettingService.selectOrganizationsettingList(organizationsetting);
        return getDataTable(list);
    }

    /**
     * 导出机构参数列表
     */
    @RequiresPermissions("human:organizationSetting:export")
    @Log(title = "机构参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Organizationsetting organizationsetting)
    {
        List<Organizationsetting> list = organizationsettingService.selectOrganizationsettingList(organizationsetting);
        ExcelUtil<Organizationsetting> util = new ExcelUtil<Organizationsetting>(Organizationsetting.class);
        util.exportExcel(response, list, "机构参数数据");
    }

    /**
     * 获取机构参数详细信息
     */
    @RequiresPermissions("human:organizationSetting:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(organizationsettingService.selectOrganizationsettingById(id));
    }

    /**
     * 新增机构参数
     */
    @RequiresPermissions("human:organizationSetting:add")
    @Log(title = "机构参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Organizationsetting organizationsetting)
    {
        return toAjax(organizationsettingService.insertOrganizationsetting(organizationsetting));
    }

    /**
     * 修改机构参数
     */
    @RequiresPermissions("human:organizationSetting:edit")
    @Log(title = "机构参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Organizationsetting organizationsetting)
    {
        return toAjax(organizationsettingService.updateOrganizationsetting(organizationsetting));
    }

    /**
     * 删除机构参数
     */
    @RequiresPermissions("human:organizationSetting:remove")
    @Log(title = "机构参数", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(organizationsettingService.deleteOrganizationsettingByIds(ids));
    }
}
