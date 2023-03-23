package com.jlkj.safety.si.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 隐患处理
 * </p>
 *
 * @author su
 * @since 2022-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeSiDangerRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 安全隐患ID;UUID
     */
    private String id;

    /**
     * 隐患等级id
     */
    private String dangerTypeId;

    /**
     * 隐患等级
     */
    private String dangerTypeName;

    /**
     * 责任单位id
     */
    private String dutyDepartId;

    /**
     * 责任单位
     */
    private String dutyDepartName;

    /**
     * 责任人id
     */
    private String dutyPersonId;

    /**
     * 责任人
     */
    private String dutyPersonName;

    /**
     * 提交单位id
     */
    private String submitDepartId;

    /**
     * 提交单位
     */
    private String submitDepartName;

    /**
     * 提交人id
     */
    private String submitPersonId;

    /**
     * 提交人
     */
    private String submitPersonName;

    /**
     * 区域ID
     */
    private String safeAreaId;

    /**
     * 区域
     */
    private String safeAreaName;

    /**
     * 隐患提交时间
     */
    private LocalDateTime dangerTime;

    /**
     * 限期完成时间
     */
    private LocalDateTime dangerDeadlineTime;

    /**
     * 隐患处理编号
     */
    private String dangerNumber;

    /**
     * 验收单位id
     */
    private String acceptanceDepartId;

    /**
     * 验收单位
     */
    private String acceptanceDepartName;

    /**
     * 验收人id
     */
    private String acceptancePersonId;

    /**
     * 验收人
     */
    private String acceptancePersonName;

    /**
     * 提交人签名图片
     */
    private String submitSignImage;

    /**
     * 提交图片1
     */
    private String submitImage1;

    /**
     * 提交图片2
     */
    private String submitImage2;

    /**
     * 提交图片3
     */
    private String submitImage3;

    /**
     * 提交图片4
     */
    private String submitImage4;

    /**
     * 验收图片1
     */
    private String acceptanceImage1;

    /**
     * 验收图片2
     */
    private String acceptanceImage2;

    /**
     * 验收图片3
     */
    private String acceptanceImage3;

    /**
     * 验收图片4
     */
    private String acceptanceImage4;

    /**
     * 验收时间
     */
    private LocalDateTime acceptanceTime;

    /**
     * 整改措施
     */
    private String disposeMeasure;

    /**
     * 整改内容
     */
    private String dangerContent;

    /**
     * 状态：1待整改，2已验收，3超时
     */
    private Integer dangerStatus;

    /**
     * 验收评价
     */
    private String acceptanceAppraise;

    /**
     * 验收人签名图片
     */
    private String acceptanceSignImage;

    /**
     * 整改内容
     */
    private String disposeContent;

    /**
     * 处理人ID
     */
    private String disposerId;

    /**
     * 处理人名称
     */
    private String disposerName;

    /**
     * 处理反馈时间
     */
    private LocalDateTime disposeTime;


}
