package com.jlkj.safety.si.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 动火作业票-附件表
 * </p>
 *
 * @author su
 * @since 2022-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeSiJobFireAppendix implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键标识;UUID
     */
    private String id;

    /**
     * 作业票id
     */
    private String jobId;

    /**
     * 附件id
     */
    private String appendixId;

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


}
