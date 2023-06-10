package com.jlkj.human.hs.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.human.hd.dto.BasisOptionsDTO;
import com.jlkj.human.hs.domain.PersonalSalary;
import com.jlkj.human.hs.domain.ProjectPay;
import com.jlkj.human.hs.service.IPersonalSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 薪资核定Controller
 *
 * @author 266861
 * @date 2023-06-01
 */
@RestController
@RequestMapping("/personalSalary")
public class PersonalSalaryController extends BaseController {
    @Autowired
    private IPersonalSalaryService personalSalaryService;

    /**
     * 查询薪资核定列表
     */
    @RequiresPermissions("human:personalSalary:list")
    @GetMapping("/list")
    public TableDataInfo list(PersonalSalary personalSalary) {
        startPage();
        List<PersonalSalary> list = personalSalaryService.selectPersonalSalaryList(personalSalary);
        return getDataTable(list);
    }

    /**
     * 导出薪资核定列表
     */
    @RequiresPermissions("human:personalSalary:export")
    @Log(title = "薪资核定", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PersonalSalary personalSalary) {
        List<PersonalSalary> list = personalSalaryService.selectPersonalSalaryList(personalSalary);
        ExcelUtil<PersonalSalary> util = new ExcelUtil<PersonalSalary>(PersonalSalary.class);
        util.exportExcel(response, list, "薪资核定数据");
    }

    /**
     * 获取薪资核定详细信息
     */
    @RequiresPermissions("human:personalSalary:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return success(personalSalaryService.selectPersonalSalaryById(id));
    }

    /**
     * 新增薪资核定
     */
    @RequiresPermissions("human:personalSalary:add")
    @Log(title = "薪资核定", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PersonalSalary personalSalary) {
        int result = personalSalaryService.insertPersonalSalary(personalSalary);
        if (result > 0) {
            return success("新增成功");
        } else {
            return error("新增失败，生效日期不能小于当前日期或最大生效日期");
        }
    }

    /**
     * 修改薪资核定
     */
    @RequiresPermissions("human:personalSalary:edit")
    @Log(title = "薪资核定", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PersonalSalary personalSalary) {
        int result = personalSalaryService.updatePersonalSalary(personalSalary);
        if (result > 0) {
            return AjaxResult.success("修改成功");
        } else {
            return AjaxResult.error("修改失败，生效日期不能小于当前日期或最大生效日期");
        }
    }

    /**
     * 删除薪资核定
     */
    @RequiresPermissions("human:personalSalary:remove")
    @Log(title = "薪资核定", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(personalSalaryService.deletePersonalSalaryByIds(ids));
    }

    /**
     * 通过公司别获取薪资支付银行编码、名称ID
     */
    @GetMapping("/getBank/{compId}")
    public List<BasisOptionsDTO> getBank(@PathVariable String compId) {
        return personalSalaryService.getSalaryBank(compId);
    }

    /**
     * 查询薪酬项目列表接口
     */
    @GetMapping("/getPayFormation/{compId}")
    public List<ProjectPay> getPayFormation(@PathVariable String compId) {
        return personalSalaryService.getPayFormation(compId);
    }
}