package com.jlkj.human.hp.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hp.domain.HumanJobTitle;
import com.jlkj.human.hp.service.HumanJobTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 职位名称数据维护Controller
 *
 * @author 266861
 * @date 2023-03-13
 */
@RestController
@RequestMapping("/jobTitle")
public class HumanJobTitleController extends BaseController
{
    @Autowired
    private HumanJobTitleService humanHpHumanJobTitleService;

    /**
     * 查询职位名称数据维护列表
     */
    @RequiresPermissions("human:jobTitle:list")
    @GetMapping("/list")
    public TableDataInfo list(HumanJobTitle humanJobTitle)
    {
        startPage();
        List<HumanJobTitle> list = humanHpHumanJobTitleService.selectHumanJobTitleList(humanJobTitle);
        return getDataTable(list);
    }

    /**
     * 查询职位名称idname列表
     */
    @GetMapping("/getIdname")
    public TableDataInfo list(String compId)
    {
        List<HumanJobTitle> list = humanHpHumanJobTitleService.selectHumanJobTitleidname(compId);
        return getDataTable(list);
    }

    /**
     * 获取职位名称数据维护详细信息
     */
    @RequiresPermissions("human:jobTitle:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return success(humanHpHumanJobTitleService.selectHumanJobTitleById(id));
    }

    /**
     * 新增职位名称数据维护
     */
    @RequiresPermissions("human:jobTitle:add")
    @Log(title = "职位名称数据维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HumanJobTitle humanJobTitle)throws Exception
    {
        return toAjax(humanHpHumanJobTitleService.insertHumanJobTitle(humanJobTitle));
    }

    /**
     * 修改职位名称数据维护
     */
    @RequiresPermissions("human:jobTitle:edit")
    @Log(title = "职位名称数据维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HumanJobTitle humanJobTitle) throws Exception
    {
        return toAjax(humanHpHumanJobTitleService.updateHumanJobTitle(humanJobTitle));
    }

    /**
     * 删除职位名称数据维护
     */
    @RequiresPermissions("human:jobTitle:remove")
    @Log(title = "职位名称数据维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(humanHpHumanJobTitleService.deleteHumanJobTitleByIds(ids));
    }
}
