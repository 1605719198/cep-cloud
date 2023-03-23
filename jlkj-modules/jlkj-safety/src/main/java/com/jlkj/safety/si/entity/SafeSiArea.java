package com.jlkj.safety.si.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 安全区域
 * </p>
 *
 * @author su
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeSiArea implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 安全区域ID
     */
    private String id;

    /**
     * 安全区域名称
     */
    private String safeAreaName;

    /**
     * 经度坐标
     */
    private String lng;

    /**
     * 纬度坐标
     */
    private String lat;

    /**
     * 创建人ID
     */
    private String createUserId;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private LocalDateTime ceateTime;


}
