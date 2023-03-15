package com.jlkj.flowable.utils;

import cn.hutool.core.util.ObjectUtil;
import com.jlkj.common.security.utils.SecurityUtils;
import com.jlkj.flowable.common.constant.TaskConstants;
import com.jlkj.system.api.model.LoginUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 工作流任务工具类
 *
 * @author xin
 * @createTime 2022/4/24 12:42
 */
public class TaskUtils {

    public static String getUserId() {
        return String.valueOf(SecurityUtils.getUserId());
    }

    /**
     * 获取用户组信息
     *
     * @return candidateGroup
     */
    public static List<String> getCandidateGroup() {
        List<String> list = new ArrayList<>();
        LoginUser user = SecurityUtils.getLoginUser();
        if (ObjectUtil.isNotNull(user)) {
            if (ObjectUtil.isNotEmpty(user.getRoles())) {
                for (String role : user.getRoles()) {
                    list.add(TaskConstants.ROLE_GROUP_PREFIX + role);
                }
            }
            if (ObjectUtil.isNotNull(user.getSysUser().getDeptId())) {
                list.add(TaskConstants.DEPT_GROUP_PREFIX + user.getSysUser().getDeptId());
            }
        }
        return list;
    }
}
