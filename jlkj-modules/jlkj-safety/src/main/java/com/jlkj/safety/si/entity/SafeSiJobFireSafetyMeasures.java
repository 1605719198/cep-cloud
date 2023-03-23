package com.jlkj.safety.si.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 动火安全作业证-安全措施
 * </p>
 *
 * @author su
 * @since 2022-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeSiJobFireSafetyMeasures implements Serializable {

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
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime confirmTime;


}
