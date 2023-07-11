package com.jlkj.human.ex.controller;

import com.jlkj.common.core.web.controller.BaseController;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.common.core.web.page.TableDataInfo;
import com.jlkj.common.security.service.TokenService;
import com.jlkj.human.ex.domain.ExamQuestionsList;
import com.jlkj.human.ex.domain.ExamQuestionsProperty;
import com.jlkj.human.ex.service.IExamQuestionsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 题目属性Controller
 * 
 * @author qnsdt
 * @date 2022-12-28
 */
@RestController
@RequestMapping("/questions/questionslist")
public class ExamQuestionsListController extends BaseController
{
    @Autowired
    private IExamQuestionsListService examQuestionsListService;

    @Autowired
    private TokenService tokenService;

    /**
     * 查询题目属性列表
     */
    @GetMapping("/list")
    public TableDataInfo list(ExamQuestionsList examQuestionsList)
    {
        startPage();
        List<ExamQuestionsProperty> list = examQuestionsListService.selectExamQuestionsList(examQuestionsList.getBankCodes());
        return getDataTable(list);
    }
    
    @GetMapping("/selectlist/{examCode}")
    public AjaxResult selectList(@PathVariable("examCode") String examCode)
    {
        List<ExamQuestionsProperty> list = examQuestionsListService.selectChoiceExamQuestionsList(examCode);
        return AjaxResult.success(list);
    }


}
