package com.jlkj.product.oi.enums;

/**
 * @description: 调度日志状态
 * @author: sdy
 * @time: 2022/5/13 9:14
 */
public enum DispatchLogStatus {
    /**
     * 未下发
     */
    NOT_SEND((short)0),
    /**
     * 未完成
     */
    NOT_FINISHED((short)1),
    /**
     * 已完成
     */
    FINISHED((short)2);
    private short status;

    DispatchLogStatus(short status) {
        this.status = status;
    }

    public short getStatus() {
        return this.status;
    }
}
