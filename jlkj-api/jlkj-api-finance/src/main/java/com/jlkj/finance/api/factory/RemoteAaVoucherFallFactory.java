package com.jlkj.finance.api.factory;

import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.finance.api.RemoteAaCompanyGroupService;
import com.jlkj.finance.api.RemoteAaVoucherService;
import com.jlkj.finance.api.bean.FinanceVoucherBean;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

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
public class RemoteAaVoucherFallFactory implements FallbackFactory<RemoteAaVoucherService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteAaVoucherFallFactory.class);

    @Override
    public RemoteAaVoucherService create(Throwable throwable)
    {
        log.error("查询核算项目类别(下拉选单用)失败:{}", throwable.getMessage());
        return new RemoteAaVoucherService()
        {
            @Override
            public Map<String, Object> doVoucher(FinanceVoucherBean bean,String source) {
                log.info("接收到参数：{}",bean);
                return null;
            }



        };
    }
}
