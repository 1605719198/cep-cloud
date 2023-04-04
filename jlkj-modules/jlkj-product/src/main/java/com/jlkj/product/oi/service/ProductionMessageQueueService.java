package com.jlkj.product.oi.service;


/**
 * @author yzl
 * @description 针对 报生产文操作 Service
 * @createDate 2022年8月26日 09:05:00
 */
public interface ProductionMessageQueueService {

    /**
     * 焦炉操作记录-报文-发送
     * @return 结果
     */
    void sendCokeOvenOperationRecordMessageQueue();

    /**
     * 干熄焦操作记录-报文-发送
     * @return 结果
     */
    void sendCokeDryQuenchingOperationRecordMessageQueue();

    /**
     * 锅炉工艺指标-报文-发送
     * @return 结果
     */
    void sendBoilerProcessIndexMessageQueue();

    /**
     * 焦仓储量记录-报文-发送
     * @return 结果
     */
    void sendCokeWarehouseStockRecordMessageQueue();

}
