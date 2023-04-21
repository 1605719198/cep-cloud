package com.jlkj.human.hd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.human.hd.domain.CancellationPerson;

import java.util.List;

/**
* @author HuangBing
* @description 针对表【human_hd_cancellation_person(人事注销记录表)】的数据库操作Service
* @createDate 2023-04-18 14:17:59
*/
public interface ICancellationPersonService extends IService<CancellationPerson> {

    /**
     * 基于表格注销
     *
     * @param cancellationPersonList 夜班数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<CancellationPerson> cancellationPersonList, Boolean isUpdateSupport, String operName);

}
