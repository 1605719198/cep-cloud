package com.jlkj.human.ex.controller;

import com.jlkj.common.core.utils.StringUtils;
import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.human.ex.domain.ExamQuestionsAnswer;
import com.jlkj.human.ex.domain.ExamQuestionsContentData;
import com.jlkj.human.ex.domain.ExamQuestionsData;
import com.jlkj.human.ex.service.IExamQuestionsAnswerService;
import com.jlkj.human.ex.service.IExamQuestionsContentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/onlineexam")
public class ExamQuestionsContentDataController {
	@Autowired
	private IExamQuestionsContentDataService examQuestionsContentDataService;
	
    @Autowired
    private IExamQuestionsAnswerService examQuestionsAnswerService;
	
    @GetMapping("/content")
    public AjaxResult option(@RequestBody ExamQuestionsContentData examQuestionsContentData)
    {
        ExamQuestionsContentData contentData = examQuestionsContentDataService.selectExamQuestionsContent(examQuestionsContentData);
        return AjaxResult.success(contentData);
    }
    
    @PostMapping("/data")
    public AjaxResult data(@RequestBody ExamQuestionsContentData examQuestionsContentData)
    {
    	ExamQuestionsContentData contentData = examQuestionsContentDataService.selectExamQuestionsContent(examQuestionsContentData);
    	
    	// 转换题号
    	
    	String str = StringUtils.toChinese(String.valueOf(contentData.getQuestionsNumber()));
    	contentData.setQuestionsNumberText("第" + str + "题");
    	
    	List<ExamQuestionsAnswer> examQuestionsOptionList = examQuestionsAnswerService.selectExamQuestionsOptionList(examQuestionsContentData.getQuestionsCode());
        
    	ExamQuestionsData examQuestionsData = new ExamQuestionsData(contentData, examQuestionsOptionList);
    	return AjaxResult.success(examQuestionsData);
    }

}
