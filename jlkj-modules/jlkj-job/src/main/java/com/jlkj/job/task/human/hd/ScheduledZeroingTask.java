package com.jlkj.job.task.human.hd;

import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.human.api.RemoteHdApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:月度定时存班
 * @Author: 266861
 * @date: 2023/7/11 11:27
 */
@Component("ScheduledZeroingTask")
public class ScheduledZeroingTask {
    @Autowired
    private RemoteHdApiService remoteHdApiService;
    public void scheduleZeroing(){
        try {
            remoteHdApiService.scheduledZeroing(SecurityConstants.INNER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
