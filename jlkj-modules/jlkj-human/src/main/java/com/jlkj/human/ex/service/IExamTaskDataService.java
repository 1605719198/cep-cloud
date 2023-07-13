package com.jlkj.human.ex.service;


import com.jlkj.human.ex.domain.ExamTaskData;
import org.springframework.stereotype.Repository;

/**
 * 
 * @Author: 111191
 * @Date: 2023年7月11日, 0011 上午 11:42:25
 * @param null
 * @return null
 */
@Repository
public interface IExamTaskDataService {
	/**
	 * 保存数据
	 * @Author: 111191
	 * @Date: 2023年7月11日, 0011 上午 11:43:34
	 * @param examTaskData
	 * @return int 操作成功笔数
	 */
	public int saveData(ExamTaskData examTaskData);
}
