package com.jlkj.safety.si.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 动土安全作业证-作业图
 * </p>
 *
 * @author su
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeJobSoilImages implements Serializable {

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
     * 编号
     */
    private String code;

    /**
     * 内容
     */
    private String content;

    /**
     * 深度
     */
    private String depth;

    /**
     * 面积
     */
    private String area;

    /**
     * 图片URL
     */
    private String url;

    /**
     * 原始文件名
     */
    private String originalFileName;

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
