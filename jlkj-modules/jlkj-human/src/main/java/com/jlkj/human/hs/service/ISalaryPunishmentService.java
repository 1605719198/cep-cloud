package com.jlkj.human.hs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.human.hs.domain.SalaryPunishment;

import java.util.List;

/**
* @author HuangBing
* @description 针对表【human_hs_salary_punishment(专业奖惩金额追补扣)】的数据库操作Service
* @createDate 2023-06-02 22:53:13
*/
public interface ISalaryPunishmentService extends IService<SalaryPunishment> {

    /**
     * 导入专业奖罚数据
     *
     * @param salaryPunishmentList 专业奖罚列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<SalaryPunishment> salaryPunishmentList, Boolean isUpdateSupport, String operName, String compId, String date);

}
