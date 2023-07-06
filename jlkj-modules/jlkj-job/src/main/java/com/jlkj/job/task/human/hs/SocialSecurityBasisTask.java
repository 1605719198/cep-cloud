//package com.jlkj.job.task.human.hs;
//
//import com.jlkj.common.core.constant.SecurityConstants;
//import com.jlkj.human.api.RemoteHsApiService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * @Description 社保核定基数年底核定下年初最新数据
// * @author 266861
// * @date 2023/7/4 9:10
// **/
//
//@Component("socialSecurityBasisTask")
//public class SocialSecurityBasisTask {
//    @Autowired
//    private RemoteHsApiService remoteHsApiService;
////    protected Logger logger = LoggerFactory.getLogger(getClass());
//
//    public void setNewYearData(){
//        System.out.println("test123");
//        try {
////            RemoteHsApiService remoteHsApiService = (RemoteHsApiService) SpringUtils.getBean("remoteHsApiService");
//            remoteHsApiService.setNewYearSocialSecurity(SecurityConstants.INNER);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
