package com.jlkj.human.ex.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.human.ex.domain.ExamQuestionsListData;
import com.jlkj.human.ex.service.IExamQuestionsListDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 *
 * @Author: 111191
 * @Date: 2023年7月11日, 0011 下午 02:13:52
 */
@RestController
@RequestMapping("/onlineexam/questionslist")
public class ExamQuestionsListDataController {
	
	@Autowired
    IExamQuestionsListDataService examQuestionsListDataService;
	/**
     * 查询问题清单
     * @Author: 111191
     * @Date: 2023年7月11日, 0011 下午 02:14:10
     * @param examQuestionsListData  查询条件
     * @return com.jlkj.common.core.web.domain.AjaxResult
     */
	@GetMapping()
    public AjaxResult getQuestionsList(ExamQuestionsListData examQuestionsListData)
    {
		List<ExamQuestionsListData> examQuestionsList = examQuestionsListDataService.selectQuestionsList(examQuestionsListData);
        return AjaxResult.success(examQuestionsList);
    }
}
