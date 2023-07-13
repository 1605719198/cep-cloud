package com.jlkj.job.task.human.hd;

import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.human.api.RemoteHdApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 月度出勤汇总
 * @Author: 266861
 * @date: 2023/7/13 8:15
 */
@Component("AttendanceSummaryTask")
public class AttendanceSummaryTask {
    @Autowired
    private RemoteHdApiService remoteHdApiService;
    public void attendanceSummary(){
        try {
            remoteHdApiService.attendanceSummary(SecurityConstants.INNER);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
