package com.jlkj.rabbit.mqrecievelistener.hunmanresourceprovider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jlkj.common.dto.human.hm.HumanresourceOrganizationTypeDTO;
import com.jlkj.rabbit.feign.hunmanresourceprovider.HumanResourceFeignService;
import com.jlkj.rabbit.service.impl.SysQueueDlxServiceImpl;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.jlkj.rabbit.mqconstant.HumanResourceConstants.*;
import static com.jlkj.rabbit.mqconstant.LogisticsConstants.*;

/**
 * @author Xin
 * @Description 人事模块队列监听---机构类别资料
 * @create 2022-05-19 09:18
 */
@Component
@Slf4j
public class HumanresourceOrganizationTypeQueueListener {

    @Autowired
    private HumanResourceFeignService humanResourceFeignService;
    @Autowired
    SysQueueDlxServiceImpl sysQueueDlxService;
    /**
     * 机构类别资料接收
     * 操作标识:
     *  actionCode: A：新增  S：修改 D：删除
     * 数据来源：
     *  dataSource：hr
     * @param msg
     * @param channel
     * @param message
     */
    @RabbitListener(queuesToDeclare = @Queue(HUMANRESOURCE_ORGANIZATION_TYPE_QUEUE))
    public void receiveListener(String msg, Channel channel, Message message) throws Exception {
        log.info("rabbitmq:{} => {}", HUMANRESOURCE_ORGANIZATION_TYPE_QUEUE, msg);
        try {
            //接到msg转换为json
            JSONObject json = JSONObject.parseObject(msg);
            //获取操作标识
            String actionCode = json.getString(ACTION_CODE);
            //获取数据来源
            String dataSource = json.getString(DATA_SOURCE);
            //获取数据data，转换为json
            JSONArray dataArr = JSONArray.parseArray(json.getString(DATA));
            //判断数据来源是否=hr
            if (dataSource.equals(HUMANRESOURCE_DATASOURCE)) {
                //执行增删改操作
                executeOperation(dataArr,actionCode);
            }
        } catch (Exception e ) {
            sysQueueDlxService.saveQueueException(HUMANRESOURCE_ORGANIZATION_TYPE_QUEUE, msg, e.toString());
            log.error("error:rabbitmq{} => {}", HUMANRESOURCE_ORGANIZATION_TYPE_QUEUE, msg);
        }

    }

    /**
     * 执行操作
     * @param dataArr
     * @param actionCode
     * @throws Exception
     */
    private void executeOperation(JSONArray dataArr,String actionCode) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        //判断操作标识
        if (actionCode.equals(HUMANRESOURCE_A)){
            //循环数组  新增
            dataArr.forEach( item -> {
                //将item复制给HumanresourceBoardDTO
                HumanresourceOrganizationTypeDTO humanresourceOrganizationTypeDTO = objectMapper.convertValue(item, HumanresourceOrganizationTypeDTO.class);
                humanResourceFeignService.addOrganizationTypeData(humanresourceOrganizationTypeDTO,TOKEN);
            });
        } else if (actionCode.equals(HUMANRESOURCE_S)) {
            //修改
            dataArr.forEach( item -> {
                HumanresourceOrganizationTypeDTO humanresourceOrganizationTypeDTO = objectMapper.convertValue(item, HumanresourceOrganizationTypeDTO.class);
                humanResourceFeignService.updateOrganizationTypeData(humanresourceOrganizationTypeDTO,TOKEN);
            });
        } else if (actionCode.equals(HUMANRESOURCE_D)) {
            //删除
            dataArr.forEach( item -> {
                HumanresourceOrganizationTypeDTO humanresourceOrganizationTypeDTO = objectMapper.convertValue(item, HumanresourceOrganizationTypeDTO.class);
                humanResourceFeignService.deleteOrganizationTypeData(humanresourceOrganizationTypeDTO,TOKEN);
            });
        } else {
            throw new Exception("操作标识传入错误！无法识别！");
        }
    }
}
