package com.jlkj.human.ex.domain;

import lombok.Data;

import java.util.List;

@Data
public class ExamTaskData {
	private ExamTaskManager examTaskManager;
	private List<ExamTaskGroup> taskGroupList;
	private List<ExamTaskQuestions> taskQuestionsList;
	
}
