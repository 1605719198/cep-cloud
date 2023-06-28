package com.jlkj.ureport.configs;

import com.jlkj.common.core.constant.SecurityConstants;
import com.jlkj.finance.api.RemoteAaApiService;
import com.jlkj.finance.api.domain.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jlkj
 */
@Component("dataSourceBean")
@Slf4j
public class DataSourceBean {
    @Autowired
    private RemoteAaApiService remoteAaApiService;
    /**
     * 第一种：返回Map集合
     * @param dsName
     * @param dataSetName
     * @param parameters
     * @return
     */
    public List<Map<String, String>> loadReportData(String dsName, String dataSetName, Map<String, Object> parameters) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> m1 = new HashMap<String, Object>();
        m1.put("year", "2000");
        m1.put("month", "1");
        m1.put("amount", 72847);
        list.add(m1);

        Map<String, Object> m2 = new HashMap<String, Object>();
        m2.put("year", "2000");
        m2.put("month", "2");
        m2.put("amount", 28429);
        list.add(m2);
        List<Map<String, String>> maps = remoteAaApiService.selectCompanyList(SecurityConstants.INNER);
        log.info("获取到数据-》{}",maps);
        return maps;
    }

    /**
     * 第二种：返回对象集合
     * @param dsName
     * @param datasetName
     * @param parameters
     * @return
     */
    public List<UserVo> loadData(String dsName, String datasetName, Map<String, Object> parameters) {
        List<UserVo> list = new ArrayList<UserVo>();
        for (int i = 0; i < 10; i++) {
            UserVo m = new UserVo();
            m.setId(String.valueOf(i));
            m.setUserNode(RandomStringUtils.random(10, true, false));
            m.setUserName("name-"+i);
            m.setSex((i%2)+"");
            list.add(m);
        }
        return list;
    }

}
