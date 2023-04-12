package com.jlkj.human.hd.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlkj.human.hd.domain.OvertimeRecord;

import java.util.List;

/**
* @author HuangBing
* @description 针对表【human_hd_overtime_record(加班记录表)】的数据库操作Service
* @createDate 2023-04-10 14:15:56
*/
public interface IOvertimeRecordService extends IService<OvertimeRecord> {

    /**
     * 导入加班资料数据
     *
     * @param overtimeRecordList 加班数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUser(List<OvertimeRecord> overtimeRecordList, Boolean isUpdateSupport, String operName);

}
