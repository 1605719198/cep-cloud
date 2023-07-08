package com.jlkj.job.task.human.hd;

import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.human.api.RemoteHdApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description 年底定时排班
 * @author 266861
 * @date 2023/7/4 9:10
 **/

@Component("ScheduledShiftsTask")
public class ScheduledShiftsTask {

    @Autowired
    private RemoteHdApiService remoteHdApiService;
    public void scheduledShifts(){
        try {
            remoteHdApiService.scheduledShifts(SecurityConstants.INNER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
