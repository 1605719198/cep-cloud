package com.jlkj.system.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.common.core.constant.ServiceNameConstants;
import com.jlkj.common.core.domain.R;
import com.jlkj.system.api.domain.SysUser;
import com.jlkj.system.api.factory.RemoteUserFallbackFactory;
import com.jlkj.system.api.model.LoginUser;

import java.util.List;

/**
 * 用户服务
 *
 * @author jlkj
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService
{
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/user/info/{username}")
    public R<LoginUser> getUserInfo(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 注册用户信息
     *
     * @param sysUser 用户信息
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/user/register")
    public R<Boolean> registerUserInfo(@RequestBody SysUser sysUser, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 获取用户列表
     * @param user
     * @param source
     * @return
     */
    @PostMapping("/user/selectUserList")
    public R<List<SysUser>> selectUserList(@RequestBody SysUser user, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @param source
     * @return
     */
    @GetMapping("/user/selectUserById/{userId}")
    public R<SysUser> selectUserById(@PathVariable("userId") Long userId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 根据角色id查询用户id
     * @param roleId
     * @param source
     * @return
     */
    @GetMapping("/user/selectUserIdsByRoleId/{roleId}")
    public R<List<Long>> selectUserIdsByRoleId(@PathVariable("roleId") Long roleId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 根据部门id查询用户id
     * @param groups
     * @param source
     * @return
     */
    @PostMapping("/user/selectList")
    public R<List<Long>> selectList(@RequestBody List<String> groups, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
