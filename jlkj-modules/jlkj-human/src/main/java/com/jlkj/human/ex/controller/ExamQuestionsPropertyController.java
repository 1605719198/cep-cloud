package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.ServletUtils;
import com.jlkj.common.core.utils.poi.ExcelUtil;
import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.log.annotation.Log;
import com.jlkj.common.log.enums.BusinessType;
import com.jlkj.common.security.annotation.RequiresPermissions;
import com.jlkj.common.security.service.TokenService;
import com.jlkj.human.ex.domain.ExamQuestionsProperty;
import com.jlkj.human.ex.service.IExamQuestionsPropertyService;
import com.jlkj.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 题目属性Controller
 * 
 * @author 111191
 * @Date:  2023-07-11
 */
@RestController
@RequestMapping("/questions/questionsproperty")
public class ExamQuestionsPropertyController extends BaseController
{
    @Autowired
    private IExamQuestionsPropertyService examQuestionsPropertyService;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询题目属性列表
     */
    @RequiresPermissions("questions:questionsproperty:list")
    @GetMapping("/list")
    public TableDataInfo list(ExamQuestionsProperty examQuestionsProperty)
    {
        startPage();
        List<ExamQuestionsProperty> list = examQuestionsPropertyService.selectExamQuestionsPropertyList(examQuestionsProperty);
        return getDataTable(list);
    }

    /**
     * 导出题目属性列表
     */
    @RequiresPermissions("questions:questionsproperty:export")
    @Log(title = "题目属性", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response,ExamQuestionsProperty examQuestionsProperty)
    {
        List<ExamQuestionsProperty> list = examQuestionsPropertyService.selectExamQuestionsPropertyList(examQuestionsProperty);
        ExcelUtil<ExamQuestionsProperty> util = new ExcelUtil<ExamQuestionsProperty>(ExamQuestionsProperty.class);
        util.exportExcel(response,list, "题目属性数据");
    }

    /**
     * 获取题目属性详细信息
     */
    @RequiresPermissions("questions:questionsproperty:query")
    @GetMapping(value = "/{questionsCode}")
    public AjaxResult getInfo(@PathVariable("questionsCode") String questionsCode)
    {
        return AjaxResult.success(examQuestionsPropertyService.selectExamQuestionsPropertyById(questionsCode));
    }

    /**
     * 新增题目属性
     */
    @RequiresPermissions("questions:questionsproperty:add")
    @Log(title = "题目属性", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody ExamQuestionsProperty examQuestionsProperty)
    {
    	LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
    	examQuestionsProperty.setCreateDept(loginUser.getSysUser().getDeptId());

        return toAjax(examQuestionsPropertyService.insertExamQuestionsProperty(examQuestionsProperty));
    }

    /**
     * 修改题目属性
     */
    @RequiresPermissions("questions:questionsproperty:edit")
    @Log(title = "题目属性", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody ExamQuestionsProperty examQuestionsProperty)
    {
        return toAjax(examQuestionsPropertyService.updateExamQuestionsProperty(examQuestionsProperty));
    }

    /**
     * 删除题目属性
     */
    @RequiresPermissions("questions:questionsproperty:remove")
    @Log(title = "题目属性", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{questionsCodes}")
    public AjaxResult remove(@PathVariable String[] questionsCodes)
    {
        return toAjax(examQuestionsPropertyService.deleteExamQuestionsPropertyByIds(questionsCodes));
    }
}
