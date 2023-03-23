package com.jlkj.safety.si.utils;

import java.util.Random;

/**
 * 作业票工具类
 * @author sdy
 * @since 2022/7/1 16:13
 */
public class JobUtil {
    /**
     * 生成审批流程id
     * @return 审批流程id
     */
    public static String generateAprovalFlowId() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Random r = new Random();
        return System.currentTimeMillis() + "" + r.nextInt(10) + r.nextInt(10) + r.nextInt(10);
    }


}
