package com.jlkj.safety.si.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 报警历史
 * </p>
 *
 * @author su
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeAlarmHis implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 行标识
     */
    private String id;

    /**
     * 报警区域
     */
    private String safeAreaName;

    /**
     * 报警类型
     */
    private String alarmType;

    /**
     * 报警位置
     */
    private String alarmPosition;

    /**
     * 报警时间
     */
    private LocalDateTime alarmTime;


}
