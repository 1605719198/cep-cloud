package com.jlkj.system.api.factory;

import com.jlkj.common.core.domain.R;
import com.jlkj.system.api.RemoteRoleService;
import com.jlkj.system.api.RemoteUserService;
import com.jlkj.system.api.domain.SysRole;
import com.jlkj.system.api.domain.SysUser;
import com.jlkj.system.api.model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户服务降级处理
 *
 * @author jlkj
 */
@Component
public class RemoteRoleFallbackFactory implements FallbackFactory<RemoteRoleService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteRoleFallbackFactory.class);

    @Override
    public RemoteRoleService create(Throwable throwable)
    {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteRoleService()
        {
            @Override
            public R<List<SysRole>> selectRoleList(SysRole role, String source) {
                return R.fail("获取角色列表失败:" + throwable.getMessage());
            }

            @Override
            public R<SysRole> selectRoleById(Long roleId, String source) {
                return R.fail("根据ID获取角色失败:" + throwable.getMessage());
            }
        };
    }
}
