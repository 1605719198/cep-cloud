package com.jlkj.safety.si.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 安全巡检类型
 * </p>
 *
 * @author su
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeSiCheckType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 巡检类型ID
     */
    private String id;

    /**
     * 巡检类型名称
     */
    private String checkTypeName;

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
