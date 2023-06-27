package com.jlkj.finance.api.factory;

import com.jlkj.finance.api.RemoteAaApiService;
import com.jlkj.finance.api.bean.FinanceVoucherBean;
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
public class RemoteAaApiFactory implements FallbackFactory<RemoteAaApiService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteAaApiFactory.class);

    @Override
    public RemoteAaApiService create(Throwable throwable)
    {

        return new RemoteAaApiService()
        {
            @Override
            public List<Map<String,String>> selectCompanyList(String source)
            {
                List<Map<String,String>> list = new ArrayList<>();
                Map<String,String> errMap = new HashMap<>(2);
                errMap.put("errMsg","查询财务公司(下拉选单用)失败:" + throwable.getMessage());
                list.add(errMap);
                return list;
            }

            @Override
            public Map<String, Object> doVoucher(FinanceVoucherBean financeVoucherBean, String source) {
                log.info("RemoteAaApiService doVoucher 失败 接收到参数：{}",financeVoucherBean);
                return null;
            }

        };


    }
}
