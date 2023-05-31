package com.jlkj.human.hs.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.SalaryExplain;
import com.jlkj.human.hs.service.ISalaryExplainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 各公司薪资发放说明Controller
 *
 * @author 266861
 * @date 2023-05-25
 */
@RestController
@RequestMapping("/salaryExplain")
public class SalaryExplainController extends BaseController
{
    @Autowired
    private ISalaryExplainService salaryExplainService;

    /**
     * 查询各公司薪资发放说明列表
     */
    @RequiresPermissions("human:salaryExplain:list")
    @GetMapping("/list")
    public TableDataInfo list(SalaryExplain salaryExplain)
    {
        startPage();
        List<SalaryExplain> list = salaryExplainService.selectSalaryExplainList(salaryExplain);
        return getDataTable(list);
    }

    /**
     * 获取各公司薪资发放说明详细信息
     */
    @RequiresPermissions("human:salaryExplain:query")
    @GetMapping(value = "/{uuid}")
    public AjaxResult getInfo(@PathVariable("uuid") String uuid)
    {
        return success(salaryExplainService.selectSalaryExplainByUuid(uuid));
    }

    /**
     * 保存各公司薪资发放说明
     */
    @RequiresPermissions("human:salaryExplain:add")
    @Log(title = "各公司薪资发放说明", businessType = BusinessType.INSERT)
    @PostMapping("/save")
    public AjaxResult add(@RequestBody List<SalaryExplain> salaryExplainList)
    {
        return toAjax(salaryExplainService.saveSalaryExplain(salaryExplainList));
    }


    /**
     * 删除各公司薪资发放说明
     */
    @RequiresPermissions("human:salaryExplain:remove")
    @Log(title = "各公司薪资发放说明", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uuids}")
    public AjaxResult remove(@PathVariable String[] uuids)
    {
        return toAjax(salaryExplainService.deleteSalaryExplainByUuids(uuids));
    }
}
