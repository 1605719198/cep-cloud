package com.jlkj.safety.si.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 6.盲板抽堵安全作业证
 * @TableName safe_si_job_blind_plate_plugging
 * @author zyf
 */
@TableName(value ="safe_si_job_blind_plate_plugging")
@Data
public class SafeJobBlindPlatePlugging implements Serializable {
    /**
     * 作业证ID
     */
    @TableId
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
     * 设备管道名称
     */
    private String equipmentPipingName;

    /**
     * 介质
     */
    private String medium;

    /**
     * 温度
     */
    private String temperature;

    /**
     * 压力
     */
    private String pressure;

    /**
     * 盲板材质
     */
    private String blindPlateMaterialQuality;

    /**
     * 盲板规格
     */
    private String blindPlateSpecifications;

    /**
     * 盲板编号
     */
    private String blindPlateCode;

    /**
     * 位置内容
     */
    private String positonContent;

    /**
     * 实施时间-堵
     */
    private LocalDateTime implementationTimePlugging;

    /**
     * 实施时间-抽
     */
    private LocalDateTime implementationTimeTakeOut;

    /**
     * 作业人-堵
     */
    private String jobPersonPlugging;

    /**
     * 作业人-抽
     */
    private String jobPersonTakeOut;

    /**
     * 监护人ID-堵
     */
    private String guardianPersonIdPlugging;

    /**
     * 监护人姓名-堵
     */
    private String guardianPersonNamePlugging;

    /**
     * 监护人ID-抽
     */
    private String guardianPersonIdTakeOut;

    /**
     * 监护人姓名-抽
     */
    private String guardianPersonNameTakeOut;

    /**
     * 生产单位作业指挥人
     */
    private String productionUnitJobDirectorPersonName;

    /**
     * 作业单位ID
     */
    private String jobUnitId;

    /**
     * 作业单位名称
     */
    private String jobUnitName;

    /**
     * 作业单位负责人ID
     */
    private String jobUnitResponsiblePersonId;

    /**
     * 作业单位负责人名称
     */
    private String jobUnitResponsiblePersonName;

    /**
     * 涉及的其它特殊作业
     */
    private String otherJob;

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
     * 危险因素识别
     */
    private String hazardIdentification;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 监护人ID
     */
    private String guardianPersonId;

    /**
     * 监护人姓名
     */
    private String guardianPersonName;
    /**
     * 抽堵(0抽1堵)
     */
    private Integer pumpingBlocking;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
