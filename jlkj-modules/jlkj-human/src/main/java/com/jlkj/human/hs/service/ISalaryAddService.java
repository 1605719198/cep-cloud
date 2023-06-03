package com.jlkj.human.hs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.human.hs.domain.SalaryAdd;

import java.util.List;

/**
* @author 89373
* @description 针对表【human_hs_salary_add(薪资项目金额追补扣)】的数据库操作Service
* @createDate 2023-05-30 18:19:49
*/
public interface ISalaryAddService extends IService<SalaryAdd> {

    /**
     * 导入薪资项目追补数据
     *
     * @param salaryAddList 薪资项目追补列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<SalaryAdd> salaryAddList, Boolean isUpdateSupport, String operName, String compId, String date);

    /**
     * 导入专项附加追补数据
     *
     * @param salaryAddList 专项附加追补列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser1(List<SalaryAdd> salaryAddList, Boolean isUpdateSupport, String operName, String compId, String date);

}
