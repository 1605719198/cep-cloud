package com.jlkj.finance.api.factory;

import com.jlkj.finance.api.RemoteAaCompanyGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 财务模块远程服务
 *
 * @author jlkj
 */
@Component
public class RemoteAaCompanyGroupFallbackFactory implements FallbackFactory<RemoteAaCompanyGroupService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteAaCompanyGroupFallbackFactory.class);

    @Override
    public RemoteAaCompanyGroupService create(Throwable throwable)
    {
        log.error("查询核算项目类别(下拉选单用)失败:{}", throwable.getMessage());
        return new RemoteAaCompanyGroupService()
        {
            @Override
            public List<Map<String,String>> selectCompanyList(String source)
            {
                List<Map<String,String>> list = new ArrayList<>();
                Map<String,String> errMap = new HashMap<>(2);
                errMap.put("errMsg","查询核算项目类别(下拉选单用)失败:" + throwable.getMessage());
                list.add(errMap);
                return list;
            }

        };
    }
}
