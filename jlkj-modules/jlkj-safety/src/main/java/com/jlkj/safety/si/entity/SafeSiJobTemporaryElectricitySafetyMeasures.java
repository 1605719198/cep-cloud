package com.jlkj.safety.si.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 临时用电安全作业证-安全措施
 * </p>
 *
 * @author su
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeSiJobTemporaryElectricitySafetyMeasures implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 行标识
     */
    private String id;

    /**
     * 作业证ID
     */
    private String jobId;

    /**
     * 序号
     */
    private Integer sort;

    /**
     * 安全措施
     */
    private String safetyMeasures;

    /**
     * 是否确认(0否,1是)
     */
    private Integer isConfirm;

    /**
     * 确认人ID
     */
    private String confirmPersonId;

    /**
     * 确认人姓名
     */
    private String confirmPersonName;

    /**
     * 确认时间
     */
    private LocalDateTime confirmTime;


}
