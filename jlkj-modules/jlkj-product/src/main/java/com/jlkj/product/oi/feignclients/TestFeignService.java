package com.jlkj.product.oi.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author zyf
 * @Description
 * @create 2022-03-18 14:56
 */
@Component
@FeignClient(value = "amqp-service-consumer")
public interface TestFeignService {

    /**
     * ceshi
     * @param params
     * @return
     */
    @GetMapping(value = "/test-op")
    public Object getTestFeignObject(Map<String, Object> params);

    /**
     * cesgu
     * @param params
     * @return
     */
    @PostMapping(value = "/test-op")
    public Object postTestFeignObject(@RequestBody Map<String, Object> params);
}
