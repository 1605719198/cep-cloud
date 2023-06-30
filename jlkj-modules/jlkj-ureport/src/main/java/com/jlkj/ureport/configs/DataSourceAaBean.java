package com.jlkj.ureport.configs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.finance.api.RemoteAaApiService;
import com.jlkj.finance.api.RemoteAaReportService;

import com.jlkj.finance.api.domain.FinanceAaLedgerAcctVo;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Map;

/**
 * @author jlkj
 */
@Component("dataSourceAaBean")
@Slf4j
public class DataSourceAaBean {
    @Autowired
    private RemoteAaReportService remoteAaReportService;
    @Autowired
    private RemoteAaApiService remoteAaApiService;
    /**
     * 第一种：返回Map集合
     * @param dsName
     * @param dataSetName
     * @param parameters
     * @return
     */
    public   List<FinanceAaLedgerAcctVo> accountBalanceTonSteelData(String dsName, String dataSetName, Map<String, Object> parameters) {
        log.info("接收到参数=》{}",parameters);

        List<Map<String, String>> maps = remoteAaReportService.accountBalanceTonSteelData(parameters,SecurityConstants.INNER);

        JSONArray array= JSONArray.parseArray(JSON.toJSONString(maps)) ;
        List<FinanceAaLedgerAcctVo> entityList = JSONArray.parseArray(array.toJSONString(),FinanceAaLedgerAcctVo.class);
    log.info("maps=》{}",maps);
        return entityList;
    }



}
