package com.jlkj.human.hs.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.CompPunishment;
import com.jlkj.human.hs.dto.CompPunishmentDTO;
import com.jlkj.human.hs.service.ICompPunishmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 各公司专业奖罚明细项目维护Controller
 *
 * @author 266861
 * @date 2023-05-25
 */
@RestController
@RequestMapping("/compPunishment")
public class CompPunishmentController extends BaseController
{
    @Autowired
    private ICompPunishmentService compPunishmentService;

    /**
     * 查询各公司专业奖罚明细项目维护列表
     */
    @RequiresPermissions("human:compPunishment:list")
    @GetMapping("/list")
    public TableDataInfo list(CompPunishment compPunishment)
    {
        startPage();
        List<CompPunishmentDTO> list = compPunishmentService.selectCompPunishmentList(compPunishment);
        return getDataTable(list);
    }


    /**
     * 获取各公司专业奖罚明细项目维护详细信息
     */
    @RequiresPermissions("human:compPunishment:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(compPunishmentService.selectCompPunishmentByUuid(uuid));
    }

    /**
     * 保存各公司专业奖罚明细项目维护
     */
    @RequiresPermissions("human:compPunishment:save")
    @Log(title = "各公司专业奖罚明细项目保存", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult add(@RequestBody List<CompPunishment> compPunishmentList)
    {
        return toAjax(compPunishmentService.saveCompPunishment(compPunishmentList));
    }

    /**
     * 修改各公司专业奖罚明细项目维护
     */
    @RequiresPermissions("human:compPunishment:edit")
    @Log(title = "各公司专业奖罚明细项目维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CompPunishment compPunishment)
    {
        return toAjax(compPunishmentService.updateCompPunishment(compPunishment));
    }

    /**
     * 删除各公司专业奖罚明细项目维护
     */
    @RequiresPermissions("human:compPunishment:remove")
    @Log(title = "各公司专业奖罚明细项目维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(compPunishmentService.deleteCompPunishmentByUuids(uuids));
    }
}
