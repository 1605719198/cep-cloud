package com.jlkj.finance.ip.feign;

import com.jlkj.common.core.constant.ServiceNameConstants;
import com.jlkj.system.api.factory.RemoteUserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author wang'mai
 */
@Component
@FeignClient(value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteUserFallbackFactory.class)
public interface SysDictDataFeignService {

    /**
     * 根据字典类型、字典键值查询数据===财务模块用
     * @author wang'mai
     * @param dictType 数据名称
     * @param dictValue 数据值
     * @return Map<String, Object> getLabelByDictValue(@RequestParam(defaultValue = "")String dictType,
     *                                             @RequestParam(defaultValue = "")String dictValue);
     */
    @GetMapping("/dict/data/getLabelByDictValue")
       Map<String, Object> getLabelByDictValue(@RequestParam(defaultValue = "")String dictType,
                                            @RequestParam(defaultValue = "")String dictValue);

    /**
     * 根据字典类型、字典键值查询数据===财务模块用
     * @param dictType 数据名称
     * @param dictValue 数据值
     * @return Object getMainAreaIdValue(@RequestParam(defaultValue = "")String dictType,
     *                               @RequestParam(defaultValue = "")String dictValue);
     */
    @GetMapping("/dict/data/getMainAreaIdValue")
    Object getMainAreaIdValue(@RequestParam(defaultValue = "")String dictType,
                              @RequestParam(defaultValue = "")String dictValue);
    /**
     * 根据字典类型、字典键值查询数据===财务模块用
     * @param dictType 数据名称
     * @return Object getMainAreaIdValue(@RequestParam(defaultValue = "")String dictType,
     *                               @RequestParam(defaultValue = "")String dictValue);
     */
    @GetMapping("/dict/data/getMainCompIdValue")
    List<Map<String,Object>>  getMainCompIdValue(@RequestParam(defaultValue = "")String dictType);
}


