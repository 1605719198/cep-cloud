package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.SocialSecurity;
import com.jlkj.human.hs.domain.SocialSecurityCompany;
import com.jlkj.human.hs.service.ISocialSecurityCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 社保公积金缴费公司维护Controller
 *
 * @author 267383
 * @date 2023-05-28
 */
@RestController
@RequestMapping("/socialSecurityCompany")
public class SocialSecurityCompanyController extends BaseController
{
    @Autowired
    private ISocialSecurityCompanyService socialSecurityCompanyService;

    /**
     * 查询社保公积金缴费公司维护列表
     */
    @RequiresPermissions("human:socialSecurityCompany:list")
    @GetMapping("/list")
    public TableDataInfo list(SocialSecurityCompany socialSecurityCompany)
    {
        startPage();
        List<SocialSecurityCompany> list = socialSecurityCompanyService.selectSocialSecurityCompanyList(socialSecurityCompany);
        return getDataTable(list);
    }

    /**
     * 导出社保公积金缴费公司维护列表
     */
    @RequiresPermissions("human:socialSecurityCompany:export")
    @Log(title = "社保公积金缴费公司维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SocialSecurityCompany socialSecurityCompany)
    {
        List<SocialSecurityCompany> list = socialSecurityCompanyService.selectSocialSecurityCompanyList(socialSecurityCompany);
        ExcelUtil<SocialSecurityCompany> util = new ExcelUtil<SocialSecurityCompany>(SocialSecurityCompany.class);
        util.exportExcel(response, list, "社保公积金缴费公司维护数据");
    }

    /**
     * 获取社保公积金缴费公司维护详细信息
     */
    @RequiresPermissions("human:socialSecurityCompany:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(socialSecurityCompanyService.selectSocialSecurityCompanyById(id));
    }

    /**
     * 保存社保公积金缴费公司维护
     */
    @RequiresPermissions("human:socialSecurityCompany:add")
    @Log(title = "社保公积金缴费比例设定保存", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public int save(@RequestBody List<SocialSecurityCompany> socialSecurityCompanyList)
    {
        return socialSecurityCompanyService.insertSocialSecurityCompany(socialSecurityCompanyList);
    }

    /**
     * 修改社保公积金缴费公司维护
     */
    @RequiresPermissions("human:socialSecurityCompany:edit")
    @Log(title = "社保公积金缴费公司维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SocialSecurityCompany socialSecurityCompany)
    {
        return toAjax(socialSecurityCompanyService.updateSocialSecurityCompany(socialSecurityCompany));
    }

    /**
     * 删除社保公积金缴费公司维护
     */
    @RequiresPermissions("human:socialSecurityCompany:remove")
    @Log(title = "社保公积金缴费公司维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(socialSecurityCompanyService.deleteSocialSecurityCompanyByIds(ids));
    }
}
