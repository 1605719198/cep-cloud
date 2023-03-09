package com.jlkj.system.api;

import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.common.core.constant.ServiceNameConstants;
import com.jlkj.common.core.domain.R;
import com.jlkj.system.api.domain.SysRole;
import com.jlkj.system.api.factory.RemoteRoleFallbackFactory;
import com.jlkj.system.api.factory.RemoteUserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户服务
 *
 * @author jlkj
 */
@FeignClient(contextId = "remoteRoleService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteRoleFallbackFactory.class)
public interface RemoteRoleService {

    /**
     * 获取角色列表
     * @param role
     * @param source
     * @return
     */
    @PostMapping("/role/selectRoleList")
    public R<List<SysRole>> selectRoleList(@RequestBody SysRole role, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 根据角色id获取角色
     * @param roleId
     * @param source
     * @return
     */
    @GetMapping("/role/selectRoleById/{roleId}")
    public R<SysRole> selectRoleById(@PathVariable("roleId") Long roleId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
