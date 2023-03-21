package com.jlkj.rabbit.mqrecievelistener.salesprovider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlkj.common.core.domain.R;
import com.jlkj.common.dto.sales.SalesTransportSiteDTO;
import com.jlkj.rabbit.feign.salesprovider.SalesFeignService;
import com.jlkj.rabbit.service.impl.SysQueueDlxServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.jlkj.rabbit.mqconstant.SalesConstants.*;

/**
 * @author xk
 * @Description 铁路到局队列监听
 * @date 2022年06月07日 16:15
 */
@Component
@Slf4j
public class SalesTransportSiteRailWayQueueListener {

    @Autowired
    SalesFeignService salesFeignService;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;

    /**
     * 铁路到局资料接收
     * 操作标识:
     *  actionCode: N：新增  R：修改 D：删除
     * 数据来源：
     *  dataSource：erp
     * @param msg
     * @param channel
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(SALES_SHJCBX1301_QUEUE))
    public void salesTransportSiteRailWayListener(String msg, Channel channel, Message message) throws Exception {

        log.info("rabbitmq:topicmode:receive-messageA => {}", msg);
        //接到msg转换为json
        JSONObject json = JSONObject.parseObject(msg);
        //获取操作标识
        String actionCode = json.getString("actionCode");
        //获取数据来源
        String dataSource = json.getString("dataSource");
        //获取数据data，转换为json
        JSONArray dataArr = JSONArray.parseArray(json.getString("data"));
        try {
            //判断数据来源是否=erp
            if (dataSource.equals(SALES_DATASOURCE)) {
                //执行增删改操作
                salesTransportSiteRailWayExecuteOperation(dataArr, actionCode);
            }
        } catch (Exception e) {
            sysQueueDlxService.saveQueueException(SALES_SHJCBX1301_QUEUE, msg, e.toString());
            log.error("error:rabbitmq:{} => {}", SALES_SHJCBX1301_QUEUE, msg);
        }
    }

    /**
     * 执行操作
     * @param dataArr
     * @param actionCode
     * @throws Exception
     */
    private void salesTransportSiteRailWayExecuteOperation(JSONArray dataArr, String actionCode) throws Exception  {
        ObjectMapper objectMapper = new ObjectMapper();
        String token = "client-test";
        //判断操作标识
        if (actionCode.equals(SALES_N)){
            //循环数组  新增
            dataArr.forEach( item -> {
                //将item复制给SalesTransportSiteDTO
                SalesTransportSiteDTO salesTransportSiteDTO = objectMapper.convertValue(item, SalesTransportSiteDTO.class);
                R result = salesFeignService.addSalesTransportSiteRailWay(salesTransportSiteDTO,token);
            });
        } else if (actionCode.equals(SALES_D)) {
            //删除
            dataArr.forEach( item -> {
                SalesTransportSiteDTO salesTransportSiteDTO = objectMapper.convertValue(item, SalesTransportSiteDTO.class);
                String id = salesTransportSiteDTO.getSiteNo();
                R result = salesFeignService.deleteSalesTransportSiteRailWay(new String[]{id},token);
            });
        } else if (actionCode.equals(SALES_R)) {
            //修改
            dataArr.forEach( item -> {
                SalesTransportSiteDTO salesTransportSiteDTO = objectMapper.convertValue(item, SalesTransportSiteDTO.class);
                R result = salesFeignService.updateSalesTransportSiteRailWay(salesTransportSiteDTO,token);
            });
        } else {
            throw new Exception("操作标识传入错误！无法识别！");
        }
    }

}
