package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.human.ex.domain.ExamUserChoice;
import com.jlkj.human.ex.domain.dto.ExamUserCopy;
import com.jlkj.human.ex.domain.dto.ExamUserData;
import com.jlkj.human.ex.service.IExamUserChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * 选择用户Controller
 * 
 * @author qnsdt
 * @date 2023-02-02
 */
@RestController
@RequestMapping("/sysuserchoice")
public class ExamUserChoiceController extends BaseController
{
    @Autowired
    private IExamUserChoiceService sysUserChoiceService;

    /**
     * 查询选择用户列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ExamUserChoice sysUserChoice)
    {
        startPage();
        List<ExamUserChoice> list = sysUserChoiceService.selectExamUserChoiceList(sysUserChoice);
        return getDataTable(list);
    }
    
    @GetMapping("/choiceuserlist")
    public AjaxResult selectGroupUserList(ExamUserData sysUser)
    {
    	System.out.println("SysUserData:" + sysUser.toString());
        List<ExamUserData> list = sysUserChoiceService.selectGroupUserList(sysUser);
        return AjaxResult.success(list);

    }

    /**
     * 导出选择用户列表
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response, ExamUserChoice sysUserChoice)
    {
        List<ExamUserChoice> list = sysUserChoiceService.selectExamUserChoiceList(sysUserChoice);
        ExcelUtil<ExamUserChoice> util = new ExcelUtil<ExamUserChoice>(ExamUserChoice.class);
        util.exportExcel(response, list, "考试分类数据");
    }

    /**
     * 获取选择用户详细信息
     */
    @GetMapping(value = "/{choiceCode}")
    public AjaxResult getInfo(@PathVariable("choiceCode") String choiceCode)
    {
        return AjaxResult.success(sysUserChoiceService.selectExamUserChoiceById(choiceCode));
    }

    /**
     * 新增选择用户
     */
    @PostMapping("/copysysuser")
    public AjaxResult copySysUser(@RequestBody ExamUserCopy sysUserCopy)
    {
        return toAjax(sysUserChoiceService.copyExamUser(sysUserCopy));
    }
    
    @PostMapping("/copytempuser")
    public AjaxResult copyTempUser(@RequestBody ExamUserCopy sysUserCopy)
    {
        return toAjax(sysUserChoiceService.copyTempUser(sysUserCopy));
    }
    /**
     * 新增选择用户
     */
    @PostMapping
    public AjaxResult add(@RequestBody ExamUserChoice sysUserChoice)
    {
        return toAjax(sysUserChoiceService.insertExamUserChoice(sysUserChoice));
    }

    /**
     * 修改选择用户
     */
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody ExamUserChoice sysUserChoice)
    {
        return toAjax(sysUserChoiceService.updateExamUserChoice(sysUserChoice));
    }

    /**
     * 删除选择用户
     */
    @GetMapping("/delete/{choiceCodes}")
    public AjaxResult remove(@PathVariable String[] choiceCodes)
    {
        return toAjax(sysUserChoiceService.deleteExamUserChoiceByIds(choiceCodes));
    }
}
