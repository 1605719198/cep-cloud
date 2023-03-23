package com.jlkj.safety.si.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 8.断路安全作业证
 * </p>
 *
 * @author su
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeSiJobCutRoad implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 作业证ID
     */
    private String id;

    /**
     * 申请单位ID
     */
    private String applyDepartId;

    /**
     * 申请单位名称
     */
    private String applyDepartName;

    /**
     * 申请人ID
     */
    private String applyPersonId;

    /**
     * 申请人姓名
     */
    private String applyPersonName;

    /**
     * 作业证编号
     */
    private String jobCode;

    /**
     * 作业单位ID
     */
    private String jobUnitId;

    /**
     * 作业单位名称
     */
    private String jobUnitName;

    /**
     * 涉及相关单位(部门)
     */
    private String otherUnits;

    /**
     * 断路原因
     */
    private String cause;

    /**
     * 断路开始时间
     */
    private LocalDateTime startTime;

    /**
     * 断路结束时间
     */
    private LocalDateTime endTime;

    /**
     * 断路示意图URL
     */
    private String url;

    /**
     * 断路相关说明
     */
    private String content;

    /**
     * 监护人ID
     */
    private String guardianPersonId;

    /**
     * 监护人姓名
     */
    private String guardianPersonName;

    /**
     * 作业所在区域ID
     */
    private String safeAreaId;

    /**
     * 作业所在区域名称
     */
    private String safeAreaName;

    /**
     * 作业详细地点
     */
    private String place;

    /**
     * 危害辨识
     */
    private String harmIdentification;

    /**
     * 实施安全教育人
     */
    private String safeEducationPersons;

    /**
     * 状态(1未完成,2已完成)
     */
    private Integer status;

    /**
     * 申请时间
     */
    private LocalDateTime applyTime;

    /**
     * 完成时间
     */
    private LocalDateTime finishTime;

    /**
     * 作业内容
     */
    private String jobContent;


    private String jobResponsiblePersonId;
    private String jobResponsiblePersonName;


}
