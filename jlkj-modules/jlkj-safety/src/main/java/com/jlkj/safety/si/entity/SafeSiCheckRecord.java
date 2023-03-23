package com.jlkj.safety.si.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 安全巡检记录表
 * </p>
 *
 * @author su
 * @since 2022-07-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeSiCheckRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键标识;UUID
     */
    private String id;

    /**
     * 巡检人id
     */
    private String checkPersonId;

    /**
     * 巡检人姓名
     */
    private String checkPersonName;

    /**
     * 巡检人部门id
     */
    private String checkDepartId;

    /**
     * 巡检人部门名称
     */
    private String checkDepartName;

    /**
     * 作业区域id
     */
    private String safeAreaId;

    /**
     * 作业区域名称
     */
    private String safeAreaName;

    /**
     * 巡检类型id
     */
    private String checkTypeId;

    /**
     * 巡检类型名称
     */
    private String checkTypeName;

    /**
     * 描述问题
     */
    private String checkContent;

    /**
     * 地点状况(1正常/2异常)
     */
    private Integer checkStatus;

    /**
     * 巡检图片1
     */
    private String checkImage1;

    /**
     * 巡检图片2
     */
    private String checkImage2;

    /**
     * 巡检图片3
     */
    private String checkImage3;

    /**
     * 巡检图片4
     */
    private String checkImage4;

    /**
     * 巡检时间
     */
    private LocalDateTime checkTime;

    /**
     * 状态(0待处理/1已处理)
     */
    private Integer checkDisposed;

    /**
     * 处理人id
     */
    private String disposerId;

    /**
     * 处理人姓名
     */
    private String disposerName;

    /**
     * 处理时间
     */
    private LocalDateTime disposeTime;

    /**
     * 处理内容描述
     */
    private String disposeContent;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人id
     */
    private String modifyUserId;

    /**
     * 修改人
     */
    private String modifyUserName;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    /**
     * 处理图片1
     */
    private String disposerImage1;

    /**
     * 处理图片2
     */
    private String disposerImage2;

    /**
     * 处理图片3
     */
    private String disposerImage3;

}
