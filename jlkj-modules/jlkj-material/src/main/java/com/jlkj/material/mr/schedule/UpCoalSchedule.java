package com.jlkj.material.mr.schedule;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.jlkj.common.core.constant.RabbitConstant.*;
import static com.jlkj.common.core.utils.RedissonUtil.getLock;

/**
 * @author zyf
 * @Description
 * @create 2022-09-09 9:34
 */
@Service
@Slf4j
public class UpCoalSchedule {

    @Autowired
    RedissonClient redissonClient;

    @Resource
    private RabbitTemplate rabbitTemplate;


///    @Scheduled(cron = "* 0/10 * * * *")
    /**
     * 测试-redis
     * @return
     */
    public void test() {
        RLock rLock = redissonClient.getLock(getLock("MaterialConsumptionMessage"));
        if (rLock.tryLock()) {
            try {
                JSONObject object = new JSONObject();
                object.put("class", "com.icsc.mr.api.mrjc150Api");
                object.put("messageId", "MR58");
                object.put("actionCode", "N");
                object.put("dataSource", "cep");
                JSONObject data = new JSONObject();
                data.put("planListNo", DateUtil.current());
                data.put("conveyance", "C");
                data.put("purposeId", "41");
                data.put("reptDate", DateUtil.format(DateUtil.date(), "yyyyMMdd"));
                data.put("deptNo", "W200");
                data.put("seqNo", DateUtil.format(DateUtil.date(), "yyyyMMddHHmm") + "00001");
                data.put("matrlnoA", "0050801");
                data.put("matrlnoB", "");
                data.put("stgNoA", "WK0100");
                data.put("stgNoB", "");
                data.put("costCenter", "0WK10");
                data.put("qty", "24000");
                data.put("waterRate", "5.20");
                object.put("data", data);
                log.info("send mq message:{}:{} => {}", MATERIAL_EXCHANGE, MATERIAL_CONSUMPTION_ROUTE_KEY, JSONObject.toJSONString(object));
                rabbitTemplate.convertAndSend(MATERIAL_EXCHANGE, MATERIAL_CONSUMPTION_ROUTE_KEY, JSONObject.toJSONString(object));
            } catch (Exception e) {
                log.info(e.toString());
            } finally {
                rLock.unlock();
            }
        }

    }
}
