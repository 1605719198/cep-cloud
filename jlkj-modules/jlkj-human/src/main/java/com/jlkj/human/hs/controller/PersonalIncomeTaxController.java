package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.PersonalIncomeTax;
import com.jlkj.human.hs.service.IPersonalIncomeTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 个人所得税比例维护Controller
 *
 * @author jiangbingchen
 * @date 2023-05-18
 */
@RestController
@RequestMapping("/personalIncomeTax")
public class PersonalIncomeTaxController extends BaseController
{
    @Autowired
    private IPersonalIncomeTaxService personalIncomeTaxService;

    /**
     * 查询个人所得税比例维护列表
     */
    @RequiresPermissions("human:personalIncomeTax:list")
    @GetMapping("/list")
    public TableDataInfo list(PersonalIncomeTax personalIncomeTax)
    {
        startPage();
        List<PersonalIncomeTax> list = personalIncomeTaxService.selectPersonalIncomeTaxList(personalIncomeTax);
        return getDataTable(list);
    }

    /**
     * 导出个人所得税比例维护列表
     */
    @RequiresPermissions("human:personalIncomeTax:export")
    @Log(title = "个人所得税比例维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PersonalIncomeTax personalIncomeTax)
    {
        List<PersonalIncomeTax> list = personalIncomeTaxService.selectPersonalIncomeTaxList(personalIncomeTax);
        ExcelUtil<PersonalIncomeTax> util = new ExcelUtil<PersonalIncomeTax>(PersonalIncomeTax.class);
        util.exportExcel(response, list, "个人所得税比例维护数据");
    }

    /**
     * 获取个人所得税比例维护详细信息
     */
    @RequiresPermissions("human:personalIncomeTax:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(personalIncomeTaxService.selectPersonalIncomeTaxById(id));
    }

    /**
     * 保存个人所得税比例维护
     */
    @RequiresPermissions("human:personalIncomeTax:add")
    @Log(title = "社保公积金缴费比例设定保存", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public int save(@RequestBody List<PersonalIncomeTax> personalIncomeTaxList)
    {
        return personalIncomeTaxService.insertPersonalIncomeTax(personalIncomeTaxList);
    }

    /**
     * 修改个人所得税比例维护
     */
    @RequiresPermissions("human:personalIncomeTax:edit")
    @Log(title = "个人所得税比例维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PersonalIncomeTax personalIncomeTax)
    {
        return toAjax(personalIncomeTaxService.updatePersonalIncomeTax(personalIncomeTax));
    }

    /**
     * 删除个人所得税比例维护
     */
    @RequiresPermissions("human:personalIncomeTax:remove")
    @Log(title = "个人所得税比例维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(personalIncomeTaxService.deletePersonalIncomeTaxByIds(ids));
    }

    /**
     * 获取版本列表
     */
    @GetMapping("/selectVersion/{type}")
    public AjaxResult selectVersion(@PathVariable String type)
    {
        return AjaxResult.success(personalIncomeTaxService.getVersionList(type));
    }
}
