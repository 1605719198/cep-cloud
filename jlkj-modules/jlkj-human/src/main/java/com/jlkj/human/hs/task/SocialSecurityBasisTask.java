package com.jlkj.human.hs.task;

import com.jlkj.common.core.utils.SpringUtils;
import com.jlkj.human.hs.service.ISocialSecurityBasisService;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

/**
 * @description: 社保核定基数定时任务
 * @Author: 266861
 * @date: 2023/7/4 9:04
 */
@Component("socialSecurityBasisTask")
public class SocialSecurityBasisTask {
    /** 
    * @Description 社保核定基数年底核定下年初最新数据
    * @author 266861
    * @date 2023/7/4 9:10
    **/
    public static void setNewYearData(){
        System.out.println("test");
        try {
            ISocialSecurityBasisService socialSecurityBasisService = (ISocialSecurityBasisService) SpringUtils.getBean("iSocialSecurityBasisService");
            socialSecurityBasisService.setNewYearSocialSecurity();
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }
}
