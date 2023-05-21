package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.SocialSecurity;
import com.jlkj.human.hs.service.ISocialSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 社保公积金缴费比例设定Controller
 *
 * @author jiangbingchen
 * @date 2023-05-11
 */
@RestController
@RequestMapping("/socialSecurity")
public class SocialSecurityController extends BaseController
{
    @Autowired
    private ISocialSecurityService socialSecurityService;

    /**
     * 查询社保公积金缴费比例设定列表
     */
    @RequiresPermissions("human:socialSecurity:list")
    @GetMapping("/list")
    public TableDataInfo list(SocialSecurity socialSecurity)
    {
        startPage();
        List<SocialSecurity> list = socialSecurityService.selectSocialSecurityList(socialSecurity);
        return getDataTable(list);
    }



    /**
     * 导出社保公积金缴费比例设定列表
     */
    @RequiresPermissions("human:socialSecurity:export")
    @Log(title = "社保公积金缴费比例设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SocialSecurity socialSecurity)
    {
        List<SocialSecurity> list = socialSecurityService.selectSocialSecurityList(socialSecurity);
        ExcelUtil<SocialSecurity> util = new ExcelUtil<SocialSecurity>(SocialSecurity.class);
        util.exportExcel(response, list, "社保公积金缴费比例设定数据");
    }

    /**
     * 获取社保公积金缴费比例设定详细信息
     */
    @RequiresPermissions("human:socialSecurity:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(socialSecurityService.selectSocialSecurityById(id));
    }

    /**
     * 保存社保公积金缴费比例设定详细信息
     */
    @RequiresPermissions("human:socialSecurity:add")
    @Log(title = "社保公积金缴费比例设定保存", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public int save(@RequestBody List<SocialSecurity> socialSecurityList)
    {
        return socialSecurityService.insertSocialSecurity(socialSecurityList);
    }

    /**
     * 修改社保公积金缴费比例设定
     */
    @RequiresPermissions("human:socialSecurity:edit")
    @Log(title = "社保公积金缴费比例设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SocialSecurity socialSecurity)
    {
        return toAjax(socialSecurityService.updateSocialSecurity(socialSecurity));
    }

    /**
     * 删除社保公积金缴费比例设定
     */
    @RequiresPermissions("human:socialSecurity:remove")
    @Log(title = "社保公积金缴费比例设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(socialSecurityService.deleteSocialSecurityByIds(ids));
    }
}
