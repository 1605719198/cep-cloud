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
     * 保存各公司专业奖罚明细项目维护
     */
    @RequiresPermissions("human:compPunishment:save")
    @Log(title = "各公司专业奖罚明细项目保存", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult add(@RequestBody List<CompPunishment> compPunishmentList)
    {
        return toAjax(compPunishmentService.saveCompPunishment(compPunishmentList));
    }


}
