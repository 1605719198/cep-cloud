package com.jlkj.safety.si.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 知识类别
 * </p>
 *
 * @author su
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeSiKnowledgeCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 知识类别ID
     */
    private String id;

    /**
     * 知识类别名称
     */
    private String knowledgeCategoryName;

    /**
     * 上级知识类别ID
     */
    private String parentKnowledgeCategoryId;

    /**
     * 序号
     */
    private Integer sort;

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
