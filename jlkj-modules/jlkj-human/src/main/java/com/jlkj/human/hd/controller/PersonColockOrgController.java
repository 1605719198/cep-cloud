package com.jlkj.human.hd.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.domain.PersonColockOrg;
import com.jlkj.human.hd.service.IPersonColockOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 人员卡钟组织机构Controller
 *
 * @author 266861
 * @date 2023-03-31
 */
@RestController
@RequestMapping("/personColockOrg")
public class PersonColockOrgController extends BaseController
{
    @Autowired
    private IPersonColockOrgService personColockOrgService;

    /**
     * 查询人员卡钟组织机构列表
     */
    @RequiresPermissions("human:personColock:list")
    @GetMapping("/list")
    public TableDataInfo list(PersonColockOrg personColockOrg)
    {
        startPage();
        List<PersonColockOrg> list = personColockOrgService.selectPersonColockOrgList(personColockOrg);
        return getDataTable(list);
    }


    /**
     * 获取人员卡钟组织机构详细信息
     */
    @RequiresPermissions("human:personColock:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(personColockOrgService.selectPersonColockOrgById(id));
    }

    /**
     * 新增人员卡钟组织机构
     */
    @RequiresPermissions("human:personColock:add")
    @Log(title = "人员卡钟组织机构", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PersonColockOrg personColockOrg)throws Exception
    {
        return toAjax(personColockOrgService.insertPersonColockOrg(personColockOrg));
    }

    /**
     * 修改人员卡钟组织机构
     */
    @RequiresPermissions("human:personColock:edit")
    @Log(title = "人员卡钟组织机构", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PersonColockOrg personColockOrg)throws Exception
    {
        return toAjax(personColockOrgService.updatePersonColockOrg(personColockOrg));
    }

    /**
     * 删除人员卡钟组织机构
     */
    @RequiresPermissions("human:personColock:remove")
    @Log(title = "人员卡钟组织机构", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(personColockOrgService.deletePersonColockOrgByIds(ids));
    }
}
