package com.jlkj.common.dto.feignclients;

import com.jlkj.common.dto.resp.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

/**
 * @author zyf
 * @Description
 * @create 2022-05-30 15:22
 */
@Component
@FeignClient(value = "system-service-provider")
public interface RolePermissionFeignClient {

    /**
     * 获取权限角色
     * @param params params
     * @param token token
     * @return result
     */
    @GetMapping(value = "/role_permission/get_role_permission")
    public Result getRoleAndPermission(@SpringQueryMap Map<String, Object> params, @RequestHeader("token") String token);
}
