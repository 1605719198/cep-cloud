package com.jlkj.system.api;

import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.common.core.constant.ServiceNameConstants;
import com.jlkj.common.core.domain.R;
import com.jlkj.system.api.domain.SysDept;
import com.jlkj.system.api.domain.SysRole;
import com.jlkj.system.api.factory.RemoteDeptFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户服务
 *
 * @author jlkj
 */
@FeignClient(contextId = "remoteDeptService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteDeptFallbackFactory.class)
public interface RemoteDeptService {

    /**
     * 根据id获取部门
     * @param deptId
     * @param source
     * @return
     */
    @GetMapping("/dept/selectDeptById/{deptId}")
    public R<SysDept> selectDeptById(@PathVariable("deptId") Long deptId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
