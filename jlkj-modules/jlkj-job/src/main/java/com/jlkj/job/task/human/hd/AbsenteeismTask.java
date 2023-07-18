package com.jlkj.job.task.human.hd;

import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.human.api.RemoteHdApiService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: 定时转旷工
 * @Author: HuangBing
 * @date: 2023/7/15
 */
@Component("AbsenteeismTask")
public class AbsenteeismTask {
    @Resource
    private RemoteHdApiService remoteHdApiService;
    public void absenteeism(){
        try {
            remoteHdApiService.absenteeism(SecurityConstants.INNER);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
