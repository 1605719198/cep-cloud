package com.jlkj.rabbit.feign.safetyprovider;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * 安环模块远程调用
 * @author sudeyou
 * @date 2022/7/11 - 9:01
 */
@Component
@FeignClient(value = "safetyandenvironmentalprotection-service-provider")
public interface SafetyFeignService {

    /**
     * 更新吊装作业证编号
     * @param params 请求参数
     * @return 结果MAP
     */
    @PostMapping("/safe/job/hoisting/update/jobcode")
    Object updateSafeJobHoistingJobCode(@RequestBody Map<String, Object> params);

    /**
     * 更新动火作业证编号
     * @param params 请求参数
     * @return 结果MAP
     */
    @PostMapping("/safe/job/fire/update/jobcode")
    Object updateSafeJobFireJobCode(@RequestBody Map<String, Object> params);

    /**
     * 更新高处作业证编号
     * @param params 请求参数
     * @return 结果MAP
     */
    @PostMapping("/safe/job/high/update/jobcode")
    Object updateSafeJobHighJobCode(@RequestBody Map<String, Object> params);

    /**
     * 更新受限空间作业证编号
     * @param params 请求参数
     * @return 结果MAP
     */
    @PostMapping("/safe/job/space/update/jobcode")
    Object updateSafeJobSpaceJobCode(@RequestBody Map<String, Object> params);

}
