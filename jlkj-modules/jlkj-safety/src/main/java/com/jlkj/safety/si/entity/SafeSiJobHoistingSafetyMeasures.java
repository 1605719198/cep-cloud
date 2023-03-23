package com.jlkj.safety.si.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 吊装安全作业证-安全措施
 * </p>
 *
 * @author su
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeSiJobHoistingSafetyMeasures implements Serializable {

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
