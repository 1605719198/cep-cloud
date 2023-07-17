package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.human.ex.domain.ExamGroupPerson;
import com.jlkj.human.ex.service.IExamGroupPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 分组用户Controller
 * 
 * @author qnsdt
 * @date 2023-02-06
 */
@RestController
@RequestMapping("/groupperson")
public class ExamGroupPersonController extends BaseController
{
    @Autowired
    private IExamGroupPersonService sysGroupPersonService;

    /**
     * 查询分组用户列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ExamGroupPerson sysGroupPerson)
    {
        startPage();
        List<ExamGroupPerson> list = sysGroupPersonService.selectSysGroupPersonList(sysGroupPerson);
        return getDataTable(list);
    }

    /**
     * 导出分组用户列表
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response, ExamGroupPerson sysGroupPerson)
    {
        List<ExamGroupPerson> list = sysGroupPersonService.selectSysGroupPersonList(sysGroupPerson);
        ExcelUtil<ExamGroupPerson> util = new ExcelUtil<ExamGroupPerson>(ExamGroupPerson.class);
        util.exportExcel(response,list, "分组用户数据");
    }

    /**
     * 获取分组用户详细信息
     */
    @GetMapping(value = "/{groupCode}")
    public AjaxResult getInfo(@PathVariable("groupCode") String groupCode)
    {
        return AjaxResult.success(sysGroupPersonService.selectSysGroupPersonById(groupCode));
    }

    /**
     * 新增分组用户
     */
    @PostMapping
    public AjaxResult add(@RequestBody List<ExamGroupPerson> sysGroupPersonList)
    {
        return toAjax(sysGroupPersonService.insertSysGroupPerson(sysGroupPersonList));
    }

    /**
     * 修改分组用户
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody ExamGroupPerson sysGroupPerson)
    {
        return toAjax(sysGroupPersonService.updateSysGroupPerson(sysGroupPerson));
    }

    /**
     * 删除分组用户
     */
    @GetMapping("/delete/{groupCodes}")
    public AjaxResult remove(@PathVariable String[] groupCodes)
    {
        return toAjax(sysGroupPersonService.deleteSysGroupPersonByIds(groupCodes));
    }
}
