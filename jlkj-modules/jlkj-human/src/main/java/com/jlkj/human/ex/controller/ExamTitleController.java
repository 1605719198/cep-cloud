package com.jlkj.human.ex.controller;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.human.ex.domain.ExamTitleData;
import com.jlkj.human.ex.service.IExamTitleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/onlineexam")
public class ExamTitleController {
	
	@Autowired
    IExamTitleDataService examTitleDataService;

	@GetMapping(value = "/{userCode}")
    public AjaxResult selectCurrentExam(@PathVariable("userCode") String userCode)
    {
    	ExamTitleData examTitleData = examTitleDataService.selectCurrentExam(userCode);
        return AjaxResult.success(examTitleData);
    }
}
