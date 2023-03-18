package com.jlkj.human.hp.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hp.domain.HumanHpJobTitle;
import com.jlkj.human.hp.service.IHumanHpJobTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 职位名称数据维护Controller
 *
 * @author 266861
 * @date 2023-03-13
 */
@RestController
@RequestMapping("/jobTitle")
public class HumanHpJobTitleController extends BaseController
{
    @Autowired
    private IHumanHpJobTitleService humanHpJobTitleService;

    /**
     * 查询职位名称数据维护列表
     */
    @RequiresPermissions("human:jobTitle:list")
    @GetMapping("/list")
    public TableDataInfo list(HumanHpJobTitle humanHpJobTitle)
    {
        startPage();
        List<HumanHpJobTitle> list = humanHpJobTitleService.selectHumanHpJobTitleList(humanHpJobTitle);
        return getDataTable(list);
    }

    /**
     * 查询职位名称idname列表
     */
    @GetMapping("/getidname")
    public TableDataInfo list(String compId)
    {
        startPage();
        List<HumanHpJobTitle> list = humanHpJobTitleService.selectHumanHpJobTitleidname(compId);
        return getDataTable(list);
    }

    /**
     * 导出职位名称数据维护列表
     */
    @RequiresPermissions("human:jobTitle:export")
    @Log(title = "职位名称数据维护", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HumanHpJobTitle humanHpJobTitle)
    {
        List<HumanHpJobTitle> list = humanHpJobTitleService.selectHumanHpJobTitleList(humanHpJobTitle);
        ExcelUtil<HumanHpJobTitle> util = new ExcelUtil<HumanHpJobTitle>(HumanHpJobTitle.class);
        util.exportExcel(response, list, "职位名称数据维护数据");
    }

    /**
     * 获取职位名称数据维护详细信息
     */
    @RequiresPermissions("human:jobTitle:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(humanHpJobTitleService.selectHumanHpJobTitleById(id));
    }

    /**
     * 新增职位名称数据维护
     */
    @RequiresPermissions("human:jobTitle:add")
    @Log(title = "职位名称数据维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HumanHpJobTitle humanHpJobTitle)throws Exception
    {
        return toAjax(humanHpJobTitleService.insertHumanHpJobTitle(humanHpJobTitle));
    }

    /**
     * 修改职位名称数据维护
     */
    @RequiresPermissions("human:jobTitle:edit")
    @Log(title = "职位名称数据维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HumanHpJobTitle humanHpJobTitle) throws Exception
    {
        return toAjax(humanHpJobTitleService.updateHumanHpJobTitle(humanHpJobTitle));
    }

    /**
     * 删除职位名称数据维护
     */
    @RequiresPermissions("human:jobTitle:remove")
    @Log(title = "职位名称数据维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(humanHpJobTitleService.deleteHumanHpJobTitleByIds(ids));
    }
}
