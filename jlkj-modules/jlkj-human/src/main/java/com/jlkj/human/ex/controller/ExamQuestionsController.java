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
import com.jlkj.human.ex.domain.ExamQuestions;
import com.jlkj.human.ex.domain.ExamQuestionsAnswer;
import com.jlkj.human.ex.domain.ExamQuestionsContent;
import com.jlkj.human.ex.domain.ExamQuestionsProperty;
import com.jlkj.human.ex.service.IExamQuestionsAnswerService;
import com.jlkj.human.ex.service.IExamQuestionsContentService;
import com.jlkj.human.ex.service.IExamQuestionsPropertyService;
import com.jlkj.human.ex.service.IExamQuestionsService;
import com.jlkj.system.api.model.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 考试题目Controller
 * 
 * @author qnsdt
 * @Date:  2022-12-28
 */
@RestController
@RequestMapping("/examquestions")
public class ExamQuestionsController extends BaseController
{
    @Autowired
    private IExamQuestionsService examQuestionsService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IExamQuestionsAnswerService examQuestionsAnswerService;

    @Autowired
    private IExamQuestionsContentService examQuestionsContentService;

    @Autowired
    private IExamQuestionsPropertyService examQuestionsPropertyService;

    /**
     * 查询考试题目列表
     */
    @RequiresPermissions("human:examquestions:list")
    @GetMapping("/list")
    public TableDataInfo list(ExamQuestions examQuestions)
    {
        startPage();
        List<ExamQuestions> list = examQuestionsService.selectExamQuestionsList(examQuestions);
        return getDataTable(list);
    }

    /**
     * 导出考试题目列表
     */
    @RequiresPermissions("human:examquestions:export")
    @Log(title = "考试题目", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response,ExamQuestions examQuestions)
    {
        List<ExamQuestions> list = examQuestionsService.selectExamQuestionsList(examQuestions);
        ExcelUtil<ExamQuestions> util = new ExcelUtil<ExamQuestions>(ExamQuestions.class);
        util.exportExcel(response,list, "考试题目数据");
    }

    /**
     * 获取考试题目详细信息
     */
    @RequiresPermissions("human:examquestions:query")
    @GetMapping(value = "/{questionsCode}")
    public AjaxResult getInfo(@PathVariable("questionsCode") String questionsCode)
    {
        return AjaxResult.success(examQuestionsService.selectExamQuestionsById(questionsCode));
    }

    /**
     * 新增考试题目
     */
    @RequiresPermissions("human:examquestions:add")
    @Log(title = "考试题目", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody ExamQuestions examQuestions)
    {
    	// 取得用户单位
    	LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
    	examQuestions.setCreateDept(loginUser.getSysUser().getDeptId());
        List<ExamQuestionsAnswer> answerList = examQuestions.getAnswerList();
        
        ExamQuestionsProperty examQuestionsProperty = new ExamQuestionsProperty(examQuestions.getBankCode(),null,examQuestions.getQuestionsCode(),examQuestions.getQuestionsTitle(),
        		examQuestions.getQuestionsType(),examQuestions.getQuestionsScore(),examQuestions.getRateNumber(),examQuestions.getRightAnswer(),examQuestions.getAnswerAnalyse(),
        		0,examQuestions.getCreateBy(), examQuestions.getCreateDept());
        
        ExamQuestionsContent examQuestionsContent =  new ExamQuestionsContent(examQuestions.getQuestionsCode(), examQuestions.getQuestionsContent());
        // 开始保存数据
        // 保存题目选项
        // 安全删除
        examQuestionsAnswerService.deleteExamQuestionsAnswerById(examQuestions.getQuestionsCode());
        for (ExamQuestionsAnswer questionsAnswer: answerList) {
        	questionsAnswer.setOrderId(null);
            examQuestionsAnswerService.insertExamQuestionsAnswer(questionsAnswer);
        }
        // 保存题目数据
        int count = 0;
        examQuestionsContentService.deleteExamQuestionsContentById(examQuestions.getQuestionsCode());
        count = examQuestionsContentService.insertExamQuestionsContent(examQuestionsContent);
        if (count == 0) {
        	return new AjaxResult(0,"更新题目信息出错!");
        }
        // 安全删除
        examQuestionsPropertyService.deleteExamQuestionsPropertyById(examQuestions.getQuestionsCode());
        return toAjax(examQuestionsPropertyService.insertExamQuestionsProperty(examQuestionsProperty));
    }

    /**
     * 修改考试题目
     */
    @RequiresPermissions("human:examquestions:edit")
    @Log(title = "考试题目", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    public AjaxResult edit(@RequestBody ExamQuestions examQuestions)
    {
        List<ExamQuestionsAnswer> answerList = examQuestions.getAnswerList();
        ExamQuestionsProperty examQuestionsProperty = new ExamQuestionsProperty(examQuestions.getBankCode(),null,examQuestions.getQuestionsCode(),examQuestions.getQuestionsTitle(),
        		examQuestions.getQuestionsType(),examQuestions.getQuestionsScore(),examQuestions.getRateNumber(),examQuestions.getRightAnswer(),examQuestions.getAnswerAnalyse(),
        		0,examQuestions.getCreateBy(), examQuestions.getCreateDept());
        
        ExamQuestionsContent examQuestionsContent =  new ExamQuestionsContent(examQuestions.getQuestionsCode(), examQuestions.getQuestionsContent());
        // 开始保存数据
        // 保存题目选项
        // 安全删除
        examQuestionsAnswerService.deleteExamQuestionsAnswerById(examQuestions.getQuestionsCode());
        for (ExamQuestionsAnswer questionsAnswer: answerList) {
            examQuestionsAnswerService.insertExamQuestionsAnswer(questionsAnswer);
        }
        // 保存题目数据
        int count = 0;
        examQuestionsContentService.deleteExamQuestionsContentById(examQuestions.getQuestionsCode());
        count = examQuestionsContentService.insertExamQuestionsContent(examQuestionsContent);
        
        if (count == 0) {
        	return new AjaxResult(0,"更新题目信息出错!");
        }
        return toAjax(examQuestionsPropertyService.updateExamQuestionsProperty(examQuestionsProperty));
    }

    /**
     * 删除考试题目
     */
    @RequiresPermissions("human:examquestions:remove")
    @Log(title = "考试题目", businessType = BusinessType.DELETE)
    @GetMapping("/delete/{questionsCodes}")
    public AjaxResult remove(@PathVariable String[] questionsCodes)
    {
    	int count = questionsCodes.length;
    	for (String questionsCode: questionsCodes) {
            examQuestionsAnswerService.deleteExamQuestionsAnswerById(questionsCode);
            examQuestionsContentService.deleteExamQuestionsContentById(questionsCode);
            examQuestionsPropertyService.deleteExamQuestionsPropertyById(questionsCode);
    	}

        return toAjax(count);
    }
}
