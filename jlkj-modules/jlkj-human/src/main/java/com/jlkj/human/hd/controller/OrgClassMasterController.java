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
import com.jlkj.human.hd.domain.OrgClassMaster;
import com.jlkj.human.hd.service.IOrgClassMasterService;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.page.TableDataInfo;

/**
 * 出勤身份Controller
 *
 * @author 266861
 * @date 2023-04-12
 */
@RestController
@RequestMapping("/orgClassMaster")
public class OrgClassMasterController extends BaseController
{
    @Autowired
    private IOrgClassMasterService orgClassMasterService;

    /**
     * 查询出勤身份列表
     */
    @RequiresPermissions("human:orgClassMaster:list")
    @GetMapping("/list")
    public TableDataInfo list(OrgClassMaster orgClassMaster)
    {
        startPage();
        List<OrgClassMaster> list = orgClassMasterService.selectOrgClassMasterList(orgClassMaster);
        return getDataTable(list);
    }

    /**
     * 导出出勤身份列表
     */
    @RequiresPermissions("human:orgClassMaster:export")
    @Log(title = "出勤身份", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrgClassMaster orgClassMaster)
    {
        List<OrgClassMaster> list = orgClassMasterService.selectOrgClassMasterList(orgClassMaster);
        ExcelUtil<OrgClassMaster> util = new ExcelUtil<OrgClassMaster>(OrgClassMaster.class);
        util.exportExcel(response, list, "出勤身份数据");
    }

    /**
     * 获取出勤身份详细信息
     */
    @RequiresPermissions("human:orgClassMaster:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(orgClassMasterService.selectOrgClassMasterById(id));
    }

    /**
     * 新增出勤身份
     */
    @RequiresPermissions("human:orgClassMaster:add")
    @Log(title = "出勤身份", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrgClassMaster orgClassMaster)
    {
        return toAjax(orgClassMasterService.insertOrgClassMaster(orgClassMaster));
    }

    /**
     * 修改出勤身份
     */
    @RequiresPermissions("human:orgClassMaster:edit")
    @Log(title = "出勤身份", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrgClassMaster orgClassMaster)
    {
        return toAjax(orgClassMasterService.updateOrgClassMaster(orgClassMaster));
    }

    /**
     * 删除出勤身份
     */
    @RequiresPermissions("human:orgClassMaster:remove")
    @Log(title = "出勤身份", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(orgClassMasterService.deleteOrgClassMasterByIds(ids));
    }
}
