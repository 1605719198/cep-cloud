package com.jlkj.product.oi.feignclients;

import com.jlkj.common.core.web.domain.AjaxResult;
import com.jlkj.product.oi.dto.material.SendDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author yzl
 * @Description 物料
 * @create 2022年11月30日 14:03:40
 */
@Component
@FeignClient(value = "material-service-provider")
public interface MaterialsCoalStockFeignService {

    /**
     * 上煤手动报文
     * @param dto   dto
     * @param token token
     * @return Result
     */
    @PostMapping("/stock/materialsCoalStock/send")
    AjaxResult materialsCoalStockSend(SendDTO dto, @RequestHeader("token") String token);


}
