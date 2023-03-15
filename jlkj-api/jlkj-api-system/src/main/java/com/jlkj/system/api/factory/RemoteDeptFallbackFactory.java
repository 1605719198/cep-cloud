package com.jlkj.system.api.factory;

import com.jlkj.common.core.domain.R;
import com.jlkj.system.api.RemoteDeptService;
import com.jlkj.system.api.RemoteRoleService;
import com.jlkj.system.api.domain.SysDept;
import com.jlkj.system.api.domain.SysRole;
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
public class RemoteDeptFallbackFactory implements FallbackFactory<RemoteDeptService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteDeptFallbackFactory.class);

    @Override
    public RemoteDeptService create(Throwable throwable)
    {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteDeptService()
        {
            @Override
            public R<SysDept> selectDeptById(Long deptId, String source) {
                return R.fail("根据ID获取部门信息失败:" + throwable.getMessage());
            }
        };
    }
}
