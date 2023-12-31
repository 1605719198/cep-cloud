package com.jlkj.system.api.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import com.jlkj.common.core.domain.R;
import com.jlkj.system.api.RemoteUserService;
import com.jlkj.system.api.domain.SysUser;
import com.jlkj.system.api.model.LoginUser;

import java.util.List;

/**
 * 用户服务降级处理
 *
 * @author jlkj
 */
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

    @Override
    public RemoteUserService create(Throwable throwable)
    {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteUserService()
        {
            @Override
            public R<LoginUser> getUserInfo(String username, String source)
            {
                return R.fail("获取用户失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> registerUserInfo(SysUser sysUser, String source)
            {
                return R.fail("注册用户失败:" + throwable.getMessage());
            }

            @Override
            public R<List<SysUser>> selectUserList(SysUser user, String source) {
                return R.fail("获取用户失败:" + throwable.getMessage());
            }

            @Override
            public R<SysUser> selectUserById(Long userId, String source) {
                return R.fail("根据id获取用户失败:" + throwable.getMessage());
            }

            @Override
            public R<List<Long>> selectUserIdsByRoleId(Long roleId, String source) {
                return R.fail("根据角色id获取用户id失败:" + throwable.getMessage());
            }

            @Override
            public R<List<Long>> selectList(List<String> groups, String source) {
                return R.fail("根据部门id获取用户id失败:" + throwable.getMessage());
            }
        };
    }
}
