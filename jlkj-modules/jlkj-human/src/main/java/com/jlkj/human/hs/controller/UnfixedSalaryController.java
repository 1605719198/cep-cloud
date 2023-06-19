package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hs.domain.UnfixedSalary;
import com.jlkj.human.hs.dto.UnfixedSalaryDTO;
import com.jlkj.human.hs.service.IUnfixedSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 未定薪人员挑选作业Controller
 *
 * @author 266861
 * @date 2023-06-15
 */
@RestController
@RequestMapping("/unfixedSalary")
public class UnfixedSalaryController extends BaseController
{
    @Autowired
    private IUnfixedSalaryService unfixedSalaryService;

    /**
     * 查询未定薪人员挑选作业列表
     */
    @RequiresPermissions("human:unfixedSalary:list")
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody UnfixedSalaryDTO dto)
    {
        startPage();
        List<UnfixedSalaryDTO> list = unfixedSalaryService.selectUncheckedList(dto);
        return getDataTable(list);
    }

    /**
     * 导出未定薪人员挑选作业列表
     */
    @RequiresPermissions("human:unfixedSalary:export")
    @Log(title = "未定薪人员挑选作业", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UnfixedSalaryDTO dto)
    {
        List<UnfixedSalary> list = unfixedSalaryService.selectUnfixedSalaryList(dto);
        ExcelUtil<UnfixedSalary> util = new ExcelUtil<UnfixedSalary>(UnfixedSalary.class);
        util.exportExcel(response, list, "未定薪人员挑选作业数据");
    }


    /**
     * 保存未定薪人员挑选作业
     */
    @RequiresPermissions("human:unfixedSalary:save")
    @Log(title = "未定薪人员挑选作业", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UnfixedSalaryDTO unfixedSalaryDTO)
    {
        return toAjax(unfixedSalaryService.saveUnfixedSalary(unfixedSalaryDTO));
    }


}
