package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.MiniStandard;
import com.jlkj.human.hs.service.IMiniStandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 各公司最低工资标准及生活保障标准设定Controller
 *
 * @author jiangbingchen
 * @date 2023-05-24
 */
@RestController
@RequestMapping("/miniStandard")
public class MiniStandardController extends BaseController
{
    @Autowired
    private IMiniStandardService miniStandardService;

    /**
     * 查询各公司最低工资标准及生活保障标准设定列表
     */
    @RequiresPermissions("human:miniStandard:list")
    @GetMapping("/list")
    public TableDataInfo list(MiniStandard miniStandard)
    {
        startPage();
        List<MiniStandard> list = miniStandardService.selectMiniStandardList(miniStandard);
        return getDataTable(list);
    }

    /**
     * 导出各公司最低工资标准及生活保障标准设定列表
     */
    @RequiresPermissions("human:miniStandard:export")
    @Log(title = "各公司最低工资标准及生活保障标准设定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MiniStandard miniStandard)
    {
        List<MiniStandard> list = miniStandardService.selectMiniStandardList(miniStandard);
        ExcelUtil<MiniStandard> util = new ExcelUtil<MiniStandard>(MiniStandard.class);
        util.exportExcel(response, list, "各公司最低工资标准及生活保障标准设定数据");
    }

    /**
     * 获取各公司最低工资标准及生活保障标准设定详细信息
     */
    @RequiresPermissions("human:miniStandard:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(miniStandardService.selectMiniStandardByUuid(uuid));
    }

    /**
     * 保存各公司最低工资标准及生活保障标准设定
     */
    @RequiresPermissions("human:miniStandard:add")
    @Log(title = "最低工资标准保存", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public int save(@RequestBody List<MiniStandard> miniStandardList)
    {
        return miniStandardService.insertMiniStandard(miniStandardList);
    }

    /**
     * 修改各公司最低工资标准及生活保障标准设定
     */
    @RequiresPermissions("human:miniStandard:edit")
    @Log(title = "各公司最低工资标准及生活保障标准设定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MiniStandard miniStandard)
    {
        return toAjax(miniStandardService.updateMiniStandard(miniStandard));
    }

    /**
     * 删除各公司最低工资标准及生活保障标准设定
     */
    @RequiresPermissions("human:miniStandard:remove")
    @Log(title = "各公司最低工资标准及生活保障标准设定", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(miniStandardService.deleteMiniStandardByUuids(uuids));
    }

    /**
     * 获取版本列表
     */
    @GetMapping("/selectVersion/{compId}")
    public AjaxResult selectVersion(@PathVariable String compId)
    {
        return AjaxResult.success(miniStandardService.getVersionList(compId));
    }
}
