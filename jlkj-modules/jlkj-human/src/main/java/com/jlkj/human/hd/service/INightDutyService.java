package com.jlkj.human.hd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.human.hd.domain.NightDuty;

import java.util.List;

/**
* @author 89373
* @description 针对表【human_hd_night_duty(夜班查询表)】的数据库操作Service
* @createDate 2023-03-30 09:39:05
*/
public interface INightDutyService extends IService<NightDuty> {

    /**
     * 导入用户数据
     *
     * @param nightDutyList 夜班数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<NightDuty> nightDutyList, Boolean isUpdateSupport, String operName);

}
